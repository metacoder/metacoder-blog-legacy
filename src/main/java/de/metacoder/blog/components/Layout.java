package de.metacoder.blog.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.tynamo.security.services.SecurityService;

/**
 * Layout component for pages of application blog.
 */
@Import(
		library =	{
					"context:scripts/sh/shCore.js",
					"context:scripts/sh/shBrushPlain.js",
					"context:scripts/sh/shBrushBash.js",
					"context:scripts/sh/shBrushCss.js",
					"context:scripts/sh/shBrushJava.js",
					"context:scripts/sh/shBrushSql.js",
					"context:scripts/sh/shBrushXml.js",
					"context:scripts/sh/shBrushDiff.js",
					"context:scripts/sh/shBrushJScript.js",
					"context:scripts/sh/shBrushScala.js",
					"context:scripts/sh/shBrushCpp.js"
					},
		stylesheet = {
					"context:css/nonzero.css",
					"context:css/blog.css",
					"context:css/sh/shCoreEclipse.css"
					})
public class Layout {

	@Inject
	private SecurityService securityService;
	
	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;
	
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	private String pageName;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Block sidebar;

	@Inject
	private ComponentResources resources;

	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String appVersion;

	public String getClassForPageName() {
		return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
	}

	public String[] getPageNames() {
		return new String[] { "Index", "About", "Contact", "Admin" };
	}
	
	public String getUsername(){
		return securityService.getSubject().getPrincipal().toString();
	}
}
