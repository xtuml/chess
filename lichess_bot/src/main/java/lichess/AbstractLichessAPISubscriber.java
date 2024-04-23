package lichess;

import types.lichess.APIException;
import types.lichess.Challenge;
import types.lichess.Game;
import types.lichess.GameEventInfo;
import types.lichess.GameState;
import types.lichess.Room;

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
	public void opponentGone(LichessAPIProvider provider, String gameId) {
	}

	@Override
	public void error(LichessAPIProvider provider, APIException error) {
	}

}
