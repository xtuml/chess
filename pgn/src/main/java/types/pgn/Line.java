package types.pgn;

import java.util.ArrayList;
import java.util.List;

public class Line {
	
	private String move;
	private List<Line> continuations;
	private Line parent;

	public Line(String move, List<Line> continuations, Line parent) {
		this.move = move;
		this.continuations = continuations;
		this.parent = parent;
	}

	public Line(String move) {
		this(move, new ArrayList<>(), null);
	}
	
	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public List<Line> getContinuations() {
		return continuations;
	}

	public void setContinuations(List<Line> continuations) {
		this.continuations = continuations;
	}
	
	public Line getParent() {
		return parent;
	}
	
	public void setParent(Line parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return continuations.size() > 0 ? move + " " + continuations.get(0) : move;
	}
	
}
