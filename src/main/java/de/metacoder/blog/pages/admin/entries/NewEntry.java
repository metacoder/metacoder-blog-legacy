package de.metacoder.blog.pages.admin.entries;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;
import de.metacoder.blog.security.BlogRoles;

@RequiresRoles(BlogRoles.ADMIN)
@RequiresAuthentication
public class NewEntry {
	
	@Inject
	private SecurityService securityService;

	@Inject
	private BlogEntryRepository blogEntryRepository;

	@Property
	private BlogEntry blogEntry;

	public Object onSuccess() {
		if (blogEntry.getAuthorName() == null) {
			blogEntry.setAuthorName(securityService.getSubject().getPrincipal().toString());
		}
		blogEntryRepository.save(blogEntry);
		return Index.class;
	}

	
}
