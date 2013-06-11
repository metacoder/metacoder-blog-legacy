package de.metacoder.blog.pages.admin.users;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.services.UserService;

public class NewUser {

	@Property
	String userName;

	@Property
	String password;

	@Component(id = "password")
	PasswordField passwordField;

	@Inject
	UserRepository userRepository;

	@Property
    UserBO userBO;

	@Component
	Form form;

	@Inject
	UserService userService;

	void onValidateFromForm() {
		final UserBO userBO = userRepository.findOne(userName);
		if (userBO != null) {
			form.recordError("Der gewählte Benutzername existiert bereits.");
		}
	}

	Object onSuccess() {
		userService.createUser(userName, password);
		return Index.class;
	}
}
