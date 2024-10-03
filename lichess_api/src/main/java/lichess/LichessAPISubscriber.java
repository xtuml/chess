package lichess;

import lichess.types.APIException;
import lichess.types.Challenge;
import lichess.types.Game;
import lichess.types.GameEventInfo;
import lichess.types.GameOverview;
import lichess.types.GameState;
import lichess.types.GameUpdate;
import lichess.types.Room;

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
	
	void gameOverview(LichessAPIProvider provider, String gameId, GameOverview gameOverview);

	void gameUpdate(LichessAPIProvider provider, String gameId, GameUpdate gameUpdate);

}
