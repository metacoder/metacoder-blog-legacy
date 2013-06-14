package de.metacoder.blog.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;



/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:git@felixbecker.name>git@felixbecker.name</a>
 */

@Import(
        library ={
            "context:scripts/angularjs/angular.min.js",
            "context:scripts/app.js",
            "context:scripts/ui-bootstrap-tpls-0.3.0.min.js"
        }, stylesheet = {
            "context:bootstrap/css/bootstrap.min.css",
            "context:bootstrap/css/bootstrap-responsive.min.css"
        })
public class AdminLayout {
	
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	String title;
	
}