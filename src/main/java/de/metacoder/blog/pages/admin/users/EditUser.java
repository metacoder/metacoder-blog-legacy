package de.metacoder.blog.pages.admin.users;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.services.UserService;

public class EditUser {

	@Inject
	SecurityService securityService;

	@Inject
	UserRepository userRepository;

	String username;

	@Property
	User user;

	@Component
	Form passwordChangeForm;

	@Property
	String newPassword;

	@Inject
	UserService userService;

	public void onActivate(final String username) {
		this.username = username;
		user = userRepository.findOne(username);
	}

	public String onPassivate() {
		return username;
	}

	public Object onSuccessFromBeanEditForm() {
		// TODO implement me
		return Index.class;
	}

	public Object onSuccessFromPasswordChangeForm() {
		if (newPassword != null && newPassword.length() > 0) {
			System.out.println("success from pw change form!");
			userService.changePasswordOfUser(user, newPassword);
			return Index.class;
		}
		return null;
	}
}
