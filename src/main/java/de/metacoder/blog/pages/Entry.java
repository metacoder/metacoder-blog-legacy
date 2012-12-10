package de.metacoder.blog.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.BlogEntryComment;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

public class Entry {

	@Inject
	BlogEntryRepository blogEntryRepository;

	@Property
	BlogEntry blogEntry;
	
	@Property
	BlogEntryComment currentComment;

	@Property 
	BlogEntryComment newComment;
	
	public void onActivate(){
		newComment = new BlogEntryComment();
	}
	
	final Pattern entryNameAndIdPattern = Pattern.compile(".*-([0-9]+)");

	private String entryNameAndId;
	
	public Object onActivate(String entryNameAndId){
		this.entryNameAndId = entryNameAndId;
		final Matcher m = entryNameAndIdPattern.matcher(entryNameAndId);
		if(m.matches()){
			final Long entryId = Long.parseLong(m.group(1));
			blogEntry = blogEntryRepository.findOne(entryId);
			return blogEntry == null ? Index.class : null;
		} else {
			return Index.class;
		}
	}
	
	public String onPassivate(){
		return entryNameAndId;
	}
	
}
