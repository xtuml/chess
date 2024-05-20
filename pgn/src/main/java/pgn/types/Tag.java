package pgn.types;

public class Tag {
	
	private String name;
	private String value;
	
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("[%s \"%s\"]", name, value);
	}

}
