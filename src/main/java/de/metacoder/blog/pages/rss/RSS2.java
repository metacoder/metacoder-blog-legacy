package de.metacoder.blog.pages.rss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.ContentType;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

@ContentType("application/rss+xml")
public class RSS2 {
	
	private static final SimpleDateFormat RFC822_DateFormat = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

	@Inject
	@Property
	private BlogEntryRepository blogEntryRepositroy;

	@Property
	BlogEntry currentEntry;

	public String getAuthorNames(){
		Collection<String> names = new ArrayList<String>();
		for(User author : currentEntry.getAuthors()){
			names.add(author.getName());
		}
		return StringUtils.join(names, ",");
	}
	
	public String getCreationDateASRFC822(){
		return RFC822_DateFormat.format(currentEntry.getCreationDate());
	}
}
