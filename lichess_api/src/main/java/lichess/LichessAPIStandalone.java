package lichess;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import lichess.types.APIException;
import lichess.types.Challenge;
import lichess.types.Game;
import lichess.types.GameEventInfo;
import lichess.types.GameOverview;
import lichess.types.GameState;
import lichess.types.GameUpdate;
import lichess.types.Room;
import lichess.types.adapters.LichessTypeAdapterFactory;

public class LichessAPIStandalone {

	private final Properties props = new Properties();
	private final Gson gson;
	private final LichessAPIConnection lichess;
	private final Subscriber sub;

	private final Path inDir;
	private final Path outDir;
	private final Path processedDir;

	private int messageNum = 0;

	LichessAPIStandalone(final String inDir, final String outDir, final String processedDir, final String configFile) {
		this.inDir = Path.of(inDir);
		this.outDir = Path.of(outDir);
		this.processedDir = Path.of(processedDir);

		// load properties
		try {
			props.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// set up gson serialization
		final var builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new LichessTypeAdapterFactory());
		gson = builder.create();

		// set up directories
		try {
			Files.createDirectories(this.inDir);
			Files.createDirectories(this.outDir);
			Files.createDirectories(this.processedDir);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// create API connection
		sub = new Subscriber();
		lichess = new LichessAPIConnection(System.out, System.err, sub);
	}

	void run() {
		// send account information
		sub.sendMessage("account", Map.of("user", lichess.account()));

		// initialize the Lichess connection
		lichess.initialize(props);

		// loop and wait for commands
		while (true) {

			// get the next command in the queue
			try {
				final Path command = Files.list(inDir).min(Path::compareTo).orElseThrow();
				try {
					// deserialize the message
					final Message msg = gson.fromJson(Files.readString(command), Message.class);

					// find the interface method based on the name in the message file
					final Method meth = Stream.of(LichessAPIProvider.class.getDeclaredMethods())
							.filter(m -> m.getName().equals(msg.getName())).findAny().orElseThrow();

					// assure all method parameters are annotated and the number matches
					final ParameterNames paramNames = meth.getAnnotation(ParameterNames.class);
					if (!(paramNames != null && Stream.of(paramNames.names()).filter(Objects::nonNull).count() == meth
							.getParameterCount() && meth.getParameterCount() == msg.getArgs().size())) {
						throw new IllegalArgumentException("Interface parameter mismatch");
					}

					// deserialize each argument
					final Object[] args = new Object[meth.getParameterCount()];
					for (String argName : msg.getArgs().keySet()) {
						for (int i = 0; i < meth.getParameterCount(); i++) {
							if (meth.getAnnotation(ParameterNames.class).names()[i].equals(argName)) {
								args[i] = gson.fromJson(msg.getArgs().get(argName), meth.getParameterTypes()[i]);
								break;
							}
						}
					}

					// invoke the interface method
					meth.invoke(lichess, args);

				} catch (NoSuchElementException | IOException | JsonSyntaxException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					System.err.println("Failed to process file: " + command);
					e.printStackTrace();
				} finally {
					Files.move(command, processedDir.resolve(String.format("%03d.json", (messageNum++) % 1000)));
				}
			} catch (NoSuchElementException e) {
				// normal behavior -- no command to process
			} catch (IOException e) {
				e.printStackTrace();
			}

			// sleep for 100ms
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}

	private static class Message {

		String name;
		Map<String, JsonElement> args;

		String getName() {
			return name;
		}

		Map<String, JsonElement> getArgs() {
			return args;
		}
	}

	private class Subscriber implements LichessAPISubscriber {

		private int messageNum = 0;

		synchronized void sendMessage(String name, Map<String, Object> args) {
			try {
				final JsonObject msg = new JsonObject();
				msg.addProperty("name", name);
				msg.add("args", gson.toJsonTree(args));
				Files.writeString(outDir.resolve(String.format("%03d.json", (messageNum++) % 1000)), gson.toJson(msg));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void gameStart(LichessAPIProvider provider, GameEventInfo gameEvent) {
			sendMessage("gameStart", Map.of("game_event", gameEvent));
		}

		@Override
		public void gameFinish(LichessAPIProvider provider, GameEventInfo gameEvent) {
			sendMessage("gameFinish", Map.of("game_event", gameEvent));
		}

		@Override
		public void challenge(LichessAPIProvider provider, Challenge challenge) {
			sendMessage("challenge", Map.of("challenge", challenge));
		}

		@Override
		public void challengeCanceled(LichessAPIProvider provider, Challenge challenge) {
			sendMessage("challengeCanceled", Map.of("challenge", challenge));

		}

		@Override
		public void gameFull(LichessAPIProvider provider, Game game) {
			sendMessage("gameFull", Map.of("game", game));
		}

		@Override
		public void gameState(LichessAPIProvider provider, String gameId, GameState gameState) {
			sendMessage("gameState", Map.of("game_id", gameId, "game_state", gameState));
		}

		@Override
		public void chatLine(LichessAPIProvider provider, String gameId, String username, String text, Room room) {
			sendMessage("chatLine", Map.of("game_id", gameId, "username", username, "text", text, "room", room));
		}

		@Override
		public void opponentGone(LichessAPIProvider provider, String gameId, boolean gone, int claimWinInSeconds) {
			sendMessage("opponentGone",
					Map.of("game_id", gameId, "gone", gone, "claim_win_in_seconds", claimWinInSeconds));
		}

		@Override
		public void error(LichessAPIProvider provider, APIException error) {
			sendMessage("error", Map.of("error", error));
		}

		@Override
		public void gameOverview(LichessAPIProvider provider, String gameId, GameOverview gameOverview) {
			sendMessage("gameOverview", Map.of("game_id", gameId, "game_overview", gameOverview));
		}

		@Override
		public void gameUpdate(LichessAPIProvider provider, String gameId, GameUpdate gameUpdate) {
			sendMessage("gameUpdate", Map.of("game_id", gameId, "game_update", gameUpdate));
		}

	}

	public static void main(String[] args) {
		LichessAPIStandalone app = new LichessAPIStandalone("incoming", "outgoing", "processed", "lichess_bot.properties");
		app.run();
	}

}
