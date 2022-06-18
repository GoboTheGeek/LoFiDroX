package ch.gobothegeek.lofidrox.services;

import ch.gobothegeek.lofidrox.exceptions.LfdSessionException;
import ch.gobothegeek.lofidrox.security.sessions.LfdSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;

@ApplicationScoped
public class SessionService {
	private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
	private HashMap<String, LfdSession> sessions;

	public SessionService() {
		this.sessions = new HashMap<>();
	}

	public void create(String username) {
		if (!this.sessions.containsKey(username)) {
			this.sessions.put(username, new LfdSession());
		}
	}

	public void add(String username, String key, Object obj) throws LfdSessionException {
		if (this.sessions.containsKey(username)) {
			this.sessions.get(username).add(key, obj);
			return;
		}
		logger.error("No session for [" + username + "]");
		throw new LfdSessionException("No session for this user (" + username + ")");
	}

	public Object get(String username, String key) throws LfdSessionException {
		if (this.sessions.containsKey(username)) {
			return this.sessions.get(username).get(key);
		}
		logger.error("No session for [" + username + "]");
		throw new LfdSessionException("No session for this user (" + username + ")");
	}

	public void delete(String username) {
		if (this.sessions.containsKey(username)) {
			this.sessions.get(username).destroy();
			this.sessions.remove(username);
		}
	}

	public boolean hasSession(String username) {
		return this.sessions.containsKey(username);
	}
}
