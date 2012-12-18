package de.metacoder.blog.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
class CaptchaGenerator {

	private static final Font CAPTCHA_FONT = new Font("Verdana", Font.BOLD, 18);
	private static final Random RANDOM = new Random();
	private static final int CAPTCHA_CHAR_X_PX = 30;
	private static final int CAPTCHA_CHAR_Y_PX = 35;
	private static final String POSSIBLE_CHARS = "23469acdefghkmnpqrtuvwxyz".toUpperCase(); 
	private static final int CAPTCHA_LENGTH = 5;
	private static final int CAPTCHA_WIDTH_PX = CAPTCHA_CHAR_X_PX*CAPTCHA_LENGTH;
	
	private Color[] possibleColors = new Color[]{ 
			Color.RED, Color.BLUE, new Color(0,128,0), Color.GRAY, Color.MAGENTA, Color.BLACK
	};
	
	private String getCaptchaString(){
		
		final StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < CAPTCHA_LENGTH; i++){
			sb.append(POSSIBLE_CHARS.charAt(RANDOM.nextInt(POSSIBLE_CHARS.length())));
		}
		
		return sb.toString();
	}
	
	private Color getRandomColor(){
		return possibleColors[RANDOM.nextInt(possibleColors.length)];
	}
	
	private BufferedImage generateImageForChar(char c){

		final BufferedImage bufferedImage = new BufferedImage(CAPTCHA_CHAR_X_PX, CAPTCHA_CHAR_Y_PX, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = bufferedImage.createGraphics();
		
		g2d.setFont(CAPTCHA_FONT);
		g2d.setPaint(getRandomColor());

		final AffineTransform at = new AffineTransform();
		
		at.setToRotation((RANDOM.nextInt(5)/ 10.0 ) -0.2);
		g2d.setTransform(at);
		g2d.drawString(c+"", 10, g2d.getFontMetrics().getHeight());

		g2d.dispose();
		
		return bufferedImage;
	}
	
	public Captcha getCaptcha(){
		
		
		final BufferedImage bufferedImage = new BufferedImage(CAPTCHA_WIDTH_PX, CAPTCHA_CHAR_Y_PX, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = bufferedImage.createGraphics();
		
		// set background white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, CAPTCHA_WIDTH_PX, CAPTCHA_CHAR_Y_PX);
		
		// draw circles and rectangles
		for (int i = 0; i< CAPTCHA_LENGTH; i++) {
			g2d.setColor(getRandomColor());

			if(RANDOM.nextInt(3) > 1){
				g2d.drawRect(RANDOM.nextInt(CAPTCHA_WIDTH_PX), RANDOM.nextInt(5)+2, 25, 25);
			} else {
				g2d.drawOval(RANDOM.nextInt(CAPTCHA_WIDTH_PX), RANDOM.nextInt(5)+2, 25, 25);
			}
		}
		
		// draw dots
		for(int x = 0; x < bufferedImage.getWidth(); x++){
			for(int y = 0; y < bufferedImage.getHeight(); y++){
				if(RANDOM.nextInt(50) == 0){
					g2d.setColor(getRandomColor());
					g2d.drawRect(x, y, 0, 0);
					
				}
			}
		}
		
		// draw captcha text 
		
		final String captchaText = getCaptchaString();
		char[] chars = captchaText.toCharArray();
		
		for(int i = 0; i < chars.length; i++){
			char c = chars[i];
			g2d.drawImage(generateImageForChar(c), CAPTCHA_CHAR_X_PX*i, 0, null);
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, CAPTCHA_WIDTH_PX-1, CAPTCHA_CHAR_Y_PX-1);
		
		g2d.dispose();
		
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", output);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new Captcha(output.toByteArray(), captchaText);
	}
}
