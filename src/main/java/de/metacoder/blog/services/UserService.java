package de.metacoder.blog.services;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.security.BlogRoles;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	
	private SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
	
	public void createUser(String username, String password){

		final User user = new User();
		user.setName(username);

		setPasswordWithRandomSaltForUser(user, password);
		
		// TODO currently each user is an admin. Fix this in later versions when you need a more granular role concept.
		user.getRoles().add(BlogRoles.ADMIN);

		userRepository.save(user);
	}
	
	public void changePasswordOfUser(User user, String newPassword){
		setPasswordWithRandomSaltForUser(user, newPassword);
		userRepository.save(user);
	}
	
	private void setPasswordWithRandomSaltForUser(User user, String newPassword){
		final ByteSource salt = secureRandomNumberGenerator.nextBytes();
		user.setPassword(new Sha512Hash(newPassword, salt).toHex()); // hex is default of the shiro credentials matcher.
		user.setSalt(salt.getBytes());
	}
}
