package br.com.yaw.servlet.filter;

import java.io.IOException;

import javax.persistence.EntityTransaction;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.yaw.entity.User;
import br.com.yaw.repository.DatanucleusTransactionUtils;


/**
 * Filter to Handle the transaction(OpenSessionInView)
 * @author Rafael Nunes
 *
 */
public class TransactionFilter implements javax.servlet.Filter{
	public void destroy() {}

	public void init(FilterConfig arg0) throws ServletException {}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		boolean isTransactionComplete = false;
		try {
			HttpSession session = ((HttpServletRequest)request).getSession(false);
			User user = session != null ? (User)session.getAttribute("loggedUser") : null;
			//DatanucleusTransactionUtils.tlUser.set(user);
			if(DatanucleusTransactionUtils.getEntityManager().getTransaction() == null || !DatanucleusTransactionUtils.getEntityManager().getTransaction().isActive())
				DatanucleusTransactionUtils.getEntityManager().getTransaction().begin();
			chain.doFilter(request, response);
			isTransactionComplete = true;
		}catch(ServletException se) {
			//log.error(":::::: Excecao no filtro de transacoes do HBM - ServletException\n" + se.getMessage());
			se.printStackTrace();
			
		}catch(IOException ioe) {
			//log.error(":::::: Excecao no filtro de transacoes do HBM - IOException" + ioe.getMessage());
			System.out.println(ioe.getMessage());
		}finally {
			EntityTransaction tx = DatanucleusTransactionUtils.getEntityManager().getTransaction(); 
			if(tx.isActive()) {
				if(isTransactionComplete && tx.isActive()) {
					tx.commit();
					//DatanucleusTransactionUtils.getEntityManager().flush();
				}else{
					if(tx.isActive()) {
						tx.rollback();
					}
				}
			}
			DatanucleusTransactionUtils.closeAllSession();
		}
		
	}
}
