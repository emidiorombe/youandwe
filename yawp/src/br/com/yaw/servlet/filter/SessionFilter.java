package br.com.yaw.servlet.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter{
	private ArrayList<String> urlList;
	private int totalURLS;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;  
		 HttpServletResponse response = (HttpServletResponse) resp;
		 String uri = request.getRequestURI();
		 boolean urlAutenticada = urlList.contains(uri); 
		 if (urlAutenticada) {
			  HttpSession session = request.getSession(false);
			 if (session == null || session.getAttribute("loggedUser") == null) {  
				 response.sendRedirect(request.getContextPath() + "/user/login");
			 }else {
				 chain.doFilter(request, response);
			 }
		 }else {
			 chain.doFilter(request, response);
		 }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("urls_no_session");  
		String tokens[] = urls.split(",");  
		urlList = new ArrayList<String>();  
		for(String url : tokens){  
			urlList.add(url);  
		}
		totalURLS = urlList.size();
	}

}
