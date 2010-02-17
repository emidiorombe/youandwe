package br.com.yaw.service;

import java.util.List;

import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;

/**
 * Interface that handle the services for users.
 * @author Rafael Nunes
 *
 */
public interface UserService {
	
	boolean authenticate(String username, String password);
	
	void addUser(User user) throws ServiceException;
	
	List<User> getAll() throws ServiceException;

	User getUserById(long id) throws ServiceException;

	void removeUser(User user2) throws ServiceException;

}
