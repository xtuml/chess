package system.lichess.adapters;

import types.lichess.PairedGame;

public class PairedGameAdapter extends PairedGame {
	
	public PairedGameAdapter(lichess.types.PairedGame o) {
		if (o != null) {
			setId(o.getId());
			setWhite(o.getWhite());
			setBlack(o.getBlack());
		}
	}

}
