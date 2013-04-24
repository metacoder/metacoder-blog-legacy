package de.metacoder.blog.jax;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.io.FileUtils;

public class NFSLoggingFilter implements Filter {

	private final File logFile = new File("/mnt/jax2nfs/tracelog/tracelog.log");
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2) throws IOException, ServletException {
		arg2.doFilter(req, resp);
	}
	
	
	private synchronized void trackRequest(){
		try {
			FileUtils.writeStringToFile(logFile, "traced request");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Starting nfs logging filter");
	}

}
