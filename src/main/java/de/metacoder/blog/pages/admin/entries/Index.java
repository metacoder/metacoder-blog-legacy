package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

public class Index {

	@Property
	BlogEntry entry;

	@Inject
	@Property
	BlogEntryRepository blogEntryRepository;

	public void onActionFromDelete(final Long blogEntryId) {
		blogEntryRepository.delete(blogEntryId);
	}
}
