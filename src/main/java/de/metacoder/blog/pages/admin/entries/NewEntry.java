package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryTO;

public class NewEntry {

	@Inject
	BlogEntryService blogEntryService;

	@Inject
	UserRepository userRepository;

	@Property
	private BlogEntryTO blogEntry;

	public Object onSuccess() {
		final UserBO currentUserBO = userRepository.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
		blogEntry.setAuthor(currentUserBO.getName());
		blogEntryService.createNewBlogEntry(blogEntry);
		return Index.class;
	}
}
