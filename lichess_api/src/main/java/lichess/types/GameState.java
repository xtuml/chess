package lichess.types;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	
	private List<String> moves = new ArrayList<>();
	private int wtime = 0;
	private int btime = 0;
	private int winc = 0;
	private int binc = 0;
	private GameStatus status;
	private Color winner;
	private boolean wdraw = false;
	private boolean bdraw = false;
	private boolean wtakeback = false;
	private boolean btakeback = false;

	public List<String> getMoves() {
		return moves;
	}

	public void setMoves(List<String> moves) {
		this.moves = moves;
	}
	
	public int getMove_count() {
		return moves.size();
	}
	
	public void setMove_count(int move_count) {
		throw new UnsupportedOperationException();
	}

	public int getWtime() {
		return wtime;
	}

	public void setWtime(int wtime) {
		this.wtime = wtime;
	}

	public int getBtime() {
		return btime;
	}

	public void setBtime(int btime) {
		this.btime = btime;
	}

	public int getWinc() {
		return winc;
	}

	public void setWinc(int winc) {
		this.winc = winc;
	}

	public int getBinc() {
		return binc;
	}

	public void setBinc(int binc) {
		this.binc = binc;
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

	public boolean getWdraw() {
		return wdraw;
	}

	public void setWdraw(boolean wdraw) {
		this.wdraw = wdraw;
	}

	public boolean getBdraw() {
		return bdraw;
	}

	public void setBdraw(boolean bdraw) {
		this.bdraw = bdraw;
	}

	public boolean getWtakeback() {
		return wtakeback;
	}

	public void setWtakeback(boolean wtakeback) {
		this.wtakeback = wtakeback;
	}

	public boolean getBtakeback() {
		return btakeback;
	}

	public void setBtakeback(boolean btakeback) {
		this.btakeback = btakeback;
	}

}
