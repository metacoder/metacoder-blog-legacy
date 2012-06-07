package de.metacoder.blog.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;

@Component
public class BlogHibernateRealm extends AuthorizingRealm {

	private final UserRepository userRepository;

	@Autowired
	public BlogHibernateRealm(final UserRepository userRepository) {
		this.userRepository = userRepository;

		// FIXME for development only
		// check if any user exits, if not generate default user
		if (!userRepository.findAll().iterator().hasNext()) {
			final User user = new User();
			user.setName("admin");
			user.setPassword("admin");
			user.getRoles().add(BlogRoles.ADMIN);
			userRepository.save(user);
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		final String principalName = getAvailablePrincipal(principals).toString();
		return getUser(principalName);
	}

	protected SimpleAccount getUser(final String username) {
		final User user = userRepository.findOne(username);

		if(user != null){
			return new SimpleAccount(user.getName(), user.getPassword(), getName(), user.getRoles(), null);
		}

		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		final SimpleAccount account = getUser(upToken.getUsername());
		return account;
	}
}
