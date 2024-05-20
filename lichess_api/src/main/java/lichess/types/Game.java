package lichess.types;

public class Game {
	
	private String id = "";
	private Variant variant;
	private TimeControl clock;
	private GameSpeed speed;
	private boolean rated = false;
	private long createdAt = 0l;
	private Player white;
	private Player black;
	private String initialFen = "";
	private GameState state;
	private String tournamentId = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Variant getVariant() {
		return variant;
	}

	public void setVariant(Variant variant) {
		this.variant = variant;
	}

	public TimeControl getClock() {
		return clock;
	}

	public void setClock(TimeControl clock) {
		this.clock = clock;
	}

	public GameSpeed getSpeed() {
		return speed;
	}

	public void setSpeed(GameSpeed speed) {
		this.speed = speed;
	}

	public boolean getRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public Player getWhite() {
		return white;
	}

	public void setWhite(Player white) {
		this.white = white;
	}

	public Player getBlack() {
		return black;
	}

	public void setBlack(Player black) {
		this.black = black;
	}

	public String getInitialFen() {
		return initialFen;
	}

	public void setInitialFen(String initialFen) {
		this.initialFen = initialFen;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
	
	public GameState getGameState() {
		return state;
	}
	
	public void setGameState(GameState state) {
		this.state = state;
	}

	public String getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(String tournamentId) {
		this.tournamentId = tournamentId;
	}
	
}
