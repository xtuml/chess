package components;

import org.xtuml.bp.core.ComponentInstance_c;

import lichess.types.APIException;
import lichess.types.Challenge;
import lichess.types.Game;
import lichess.types.GameEventInfo;
import lichess.types.GameOverview;
import lichess.types.GameState;
import lichess.types.GameUpdate;
import lichess.types.Room;

public interface ILichessAPIFromProvider {

	void gameStart(ComponentInstance_c senderReceiver, GameEventInfo gameEvent);

	void gameFinish(ComponentInstance_c senderReceiver, GameEventInfo gameEvent);

	void challenge(ComponentInstance_c senderReceiver, Challenge challenge);
	
	void challengeCanceled(ComponentInstance_c senderReceiver, Challenge challenge);

	void gameFull(ComponentInstance_c senderReceiver, Game game);

	void gameState(ComponentInstance_c senderReceiver, String gameId, GameState gameState);

	void chatLine(ComponentInstance_c senderReceiver, String gameId, String username, String text, Room room);
	
	void opponentGone(ComponentInstance_c senderReceiver, String gameId, boolean gone, int claimWinInSeconds);

	void error(ComponentInstance_c senderReceiver, APIException error);

	void gameOverview(ComponentInstance_c senderReceiver, String gameId, GameOverview gameOverview);

	void gameUpdate(ComponentInstance_c senderReceiver, String gameId, GameUpdate gameUpdate);

}
