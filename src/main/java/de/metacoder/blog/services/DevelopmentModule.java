package de.metacoder.blog.services;

import de.metacoder.blog.transferobjects.BlogEntryTO;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;

import java.util.UUID;

/**
 * This module is automatically included as part of the Tapestry IoC Registry if
 * <em>tapestry.execution-mode</em> includes <code>development</code>.
 */
public class DevelopmentModule {

	@Contribute(WebSecurityManager.class)
	public static void resetAdminPassword(final Configuration<Object> configuration, final UserService userService, final BlogEntryService blogEntryService) {
		userService.createUser("dev-admin", "admin");

        /* create 100 blog posts */
        for(int i = 0; i < 100; i++){
            BlogEntryTO blogEntryTo = new BlogEntryTO();
            blogEntryTo.setTitle("Test title " + i );
            blogEntryTo.setAuthor("dev-admin");
            blogEntryTo.setContent("das ist ein test blog entry eintrag (" + i + ") - " + UUID.randomUUID().toString());
            blogEntryService.createNewBlogEntry(blogEntryTo);
        }
	}

	public static void contributeApplicationDefaults(
			final MappedConfiguration<String, Object> configuration) {
		// The factory default is true but during the early stages of an
		// application overriding to false is a good idea. In addition, this is
		// often overridden on the command line as
		// -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, false);

		// The application version number is incorprated into URLs for some
		// assets. Web browsers will cache assets because of the far future
		// expires header. If existing assets are changed, the version number
		// should also change, to force the browser to download new versions.
		final String version = DevelopmentModule.class.getPackage().getImplementationVersion();
		configuration.add(SymbolConstants.APPLICATION_VERSION,
				version != null ? version + "-DEV": "0.0.1-SNAPSHOT-DEV");
	}
}
