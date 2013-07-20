package de.metacoder.blog;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AprilFirstFilter implements Filter {
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		if(isAprilFirst() && false){
			arg1.getOutputStream().println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
											"<html><head><title>Metacoder.de</title></head><body style=\"background-color:#393058; background-image:url('http://www.afriserver.de/deadjim/deadjim.png'); background-repeat:no-repeat; background-position:center; background-attachment:fixed;\"></body></html>");
		} else {
			arg2.doFilter(arg0, arg1);
		}
	}

	private boolean isAprilFirst() {
		// TODO IMPLEMENT
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
