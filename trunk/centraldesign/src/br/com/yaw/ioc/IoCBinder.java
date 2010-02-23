package br.com.yaw.ioc;


import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Define os binders para IoC
 * @author Rafael Nunes
 *
 */
public class IoCBinder implements Module{
	public void configure(Binder binder) {
        //Service Binders
        //binder.bind(UserService.class).to(UserServiceImpl.class);
        
        //Repository Binders
        //binder.bind(UserRepository.class).to(UserRepositoryDAO.class);
    }

}
