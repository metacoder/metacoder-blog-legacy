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
                "context:scripts/angularjs/angular.min.js"
        })
public class AdminLayout {
	
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	String title;
	
}
