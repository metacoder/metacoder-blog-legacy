package de.metacoder.blog.pages;

import org.apache.tapestry5.annotations.Import;

@Import(
        library ={
                "context:scripts/angularjs/angular.min.js",
                "context:scripts/app.js",
                "context:scripts/ui-bootstrap-tpls-0.3.0.min.js"
        }, stylesheet = {
        "context:bootstrap/css/bootstrap.min.css",
        "context:bootstrap/css/bootstrap-responsive.min.css"
})
public class Admin {

}
