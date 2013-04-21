package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryTO;

public class NewEntry {

	@Inject
	private SecurityService securityService;

	@Inject
	BlogEntryService blogEntryService;

	@Inject
	UserRepository userRepository;

	@Property
	private BlogEntryTO blogEntry;

	public Object onSuccess() {
		final User currentUser = userRepository.findOne(securityService.getSubject().getPrincipal().toString());
		blogEntry.setAuthor(currentUser.getName());
		blogEntryService.createNewBlogEntry(blogEntry);
		return Index.class;
	}
}
