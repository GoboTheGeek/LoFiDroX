package ch.gobothegeek.lofidrox.security;

import ch.gobothegeek.lofidrox.security.sessions.LfdUser;
import ch.gobothegeek.lofidrox.services.SessionService;
import org.apache.deltaspike.security.api.authorization.Secures;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;

@ApplicationScoped
public class LfdAuthorizer {

	@Inject private SessionService sessionService;

	@Secures
	@LfdSecured
	public boolean doSecuredCheck(InvocationContext invocationContext, BeanManager manager, @LfdUserLogged LfdUser user) throws Exception {
		return ((null != user) && (this.sessionService.hasSession(user.getUser())));
	}
}