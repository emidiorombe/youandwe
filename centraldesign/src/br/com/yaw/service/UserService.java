package br.com.yaw.service;

import br.com.yaw.entity.User;

/**
 * Interface that handle the services for users.
 * @author Rafael Nunes
 *
 */
public interface UserService {
	
	boolean authenticate(String username, String password);
	
	void addUser(User user);
	
	
}
