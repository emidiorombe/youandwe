package br.com.yaw.repository;

import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;

/**
 * Implementation of repository
 * @author Rafael Nunes
 *
 */
public class UserRepositoryDAO extends BaseDAO<User, Long> implements UserRepository{

	@Override
	public User getUserByLoginAndPassword(String username, String password) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.repository.UserRepository#addUser(br.com.yaw.entity.User)
	 */
	@Override
	public void addUser(User user) throws RepositoryException {
		save(user);
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.repository.UserRepository#getUserById(long)
	 */
	@Override
	public User getUserById(long id) throws RepositoryException {
		return getByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.repository.UserRepository#removeUser(br.com.yaw.entity.User)
	 */
	@Override
	public void removeUser(User user2) throws RepositoryException {
		delete(user2);
		
	}
	
	

}
