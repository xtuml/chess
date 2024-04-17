package components;

import org.xtuml.bp.core.ComponentInstance_c;

import types.DeclineReason;
import types.Result;
import types.Room;
import types.User;

public interface ILichessAPIToProvider {

	Result move(ComponentInstance_c senderReceiver, String gameId, String move);

	Result chat(ComponentInstance_c senderReceiver, String gameId, String text, Room room);

	Result abort(ComponentInstance_c senderReceiver, String gameId);

	Result resign(ComponentInstance_c senderReceiver, String gameId);

	Result draw(ComponentInstance_c senderReceiver, String gameId, boolean accept);

	Result acceptChallenge(ComponentInstance_c senderReceiver, String challengeId);

	Result declineChallenge(ComponentInstance_c senderReceiver, String challengeId, DeclineReason reason);

	Result connect(ComponentInstance_c senderReceiver);
	
	User getUser(ComponentInstance_c senderReceiver);

}
