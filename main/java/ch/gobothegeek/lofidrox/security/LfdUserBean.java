package ch.gobothegeek.lofidrox.security;

import ch.gobothegeek.lofidrox.security.sessions.LfdUser;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class LfdUserBean implements Serializable {
	@Inject
	private LfdUser user;

	public LfdUser getUser() { return user; }
	public void setUser(LfdUser user) { this.user = user; }

	@Produces
	@LfdUserLogged
	@RequestScoped
	public LfdUser getLfdUser() {
		return this.user;
	}
}

