package de.metacoder.blog.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

public class Login
{
	@Inject
	private SecurityService securityService;
	
	public String getUsername(){
		return securityService.getSubject().getPrincipal().toString();
	}
}
