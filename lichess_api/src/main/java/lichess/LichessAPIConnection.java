package lichess;

import java.io.IOException;
import java.io.OutputStream;
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

import lichess.types.APIException;
import lichess.types.BotEvent;
import lichess.types.ChatLine;
import lichess.types.Color;
import lichess.types.DeclineReason;
import lichess.types.Game;
import lichess.types.GameEvent;
import lichess.types.GameState;
import lichess.types.OpponentGone;
import lichess.types.Room;
import lichess.types.User;
import lichess.types.UserTitle;
import lichess.types.Variant;
import lichess.types.adapters.LichessTypeAdapterFactory;

public class LichessAPIConnection implements LichessAPIProvider {

	private final String baseUrl;
	private final String accessToken;
	private final HttpClient client;
	private final Gson gson;
	private PrintStream out;
	private PrintStream err;
	private LichessAPISubscriber peer;
	private User user;

	public LichessAPIConnection(PrintStream out, PrintStream err, LichessAPISubscriber peer, Properties props) {
		this.peer = peer;
		
		// set up gson serialization
		final var builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new LichessTypeAdapterFactory());
		gson = builder.create();

		// load configuration
		this.baseUrl = props.getProperty("api_base_url");
		this.accessToken = props.getProperty("access_token");
		
		// set up debug logging
		final var nullOutputStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		};
		this.out = new PrintStream(nullOutputStream);
		this.err = new PrintStream(nullOutputStream);
		if (Boolean.parseBoolean(props.getProperty("enable_debug_logging", "false"))) {
			this.out = out;
			this.err = err;
		}

		// create the HTTP client
		client = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20))
				.build();

	}

	public void initialize() {
		// check if the account is a bot and upgrade if necessary
		if (user == null) {
			upgradeAccount();
		}

		// wait for incoming events
		handleBotEvents();
		
		peer.connected(this);
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
	public User account() {
		return Optional.ofNullable(user).orElseGet(() -> {
			if (upgradeAccount()) {
				return user;
			} else {
				return new User();
			}
		});
	}

	@Override
	public boolean createChallenge(String user, boolean rated, int clockLimit, int clockIncrement, Color color,
			Variant variant, String fen) {
		final var params = new HashMap<String, String>();
		params.put("rated", Boolean.toString(rated));
		if (clockLimit >= 0 && clockIncrement >= 0) {
			params.put("clock.limit", Integer.toString(clockLimit));
			params.put("clock.increment", Integer.toString(clockIncrement));
		}
		params.put("color", color.name().toLowerCase());
		params.put("variant", variant.getValue());
		if (fen != null && !fen.isEmpty()) {
			params.put("fen", fen);
		}
		return postRequest(String.format("%s/api/challenge/%s", baseUrl, user), "application/x-www-form-urlencoded",
				params);
	}

	@Override
	public boolean claimVictory(String gameId) {
		return postRequest(String.format("%s/api/bot/game/%s/claim-victory", baseUrl, gameId));
	}

	private boolean upgradeAccount() {
		out.println("Upgrading to bot account...");
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
						out.printf("Congrats, %s! you are now a bot.\n", user.getUsername());
					}
					return result;
				} else {
					out.printf("User '%s' is already a bot.\n", user.getUsername());
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
		out.println("Waiting for incoming events...");
		final var url = String.format("%s/api/stream/event", baseUrl);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/x-ndjson")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		client.sendAsync(request, BodyHandlers.ofLines()).thenAccept(response -> {
			switch (response.statusCode()) {
			case 200:
				response.body().forEach(line -> {
					if (!line.isEmpty()) {
						out.println(line);
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
							err.printf("Unrecognized event type: %s", event.getType());
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
		out.printf("Handling events for game: %s\n", gameId);
		final var url = String.format("%s/api/bot/game/stream/%s", baseUrl, gameId);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/x-ndjson")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		client.sendAsync(request, BodyHandlers.ofLines()).thenAccept(response -> {
			switch (response.statusCode()) {
			case 200:
				response.body().forEach(line -> {
					if (!line.isEmpty()) {
						out.println(line);
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
							final var opponentGone = gson.fromJson(line, OpponentGone.class);
							peer.opponentGone(this, gameId, opponentGone.getGone(), opponentGone.getClaimWinSeconds());
							break;
						default:
							err.printf("Unrecognized event type: %s", event.getType());
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
