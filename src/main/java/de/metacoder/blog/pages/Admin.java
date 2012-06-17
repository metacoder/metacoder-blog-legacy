package de.metacoder.blog.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.security.BlogRoles;


/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 */
@RequiresRoles(BlogRoles.ADMIN)
@RequiresAuthentication
public class Admin {

	@Property
	@Persist
	protected Object activeAdminModule;

	@InjectComponent
	protected Zone adminModuleZone;

	@Inject
	protected Block editEntryBlock;

	@Inject
	protected Block userManagementBlock;

	@Inject
	protected Block entryManagementBlock;
	
	public Zone onShowEditEntryPanel(){
		activeAdminModule = editEntryBlock;
		return adminModuleZone;
	}

	public Zone onShowUserManagementPanel(){
		activeAdminModule = userManagementBlock;
		return adminModuleZone;
	}
	
	public Zone onShowEntryManagementPanel(){
		activeAdminModule = entryManagementBlock;
		return adminModuleZone;
	}
	
}
