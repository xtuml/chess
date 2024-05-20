package pgn.types;

import java.util.List;

public class PGNGame {
	
	private List<Tag> tags;
	private List<Line> moves;
	private GameResult result;

	public PGNGame(List<Tag> tags, List<Line> moves, GameResult result) {
		this.tags = tags;
		this.moves = moves;
		this.result = result;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Line> getMoves() {
		return moves;
	}

	public void setMoves(List<Line> moves) {
		this.moves = moves;
	}

	public GameResult getResult() {
		return result;
	}

	public void setResult(GameResult result) {
		this.result = result;
	}

}
