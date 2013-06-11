package de.metacoder.blog.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;

public class Profile {

	@Inject
	UserRepository userRepository;

	@Property
    UserBO userBO;

	public Object onActivate(final String username) {
		userBO = userRepository.findOne(username);

		if (userBO == null) {
			return Index.class;
		}

		return null;
	}
}
