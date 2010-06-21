package br.com.yaw.service;

import java.util.List;

import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;

/**
 * Interface that handle the services for users.
 * @author Rafael Nunes
 *
 */
public interface UserService {
	
	User authenticate(String username, String password) throws ServiceException;
	
	void addUser(User user) throws ServiceException;
	
	List<User> getAll() throws ServiceException;

	User getUserById(long id) throws ServiceException;

	void removeUser(User user2) throws ServiceException;

	void reloadUser(User logged) throws ServiceException;

	List<User> getUserNetwork(User user)throws ServiceException;

	void addContact(User logged, long contactId)throws ServiceException;
	
	public User getUserByEmail(String email) throws ServiceException;

	void updateUser(User user) throws ServiceException;

}
