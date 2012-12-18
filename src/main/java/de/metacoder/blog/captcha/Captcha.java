package de.metacoder.blog.captcha;

import org.apache.commons.codec.binary.Base64;


public class Captcha {
	
	private String text;
	
	private String base64encodedBytes;
	
	public Captcha(byte[] bytes, String text) {
		this.text = text;
		this.base64encodedBytes = Base64.encodeBase64String(bytes);
	}

	public String getBase64encodedBytes() {
		return base64encodedBytes;
	}

	public String getText() {
		return text;
	}

}
