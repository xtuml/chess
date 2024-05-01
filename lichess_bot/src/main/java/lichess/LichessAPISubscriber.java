package lichess;

import types.lichess.APIException;
import types.lichess.Challenge;
import types.lichess.Game;
import types.lichess.GameEventInfo;
import types.lichess.GameState;
import types.lichess.Room;

public interface LichessAPISubscriber {

	void gameStart(LichessAPIProvider provider, GameEventInfo gameEvent);

	void gameFinish(LichessAPIProvider provider, GameEventInfo gameEvent);

	void challenge(LichessAPIProvider provider, Challenge challenge);
	
	void challengeCanceled(LichessAPIProvider provider, Challenge challenge);

	void gameFull(LichessAPIProvider provider, Game game);

	void gameState(LichessAPIProvider provider, String gameId, GameState gameState);

	void chatLine(LichessAPIProvider provider, String gameId, String username, String text, Room room);
	
	void opponentGone(LichessAPIProvider provider, String gameId, boolean gone, int claimWinInSeconds);
	
	void error(LichessAPIProvider provider, APIException error);

}
