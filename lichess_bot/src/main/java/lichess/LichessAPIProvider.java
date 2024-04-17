package lichess;

import types.Result;
import types.Room;
import types.User;

public interface LichessAPIProvider {

	Result move(String gameId, String move);

	Result chat(String gameId, String text, Room room);

	Result abort(String gameId);

	Result resign(String gameId);

	Result draw(String gameId, boolean accept);

	Result acceptChallenge(String challengeId);

	Result declineChallenge(String challengeId);
	
	User getUser();

}
