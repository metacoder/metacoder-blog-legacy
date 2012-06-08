package de.metacoder.blog.components;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeforeRenderBody;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import de.metacoder.blog.pages.Index;
import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

public class EditBlogEntryPanel {
	@Inject
	private SecurityService securityService;

	@Inject
	private BlogEntryRepository blogEntryRepository;

	private Long id;

	@Property
	private BlogEntry blogEntry;

	public void onActivate(final Long id) {
		this.id = id;
		blogEntry = blogEntryRepository.findOne(id);
	}

	public Long onPassivate() {
		return id;
	}

	public Object onSuccess() {
		if (blogEntry.getAuthorName() == null) {
			blogEntry.setAuthorName(securityService.getSubject().getPrincipal().toString());
		}
		blogEntryRepository.save(blogEntry);
		return Index.class;
	}
}
