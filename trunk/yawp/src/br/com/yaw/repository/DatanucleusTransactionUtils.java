package br.com.yaw.repository;

import javax.persistence.EntityManager;


/**
 * Util operations for transaction handle
 * @author Rafael Nunes
 *
 */
public class DatanucleusTransactionUtils {
	private static final ThreadLocal<EntityManager> tlSession = new ThreadLocal<EntityManager> ();
	
	/**
	 * Get a reference for the current EntityManager
	 * @return
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = tlSession.get();
		if(em == null || !em.isOpen()) {
			em = EMFactory.get().createEntityManager();
		}
        tlSession.set(em);
        return em;
	}
	
	/**
	 * Método responsável por fechar uma conexão com o JPA
	 * @throws HibernateException
	 */
	public static void closeEntityManager(){
		EntityManager s = tlSession.get();
        if (s != null) {
        	try{
        		if(s.isOpen()) {
        			s.close();
        		}
        	}catch (Exception se) {
				//log.warn("::::::Não conseguiu fechar a conexão");
			}
        }
        tlSession.set(null);
    }
	
	public static void closeAllSession(){
		closeEntityManager();
	}
}
