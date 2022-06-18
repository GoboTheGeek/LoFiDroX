package ch.gobothegeek.lofidrox.model.entities;

import java.io.Serializable;
import java.util.Objects;

public class UserPK implements Serializable {
	private String username;

	public UserPK() { }

	public UserPK(String username) {
		this.username = username;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserPK that = (UserPK) o;
		return Objects.equals(username, that.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public String toString() {
		return "UserEntityPK{" + "username='" + username + '\'' + '}';
	}
}
