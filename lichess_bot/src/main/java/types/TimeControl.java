package types;

import com.google.gson.annotations.SerializedName;

public class TimeControl {
	
	@SerializedName(value = "type")
	private String controlType = "";
	private int limit = 0;
	private int increment = 0;
	private int daysPerTurn = 0;
	private String show = "";

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String type) {
		this.controlType = type;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public int getDaysPerTurn() {
		return daysPerTurn;
	}

	public void setDaysPerTurn(int daysPerTurn) {
		this.daysPerTurn = daysPerTurn;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
	
}
