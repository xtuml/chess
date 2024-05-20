package lichess.types;

public class GameEventInfo {
	
	private String id = "";
	private GameSource source;
	private GameStatus status;
	private Color winner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GameSource getSource() {
		return source;
	}

	public void setSource(GameSource source) {
		this.source = source;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Color getWinner() {
		return winner;
	}

	public void setWinner(Color winner) {
		this.winner = winner;
	}

}
