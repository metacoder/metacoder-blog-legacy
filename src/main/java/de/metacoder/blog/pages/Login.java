package de.metacoder.blog.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 */
public class Login {

	@Inject
	private SecurityService securityService;

	public String getUsername() {
		return securityService.getSubject().getPrincipal().toString();
	}
}
