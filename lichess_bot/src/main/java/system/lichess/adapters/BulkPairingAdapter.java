package system.lichess.adapters;

import types.lichess.BulkPairing;
import types.lichess.PairedGame;
import types.lichess.Variant;

public class BulkPairingAdapter extends BulkPairing {
	
	public BulkPairingAdapter(lichess.types.BulkPairing o) {
		if (o != null) {
			setId(o.getId());
			setGames(o.getGames().stream().map(PairedGameAdapter::new).toArray(PairedGame[]::new));
			setVariant(EnumAdapter.adapt(o.getVariant(), Variant.class));
			setClock(new TimeControlAdapter(o.getClock()));
			setPairAt(o.getPairAt());
			setPairedAt(o.getPairedAt());
			setRated(o.getRated());
			setStartClocksAt(o.getStartClocksAt());
			setScheduledAt(o.getScheduledAt());
		}
	}

}
