package system.lichess.adapters;

import types.lichess.Color;
import types.lichess.GameState;
import types.lichess.GameStatus;

public class GameStateAdapter extends GameState {

	public GameStateAdapter(lichess.types.GameState o) {
		if (o != null) {
			setMoves(o.getMoves().toArray(new String[0]));
			setMove_count(o.getMove_count());
			setWtime(o.getWtime());
			setBtime(o.getBtime());
			setWinc(o.getWinc());
			setBinc(o.getBinc());
			setStatus(EnumAdapter.adapt(o.getStatus(), GameStatus.class));
			setWinner(EnumAdapter.adapt(o.getWinner(), Color.class));
			setWdraw(o.getWdraw());
			setBdraw(o.getBdraw());
			setWtakeback(o.getWtakeback());
			setBtakeback(o.getBtakeback());
		}
	}

}
