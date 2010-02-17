package br.com.yaw.repository;

import java.util.List;

import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;

/**
 * Persistence operations for users.
 * @author Rafael Nunes
 *
 */
public interface UserRepository {
	
	/**
	 * Get user by his username and password(a.k.a authenticate user)
	 * @param username
	 * @param password
	 * @return
	 */
	User getUserByLoginAndPassword(String username, String password);

	void addUser(User user) throws RepositoryException;
	
	List<User> getAll() throws RepositoryException;

	User getUserById(long id) throws RepositoryException;

	void removeUser(User user2) throws RepositoryException;
}
