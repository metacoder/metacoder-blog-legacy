package de.metacoder.blog.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.User;
import de.metacoder.blog.persistence.repositories.UserRepository;

public class Profile {
	
	@Inject UserRepository userRepository;

	@Property User user;
	
	public Object onActivate(String username){
		user = userRepository.findOne(username);
		
		if(user == null){
			return Index.class;
		}
		
		return null;
	}
}

