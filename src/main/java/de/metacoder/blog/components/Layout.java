package de.metacoder.blog.components;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.metacoder.blog.security.BlogRoles;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.springframework.security.core.context.SecurityContextHolder;

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

	public Set<String> getPageNames() {
		Set<String> pages = new HashSet<String>(Arrays.asList(new String[] { "Index", "About", "Contact" }));

        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(BlogRoles.ADMIN)){
            pages.add("Admin");
        }

		return pages;
	}
	
}
