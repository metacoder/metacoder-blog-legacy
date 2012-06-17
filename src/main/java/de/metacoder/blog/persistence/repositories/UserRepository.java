package de.metacoder.blog.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.metacoder.blog.persistence.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, String>{
}
