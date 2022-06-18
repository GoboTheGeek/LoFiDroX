package ch.gobothegeek.lofidrox.model.json.user;

import java.util.List;

// a json wrapper used to return users list
public class JsonUsersList {
	private List<String> users;

	public JsonUsersList() { }

	public JsonUsersList(List<String> users) {
		this.users = users;
	}

	public List<String> getUsers() { return users; }
	public void setUsers(List<String> users) { this.users = users; }

}
