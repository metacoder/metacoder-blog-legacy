package de.metacoder.blog.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.metacoder.blog.persistence.entities.BlogEntry;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 */
public interface BlogEntryRepository extends PagingAndSortingRepository<BlogEntry, Long>{
	
	@Query("FROM BlogEntry b ORDER BY b.creationDate DESC")
	public List<BlogEntry> getAllOrderedByCreationDate();
	
}
