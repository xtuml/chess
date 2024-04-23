package types.lichess;

public class Player {
	
	private String id = "";
	private String name = "";
	private int rating = 0;
	private boolean provisional = false;
	private boolean online = false;
	private boolean patron = false;
	private UserTitle title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean getProvisional() {
		return provisional;
	}

	public void setProvisional(boolean provisional) {
		this.provisional = provisional;
	}

	public boolean getOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean getPatron() {
		return patron;
	}

	public void setPatron(boolean patron) {
		this.patron = patron;
	}

	public UserTitle getTitle() {
		return title;
	}

	public void setTitle(UserTitle title) {
		this.title = title;
	}

}
