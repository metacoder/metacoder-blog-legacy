package de.metacoder.blog.persistence.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.metacoder.blog.persistence.entities.BlogEntry;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 * For development purposes only :-) 
 */
@Component
public class BlogEntryRepository {

	private List<BlogEntry> blogEntryList = new ArrayList<BlogEntry>();
	
	
	public void save(BlogEntry blogEntry){
		blogEntryList.add(blogEntry);
	}
	
	public List<BlogEntry> findAll() {
		return blogEntryList;
	}
	
}
