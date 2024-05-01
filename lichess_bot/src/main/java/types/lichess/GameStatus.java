package types.lichess;

import java.util.stream.Stream;

public enum GameStatus {

	CREATED(10),
	STARTED(20),
	ABORTED(25),
	MATE(30),
	RESIGN(31),
	STALEMATE(32),
	TIMEOUT(33),
	DRAW(34),
	OUTOFTIME(35),
	CHEAT(36),
	NOSTART(37),
	UNKNOWNFINISH(38),
	VARIANTEND(60);

	private int value;

	GameStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static GameStatus fromValue(int value) {
		return Stream.of(GameStatus.values()).filter(s -> s.getValue() == value).findAny().orElseThrow();
	}

}
