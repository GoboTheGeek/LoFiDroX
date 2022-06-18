package ch.gobothegeek.lofidrox.model.json.user;

// a json wrapper used to register a new account
public class JsonUserRegister {
	private String username;
	private String pwd;
	private Boolean registered = Boolean.FALSE;

	public JsonUserRegister() { }

	public JsonUserRegister(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }

	public Boolean getRegistered() { return registered; }
	public void setRegistered(Boolean registered) { this.registered = registered; }
}
