package br.com.yaw.ioc;

import br.com.yaw.guice.Notifier;
import br.com.yaw.guice.SendMail;
import br.com.yaw.repository.UserRepository;
import br.com.yaw.repository.UserRepositoryDAO;
import br.com.yaw.service.UserService;
import br.com.yaw.service.UserServiceImpl;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Define os binders para IoC
 * @author Rafael Nunes
 *
 */
public class IoCBinder implements Module{
	public void configure(Binder binder) {
        binder.bind(Notifier.class).to(SendMail.class);
        
        //Service Binders
        binder.bind(UserService.class).to(UserServiceImpl.class);
        
        //Repository/DAO Binders
        binder.bind(UserRepository.class).to(UserRepositoryDAO.class);
    }

}
