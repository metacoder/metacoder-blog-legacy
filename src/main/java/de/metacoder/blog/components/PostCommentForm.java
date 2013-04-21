package de.metacoder.blog.components;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.captcha.Captcha;
import de.metacoder.blog.captcha.CaptchaPool;
import de.metacoder.blog.services.BlogEntryService;
import de.metacoder.blog.transferobjects.BlogEntryCommentTO;
import de.metacoder.blog.transferobjects.BlogEntryTO;

public class PostCommentForm {

	
	@Inject
	private BlogEntryService blogEntryService;
	
	@Component
	private Form commentForm;

	@Component
	private TextArea contentArea;
	
	@Component
	private TextField nameField;
	
	@Component
	private TextField emailField;
	
	@Parameter(required=true)
	private BlogEntryTO entry;
	
	@Parameter(required=true)
	@Property
	private BlogEntryCommentTO newComment;
	
	public void onSuccessFromCommentForm(){
		blogEntryService.addComment(entry, newComment);
	}
	
	
	// captcha stuff
	@Inject
	private CaptchaPool captchaPool;
	
	@Property
	Captcha captcha;
	
	@SetupRender
	public void setupRender() throws Exception {
		captcha = captchaPool.getCaptcha();
		captchaSolveText = captcha.getText();
	}

	@Component
	TextField captchaTextField;
	
	@Property
	private String captchaText;
	
	@Property
	@Persist(PersistenceConstants.SESSION)
	private String captchaSolveText;
	
	public void onValidateFromCommentForm() throws Exception {
		
		if(!captchaSolveText.equalsIgnoreCase(captchaText)){
			commentForm.recordError("you must solve the captcha correctly!");
		}
	}
}
