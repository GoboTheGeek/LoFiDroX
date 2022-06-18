package ch.gobothegeek.lofidrox.security.urls;

import ch.gobothegeek.lofidrox.services.UserService;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
// Check if user is not logged in
public class LfdUnsecuredUrl implements AccessDecisionVoter {

	@Inject private UserService userService;

	@Override
	public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext voterCtx) {
		return null;
	}
}
