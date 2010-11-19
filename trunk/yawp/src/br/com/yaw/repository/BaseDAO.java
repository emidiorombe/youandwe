package br.com.yaw.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.yaw.exception.RepositoryException;

/**
 * Base for every dao to centralize common operations(CRUD)
 * @author Rafael Nunes
 *
 */
public class BaseDAO<Entity, Id extends Serializable> {
	
	private Class<Entity> entity = null;
	protected Map<String, Object> paramsToQuery = new HashMap<String, Object>();
	
	private Class<Entity> getEntity(){
		if(entity == null) {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			entity = (Class<Entity>) type.getActualTypeArguments()[0];
		}
		
		return entity;
	}
	
	/**
	 * Returns the Entity by its primary key
	 */
	public Entity getByPrimaryKey(Id id) throws RepositoryException{
		return (Entity)DatanucleusCRUDUtils.getById(getEntity(), id);
	}
	
	/**
	 * Returns the Generic Entity by its primary key
	 */
	public Object getGenericEntityByPrimaryKey(Class ent, Serializable key) throws RepositoryException{
		return DatanucleusCRUDUtils.getById(ent, key);
	}
	
	/**
	 * Insert or update an entity
	 * @param entity
	 * @throws RepositoryException
	 */
	public void save(Entity entity) throws RepositoryException {
		DatanucleusCRUDUtils.save(entity);
	}
	
	/**
	 * Insert or update an generic entity
	 * @param entity
	 * @throws RepositoryException
	 */
	public void saveGeneric(Object entity) throws RepositoryException {
		DatanucleusCRUDUtils.saveGeneric(entity);
	}
	
	/**
	 * Commit actual transaction
	 * @param entity
	 * @throws RepositoryException
	 */
	public void commitTransaction() throws RepositoryException {
		DatanucleusCRUDUtils.commitTransaction();	
	}
	
	/**
	 * Commit actual transaction
	 * @param entity
	 * @throws RepositoryException
	 */
	public void restartTransaction() throws RepositoryException {
		DatanucleusCRUDUtils.restartTransaction();	
	}
	
	
	/**
	 * Removes an entity
	 * @param entity
	 * @throws RepositoryException
	 */
	public void delete(Entity entity) throws RepositoryException{
		DatanucleusCRUDUtils.delete(entity);
	}
	
	/**
	 * Executes an generic query
	 * @param hql
	 * @return
	 * @throws RepositoryException
	 */
	public List executeQuery(String hql) throws RepositoryException {
		return DatanucleusCRUDUtils.executeQuery(hql);
	}
	
	/**
	 * Executes an generic query
	 * @param hql
	 * @return
	 * @throws RepositoryException
	 */
	public List executeQuery(String hql, int init, int end) throws RepositoryException {
		return DatanucleusCRUDUtils.executeQuery(hql, null, init, end);
	}
	
	/**
	 * Executes an generic update query
	 * @param hql
	 * @return
	 * @throws RepositoryException
	 */
	public void executeUpdateQuery(String hql) throws RepositoryException {
		DatanucleusCRUDUtils.executeUpdateQuery(hql);
	}
	
	/**
	 * Executes an generic query
	 * @param hql
	 * @return
	 * @throws RepositoryException
	 */
	public List executeQuery(String hql, Map params, int init, int end) throws RepositoryException {
		return DatanucleusCRUDUtils.executeQuery(hql, params, init, end);
	}
	
	/**
	 * Executes a generic query with parameters
	 * @param hql
	 * @param params
	 * @return
	 * @throws RepositoryException
	 */
	public List executeQuery(String hql, Map params) throws RepositoryException {
		List retorno = DatanucleusCRUDUtils.executeQuery(hql, params); 
		params.clear();
		return retorno;
	}
	
	/**
	 * Executes a generic update query with parameters
	 * @param hql
	 * @param params
	 * @return
	 * @throws RepositoryException
	 */
	public void executeUpdateQuery(String hql, Map params) throws RepositoryException {
		DatanucleusCRUDUtils.executeUpdateQuery(hql, params); 
		params.clear();
	}
	
	/**
	 * Executes an generic query returning unique result
	 * @param hql
	 * @return
	 * @throws RepositoryException
	 */
	public Object executeQueryOneResult(String hql) throws RepositoryException {
		return DatanucleusCRUDUtils.executeQueryOneResult(hql);
	}
	
	/**
	 *  Executes an generic query with parameters returning unique result
	 * @param hql
	 * @param params
	 * @return
	 * @throws RepositoryException
	 */
	public Object executeQueryOneResult(String hql, Map params) throws RepositoryException {
		Object retorno = DatanucleusCRUDUtils.executeQueryOneResult(hql, params); 
		params.clear();
		return retorno;
	}
	
	/**
	 * Return all occurrences of an specific entity
	 * @param entity
	 * @return
	 * @throws RepositoryException
	 */
	public List getAll() throws RepositoryException {
		return DatanucleusCRUDUtils.getAll(getEntity().getSimpleName());
	}
	
	/**
	 * Adding a parameter to jql
	 * @param paramName
	 * @param paramValue
	 */
	protected void addParamToQuery(String paramName, Object paramValue) {
		paramsToQuery.put(paramName, paramValue);
	}
	
	/**
	 * Reload/Refresh the entity with the last information of persistence mechanism
	 * @param entity
	 * @throws RepositoryException
	 */
	public void reloadEntity(Entity entity) throws RepositoryException {
		DatanucleusCRUDUtils.refreshObject(entity);
		
	}
	
	/**
	 * Clean the jpa session
	 * @throws RepositoryException
	 */
	public void limparSessao() throws RepositoryException {
		DatanucleusCRUDUtils.clearSession();
	}
	
	
	/**
	 * Flush the jpa session
	 * @throws RepositoryException 
	 */
	public void flushSession() throws RepositoryException{
		DatanucleusCRUDUtils.flushSession();
	}
	
	public void beginTransaction() throws RepositoryException{
		DatanucleusCRUDUtils.beginTransaction();
	}
	
	public void rollbackTransaction() throws RepositoryException{
		DatanucleusCRUDUtils.rollBackTransaction();
	}

	public void finishTransaction() throws RepositoryException{
		DatanucleusTransactionUtils.closeAllSession();
	}


}