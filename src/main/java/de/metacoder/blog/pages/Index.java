package de.metacoder.blog.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryTO;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 *
 * Start page of application blog.
 */
public class Index {

	private static final int PAGE_SIZE = 5;

	@Inject
	private BlogEntryService blogEntryService;

	@Property
	BlogEntryTO blogEntry;
	
	@Property User author;

	private int pageId = 0;

	public List<BlogEntryTO> getBlogEntries() {
		return blogEntryService.getAllBlogEntriesOrderedByDate(PAGE_SIZE, pageId);
	}

	public void onActivate(final int pageId) {
		this.pageId = pageId >= 0 ? pageId : 0;
	}

	public int getNextPage() {
		return pageId + 1;
	}

	public boolean getHasNextPage() {
		return blogEntryService.numRows() > (pageId + 1) * PAGE_SIZE;
	}

	public boolean getHasPreviousPage() {
		return pageId > 0;
	}

	public int getPreviousPage() {
		return pageId - 1;
	}
}
