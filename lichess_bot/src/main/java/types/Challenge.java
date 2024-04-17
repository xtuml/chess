package types;

public class Challenge {
	
	private String id = "";
	private String url = "";
	private ChallengeStatus status;
	private Player challenger;
	private Player destUser;
	private Variant variant;
	private boolean rated = false;
	private GameSpeed speed;
	private TimeControl timeControl;
	private Color color;
	private ChallengeDirection direction;
	private String initialFen = "";
	private String declineReason = "";
	private String declineReasonKey = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ChallengeStatus getStatus() {
		return status;
	}

	public void setStatus(ChallengeStatus status) {
		this.status = status;
	}

	public Player getChallenger() {
		return challenger;
	}

	public void setChallenger(Player challenger) {
		this.challenger = challenger;
	}

	public Player getDestUser() {
		return destUser;
	}

	public void setDestUser(Player destUser) {
		this.destUser = destUser;
	}

	public Variant getVariant() {
		return variant;
	}

	public void setVariant(Variant variant) {
		this.variant = variant;
	}

	public boolean getRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}

	public GameSpeed getSpeed() {
		return speed;
	}

	public void setSpeed(GameSpeed speed) {
		this.speed = speed;
	}

	public TimeControl getTimeControl() {
		return timeControl;
	}

	public void setTimeControl(TimeControl timeControl) {
		this.timeControl = timeControl;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ChallengeDirection getDirection() {
		return direction;
	}

	public void setDirection(ChallengeDirection direction) {
		this.direction = direction;
	}

	public String getInitialFen() {
		return initialFen;
	}

	public void setInitialFen(String initialFen) {
		this.initialFen = initialFen;
	}

	public String getDeclineReason() {
		return declineReason;
	}

	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}

	public String getDeclineReasonKey() {
		return declineReasonKey;
	}

	public void setDeclineReasonKey(String declineReasonKey) {
		this.declineReasonKey = declineReasonKey;
	}

}
