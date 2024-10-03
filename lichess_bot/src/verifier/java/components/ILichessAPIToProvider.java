package components;

import java.util.Properties;

import org.xtuml.bp.core.ComponentInstance_c;

import lichess.types.BulkPairing;
import lichess.types.Color;
import lichess.types.DeclineReason;
import lichess.types.Room;
import lichess.types.User;
import lichess.types.Variant;

public interface ILichessAPIToProvider {

	boolean move(ComponentInstance_c senderReceiver, String gameId, String move);

	boolean chat(ComponentInstance_c senderReceiver, String gameId, String text, Room room);

	boolean abort(ComponentInstance_c senderReceiver, String gameId);

	boolean resign(ComponentInstance_c senderReceiver, String gameId);

	boolean draw(ComponentInstance_c senderReceiver, String gameId, Boolean accept);

	boolean takeback(ComponentInstance_c senderReceiver, String gameId, Boolean accept);

	boolean acceptChallenge(ComponentInstance_c senderReceiver, String challengeId);

	boolean declineChallenge(ComponentInstance_c senderReceiver, String challengeId, DeclineReason reason);

	User account(ComponentInstance_c senderReceiver);
	
	boolean createChallenge(ComponentInstance_c senderReceiver, String user, Boolean rated, int clock_limit, int clock_increment, Color color, Variant variant, String fen);

	boolean claimVictory(ComponentInstance_c senderReceiver, String gameId);

  BulkPairing bulkPairing(ComponentInstance_c senderReceiver, String players, int clock_limit, int clock_increment, int days, int pair_at, int start_clocks_at, Boolean rated, Variant variant, String fen, String msg, String rules);

  boolean initialize(ComponentInstance_c senderReceiver, Properties properties);

  User upgradeToBot(ComponentInstance_c senderReceiver);

  void handleBotEvents(ComponentInstance_c senderReceiver);

  void handleBotGameEvents(ComponentInstance_c senderReceiver, String game_id);

  void handleGameEvents(ComponentInstance_c senderReceiver, String game_id);

}
