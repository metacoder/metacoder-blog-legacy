package de.metacoder.blog.pages;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.security.BlogRoles;

@RequiresRoles(BlogRoles.ADMIN)
public class Admin {

	@Property
	private String activeModule;

	@InjectComponent
	private Zone adminModuleZone;

	@Inject
	private Block loginLinkBlock;
	
	@Inject
	private Block loginFormBlock;
	
	public Zone onShowLoginLink() {
		activeModule = "loginLink";
		return adminModuleZone;
	}

	public Zone onShowLoginForm() {
		activeModule = "loginForm";
		return adminModuleZone;
	}
	
	public Object getActiveAdminModule() {
		if("loginLink".equals(activeModule)){
			return loginLinkBlock;
		} else {
			return loginFormBlock;
		}
	}

}
