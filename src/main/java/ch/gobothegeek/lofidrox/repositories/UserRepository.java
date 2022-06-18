package ch.gobothegeek.lofidrox.repositories;

import ch.gobothegeek.lofidrox.model.entities.User;
import ch.gobothegeek.lofidrox.model.entities.UserPK;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// repository used to access users table
@ApplicationScoped
@Repository(forEntity = User.class)
public abstract class UserRepository extends LfdRepository<User, UserPK> {
	private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	// return user if found by its username
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract Optional<User> findByUsername(String username);

	// with username and encoded password, returns the required user (if found)
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract Optional<User> findByUsernameAndPwd(String username, String pwd);

	// create the required user, with encrpyted password
	@Transactional(Transactional.TxType.REQUIRED)
	public User createUser(String username, String pwd) {
		User user;

		user = new User(username, pwd);
		user = this.save(user);
		return user;
	}

	// return list of users
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract List<User> findAllOrderByUsernameAsc();
}
