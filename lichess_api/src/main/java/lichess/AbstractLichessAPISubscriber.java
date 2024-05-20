package lichess;

import lichess.types.APIException;
import lichess.types.Challenge;
import lichess.types.Game;
import lichess.types.GameEventInfo;
import lichess.types.GameState;
import lichess.types.Room;

public abstract class AbstractLichessAPISubscriber implements LichessAPISubscriber {

	@Override
	public void gameStart(LichessAPIProvider provider, GameEventInfo gameEvent) {
	}

	@Override
	public void gameFinish(LichessAPIProvider provider, GameEventInfo gameEvent) {
	}

	@Override
	public void challenge(LichessAPIProvider provider, Challenge challenge) {
	}

	@Override
	public void challengeCanceled(LichessAPIProvider provider, Challenge challenge) {
	}

	@Override
	public void gameFull(LichessAPIProvider provider, Game game) {
	}

	@Override
	public void gameState(LichessAPIProvider provider, String gameId, GameState gameState) {
	}

	@Override
	public void chatLine(LichessAPIProvider provider, String gameId, String username, String text, Room room) {
	}

	@Override
	public void opponentGone(LichessAPIProvider provider, String gameId, boolean gone, int claimWinInSeconds) {
	}

	@Override
	public void error(LichessAPIProvider provider, APIException error) {
	}

}
