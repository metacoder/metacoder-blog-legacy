package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

public class EditEntry {
	
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
		blogEntryRepository.save(blogEntry);
		return Index.class;
	}
	
}
