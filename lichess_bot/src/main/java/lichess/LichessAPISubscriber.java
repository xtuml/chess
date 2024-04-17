package lichess;

import types.APIException;
import types.Challenge;
import types.Game;
import types.GameEventInfo;
import types.GameState;
import types.Room;

public interface LichessAPISubscriber {

	void gameStart(LichessAPIProvider provider, GameEventInfo gameEvent);

	void gameFinish(LichessAPIProvider provider, GameEventInfo gameEvent);

	void challenge(LichessAPIProvider provider, Challenge challenge);
	
	void challengeCanceled(LichessAPIProvider provider, Challenge challenge);

	void gameFull(LichessAPIProvider provider, Game game);

	void gameState(LichessAPIProvider provider, String gameId, GameState gameState);

	void chatLine(LichessAPIProvider provider, String gameId, String username, String text, Room room);
	
	void opponentGone(LichessAPIProvider provider, String gameId);
	
	void error(LichessAPIProvider provider, APIException error);
	
}
