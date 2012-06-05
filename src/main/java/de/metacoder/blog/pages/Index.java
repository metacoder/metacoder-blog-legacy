package de.metacoder.blog.pages;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

/**
 * Start page of application blog.
 */
public class Index
{
	
	private static final int PAGE_SIZE = 10;
	
	@Inject
	private BlogEntryRepository blogEntryRepository;
	
    @Persist
    @Property
    private int clickCount;

    @Inject
    private AlertManager alertManager;

    @Property
    private BlogEntry blogEntry;
    
    private int pageId = 0;
    
    public List<BlogEntry> getBlogEntries(){
    	PageRequest pageRequest = new PageRequest(pageId, PAGE_SIZE, new Sort(Direction.DESC, "creationDate"));
    	return blogEntryRepository.findAll(pageRequest).getContent();
    }
    
    public Date getCurrentTime()
    {
        return new Date();
    }

    public void onActionFromDelete(Long blogEntryId) {
    	blogEntryRepository.delete(blogEntryId);
    }

    public void onActivate(int pageId){
    	this.pageId = pageId;
    }

    public int getNextPage(){
    	return pageId + 1;
    }

    public boolean getHasNextPage(){
    	return blogEntryRepository.count() > (pageId+1)*PAGE_SIZE;
    }

    public boolean getHasPreviousPage(){
    	return pageId > 0;
    }

    public int getPreviousPage(){
    	return pageId - 1;
    }
}
