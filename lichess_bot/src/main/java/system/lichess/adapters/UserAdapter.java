package system.lichess.adapters;

import types.lichess.User;
import types.lichess.UserTitle;

public class UserAdapter extends User {

	public UserAdapter(lichess.types.User o) {
		if (o != null) {
			setId(o.getId());
			setUsername(o.getUsername());
			setTitle(EnumAdapter.adapt(o.getTitle(), UserTitle.class));
		}
	}

}
