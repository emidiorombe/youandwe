package br.com.yaw.repository;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.yaw.exception.RepositoryException;

public class DatanucleusCRUDUtils {
	/**
	 * Exceutes a query with a given jql
	 * @param hql
	 * @return
	 * @throws RepositoryException
	 */
	public static List executeQuery(String jql) throws RepositoryException {
		return executeQuery(jql, null);
	}
	
	/**
	 * Exceutes a query with a given jql and its parameters
	 * @param hql
	 * @param params
	 * @return
	 */
	public static List executeQuery(String jql, Map params) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			Query query = em.createQuery(jql);
			if(params != null && params.size() > 0) {
				Iterator it = params.keySet().iterator();
				while(it.hasNext()) {
					String key = (String) it.next();
					Object value = params.get(key);
					query.setParameter(key, value);
				}
			}
			List retorno  = query.getResultList();
			return retorno;
		}catch (Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}finally {
		}
	}
	
	/**
	 * Execute a query with a given jql returning just one result.
	 * @param hql
	 * @return
	 * @throws RepositoryException 
	 */
	public static Object executeQueryOneResult(String hql) throws RepositoryException {
		return executeQueryOneResult(hql, null);
	}
	
	/**
	 * Execute a query with a given jql and its parameters, returning just one result.
	 * @param hql
	 * @param params
	 * @return
	 */
	public static Object executeQueryOneResult(String jql, Map params) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			Query query = em.createQuery(jql);
			if(params != null && params.size() > 0) {
				Iterator it = params.keySet().iterator();
				while(it.hasNext()) {
					String key = (String) it.next();
					Object value = params.get(key);
					query.setParameter(key, value);
				}
			}
			Object retorno  = query.getSingleResult();
			return retorno;
		}catch (Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}finally {
		}
	}
	
	/**
	 * Insert or update an entity
	 * @param obj
	 * @throws RepositoryException
	 */
	public static void save(Object obj) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			em.merge(obj);
		}catch(Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}finally {
		}
	}
	
	/**
	 * Returns an object by its ID/PK
	 * @param klass
	 * @param id
	 * @return
	 * @throws RepositoryException
	 */
	public static Object getById(Class klass, Serializable id) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			Object obj = em.find(klass, id);
			return obj;
		}catch(Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}finally {
		}
	}
	
	/**
	 * Removes an entity
	 * @param obj
	 * @throws RepositoryException
	 */
	public static void delete(Object obj) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			em.remove(obj);
		}catch(Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}
	}
	
	/**
	 * Returns all registers of an given entity
	 * @param obj
	 * @return
	 * @throws RepositoryException
	 */
	public static List getAll(String entityName) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			String hql = "select x from " + entityName + " x";
			Query query = em.createQuery(hql);
			List retorno = query.getResultList();
			return retorno;
		}catch(Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	/**
	 * Refresh an object in the datanucleus/jpa context
	 * @param obj
	 * @throws RepositoryException
	 */
	public static void refreshObject(Object obj) throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			em.refresh(obj);
		}catch(Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}
	}

	/**
	 * Cleans the datanucleus/jpa context
	 * @throws RepositoryException 
	 */
	public static void clearSession() throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			em.clear();
		}catch(Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}
		
	}
	
	/**
	 * Flushs the datanucleus/jpa context
	 * @throws RepositoryException 
	 */
	public static void flushSession() throws RepositoryException {
		EntityManager em = DatanucleusTransactionUtils.getEntityManager();
		try {
			em.clear();
		}catch(Exception he) {
			he.printStackTrace();
			throw new RepositoryException(he);
		}
		
	}
}
