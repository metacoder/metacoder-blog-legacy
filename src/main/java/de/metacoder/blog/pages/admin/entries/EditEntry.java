package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryTO;

public class EditEntry {

	@Inject
	private BlogEntryService blogEntryService;

	private Long id;

	@Property
	private BlogEntryTO blogEntry;

	public void onActivate(final Long id) {
		this.id = id;
		blogEntry = blogEntryService.getEntry(id);
	}

	public Long onPassivate() {
		return id;
	}

	public Object onSuccess() {
		blogEntryService.updateBlogEntry(blogEntry);
		return Index.class;
	}
}
