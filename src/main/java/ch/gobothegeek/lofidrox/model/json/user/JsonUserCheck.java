package ch.gobothegeek.lofidrox.model.json.user;

// a json wrapper used to check if user is logged in
public class JsonUserCheck {
	private String username;
	private Boolean session = Boolean.FALSE;

	public JsonUserCheck() { }

	public JsonUserCheck(String username) {
		this.username = username;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public Boolean getSession() { return session; }
	public void setSession(Boolean session) { this.session = session; }
}
