package de.metacoder.blog.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;



/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 */

@Import(
        library ={
                "context:scripts/jquery/jquery-2.0.2.min.js",
                "context:bootstrap/js/bootstrap.min.js",
                "context:scripts/angularjs/angular.min.js",
                "context:scripts/app.js"
        }, stylesheet = {
            "context:bootstrap/css/bootstrap.min.css",
            "context:bootstrap/css/bootstrap-responsive.min.css"
        })
public class AdminLayout {
	
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	String title;
	
}