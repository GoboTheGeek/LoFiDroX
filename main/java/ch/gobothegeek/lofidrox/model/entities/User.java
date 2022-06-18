package ch.gobothegeek.lofidrox.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

// Described a user in this application
@Entity
@Table(name = "PUBLIC.PUBLIC.USERS")
@IdClass(UserPK.class)
public class User implements Serializable {
	@Id @Column(name="USERNAME") private String username; // username for this account
	@Column(name="PWD") private String pwd; // encrypted password

	public User() { }

	public User(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User that = (User) o;
		return Objects.equals(username, that.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public String toString() {
		return "UserEntity{" + "username='" + username + '\'' + ", pwd='" + pwd + '\'' + '}';
	}
}
