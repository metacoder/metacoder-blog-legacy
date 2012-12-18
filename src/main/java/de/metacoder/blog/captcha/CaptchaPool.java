package de.metacoder.blog.captcha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaptchaPool implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaPool.class);
	
	private volatile List<Captcha> captchas;

	private final static int CAPTCHA_POOL_SIZE = 100;
	
	private volatile AtomicLong captchaDeliveryCount = new AtomicLong(0);
	
	@Autowired private CaptchaGenerator captchaGenerator;

	private List<Captcha> generateCaptchas() {
		final List<Captcha> newCaptchas = new ArrayList<Captcha>(CAPTCHA_POOL_SIZE);
		
		for(int i = 0; i < CAPTCHA_POOL_SIZE; i++){
			newCaptchas.add(captchaGenerator.getCaptcha());
		}
		
		return Collections.unmodifiableList(newCaptchas);
	}
	
	public Captcha getCaptcha(){
		captchaDeliveryCount.incrementAndGet();
		return captchas.get(new Random().nextInt(CAPTCHA_POOL_SIZE));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		captchas = generateCaptchas();

		final Thread poolMaintainingThread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {

					if(captchaDeliveryCount.get() > (CAPTCHA_POOL_SIZE * 10)){ 
						LOGGER.debug("generating new captcha pool (hit count was: " + captchaDeliveryCount.get() + ")");
						captchaDeliveryCount.set(0);
						captchas = generateCaptchas();
						LOGGER.debug("generation of " + CAPTCHA_POOL_SIZE + " new captchas done!");
					}
					
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		});

		poolMaintainingThread.setDaemon(true);
		poolMaintainingThread.start();
	}

}
