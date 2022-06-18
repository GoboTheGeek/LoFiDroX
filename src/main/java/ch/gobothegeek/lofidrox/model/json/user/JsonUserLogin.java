package ch.gobothegeek.lofidrox.model.json.user;

// a json wrapper used to log in user
public class JsonUserLogin {
	private String username;
	private String pwd;
	private Boolean logged = Boolean.FALSE;

	public JsonUserLogin() { }

	public JsonUserLogin(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }

	public Boolean getLogged() { return logged; }
	public void setLogged(Boolean logged) { this.logged = logged; }
}
