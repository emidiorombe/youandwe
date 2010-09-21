package br.com.yaw.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestHandlerFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try {
			req.setCharacterEncoding("ISO-8859-1");
			chain.doFilter(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
		
	}

}
