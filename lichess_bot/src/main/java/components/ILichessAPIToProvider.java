package components;

import java.util.Properties;

import org.xtuml.bp.core.ComponentInstance_c;

import types.lichess.Color;
import types.lichess.DeclineReason;
import types.lichess.Room;
import types.lichess.User;
import types.lichess.Variant;

public interface ILichessAPIToProvider {

	boolean move(ComponentInstance_c senderReceiver, String gameId, String move);

	boolean chat(ComponentInstance_c senderReceiver, String gameId, String text, Room room);

	boolean abort(ComponentInstance_c senderReceiver, String gameId);

	boolean resign(ComponentInstance_c senderReceiver, String gameId);

	boolean draw(ComponentInstance_c senderReceiver, String gameId, Boolean accept);

	boolean takeback(ComponentInstance_c senderReceiver, String gameId, Boolean accept);

	boolean acceptChallenge(ComponentInstance_c senderReceiver, String challengeId);

	boolean declineChallenge(ComponentInstance_c senderReceiver, String challengeId, DeclineReason reason);

	void connect(ComponentInstance_c senderReceiver, Properties properties);
	
	User account(ComponentInstance_c senderReceiver);
	
	boolean createChallenge(ComponentInstance_c senderReceiver, String user, Boolean rated, int clock_limit, int clock_increment, Color color, Variant variant, String fen);

	boolean claimVictory(ComponentInstance_c senderReceiver, String gameId);

}
