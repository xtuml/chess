package lichess;

import types.lichess.Color;
import types.lichess.DeclineReason;
import types.lichess.Room;
import types.lichess.User;
import types.lichess.Variant;

public interface LichessAPIProvider {

	boolean move(String gameId, String move);

	boolean chat(String gameId, String text, Room room);

	boolean abort(String gameId);

	boolean resign(String gameId);

	boolean draw(String gameId, boolean accept);

	boolean takeback(String gameId, boolean accept);

	boolean acceptChallenge(String challengeId);

	boolean declineChallenge(String challengeId, DeclineReason reason);
	
	User account();

	boolean createChallenge(String user, boolean rated, int clock_limit, int clock_increment, Color color, Variant variant, String fen);
	
	boolean claimVictory(String gameId);

}
