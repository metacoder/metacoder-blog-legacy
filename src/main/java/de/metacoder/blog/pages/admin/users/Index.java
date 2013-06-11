package de.metacoder.blog.pages.admin.users;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;

public class Index {

	@Property
    UserBO userBO;

	@Inject
	@Property
	UserRepository userRepository;

	public void onActionFromDelete(final String username) {
		userRepository.delete(username);
	}
}
