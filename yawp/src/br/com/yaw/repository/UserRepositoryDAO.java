package br.com.yaw.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.utils.StringUtilities;

/**
 * Implementation of repository
 * @author Rafael Nunes
 *
 */
public class UserRepositoryDAO extends BaseDAO<User, Key> implements UserRepository{

	@Override
	public User getUserByLoginAndPassword(String username, String password) throws RepositoryException {
		User us = null;
		try {
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select u from User u where u.contactEmail = :username and u.password = :pass");
			addParamToQuery("username", username);
			addParamToQuery("pass", password);
			us =  (User) executeQueryOneResult(jql.toString(), paramsToQuery);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return us;
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.repository.UserRepository#addUser(br.com.yaw.entity.User)
	 */
	@Override
	public void addUser(User user) throws RepositoryException {
		try {
			beginTransaction();
			save(user);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.repository.UserRepository#getUserById(long)
	 */
	@Override
	public User getUserById(long id) throws RepositoryException {
		User u = null;
		 try{
			 beginTransaction();
			 u = getByPrimaryKey(KeyFactory.createKey("User", id));
			 commitTransaction();
			}catch (RepositoryException re) {
				rollbackTransaction();
				throw re;
			}finally {
				finishTransaction();
			}
			return u;
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.repository.UserRepository#removeUser(br.com.yaw.entity.User)
	 */
	@Override
	public void removeUser(User user2) throws RepositoryException {
		try{
			beginTransaction();
			delete(user2);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}

	@Override
	public User getUserByEmail(String contactEmail) throws RepositoryException {
		User u = null;
		try {
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select u from User u where u.contactEmail = :username");
			addParamToQuery("username", contactEmail);
			u =  (User) executeQueryOneResult(jql.toString(), paramsToQuery);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return u;
	
	}

	@Override
	public void reloadUser(User user) throws RepositoryException {
		try{
			beginTransaction();
			reloadEntity(user);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
	}

	@Override
	public List<Long> getFriends(User user) throws RepositoryException {
		List<Long> list = new ArrayList<Long>();
		try {
			beginTransaction();
			User u = getByPrimaryKey(user.getKey());
			list = u.getContacts();
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return list;
	}

	@Override
	public List<User> getUserListByIds(List<Long> network)
			throws RepositoryException {
		List<User> list = new ArrayList<User>();
		try{
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select u from User u where u in " + StringUtilities.listLongToInClause(network));
			list = executeQuery(jql.toString());
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return list;
	}

	@Override
	public void addContact(User logged, long contactId) throws RepositoryException {
		try {
			beginTransaction();
			User u = getByPrimaryKey(logged.getKey());
			u.getContacts().add(contactId);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}

}
