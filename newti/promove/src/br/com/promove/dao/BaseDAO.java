package br.com.promove.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.promove.exception.DAOException;

/**
 * Classe responsável por realizar operações genéricas dos DAOs.
 * @author Rafael Nunes
 *
 */
public abstract class BaseDAO<Id extends Serializable, Entity> implements Serializable {

	private Class<Entity> entity = null;
	protected Map<String, Object> paramsToQuery = new HashMap<String, Object>();
	
	
	private Class<Entity> getEntity(){
		if(entity == null) {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			entity = (Class<Entity>) type.getActualTypeArguments()[1];
		}
		return entity;
	}
	
	/**
	 * Retorna uma entidade baseada em sua chave primaria.
	 */
	public Entity getByPrimaryKey(Id id) throws DAOException{
		return (Entity)HibernateCRUD.getById(getEntity(), id);
	}
	
	/**
	 * Insere ou Atualiza no mecanismo de persistência uma entidade genérica
	 * @param entity
	 * @throws DAOException
	 */
	public void save(Entity entity) throws DAOException {
		HibernateCRUD.save(entity);
	}
	
	/**
	 * Insere no mecanismo de persistencia uma entidade generica que j� possui um ID
	 * @param entity
	 * @throws DAOException
	 */
	public void saveWithId(Entity entity) throws DAOException {
		HibernateCRUD.saveWithId(entity);
	}
	
	/**
	 * Remove do mecanismo de persist�ncia uma entidade gen�rica.
	 * @param entity
	 * @throws DAOException
	 */
	public void delete(Entity entity) throws DAOException{
		HibernateCRUD.delete(entity);
	}
	
	/**
	 * Executa uma query gen�rica sem par�metros.
	 * @param hql
	 * @return
	 * @throws DAOException
	 */
	public List executeQuery(String hql, int init, int end) throws DAOException {
		return HibernateCRUD.executeQuery(hql, init, end);
	}
	
	/**
	 * Executa uma query gen�rica com par�metros.
	 * @param hql
	 * @param params
	 * @return
	 * @throws DAOException
	 */
	public List executeQuery(String hql, Map params, int init, int end) throws DAOException {
		List retorno = HibernateCRUD.executeQuery(hql, params, init, end);
		params.clear();
		return retorno;
	}
	
	/**
	 * Executa uma query gen�rica sem par�metros.
	 * @param hql
	 * @return
	 * @throws DAOException
	 */
	public Object executeQueryOneResult(String hql) throws DAOException {
		return HibernateCRUD.executeQueryOneResult(hql);
	}
	
	/**
	 * Executa uma query genérica com parâmetros.
	 * @param hql
	 * @param params
	 * @return
	 * @throws DAOException
	 */
	public Object executeQueryOneResult(String hql, Map params) throws DAOException {
		Object retorno = HibernateCRUD.executeQueryOneResult(hql, params); 
		params.clear();
		return retorno;
	}
	
	/**
	 * Executa uma query em SQL nativo.
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List executeSQLQuery(String sql) throws DAOException {
		return HibernateCRUD.executeSQLQuery(sql);
	}
	
	/**
	 * Executa um UPDATE/INSERT/DELETE em SQL nativo
	 * @param sql
	 * @throws DAOException
	 */
	public void executeSQLUpdate(String sql) throws DAOException {
		HibernateCRUD.executeSQLUpdate(sql);
	}
	
	/**
	 * Retorna todas as ocorr�ncias de uma determinada entidade
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public List getAll() throws DAOException {
		return HibernateCRUD.getAll(getEntity().getSimpleName());
	}

	public List getAll(String sortField) throws DAOException {
		return HibernateCRUD.getAll(getEntity().getSimpleName(), sortField);
	}
	

	/**
	 * Atualiza um objeto j� vinculado a sess�o do HBM
	 * @param entity
	 * @throws DAOException
	 */
	public void updateObject(Entity entity) throws DAOException {
		HibernateCRUD.updateInSession(entity);
	}
	
	/**
	 * M�todo respons�vel por adicionar par�metros a query HQL.
	 * @param paramName
	 * @param paramValue
	 */
	protected void addParamToQuery(String paramName, Object paramValue) {
		paramsToQuery.put(paramName, paramValue);
	}
	
	/**
	 * Recarrega uma entidade com as informações do banco de dados.
	 * @param entity
	 * @throws DAOException
	 */
	public void reloadEntity(Entity entity) throws DAOException {
		HibernateCRUD.refreshObject(entity);
		
	}
	
	/**
	 * Limpa os objetos da sess�o para n�o ocorrer 'Automatic irty Checking'
	 * @throws DAOException
	 */
	public void limparSessao() throws DAOException {
		HibernateCRUD.clearSession();
	}
	
	/**
	 * Remove o objeto da sess�o de persist�ncia.
	 * @param ent
	 * @throws DAOException
	 */
	public void unloadObject(Entity ent) throws DAOException {
		HibernateCRUD.unloadObject(ent);
	}
	
	/**
	 * Realiza um flush na sess�o de persist�ncia.
	 * @throws DAOException 
	 */
	public void flushSession() throws DAOException{
		HibernateCRUD.flushSession();
	}
	
	/**
	 * Realiza um rebuild na sess�o de persist�ncia.
	 * @throws DAOException 
	 */
	public void rebuildSession() throws DAOException{
		HibernateCRUD.rebuildSession();
	}
	
	public <T>T getGenericObject(Class<T> c, Integer i) throws DAOException{
		return (T)HibernateCRUD.getById(c, i);
	}
}
