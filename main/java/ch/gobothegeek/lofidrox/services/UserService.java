package ch.gobothegeek.lofidrox.services;

import ch.gobothegeek.lofidrox.exceptions.LfdException;
import ch.gobothegeek.lofidrox.model.entities.User;
import ch.gobothegeek.lofidrox.repositories.UserRepository;
import ch.gobothegeek.lofidrox.security.LfdUserBean;
import ch.gobothegeek.lofidrox.utils.GtgPasswordUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Inject private UserRepository userRepository;
	@Inject private SessionService sessionService;
	//@Inject private Event<LfdUserLoggedEvent> lfdUserLoggedEvent;
	@Inject private LfdUserBean lfdUserBean;

	@Transactional(Transactional.TxType.REQUIRED)
	public Boolean loginUser(String username, String pwd) {
		Optional<User> user;
		String encPwd;

		try {
			encPwd = GtgPasswordUtils.hashPassword(pwd, Hex.decodeHex(ConfigResolver.getPropertyValue("application.password.salt")),
				ConfigResolver.getPropertyValue("application.password.algorithm"),
				Integer.parseInt(ConfigResolver.getPropertyValue("application.password.iteration")),
				Integer.parseInt(ConfigResolver.getPropertyValue("application.password.keylength")));
			user = this.userRepository.findByUsernameAndPwd(username, encPwd);
			if (user.isPresent()) {
				this.sessionService.create(username);
				//this.lfdUserLoggedEvent.fire(new LfdUserLoggedEvent());
				this.lfdUserBean.setUser(new ch.gobothegeek.lofidrox.security.sessions.LfdUser(username));
				return Boolean.TRUE;
			}
			logger.error("User not found or wrong password [" + username + "]");
			return Boolean.FALSE;
		} catch (DecoderException e) {
			System.out.println("Unable to decode salt [" + ConfigResolver.getPropertyValue("application.password.salt") + "]");
			e.printStackTrace();
			logger.error("Unable to decode salt [" + ConfigResolver.getPropertyValue("application.password.salt") + "]", e);
		}
		return Boolean.FALSE;
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public User createUser(String username, String pwd) throws LfdException {
		Optional<User> userReg;
		String encPwd;

		if (null != pwd) {
			userReg = this.userRepository.findByUsername(username);
			if (userReg.isEmpty()) {
				try {
					encPwd = GtgPasswordUtils.hashPassword(pwd, Hex.decodeHex(ConfigResolver.getPropertyValue("application.password.salt")), ConfigResolver.getPropertyValue("application.password.algorithm"), Integer.parseInt(ConfigResolver.getPropertyValue("application.password.iteration")), Integer.parseInt(ConfigResolver.getPropertyValue("application.password.keylength")));
					return this.userRepository.createUser(username, encPwd);
				} catch (DecoderException e) {
					logger.error("Unable to decode salt [" + ConfigResolver.getPropertyValue("application.password.salt") + "]", e);
					throw new LfdException("Unable to hash password");
				}
			}
		}
		return null;
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void logoffUser(String username) {
		if (this.sessionService.hasSession(username)) {
			this.sessionService.delete(username);
		}
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public List<User> listUsers() {
		return this.userRepository.findAllOrderByUsernameAsc();
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public List<String> listUsernames() {
		List<User> users = this.userRepository.findAllOrderByUsernameAsc();
		List<String> usernames;

		if (null != users) {
			usernames = new ArrayList<>();
			if (0 < users.size()) {
				for (User usr : users) { usernames.add(usr.getUsername()); }
			}
			return usernames;
		}
		return null;
	}
}
