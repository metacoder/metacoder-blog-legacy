package de.metacoder.blog.services;

import de.metacoder.blog.persistence.entities.UserBO;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.security.BlogRoles;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	
	private SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
	
	public void createUser(String username, String password){

		final UserBO userBO = new UserBO();
		userBO.setName(username);

		setPasswordWithRandomSaltForUser(userBO, password);
		
		// TODO currently each userBO is an admin. Fix this in later versions when you need a more granular role concept.
		userBO.getRoles().add(BlogRoles.ADMIN);

		userRepository.save(userBO);
	}
	
	public void changePasswordOfUser(UserBO userBO, String newPassword){
		setPasswordWithRandomSaltForUser(userBO, newPassword);
		userRepository.save(userBO);
	}
	
	private void setPasswordWithRandomSaltForUser(UserBO userBO, String newPassword){
		final ByteSource salt = secureRandomNumberGenerator.nextBytes();
		userBO.setPassword(new Sha512Hash(newPassword, salt).toHex()); // hex is default of the shiro credentials matcher.
		userBO.setSalt(salt.getBytes());
	}
}
