package ch.gobothegeek.lofidrox.security.permissions;

import java.util.HashMap;

// Describes the permissions for all users
public class LfdPermissionsManager {
	private HashMap<String, HashMap<String, LfdPermissionType>> perms; // username -> (object name = LfdPermissionType)

	public LfdPermissionsManager() {
		this.perms = new HashMap<>();
	}

	// check if user has the required permission on object
	public boolean hasPermission(String username, String object, LfdPermissionType type) {
		return (this.perms.containsKey(username) && (this.perms.get(username).containsKey(object) && (type.equals(this.perms.get(username).get(object)))));
	}

	// TODO/GSG: a completer
	// load all permissions from database
	public void loadFromDatabase() {

	}
}
