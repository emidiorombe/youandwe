package br.com.yaw.service;


import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;
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
	public boolean authenticate(String username, String password) {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see br.com.yaw.service.UserService#addUser(br.com.yaw.entity.User)
	 */
	@Override
	public void addUser(User user) {
		try {
			userRepository = ServiceFactory.getService(UserRepository.class);
			userRepository.addUser(user);
		} catch (RepositoryException e) {
			// TODO Tratamento de erros apropriado dessa vez, por favor.
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
