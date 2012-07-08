package de.metacoder.blog.mixins;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "context:scripts/tiny_mce/tiny_mce.js", "context:scripts/tiny_mce/tiny_mce_mixin_init.js"  })
public class TinyMCE {

	@InjectContainer
	private TextArea textArea;

	@Environmental
	private JavaScriptSupport javaScriptSupport;
	
	@BeginRender
	public void enableTinyMCE() {
		javaScriptSupport.addScript("enableMCEAsMixin('%s');", textArea.getClientId());
	}
}
