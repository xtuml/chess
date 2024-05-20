package system.lichess.adapters;

import types.lichess.APIException;

public class APIExceptionAdapter extends APIException {

	public APIExceptionAdapter(lichess.types.APIException o) {
		if (o != null) {
			setError(o.getError());
			setStatus(o.getStatus());
		}
	}

}
