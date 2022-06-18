package ch.gobothegeek.lofidrox.model.json.user;

// a json wrapper used to log in user
public class JsonUserLogout {
	private String username;
	private Boolean exited = Boolean.FALSE;

	public JsonUserLogout() { }

	public JsonUserLogout(String username) {
		this.username = username;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public Boolean getExited() { return exited; }
	public void setExited(Boolean exited) { this.exited = exited; }
}
