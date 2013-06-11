package de.metacoder.blog.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.metacoder.blog.persistence.entities.UserBO;

public interface UserRepository extends PagingAndSortingRepository<UserBO, String>{
}
