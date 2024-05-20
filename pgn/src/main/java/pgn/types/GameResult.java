package pgn.types;

import java.util.stream.Stream;

public enum GameResult {

	WHITE_WIN("1-0"), BLACK_WIN("0-1"), DRAW("1/2-1/2"), ASTERISK("*");

	private final String value;

	GameResult(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static GameResult fromValue(String value) {
		return Stream.of(GameResult.values()).filter(gr -> gr.getValue().equals(value)).findAny().orElseThrow();
	}
	
	@Override
	public String toString() {
		return getValue();
	}

}
