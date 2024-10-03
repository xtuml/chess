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
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lichess.types.APIException;
import lichess.types.BotEvent;
import lichess.types.BulkPairing;
import lichess.types.ChatLine;
import lichess.types.Color;
import lichess.types.DeclineReason;
import lichess.types.Game;
import lichess.types.GameEvent;
import lichess.types.GameOverview;
import lichess.types.GameState;
import lichess.types.GameStreamItem;
import lichess.types.GameUpdate;
import lichess.types.OpponentGone;
import lichess.types.Room;
import lichess.types.User;
import lichess.types.UserTitle;
import lichess.types.Variant;
import lichess.types.adapters.LichessTypeAdapterFactory;

public class LichessAPIConnection implements LichessAPIProvider {

	private final HttpClient client;
	private final Gson gson;

	private String baseUrl;
	private String accessToken;
	private PrintStream out;
	private PrintStream err;
	private LichessAPISubscriber peer;
	private User user;

	public LichessAPIConnection(PrintStream out, PrintStream err, LichessAPISubscriber peer) {
		this(() -> out, () -> err, peer);
	}

	public LichessAPIConnection(Supplier<PrintStream> outSupplier, Supplier<PrintStream> errSupplier, LichessAPISubscriber peer) {
		this.peer = peer;

		// set up gson serialization
		final var builder = new GsonBuilder();
		final var adapterFactory = new LichessTypeAdapterFactory();
		builder.registerTypeAdapterFactory(adapterFactory);
		gson = builder.create();
		adapterFactory.setGson(gson);

		// set up logging
		this.out = new DynamicPrintStream(outSupplier);
		this.err = new DynamicPrintStream(errSupplier);

		// create the HTTP client
		client = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20))
				.build();

	}

	@Override
	public boolean initialize(Properties props) {
		// load configuration
		this.baseUrl = props.getProperty("api_base_url");
		this.accessToken = props.getProperty("access_token");

		// set up debug logging
		if (!Boolean.parseBoolean(props.getProperty("enable_debug_logging", "false"))) {
			final var nullOutputStream = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
				}
			};
			out = new PrintStream(nullOutputStream);
			err = new PrintStream(nullOutputStream);
		}

		return true;
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
		final var url = String.format("%s/api/account", baseUrl);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/json")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		try {
			final var response = client.send(request, BodyHandlers.ofString());
			switch (response.statusCode()) {
			case 200:
				user = gson.fromJson(response.body(), User.class);
				break;
			case 401:
				peer.error(this, new APIException(url, response.statusCode(), response.body()));
				break;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
			}
		} catch (IOException | InterruptedException e) {
			peer.error(this, new APIException(e));
		}
		return user;
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
	public BulkPairing bulkPairing(String players, int clockLimit, int clockIncrement, int days, int pairAt,
			int startClocksAt, boolean rated, Variant variant, String fen, String message, String rules) {
		final var params = new HashMap<String, String>();
		params.put("players", players);
		if (clockLimit >= 0 && clockIncrement >= 0) {
			params.put("clock.limit", Integer.toString(clockLimit));
			params.put("clock.increment", Integer.toString(clockIncrement));
		}
		if (days >= 0) {
			params.put("days", Integer.toString(days));
		}
		if (pairAt >= 0) {
			params.put("pairAt", Integer.toString(pairAt));
		}
		if (startClocksAt >= 0) {
			params.put("startClocksAt", Integer.toString(startClocksAt));
		}
		params.put("rated", Boolean.toString(rated));
		params.put("variant", variant.getValue());
		if (fen != null && !fen.isEmpty()) {
			params.put("fen", fen);
		}
		if (message != null && !message.isEmpty()) {
			params.put("message", message);
		}
		if (rules != null && !rules.isEmpty()) {
			params.put("rules", rules);
		}
		return postRequest(String.format("%s/api/bulk-pairing", baseUrl),
				response -> gson.fromJson(response.body(), BulkPairing.class)
				, "application/x-www-form-urlencoded",
				params).orElse(new BulkPairing());
	}

	@Override
	public boolean claimVictory(String gameId) {
		return postRequest(String.format("%s/api/bot/game/%s/claim-victory", baseUrl, gameId));
	}

	@Override
	public User upgradeToBot() {
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
				} else {
					out.printf("User '%s' is already a bot.\n", user.getUsername());
				}
				break;
			case 401:
				peer.error(this, new APIException(url, response.statusCode(), response.body()));
				break;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
			}
		} catch (IOException | InterruptedException e) {
			peer.error(this, new APIException(e));
		}
		return user;
	}

	@Override
	public void handleBotEvents() {
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

	@Override
	public void handleBotGameEvents(String gameId) {
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
						}
					}
				});
				break;
			case 400:
			case 401:
				peer.error(this, new APIException(url, response.statusCode(),
						response.body().collect(Collectors.joining("\n"))));
				break;
			case 404:
				peer.error(this, new APIException(url, response.statusCode(), "Game does not exist"));
				break;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
			}
		});
	}

	@Override
	public void handleGameEvents(String gameId) {
		out.printf("Handling events for game: %s\n", gameId);
		final var url = String.format("%s/api/stream/game/%s", baseUrl, gameId);
		final var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/x-ndjson")
				.header("Authorization", String.format("Bearer %s", accessToken)).GET().build();
		client.sendAsync(request, BodyHandlers.ofLines()).thenAccept(response -> {
			switch (response.statusCode()) {
			case 200:
				response.body().forEach(line -> {
					if (!line.isEmpty()) {
						out.println(line);
						final var event = gson.fromJson(line, GameStreamItem.class);
						if (event instanceof GameOverview) {
							peer.gameOverview(this, gameId, (GameOverview) event);
						} else if (event instanceof GameUpdate) {
							peer.gameUpdate(this, gameId, (GameUpdate) event);
						} else {
							err.printf("Unrecognized event type: %s", event.getClass().getName());
						}
					}
				});
				break;
			case 400:
			case 401:
				peer.error(this, new APIException(url, response.statusCode(),
						response.body().collect(Collectors.joining("\n"))));
				break;
			case 404:
				peer.error(this, new APIException(url, response.statusCode(), "Game does not exist"));
				break;
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
			}
		});
	}

	private boolean postRequest(String url) {
		return postRequest(url, null, null);
	}

	private boolean postRequest(String url, String contentType, Map<String, String> formParameters) {
		return postRequest(url, res -> true, contentType, formParameters).isPresent();
	}

	private <T> Optional<T> postRequest(String url, Function<HttpResponse<String>, T> responseParser, String contentType, Map<String, String> formParameters) {
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
				return Optional.of(responseParser.apply(response));
			case 400:
			case 401:
				peer.error(this, new APIException(url, response.statusCode(), response.body()));
				return Optional.empty();
			default:
				peer.error(this, new APIException(url, response.statusCode(), "Unexpected response code"));
				return Optional.empty();
			}
		} catch (IOException | InterruptedException e) {
			peer.error(this, new APIException(e));
			return Optional.empty();
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
	
	private static class DynamicPrintStream extends PrintStream {
		
		public DynamicPrintStream(Supplier<? extends OutputStream> osSupplier) {
			super(new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					osSupplier.get().write(b);
				}
			});
		}
	}

}
