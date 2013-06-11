package de.metacoder.blog.pages.admin.entries;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryTO;

public class Index {

	@Property
	BlogEntryTO entry;

	@Inject
	@Property
	BlogEntryService blogEntryService;


}
