package system.lichess;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IPort;
import lichess.LichessAPIConnection;
import lichess.LichessAPIProvider;
import lichess.LichessAPISubscriber;
import lichess.types.APIException;
import lichess.types.Challenge;
import lichess.types.DeclineReason;
import lichess.types.Game;
import lichess.types.GameEventInfo;
import lichess.types.GameOverview;
import lichess.types.GameState;
import lichess.types.GameUpdate;
import lichess.types.Room;
import system.Lichess;
import system.lichess.adapters.APIExceptionAdapter;
import system.lichess.adapters.BulkPairingAdapter;
import system.lichess.adapters.ChallengeAdapter;
import system.lichess.adapters.EnumAdapter;
import system.lichess.adapters.GameAdapter;
import system.lichess.adapters.GameEventInfoAdapter;
import system.lichess.adapters.GameStateAdapter;
import system.lichess.adapters.UserAdapter;
import types.Properties;
import types.lichess.BulkPairing;
import types.lichess.Color;
import types.lichess.User;
import types.lichess.Variant;

public class LichessAPIAdapter extends LichessAPI {

	private final LichessAPIConnection lichess;

	public LichessAPIAdapter(Lichess context, IPort<?> peer) {
		super(context, peer);
		lichess = new LichessAPIConnection(System.out, System.err, new LichessAPIHandler());
	}

	@Override
	public boolean initialize(final Properties properties) throws XtumlException {
		return lichess.initialize(properties);
	}

	@Override
	public boolean acceptChallenge(final String p_challenge_id) throws XtumlException {
		return lichess.acceptChallenge(p_challenge_id);
	}

	@Override
	public boolean move(final String p_game_id, final String p_move) throws XtumlException {
		return lichess.move(p_game_id, p_move);
	}

	@Override
	public boolean claimVictory(final String p_game_id) throws XtumlException {
		return lichess.claimVictory(p_game_id);
	}

	@Override
	public boolean chat(final String p_game_id, final String p_text, final types.lichess.Room p_room)
			throws XtumlException {
		return lichess.chat(p_game_id, p_text, EnumAdapter.adapt(p_room, Room.class));
	}

	@Override
	public boolean takeback(final String p_game_id, final boolean p_accept) throws XtumlException {
		return lichess.takeback(p_game_id, p_accept);
	}

	@Override
	public User account() throws XtumlException {
		return new UserAdapter(lichess.account());
	}

	@Override
	public boolean draw(final String p_game_id, final boolean p_accept) throws XtumlException {
		return lichess.draw(p_game_id, p_accept);
	}

	@Override
	public boolean declineChallenge(final String p_challenge_id, final types.lichess.DeclineReason p_reason)
			throws XtumlException {
		return lichess.declineChallenge(p_challenge_id, EnumAdapter.adapt(p_reason, DeclineReason.class));
	}

	@Override
	public boolean resign(final String p_game_id) throws XtumlException {
		return lichess.resign(p_game_id);
	}

	@Override
	public boolean abort(final String p_game_id) throws XtumlException {
		return lichess.abort(p_game_id);
	}

	@Override
	public boolean createChallenge(final String p_user, final boolean p_rated, final int p_clock_limit,
			final int p_clock_increment, final Color p_color, final Variant p_variant, final String p_fen)
			throws XtumlException {
		return lichess.createChallenge(p_user, p_rated, p_clock_limit, p_clock_increment,
				EnumAdapter.adapt(p_color, lichess.types.Color.class),
				EnumAdapter.adapt(p_variant, lichess.types.Variant.class), p_fen);
	}

	@Override
	public BulkPairing bulkPairing(String p_players, int p_clock_limit, int p_clock_increment, int p_days, int p_pair_at,
			int p_start_clocks_at, boolean p_rated, Variant p_variant, String p_fen, String p_msg, String p_rules)
			throws XtumlException {
		return new BulkPairingAdapter(lichess.bulkPairing(p_players, p_clock_limit, p_clock_increment, p_days, p_pair_at, p_start_clocks_at,
				p_rated, EnumAdapter.adapt(p_variant, lichess.types.Variant.class), p_fen, p_msg, p_rules));
	}

	@Override
	public void handleBotEvents() throws XtumlException {
		lichess.handleBotEvents();
	}

	@Override
	public void handleBotGameEvents(String p_game_id) throws XtumlException {
		lichess.handleBotGameEvents(p_game_id);
	}

	@Override
	public void handleGameEvents(String p_game_id) throws XtumlException {
		lichess.handleGameEvents(p_game_id);
	}

	@Override
	public User upgradeToBot() throws XtumlException {
		return new UserAdapter(lichess.upgradeToBot());
	}

	private class LichessAPIHandler implements LichessAPISubscriber {

		@Override
		public void gameStart(LichessAPIProvider provider, GameEventInfo gameEvent) {
			try {
				LichessAPIAdapter.this.gameStart(new GameEventInfoAdapter(gameEvent));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void gameFinish(LichessAPIProvider provider, GameEventInfo gameEvent) {
			try {
				LichessAPIAdapter.this.gameFinish(new GameEventInfoAdapter(gameEvent));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void challenge(LichessAPIProvider provider, Challenge challenge) {
			try {
				LichessAPIAdapter.this.challenge(new ChallengeAdapter(challenge));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void challengeCanceled(LichessAPIProvider provider, Challenge challenge) {
			try {
				LichessAPIAdapter.this.challengeCanceled(new ChallengeAdapter(challenge));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void gameFull(LichessAPIProvider provider, Game game) {
			try {
				LichessAPIAdapter.this.gameFull(new GameAdapter(game));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void gameState(LichessAPIProvider provider, String gameId, GameState gameState) {
			try {
				LichessAPIAdapter.this.gameState(gameId, new GameStateAdapter(gameState));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void chatLine(LichessAPIProvider provider, String gameId, String username, String text,
				lichess.types.Room room) {
			try {
				LichessAPIAdapter.this.chatLine(gameId, username, text,
						EnumAdapter.adapt(room, types.lichess.Room.class));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void opponentGone(LichessAPIProvider provider, String gameId, boolean gone, int claimWinInSeconds) {
			try {
				LichessAPIAdapter.this.opponentGone(gameId, gone, claimWinInSeconds);
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void error(LichessAPIProvider provider, APIException error) {
			try {
				LichessAPIAdapter.this.error(new APIExceptionAdapter(error));
			} catch (XtumlException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void gameOverview(LichessAPIProvider provider, String gameId, GameOverview gameOverview) {
			throw new UnsupportedOperationException("TODO API operation not implemented for Ciera");
		}

		@Override
		public void gameUpdate(LichessAPIProvider provider, String gameId, GameUpdate gameUpdate) {
			throw new UnsupportedOperationException("TODO API operation not implemented for Ciera");
		}

	}

}
