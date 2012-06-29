package de.metacoder.blog.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.User;

public class BlogEntryDisplay {
	@Parameter(required=true)
	@Property
	BlogEntry entry;

	@Parameter
	@Property
	boolean displayTitle = true;
	
	@Property
	User author;
	
	public String getLinkContext(){
		final String title = entry.getTitle() + "-" + entry.getId();
		return title.replaceAll("[^A-Za-z0-9]", "-");
	}
}
