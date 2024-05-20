package lichess.types;

public class BotEvent {
	
	private BotEventType type;
	private GameEventInfo game;
	private Challenge challenge;

	public BotEventType getType() {
		return type;
	}
	public void setType(BotEventType type) {
		this.type = type;
	}
	public GameEventInfo getGame() {
		return game;
	}
	public void setGame(GameEventInfo game) {
		this.game = game;
	}
	public Challenge getChallenge() {
		return challenge;
	}
	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

}
