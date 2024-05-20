package system.lichess.adapters;

import types.lichess.Game;
import types.lichess.GameSpeed;
import types.lichess.Variant;

public class GameAdapter extends Game {

	public GameAdapter(lichess.types.Game o) {
		if (o != null) {
			setId(o.getId());
			setVariant(EnumAdapter.adapt(o.getVariant(), Variant.class));
			setClock(new TimeControlAdapter(o.getClock()));
			setSpeed(EnumAdapter.adapt(o.getSpeed(), GameSpeed.class));
			setRated(o.getRated());
			setCreatedAt(o.getCreatedAt());
			setWhite(new PlayerAdapter(o.getWhite()));
			setBlack(new PlayerAdapter(o.getBlack()));
			setInitialFen(o.getInitialFen());
			setGameState(new GameStateAdapter(o.getGameState()));
			setTournamentId(o.getTournamentId());
		}
	}

}
