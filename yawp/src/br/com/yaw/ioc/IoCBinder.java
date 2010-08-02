package br.com.yaw.ioc;

import br.com.yaw.repository.CommentDAO;
import br.com.yaw.repository.CommentRepository;
import br.com.yaw.repository.CompanyDAO;
import br.com.yaw.repository.CompanyRepository;
import br.com.yaw.repository.CompanyTagDAO;
import br.com.yaw.repository.CompanyTagRepository;
import br.com.yaw.repository.UserImageDAO;
import br.com.yaw.repository.UserImageRepository;
import br.com.yaw.repository.UserRepository; 
import br.com.yaw.repository.UserRepositoryDAO;
import br.com.yaw.service.CompanyService;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.CompanyServiceImpl;
import br.com.yaw.service.CommentServiceImpl;
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
        //Service Binders
        binder.bind(UserService.class).to(UserServiceImpl.class);
        binder.bind(CompanyService.class).to(CompanyServiceImpl.class);
        binder.bind(CommentService.class).to(CommentServiceImpl.class);
        
        //Repository Binders
        binder.bind(UserRepository.class).to(UserRepositoryDAO.class);
        binder.bind(CompanyRepository.class).to(CompanyDAO.class);
        binder.bind(CommentRepository.class).to(CommentDAO.class);
        binder.bind(CompanyTagRepository.class).to(CompanyTagDAO.class);
        binder.bind(UserImageRepository.class).to(UserImageDAO.class);
        
    }

}

