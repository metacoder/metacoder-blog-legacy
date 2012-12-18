package de.metacoder.blog.components;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;
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
import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.BlogEntryComment;
import de.metacoder.blog.persistence.repositories.BlogEntryRepository;

public class PostCommentForm {

	private static String secret = RandomStringUtils.randomAlphabetic(16); // 16 chars
	private final SecretKey key;
	
	public PostCommentForm(){
		key = new SecretKeySpec(secret.getBytes(), "AES");
	}
	
	
	@Inject
	private BlogEntryRepository blogEntryRepository;
	
	@Component
	private Form commentForm;

	@Component
	private TextArea contentArea;
	
	@Component
	private TextField nameField;
	
	@Component
	private TextField emailField;
	
	@Parameter(required=true)
	private BlogEntry entry;
	
	@Parameter(required=true)
	@Property
	private BlogEntryComment newComment;
	
	public void onSuccessFromCommentForm(){
		entry.getComments().add(newComment);
		blogEntryRepository.save(entry);
	}
	
	
	// captcha stuff
	@Inject
	private CaptchaPool captchaPool;
	
	@Property
	Captcha captcha;
	
	@SetupRender
	public void setupRender() throws Exception {
		captcha = captchaPool.getCaptcha();
		
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		captchaSolveText = cipher.doFinal(captcha.getText().getBytes("UTF-8"));
	}

	@Component
	TextField captchaTextField;
	
	@Property
	private String captchaText;
	
	@Property
	@Persist(PersistenceConstants.CLIENT)
	private byte[] captchaSolveText;
	
	
	public void onValidateFromCommentForm() throws Exception {
		
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, key);

		final String solveTextDecrypted = new String(cipher.doFinal(captchaSolveText), "UTF-8");
		
		if(!solveTextDecrypted.equalsIgnoreCase(captchaText)){
			commentForm.recordError("you must solve the captcha correctly!");
		}
	}
}
