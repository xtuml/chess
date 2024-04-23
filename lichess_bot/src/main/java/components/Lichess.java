package components;

import java.util.Properties;

import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.CorePlugin;

import lichess.LichessAPIConnection;
import lichess.LichessAPIProvider;
import lichess.LichessAPISubscriber;
import types.lichess.APIException;
import types.lichess.Challenge;
import types.lichess.Color;
import types.lichess.DeclineReason;
import types.lichess.Game;
import types.lichess.GameEventInfo;
import types.lichess.GameState;
import types.lichess.Result;
import types.lichess.Room;
import types.lichess.User;
import types.lichess.Variant;

public class Lichess implements ILichessAPIToProvider {

	private final ILichessAPIFromProvider engine;
	private LichessAPIConnection lichess;

	public Lichess(ILichessAPIFromProvider engine) {
		this.engine = engine;
	}

	@Override
	public void connect(ComponentInstance_c senderReceiver, Properties properties) {
		if (lichess == null) {
			lichess = new LichessAPIConnection(CorePlugin.out, new LichessAPIHandler(), properties);
			lichess.initialize();
		}
	}

	@Override
	public Result move(ComponentInstance_c senderReceiver, String gameId, String move) {
		return lichess.move(gameId, move);
	}

	@Override
	public Result chat(ComponentInstance_c senderReceiver, String gameId, String text, Room room) {
		return lichess.chat(gameId, text, room);
	}

	@Override
	public Result abort(ComponentInstance_c senderReceiver, String gameId) {
		return lichess.abort(gameId);
	}

	@Override
	public Result resign(ComponentInstance_c senderReceiver, String gameId) {
		return lichess.resign(gameId);
	}

	@Override
	public Result draw(ComponentInstance_c senderReceiver, String gameId, Boolean accept) {
		return lichess.draw(gameId, accept);
	}

	@Override
	public Result acceptChallenge(ComponentInstance_c senderReceiver, String challengeId) {
		return lichess.acceptChallenge(challengeId);
	}

	@Override
	public Result declineChallenge(ComponentInstance_c senderReceiver, String challengeId, DeclineReason reason) {
		return lichess.declineChallenge(challengeId, reason);
	}
	
	@Override
	public User getUser(ComponentInstance_c senderReceiver) {
		return lichess.getUser();
	}

	@Override
	public Result createChallenge(ComponentInstance_c senderReceiver, String user, Boolean rated, int clock_limit,
			int clock_increment, Color color, Variant variant, String fen) {
		return lichess.createChallenge(user, rated, clock_limit, clock_increment, color, variant, fen);
	}

	private class LichessAPIHandler implements LichessAPISubscriber {

		@Override
		public void gameStart(LichessAPIProvider provider, GameEventInfo gameEvent) {
			engine.gameStart(null, gameEvent);
		}

		@Override
		public void gameFinish(LichessAPIProvider provider, GameEventInfo gameEvent) {
			engine.gameFinish(null, gameEvent);
		}

		@Override
		public void challenge(LichessAPIProvider provider, Challenge challenge) {
			engine.challenge(null, challenge);
		}

		@Override
		public void challengeCanceled(LichessAPIProvider provider, Challenge challenge) {
			engine.challengeCanceled(null, challenge);
		}

		@Override
		public void gameFull(LichessAPIProvider provider, Game game) {
			engine.gameFull(null, game);
		}

		@Override
		public void gameState(LichessAPIProvider provider, String gameId, GameState gameState) {
			engine.gameState(null, gameId, gameState);
		}

		@Override
		public void chatLine(LichessAPIProvider provider, String gameId, String username, String text, Room room) {
			engine.chatLine(null, gameId, username, text, room);
		}

		@Override
		public void opponentGone(LichessAPIProvider provider, String gameId) {
			engine.opponentGone(null, gameId);
		}

		@Override
		public void error(LichessAPIProvider provider, APIException error) {
			engine.error(null, error);
		}
	}

}
