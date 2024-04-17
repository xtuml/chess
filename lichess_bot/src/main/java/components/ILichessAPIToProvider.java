package components;

import org.xtuml.bp.core.ComponentInstance_c;

import types.Color;
import types.DeclineReason;
import types.Result;
import types.Room;
import types.User;
import types.Variant;

public interface ILichessAPIToProvider {

	Result move(ComponentInstance_c senderReceiver, String gameId, String move);

	Result chat(ComponentInstance_c senderReceiver, String gameId, String text, Room room);

	Result abort(ComponentInstance_c senderReceiver, String gameId);

	Result resign(ComponentInstance_c senderReceiver, String gameId);

	Result draw(ComponentInstance_c senderReceiver, String gameId, Boolean accept);

	Result acceptChallenge(ComponentInstance_c senderReceiver, String challengeId);

	Result declineChallenge(ComponentInstance_c senderReceiver, String challengeId, DeclineReason reason);

	Result connect(ComponentInstance_c senderReceiver);
	
	User getUser(ComponentInstance_c senderReceiver);
	
	Result createChallenge(ComponentInstance_c senderReceiver, String user, Boolean rated, int clock_limit, int clock_increment, Color color, Variant variant, String fen);

}
