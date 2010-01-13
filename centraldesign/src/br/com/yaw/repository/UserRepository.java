package br.com.yaw.repository;

import br.com.yaw.entity.User;

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
}
