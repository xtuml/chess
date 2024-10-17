package components;

import java.util.Properties;

import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.CorePlugin;

import lichess.LichessAPIConnection;
import lichess.LichessAPIProvider;
import lichess.LichessAPISubscriber;
import lichess.types.APIException;
import lichess.types.BulkPairing;
import lichess.types.Challenge;
import lichess.types.Color;
import lichess.types.DeclineReason;
import lichess.types.Game;
import lichess.types.GameEventInfo;
import lichess.types.GameOverview;
import lichess.types.GameState;
import lichess.types.GameUpdate;
import lichess.types.Room;
import lichess.types.User;
import lichess.types.Variant;

public class Lichess implements ILichessAPIToProvider {

	private final ILichessAPIFromProvider engine;
	private final LichessAPIConnection lichess;

	public Lichess(ILichessAPIFromProvider engine) {
		this.engine = engine;
		lichess = new LichessAPIConnection(() -> CorePlugin.out, () -> CorePlugin.err, new LichessAPIHandler());
	}

	@Override
	public boolean initialize(ComponentInstance_c senderReceiver, Properties properties) {
		return lichess.initialize(properties);
	}

	@Override
	public boolean move(ComponentInstance_c senderReceiver, String gameId, String move) {
		return lichess.move(gameId, move);
	}

	@Override
	public boolean chat(ComponentInstance_c senderReceiver, String gameId, String text, Room room) {
		return lichess.chat(gameId, text, room);
	}

	@Override
	public boolean abort(ComponentInstance_c senderReceiver, String gameId) {
		return lichess.abort(gameId);
	}

	@Override
	public boolean resign(ComponentInstance_c senderReceiver, String gameId) {
		return lichess.resign(gameId);
	}

	@Override
	public boolean draw(ComponentInstance_c senderReceiver, String gameId, Boolean accept) {
		return lichess.draw(gameId, accept);
	}

	@Override
	public boolean takeback(ComponentInstance_c senderReceiver, String gameId, Boolean accept) {
		return lichess.takeback(gameId, accept);
	}

	@Override
	public boolean acceptChallenge(ComponentInstance_c senderReceiver, String challengeId) {
		return lichess.acceptChallenge(challengeId);
	}

	@Override
	public boolean declineChallenge(ComponentInstance_c senderReceiver, String challengeId, DeclineReason reason) {
		return lichess.declineChallenge(challengeId, reason);
	}
	
	@Override
	public User account(ComponentInstance_c senderReceiver) {
		return lichess.account();
	}

	@Override
	public boolean createChallenge(ComponentInstance_c senderReceiver, String user, Boolean rated, int clock_limit,
			int clock_increment, Color color, Variant variant, String fen) {
		return lichess.createChallenge(user, rated, clock_limit, clock_increment, color, variant, fen);
	}

	@Override
	public boolean claimVictory(ComponentInstance_c senderReceiver, String gameId) {
		return lichess.claimVictory(gameId);
	}

	@Override
	public BulkPairing bulkPairing(ComponentInstance_c senderReceiver, String players, int clock_limit, int clock_increment,
			int days, int pair_at, int start_clocks_at, Boolean rated, Variant variant, String fen, String msg,
			String rules) {
		return lichess.bulkPairing(players, clock_limit, clock_increment, days, pair_at, start_clocks_at, rated, variant, fen, msg, rules);
	}

	@Override
	public User upgradeToBot(ComponentInstance_c senderReceiver) {
		return lichess.upgradeToBot();
	}

	@Override
	public void handleBotEvents(ComponentInstance_c senderReceiver) {
		lichess.handleBotEvents();
	}

	@Override
	public void handleBotGameEvents(ComponentInstance_c senderReceiver, String game_id) {
		lichess.handleBotGameEvents(game_id);
	}

	@Override
	public void handleGameEvents(ComponentInstance_c senderReceiver, String game_id) {
		lichess.handleGameEvents(game_id);
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
		public void opponentGone(LichessAPIProvider provider, String gameId, boolean gone, int claimWinInSeconds) {
			engine.opponentGone(null, gameId, gone, claimWinInSeconds);
		}
	
		@Override
		public void error(LichessAPIProvider provider, APIException error) {
			engine.error(null, error);
		}

		@Override
		public void gameOverview(LichessAPIProvider provider, String gameId, GameOverview gameOverview) {
			engine.gameOverview(null, gameId, gameOverview);
		}

		@Override
		public void gameUpdate(LichessAPIProvider provider, String gameId, GameUpdate gameUpdate) {
			engine.gameUpdate(null, gameId, gameUpdate);
			
		}
	
	}

}
