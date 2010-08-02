package br.com.yaw.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.apphosting.api.ApiProxy;


/**
 * Filtro para lidar com os períodos de manutenção do GAE
 * @author Rafael Nunes
 *
 */
public class GAEDownTimeFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {

		try {
			chain.doFilter(srequest, sresponse);
		}catch(ApiProxy.CapabilityDisabledException ce) {
			//GAE esta em periodo de manutenção, datastore em modo read-only
			HttpServletRequest request =  (HttpServletRequest) srequest;
			RequestDispatcher rd = request.getRequestDispatcher("/pages/downtime.jsp");
			rd.forward(srequest, sresponse);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	
}
