package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.transaction.annotation.Transactional;
import org.tynamo.security.services.SecurityService;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;
import de.metacoder.blog.persistence.repositories.UserRepository;

public class NewEntry {
	
	@Inject
	private SecurityService securityService;

	@Inject
	BlogEntryRepository blogEntryRepository;

	@Inject
	UserRepository userRepository;
	
	@Property
	private BlogEntry blogEntry;

	@Transactional
	public Object onSuccess() {
		User currentUser = userRepository.findOne(securityService.getSubject().getPrincipal().toString());
		blogEntry.getAuthors().add(currentUser);
		blogEntryRepository.save(blogEntry);
		return Index.class;
	}

	
}
