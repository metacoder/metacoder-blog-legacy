package de.metacoder.blog.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.services.UserService;

@Component
public class BlogHibernateRealm extends AuthorizingRealm {

	private final UserRepository userRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogHibernateRealm.class);
	
	@Autowired
	public BlogHibernateRealm(final UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;

		CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
		setCredentialsMatcher(credentialsMatcher);

		// check if any user exits, if not generate default user
		if (!userRepository.findAll().iterator().hasNext()) {
			
			// use a randomly generated base64 string as password (the base64 string only contains chars on your keyboard...)
			String password = new SecureRandomNumberGenerator().nextBytes().toBase64();
			userService.createUser("admin", password);

			LOGGER.info("No user found in database. Creating default admin user with username 'admin' and password '"+password+"'. You should definitly change the password immediately!");
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		final String principalName = getAvailablePrincipal(principals).toString();
		final User user = userRepository.findOne(principalName);
		return user != null ? new SimpleAuthorizationInfo(user.getRoles()) : null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		final User user = userRepository.findOne(upToken.getUsername());

		if(user != null){
			SimpleAccount x = new SimpleAccount(user.getName(), user.getPassword(), getName(), user.getRoles(), null);
			x.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
			return x;
		}

		return null;
	}
}
