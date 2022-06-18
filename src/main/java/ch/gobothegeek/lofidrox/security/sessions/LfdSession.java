package ch.gobothegeek.lofidrox.security.sessions;

import ch.gobothegeek.lofidrox.exceptions.LfdSessionException;

import java.util.HashMap;

public class LfdSession {
	private HashMap<String, Object> properties;

	public LfdSession() {
		this.properties = new HashMap<>();
	}

	public void destroy() {
		this.properties.clear();
		this.properties = null;
	}

	public void add(String key, Object obj) {
		if (!this.properties.containsKey(key)) {
			this.properties.put(key, obj);
		} else {
			this.properties.replace(key, obj);
		}
	}

	public Object get(String key) throws LfdSessionException {
		if (this.properties.containsKey(key)) { return this.properties.get(key); }
		throw new LfdSessionException("No such object in session (" + key + ")");
	}
}
