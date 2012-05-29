package de.metacoder.blog.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 * 
 */
public class EditEntry {
	
	@Inject
	private BlogEntryRepository blogEntryRepository;
	
	@Property
	private BlogEntry blogEntry = new BlogEntry();

	public void onSuccess(){
		System.out.println("bla: " + blogEntry);
		blogEntryRepository.save(blogEntry);
		blogEntry = new BlogEntry();
	}

	public void onFailure(){
		System.out.println("OH NO");
	}
}
