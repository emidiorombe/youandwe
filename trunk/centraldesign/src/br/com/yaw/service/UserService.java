package br.com.yaw.service;

/**
 * Interface that handle the services for users.
 * @author Rafael Nunes
 *
 */
public interface UserService {
	
	boolean authenticate(String username, String password);
	
	
}
