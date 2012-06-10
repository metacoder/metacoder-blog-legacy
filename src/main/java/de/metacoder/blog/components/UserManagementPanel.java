package de.metacoder.blog.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;

public class UserManagementPanel {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementPanel.class);
	
	@Inject
	private UserRepository userRepository;
	
	@Property
	private User user;
	
	public Iterable<User> getAllUsers() {
		return userRepository.findAll(new Sort(Direction.DESC, "creationDate"));
	}
	
	public void onEditUser(@RequestParameter("username") String username){
		LOGGER.info("called edit for user: " + username);
	}
	
	public void onDeleteUser(@RequestParameter("username") String username){
		LOGGER.info("called delete for user: " + username);
	}

}
