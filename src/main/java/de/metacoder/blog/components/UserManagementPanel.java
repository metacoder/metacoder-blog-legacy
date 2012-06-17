package de.metacoder.blog.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.services.UserService;

public class UserManagementPanel {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementPanel.class);

	private String userName;

	private String password;

	@Component(id = "password")
	private PasswordField passwordField;

	@Inject
	private UserRepository userRepository;

	@Property
	private User user;

	@Component
	private Form form;

	@Inject
	private UserService userService;
	
	public Iterable<User> getAllUsers() {
		return userRepository.findAll(new Sort(Direction.DESC, "creationDate"));
	}

	public void onEditUser(@RequestParameter("username") String username) {
		LOGGER.info("called edit for user: " + username);
	}

	public void onDeleteUser(@RequestParameter("username") String username) {
		LOGGER.info("called delete for user: " + username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	void onValidateFromForm()
    {
		User user = userRepository.findOne(userName);
        if (user != null)
        {
            form.recordError("Der gewählte Benutzername existiert bereits.");
        }
    }
	
	void onSuccess(){
		userService.createUser(userName, password);
	}

}
