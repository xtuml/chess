package components;

import org.xtuml.bp.core.ComponentInstance_c;

import types.APIException;
import types.Challenge;
import types.Game;
import types.GameEventInfo;
import types.GameState;
import types.Room;

public interface ILichessAPIFromProvider {

	void gameStart(ComponentInstance_c senderReceiver, GameEventInfo gameEvent);

	void gameFinish(ComponentInstance_c senderReceiver, GameEventInfo gameEvent);

	void challenge(ComponentInstance_c senderReceiver, Challenge challenge);
	
	void challengeCanceled(ComponentInstance_c senderReceiver, Challenge challenge);

	void gameFull(ComponentInstance_c senderReceiver, Game game);

	void gameState(ComponentInstance_c senderReceiver, String gameId, GameState gameState);

	void chatLine(ComponentInstance_c senderReceiver, String gameId, String username, String text, Room room);
	
	void opponentGone(ComponentInstance_c senderReceiver, String gameId);

	void error(ComponentInstance_c senderReceiver, APIException error);

}
