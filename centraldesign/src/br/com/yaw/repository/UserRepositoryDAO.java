package br.com.yaw.repository;

import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;

/**
 * Implementation of repository
 * @author Rafael Nunes
 *
 */
public class UserRepositoryDAO extends BaseDAO<User, Integer> implements UserRepository{

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
	
	

}
