package de.metacoder.blog.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryCommentTO;
import de.metacoder.blog.transferobjects.BlogEntryTO;

public class Entry {

	@Inject
	BlogEntryService blogEntryService;

	@Property
	BlogEntryTO blogEntry;
	
	@Property
	BlogEntryCommentTO currentComment;

	@Property 
	BlogEntryCommentTO newComment;
	
	public void onActivate(){
		newComment = new BlogEntryCommentTO();
	}
	
	final Pattern entryNameAndIdPattern = Pattern.compile(".*-([0-9]+)");

	private String entryNameAndId;
	
	public Object onActivate(String entryNameAndId){
		this.entryNameAndId = entryNameAndId;
		final Matcher m = entryNameAndIdPattern.matcher(entryNameAndId);
		if(m.matches()){
			final Long entryId = Long.parseLong(m.group(1));
			blogEntry = blogEntryService.getEntry(entryId);
			return blogEntry == null ? Index.class : null;
		} else {
			return Index.class;
		}
	}
	
	public String onPassivate(){
		return entryNameAndId;
	}
	
}
