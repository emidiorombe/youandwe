package br.com.promove.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import br.com.promove.dao.HibernateSessionFactory;

public class TransactionFilter implements Filter{
	private static Logger log = Logger.getLogger(TransactionFilter.class);
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		boolean transacaoCompleta = false;
		boolean openSession = false;
		try {
			String uri = ((HttpServletRequest)request).getRequestURI();
			if(!uri.endsWith("js") && !uri.endsWith("css") && !uri.endsWith("html") && !uri.endsWith("png") && !uri.endsWith("gif")) {
				openSession = true;
				HibernateSessionFactory.getSession().beginTransaction();
			}
			chain.doFilter(request, response);
			transacaoCompleta = true;
		}catch(ServletException se) {
			log.error(":::::: Excecao no filtro de transacoes do HBM - ServletException\n" + se.getMessage());
			
		}catch(IOException ioe) {
			log.error(":::::: Excecao no filtro de transacoes do HBM - IOException" + ioe.getMessage());
		}finally {
			if(openSession) {
				Transaction tx = HibernateSessionFactory.getSession().getTransaction(); 
				if(tx.isActive()) {
					if(transacaoCompleta) {
						tx.commit();
						HibernateSessionFactory.getSession().flush();
					}else {
						tx.rollback();
					}
						
				}
				HibernateSessionFactory.closeSession();
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
