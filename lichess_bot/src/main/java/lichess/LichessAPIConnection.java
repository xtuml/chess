package lichess;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import types.adapters.LichessTypeAdapterFactory;
import types.lichess.APIException;
import types.lichess.BotEvent;
import types.lichess.ChatLine;
import types.lichess.Color;
import types.lichess.DeclineReason;
import types.lichess.Game;
import types.lichess.GameEvent;
import types.lichess.GameState;
import types.lichess.Room;
import types.lichess.User;
import types.lichess.UserTitle;
import types.lichess.Variant;

public class LichessAPIConnection implements LichessAPIProvider {

	private final String baseUrl;
	private final String accessToken;
	private final HttpClient client;
	private final Gson gson;
	private PrintStream logger;
	private LichessAPISubscriber peer;
	private User user;

	public LichessAPIConnection(PrintStream logger, LichessAPISubscriber peer, Properties props) {
		this.logger = logger;
		this.peer = peer;

		// set up gson serialization
		final var builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new LichessTypeAdapterFactory());
		gson = builder.create();

		// load configuration
		this.baseUrl = props.getProperty("api_base_url");
		this.accessToken = props.getProperty("access_token");

		// create the HTTP client
		client = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20))
				.build();

	}

	public void initialize() {
		// check if the account is a bot and upgrade if necessary
		upgradeAccount();

		// wait for incoming events
		handleBotEvents();
	}

	@Override
	public boolean move(String gameId, String move) {
		return postRequest(String.format("%s/api/bot/game/%s/move/%s", baseUrl, gameId, move));
	}

	@Override
	public boolean chat(String gameId, String text, Room room) {
		return postRequest(String.format("%s/api/bot/game/%s/chat", baseUrl, gameId),
				"application/x-www-form-urlencoded", Map.of("room", room.name().toLowerCase(), "text", text));
	}

	@Override
	public boolean abort(String gameId) {
		return postRequest(String.format("%s/api/bot/game/%s/abort", baseUrl, gameId));
	}

	@Override
	public boolean resign(String gameId) {
		return postRequest(String.format("%s/api/bot/game/%s/resign", baseUrl, gameId));
	}

	@Override
	public boolean draw(String gameId, boolean accept) {
		return postRequest(String.format("%s/api/bot/game/%s/draw/%s", baseUrl, gameId, accept ? "yes" : "no"));
	}

	@Override
	public boolean takeback(String gameId, boolean accept) {
		return postRequest(String.format("%s/api/bot/game/%s/takeback/%s", baseUrl, gameId, accept ? "yes" : "no"));
	}

	@Override
	public boolean acceptChallenge(String challengeId) {
		return postRequest(String.format("%s/api/challenge/%s/accept", baseUrl, challengeId));
	}

	@Override
	public boolean declineChallenge(String challengeId, DeclineReason reason) {
		return postRequest(String.format("%s/api/challenge/%s/decline", baseUrl, challengeId),
				"application/x-www-form-urlencoded", Map.of("reason", reason.getValue()));
	}

	@Override
	public User getUser() {
		return Optional.ofNullable(user).orElse(new User());
	}

	@Override
	public boolean createChallenge(String user, boolean rated, int clock_limit, int clock_increment, Color color,
			Variant variant, String fen) {
		final var params = new HashMap<String, String>();
		params.put("rated", Boolean.toString(rated));
		if (clock_limit >= 0 && clock_increment >= 0) {
			params.put("clock.limit", Integer.toString(clock_limit));
			params.put("clock.increment", Integer.toString(clock_increment));
		}
		params.put("color", color.name().toLowerCase());
		params.put("variant", variant.getValue());
		if (fen != null && !fen.isEmpty()) {
			params.put("fen", fen);
		}
		return postRequest(String.format("%s/api/challenge/%s", baseUrl, user), "application/x-www-form-urlencoded",
				params);
	}

	private boolean upgradeAccount() {
		logger.println("Upgrading to bot account...");
		final var url = String.format("%s/api/account", baseUrl);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/json")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		try {
			final var response = client.send(request, BodyHandlers.ofString());
			switch (response.statusCode()) {
			case 200:
				user = gson.fromJson(response.body(), User.class);
				if (!UserTitle.BOT.equals(user.getTitle())) {
					final var result = postRequest(String.format("%s/api/bot/account/upgrade", baseUrl));
					if (result) {
						logger.printf("Congrats, %s! you are now a bot.\n", user.getUsername());
					}
					return result;
				} else {
					logger.printf("User '%s' is already a bot.\n", user.getUsername());
					return true;
				}
			case 401:
				peer.error(this, new APIException(url, response.statusCode(), response.body()));
				return false;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
				return false;
			}
		} catch (IOException | InterruptedException e) {
			peer.error(this, new APIException(e));
			return false;
		}
	}

	private void handleBotEvents() {
		logger.println("Waiting for incoming events...");
		final var url = String.format("%s/api/stream/event", baseUrl);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/x-ndjson")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		client.sendAsync(request, BodyHandlers.ofLines()).thenAccept(response -> {
			switch (response.statusCode()) {
			case 200:
				response.body().forEach(line -> {
					if (!line.isEmpty()) {
						logger.println(line);
						final var event = gson.fromJson(line, BotEvent.class);
						switch (event.getType()) {
						case GAMESTART:
							// start handling events for this game
							handleGameEvents(event.getGame().getId());
							peer.gameStart(this, event.getGame());
							break;
						case GAMEFINISH:
							peer.gameFinish(this, event.getGame());
							break;
						case CHALLENGE:
							peer.challenge(this, event.getChallenge());
							break;
						case CHALLENGECANCELED:
							peer.challengeCanceled(this, event.getChallenge());
							break;
						default:
							// TODO ignore
							break;
						}
					}
				});
				break;
			case 400:
			case 401:
				peer.error(this, new APIException(url, response.statusCode(),
						response.body().collect(Collectors.joining("\n"))));
				break;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
			}
		});
	}

	private void handleGameEvents(String gameId) {
		logger.printf("Handling events for game: %s\n", gameId);
		final var url = String.format("%s/api/bot/game/stream/%s", baseUrl, gameId);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/x-ndjson")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		client.sendAsync(request, BodyHandlers.ofLines()).thenAccept(response -> {
			switch (response.statusCode()) {
			case 200:
				response.body().forEach(line -> {
					if (!line.isEmpty()) {
						logger.println(line);
						final var event = gson.fromJson(line, GameEvent.class);
						switch (event.getType()) {
						case GAMEFULL:
							peer.gameFull(this, gson.fromJson(line, Game.class));
							break;
						case GAMESTATE:
							peer.gameState(this, gameId, gson.fromJson(line, GameState.class));
							break;
						case CHATLINE:
							final var chatLine = gson.fromJson(line, ChatLine.class);
							peer.chatLine(this, gameId, chatLine.getUsername(), chatLine.getText(), chatLine.getRoom());
							break;
						case OPPONENTGONE:
							// TODO figure this out
							break;
						default:
							// TODO ignore
							break;
						}
					}
				});
				break;
			case 400:
			case 401:
				peer.error(this, new APIException(url, response.statusCode(),
						response.body().collect(Collectors.joining("\n"))));
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
			}
		});
		logger.printf("Game over: %s\n", gameId);
	}

	private boolean postRequest(String url) {
		return postRequest(url, null, null);
	}

	private boolean postRequest(String url, String contentType, Map<String, String> formParameters) {
		var requestBuilder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Authorization", String.format("Bearer %s", accessToken));
		if (contentType != null) {
			requestBuilder = requestBuilder.header("Content-Type", contentType);
		}
		final var bodyPublisher = formParameters == null ? BodyPublishers.noBody()
				: BodyPublishers.ofString(encodeParameters(formParameters));
		requestBuilder = requestBuilder.POST(bodyPublisher);
		try {
			final var response = client.send(requestBuilder.build(), BodyHandlers.ofString());
			switch (response.statusCode()) {
			case 200:
				return true;
			case 400:
			case 401:
				peer.error(this, new APIException(url, response.statusCode(), response.body()));
				return false;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
				return false;
			}
		} catch (IOException | InterruptedException e) {
			peer.error(this, new APIException(e));
			return false;
		}
	}

	private static String encodeParameters(Map<String, String> rawParams) {
		return rawParams.entrySet().stream().map(entry -> {
			try {
				return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()) + "="
						+ URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.joining("&"));
	}

}
