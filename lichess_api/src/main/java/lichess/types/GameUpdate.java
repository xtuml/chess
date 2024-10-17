package lichess.types;

public class GameUpdate extends GameStreamItem {
	
	private String fen;
	private int wc;
	private int bc;
	
	public String getFen() {
		return fen;
	}
	
	public void setFen(String fen) {
		this.fen = fen;
	}

	public int getWc() {
		return wc;
	}

	public void setWc(int wc) {
		this.wc = wc;
	}

	public int getBc() {
		return bc;
	}

	public void setBc(int bc) {
		this.bc = bc;
	}

}
