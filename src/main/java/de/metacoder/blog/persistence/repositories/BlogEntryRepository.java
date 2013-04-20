package de.metacoder.blog.persistence.repositories;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.metacoder.blog.persistence.entities.BlogEntry;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 */
@Transactional
public interface BlogEntryRepository extends Repository<BlogEntry, Long>{
	
	@Cacheable(value="blogEntryCache", key="'allOrderedByDate'")
	@Query("FROM BlogEntry b ORDER BY b.creationDate DESC")
	public List<BlogEntry> getAllOrderedByCreationDate();
	
	@Cacheable(value="blogEntryCache")
	Iterable<BlogEntry> findAll(Sort sort);
	
	@Cacheable(value="blogEntryCache")
	Page<BlogEntry> findAll(Pageable pageable);
	
	@Cacheable(value="blogEntryCache")
	BlogEntry findOne(Long id);

	@Cacheable(value="blogEntryCache", key="'findAll'")
	Iterable<BlogEntry> findAll();
	
	@Cacheable(value="blogEntryCache")
	long count();
	
	@CacheEvict(value = "blogEntryCache", allEntries = true)
	BlogEntry save(BlogEntry entity);
	
	@CacheEvict(value = "blogEntryCache", allEntries = true)
	void delete(Long id);

}
