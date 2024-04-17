package types;

public class OpponentGone {
	
	private boolean gone = false;
	private int claimWinSeconds = 0;

	public boolean getGone() {
		return gone;
	}

	public void setGone(boolean gone) {
		this.gone = gone;
	}

	public int getClaimWinSeconds() {
		return claimWinSeconds;
	}

	public void setClaimWinSeconds(int claimWinSeconds) {
		this.claimWinSeconds = claimWinSeconds;
	}
	
}
