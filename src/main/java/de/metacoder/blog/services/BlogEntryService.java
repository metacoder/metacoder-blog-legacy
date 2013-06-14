package de.metacoder.blog.services;

import de.metacoder.blog.Caches;
import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.security.DynamicSaltedUserDetails;
import de.metacoder.blog.transferobjects.BlogEntryCommentTO;
import de.metacoder.blog.transferobjects.BlogEntryTO;
import de.metacoder.blog.transferobjects.CreateOrUpdateBlogEntryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Service
@Cacheable(Caches.BLOG_ENTRY_TOS)
@Controller
@RequestMapping("/entries")
public class BlogEntryService {

	@Autowired
	private BlogEntryRepository blogEntryRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Cacheable(value=Caches.BLOG_ENTRY_TOS, key="'allBlogEntriesOrderedByCreationDate'")
	public List<BlogEntryTO> getAllOrderedByCreationDate() {
		final List<BlogEntryTO> listToReturn = new ArrayList<>();
		
		for(BlogEntry entry : blogEntryRepository.getAllOrderedByCreationDate()){
			listToReturn.add(new BlogEntryTO(entry));
		}
		
		return listToReturn;
	}
	
	@Cacheable(value=Caches.BLOG_ENTRY_TOS, key="'page'.concat(#pageNumber).concat('size').concat(#pageSize)")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public List<BlogEntryTO> getAllBlogEntriesOrderedByDate(int pageSize, int pageNumber){
        final PageRequest pageRequest = new PageRequest(pageNumber, pageSize, new Sort(Direction.DESC, "creationDate"));

        final List<BlogEntryTO> listToReturn = new ArrayList<>();

        for(BlogEntry entry : blogEntryRepository.findAll(pageRequest).getContent()){
            listToReturn.add(new BlogEntryTO(entry));
        }

        return listToReturn;
	}

	@Cacheable(value=Caches.BLOG_ENTRY_TOS, key="'numRows'")
    @RequestMapping(value="/numRows", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public long numRows() {
        return blogEntryRepository.count();
	}

	@Cacheable(value=Caches.BLOG_ENTRY_TOS, key="'blogEntry'.concat(#entryId)")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
	public BlogEntryTO getEntry(@PathVariable("id") Long entryId) {
		return new BlogEntryTO(blogEntryRepository.findOne(entryId));
	}

	@Transactional
	@CacheEvict(value={Caches.BLOG_ENTRY_TOS, Caches.GLOBAL_PAGE_CACHE}, allEntries=true)
	public void addComment(BlogEntryTO entry, BlogEntryCommentTO newComment) {
		BlogEntry blogEntry = blogEntryRepository.findOne(entry.getId());
		blogEntry.getComments().add(newComment.toBlogEntryComment());
		blogEntryRepository.save(blogEntry);
	}

	@Transactional
	@CacheEvict(value={Caches.BLOG_ENTRY_TOS, Caches.GLOBAL_PAGE_CACHE}, allEntries=true)
	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void createOrUpdateBlogEntry(@RequestBody CreateOrUpdateBlogEntryTO createNewBlogEntryTO) {

        final BlogEntry newEntry;
        if(createNewBlogEntryTO.getId() != null){
            newEntry = blogEntryRepository.findOne(createNewBlogEntryTO.getId());
            newEntry.setId(createNewBlogEntryTO.getId());
        } else {
            newEntry = new BlogEntry();
            newEntry.setAuthor(userRepository.findOne(((DynamicSaltedUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())); // TODO more generic?
        }

        newEntry.setContent(createNewBlogEntryTO.getContent());
        newEntry.setTitle(createNewBlogEntryTO.getTitle());

        blogEntryRepository.save(newEntry);
	}

	@Transactional
	@CacheEvict(value={Caches.BLOG_ENTRY_TOS, Caches.GLOBAL_PAGE_CACHE}, allEntries=true)
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
	public void deleteEntry(@PathVariable Long id) {
        blogEntryRepository.delete(id);
	}
	
}
