package lichess;

import types.Color;
import types.DeclineReason;
import types.Result;
import types.Room;
import types.User;
import types.Variant;

public interface LichessAPIProvider {

	Result move(String gameId, String move);

	Result chat(String gameId, String text, Room room);

	Result abort(String gameId);

	Result resign(String gameId);

	Result draw(String gameId, boolean accept);

	Result acceptChallenge(String challengeId);

	Result declineChallenge(String challengeId, DeclineReason reason);
	
	User getUser();

	Result createChallenge(String user, boolean rated, int clock_limit, int clock_increment, Color color, Variant variant, String fen);

}
