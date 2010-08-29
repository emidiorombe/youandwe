package br.com.yaw.service;


import java.util.ArrayList;
import java.util.List;

import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.exception.UsuarioExistenteException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.UserRepository;

/**
 * Implementation of UserService
 * @author Rafael Nunes
 *
 */
public class UserServiceImpl implements UserService {
	UserRepository userRepository;
	
	@Override
	public User authenticate(String username, String password) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			return userRepository.getUserByLoginAndPassword(username, password);
			
		}catch(RepositoryException re) {
			throw new ServiceException("login.invalido", re);
		}
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.service.UserService#addUser(br.com.yaw.entity.User)
	 */
	@Override
	public void addUser(User user) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			
			if(userRepository.getUserByEmail(user.getContactEmail()) != null){
				throw new UsuarioExistenteException("usuario.existente");
			}
			
			userRepository.addUser(user);
		} catch (RepositoryException e) {
			//TODO log this
			throw new ServiceException(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see br.com.yaw.service.UserService#updateUser(br.com.yaw.entity.User)
	 */
	@Override
	public void updateUser(User user) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			
			userRepository.addUser(user);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
		
	}
	
	public User getUserByEmail(String email) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			return userRepository.getUserByEmail(email);
			
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.service.UserService#getAll()
	 */
	@Override
	public List<User> getAll() throws ServiceException {
		List<User> users = null;
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			users = userRepository.getAll();
		} catch (RepositoryException re) {
			//TODO log this
			throw new ServiceException(re);
		}
		return users;
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.service.UserService#getUserById(long)
	 */
	@Override
	public User getUserById(long id) throws ServiceException {
		User user = null;
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			user = userRepository.getUserById(id);
		} catch (RepositoryException re) {
			// TODO: log this
			throw new ServiceException(re);
		}
		return user;
		
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.service.UserService#removeUser(br.com.yaw.entity.User)
	 */
	@Override
	public void removeUser(User userToDel) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			userRepository.removeUser(userToDel);
		} catch (RepositoryException e) {
			//TODO log this
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void reloadUser(User user) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			userRepository.reloadUser(user);
		} catch (RepositoryException e) {
			//TODO log this
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> getUserNetwork(User user) throws ServiceException {
		List<User> network = new ArrayList<User>();
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			List<Long> friends = userRepository.getFriends(user);
			if(friends.size() > 0)
				network = userRepository.getUserListByIds(friends);
			return network;
		} catch (RepositoryException re) {
			//TODO log this
			throw new ServiceException(re);
		}
		
	}

	@Override
	public void addContact(User logged, long contactId) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			logged.addContact(contactId);
			
			userRepository.addUser(logged);
		}catch(RepositoryException re) {
			//TODO log this
			throw new ServiceException(re);
		}
		
	}

	@Override
	public List<User> getUserByName(String name) throws ServiceException {
		List<User> lista = new ArrayList<User>();
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			userRepository.getFriends(null);
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
		return lista;
	}

	@Override
	public void removeContact(User logged, long userId) throws ServiceException {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			logged.removeContact(userId);
			
			userRepository.addUser(logged);
		}catch(RepositoryException re) {
			//TODO log this
			throw new ServiceException(re);
		}
		
	}
	
	
	
}
