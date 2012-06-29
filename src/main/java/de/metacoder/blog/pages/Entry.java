package de.metacoder.blog.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

public class Entry {

	@Inject
	BlogEntryRepository blogEntryRepository;

	@Property
	BlogEntry blogEntry;

	final Pattern entryNameAndIdPattern = Pattern.compile(".*-([0-9]+)");

	public Object onActivate(String entryNameAndId){
		final Matcher m = entryNameAndIdPattern.matcher(entryNameAndId);
		if(m.matches()){
			final Long entryId = Long.parseLong(m.group(1));
			blogEntry = blogEntryRepository.findOne(entryId);
			return blogEntry == null ? Index.class : null;
		} else {
			return Index.class;
		}
	}
}
