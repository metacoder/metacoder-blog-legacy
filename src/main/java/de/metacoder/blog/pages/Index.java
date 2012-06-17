package de.metacoder.blog.pages;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;
import de.metacoder.blog.security.BlogRoles;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 *
 * Start page of application blog.
 */
public class Index {

	private static final int PAGE_SIZE = 5;

	@Inject
	private BlogEntryRepository blogEntryRepository;

	@Property
	private BlogEntry blogEntry;

	private int pageId = 0;

	public List<BlogEntry> getBlogEntries() {
		final PageRequest pageRequest = new PageRequest(pageId, PAGE_SIZE, new Sort(
				Direction.DESC, "creationDate"));
		return blogEntryRepository.findAll(pageRequest).getContent();
	}

	@RequiresRoles(BlogRoles.ADMIN)
	@RequiresAuthentication
	public void onActionFromDelete(final Long blogEntryId) {
		blogEntryRepository.delete(blogEntryId);
	}

	public void onActivate(final int pageId) {
		this.pageId = pageId;
	}

	public int getNextPage() {
		return pageId + 1;
	}

	public boolean getHasNextPage() {
		return blogEntryRepository.count() > (pageId + 1) * PAGE_SIZE;
	}

	public boolean getHasPreviousPage() {
		return pageId > 0;
	}

	public int getPreviousPage() {
		return pageId - 1;
	}
}
