package system.lichess.adapters;

import types.lichess.UserTitle;
import types.lichess.Player;

public class PlayerAdapter extends Player {

	public PlayerAdapter(lichess.types.Player o) {
		if (o != null) {
			setId(o.getId());
			setName(o.getName());
			setRating(o.getRating());
			setProvisional(o.getProvisional());
			setOnline(o.getOnline());
			setPatron(o.getPatron());
			setTitle(EnumAdapter.adapt(o.getTitle(), UserTitle.class));
		}
	}

}
