package lichess.types;

import java.util.List;

public class BulkPairing {
	
	private String id;
	private List<PairedGame> games;
	private Variant variant;
	private TimeControl clock;
	private long pairAt;
	private long pairedAt;
	private boolean rated;
	private long startClocksAt;
	private long scheduledAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<PairedGame> getGames() {
		return games;
	}

	public void setGames(List<PairedGame> games) {
		this.games = games;
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

	public long getPairAt() {
		return pairAt;
	}

	public void setPairAt(long pairAt) {
		this.pairAt = pairAt;
	}

	public long getPairedAt() {
		return pairedAt;
	}

	public void setPairedAt(long pairedAt) {
		this.pairedAt = pairedAt;
	}

	public boolean getRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}

	public long getStartClocksAt() {
		return startClocksAt;
	}

	public void setStartClocksAt(long startClocksAt) {
		this.startClocksAt = startClocksAt;
	}

	public long getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(long scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

}
