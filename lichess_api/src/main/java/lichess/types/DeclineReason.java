package lichess.types;

public enum DeclineReason {
	
	GENERIC("generic"),
	LATER("later"),
	TOOFAST("tooFast"),
	TOOSLOW("tooSlow"),
	TIMECONTROL("timeControl"),
	RATED("rated"),
	CASUAL("casual"),
	STANDARD("standard"),
	VARIANT("variant"),
	NOBOT("noBot"),
	ONLYBOT("onlyBot");
	
	private final String value;

	DeclineReason(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
