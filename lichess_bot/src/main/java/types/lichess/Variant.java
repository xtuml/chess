package types.lichess;

public enum Variant {
	
	STANDARD("standard"),
	CHESS960("chess960"),
	CRAZYHOUSE("crazyhouse"),
	ANTICHESS("antichess"),
	ATOMIC("atomic"),
	HORDE("horde"),
	KINGOFTHEHILL("kingOfTheHill"),
	RACINGKINGS("racingKings"),
	THREECHECK("threeCheck"),
	FROMPOSITION("fromPosition");

	private final String value;

	Variant(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
