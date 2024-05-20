package system.lichess.adapters;

import types.lichess.Challenge;
import types.lichess.ChallengeDirection;
import types.lichess.ChallengeStatus;
import types.lichess.Color;
import types.lichess.GameSpeed;
import types.lichess.Variant;

public class ChallengeAdapter extends Challenge {

	public ChallengeAdapter(lichess.types.Challenge o) {
		if (o != null) {
			setId(o.getId());
			setUrl(o.getUrl());
			setStatus(EnumAdapter.adapt(o.getStatus(), ChallengeStatus.class));
			setChallenger(new PlayerAdapter(o.getChallenger()));
			setDestUser(new PlayerAdapter(o.getDestUser()));
			setVariant(EnumAdapter.adapt(o.getVariant(), Variant.class));
			setRated(o.getRated());
			setSpeed(EnumAdapter.adapt(o.getSpeed(), GameSpeed.class));
			setTimeControl(new TimeControlAdapter(o.getTimeControl()));
			setColor(EnumAdapter.adapt(o.getColor(), Color.class));
			setDirection(EnumAdapter.adapt(o.getDirection(), ChallengeDirection.class));
			setInitialFen(o.getInitialFen());
			setDeclineReason(o.getDeclineReason());
			setDeclineReasonKey(o.getDeclineReasonKey());
		}
	}

}
