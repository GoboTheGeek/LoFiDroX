package ch.gobothegeek.lofidrox.security.sessions;

import java.io.Serializable;

public class LfdUser implements Serializable {
	private String user;

	public LfdUser() { }

	public LfdUser(String user) {
		this.user = user;
	}

	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }
}
