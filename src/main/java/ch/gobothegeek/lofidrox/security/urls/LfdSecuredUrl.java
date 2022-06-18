package ch.gobothegeek.lofidrox.security.urls;

import ch.gobothegeek.lofidrox.security.permissions.LfdPermissionsManager;
import ch.gobothegeek.lofidrox.security.permissions.LfdSecurityPermission;
import ch.gobothegeek.lofidrox.security.roles.LfdSecurityRole;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Set;

// Used to secure an url. Can be used with @LfdSecurityPermission et @LfdSecurityRole annotations
// voir https://deltaspike.apache.org/documentation/security.html
// voir https://github.com/rafabene/demo_deltaspike
@ApplicationScoped
public class LfdSecuredUrl implements AccessDecisionVoter {

	@Inject private LfdPermissionsManager lfdPermissionsManager;

	@Override
	public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext voterCtx) {
		Method method = voterCtx.<InvocationContext>getSource().getMethod();
		LfdSecurityRole annoRole;
		LfdSecurityPermission annoPerm;

		// as url is secured, only logged users can access
		// check if user has the required role (if needed)
		annoRole = method.getDeclaredAnnotation(LfdSecurityRole.class);
		if (null != annoRole) {

		}
		// check if user has the required permission (if needed)
		annoPerm = method.getDeclaredAnnotation(LfdSecurityPermission.class);
		if (null != annoPerm) {

		}
		return null;
	}
}
