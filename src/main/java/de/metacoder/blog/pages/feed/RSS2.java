package de.metacoder.blog.pages.feed;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.ContentType;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

@ContentType("application/rss+xml")
public class RSS2 {
	
	private static final SimpleDateFormat RFC822_DateFormat = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

	@Inject
	@Property
	private BlogEntryRepository blogEntryRepositroy;

	@Property
	BlogEntry currentEntry;

	public String getCreationDateASRFC822(){
		return RFC822_DateFormat.format(currentEntry.getCreationDate());
	}
	
	public List<BlogEntry> getEntriesForFeed(){
		return blogEntryRepositroy.findAll(new PageRequest(0, 10, Direction.DESC, "creationDate")).getContent();
	}
}
