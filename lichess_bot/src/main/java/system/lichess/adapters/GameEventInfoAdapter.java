package system.lichess.adapters;

import types.lichess.Color;
import types.lichess.GameEventInfo;
import types.lichess.GameSource;
import types.lichess.GameStatus;

public class GameEventInfoAdapter extends GameEventInfo {

	public GameEventInfoAdapter(lichess.types.GameEventInfo o) {
		if (o != null) {
			setId(o.getId());
			setSource(EnumAdapter.adapt(o.getSource(), GameSource.class));
			setStatus(EnumAdapter.adapt(o.getStatus(), GameStatus.class));
			setWinner(EnumAdapter.adapt(o.getWinner(), Color.class));
		}
	}

}
