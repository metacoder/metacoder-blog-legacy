package de.metacoder.blog.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import de.metacoder.blog.transferobjects.BlogEntryTO;

@Import(library={"context:scripts/sh/startSyntaxHighlighter.js"})
public class BlogEntryDisplay {
	@Parameter(required=true)
	@Property
	BlogEntryTO entry;

	@Parameter
	@Property
	boolean displayTitle = true; // TODO meaningful name
	
	public String getLinkContext(){
		final String title = entry.getTitle() + "-" + entry.getId();
		return title.replaceAll("[^A-Za-z0-9]", "-");
	}
}
