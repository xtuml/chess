package lichess;

import java.util.Properties;

import lichess.types.BulkPairing;
import lichess.types.Color;
import lichess.types.DeclineReason;
import lichess.types.Room;
import lichess.types.User;
import lichess.types.Variant;

public interface LichessAPIProvider {

	@ParameterNames(names = { "game_id", "move" })
	boolean move(String gameId, String move);

	@ParameterNames(names = { "game_id", "text", "room" })
	boolean chat(String gameId, String text, Room room);

	@ParameterNames(names = { "game_id" })
	boolean abort(String gameId);

	@ParameterNames(names = { "game_id" })
	boolean resign(String gameId);

	@ParameterNames(names = { "game_id", "accept" })
	boolean draw(String gameId, boolean accept);

	@ParameterNames(names = { "game_id", "accept" })
	boolean takeback(String gameId, boolean accept);

	@ParameterNames(names = { "challenge_id" })
	boolean acceptChallenge(String challengeId);

	@ParameterNames(names = { "challenge_id", "reason" })
	boolean declineChallenge(String challengeId, DeclineReason reason);

	User account();

	@ParameterNames(names = { "user", "rated", "clock_limit", "clock_increment", "color", "variant", "fen" })
	boolean createChallenge(String user, boolean rated, int clockLimit, int clockIncrement, Color color,
			Variant variant, String fen);

	@ParameterNames(names = { "game_id" })
	boolean claimVictory(String gameId);

	@ParameterNames(names = { "players", "clock_limit", "clock_increment", "days", "pair_at", "start_clocks_at",
			"rated", "variant", "fen", "msg", "rules" })
	BulkPairing bulkPairing(String players, int clockLimit, int clockIncrement, int days, int pairAt, int startClocksAt,
			boolean rated, Variant variant, String fen, String message, String rules);

	@ParameterNames(names = { "properties" })
	boolean initialize(Properties properties);

	User upgradeToBot();

	void handleBotEvents();

	@ParameterNames(names = { "game_id" })
	void handleBotGameEvents(String gameId);

	@ParameterNames(names = { "game_id" })
	void handleGameEvents(String gameId);

}
