package system.lichess.adapters;

import types.lichess.TimeControl;

public class TimeControlAdapter extends TimeControl {

	public TimeControlAdapter(lichess.types.TimeControl o) {
		if (o != null) {
			setControlType(o.getControlType());
			setLimit(o.getLimit());
			setIncrement(o.getIncrement());
			setDaysPerTurn(o.getDaysPerTurn());
			setShow(o.getShow());
		}
	}

}
