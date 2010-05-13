package br.com.yaw.service;

import java.util.ArrayList;
import java.util.List;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.CommentRepository;
import br.com.yaw.repository.UserRepository;

public class CommentServiceImpl implements CommentService {
	private CommentRepository repository;
	private UserRepository userRepository;
	
	@Override
	public List<Comment> getCommentsByCompany(long companyId) throws ServiceException {
		List<Comment> lista = new ArrayList<Comment>();
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			lista = repository.getCommentsByCompany(companyId, 0, 1000);
		}catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.comentarios",re);
		}
		return lista;
	}
	
	public void addComment(Comment comment) throws ServiceException{
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			repository.addComment(comment);
		}catch (RepositoryException re) {
			throw new ServiceException("impossivel.incluir.comentario",re);
		}
	}

	@Override
	public List<Comment> getCommentsByUser(User user) throws ServiceException {
		List<Comment> lista = new ArrayList<Comment>();
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			lista = repository.getCommentsByUser(user, 0, 1000);
		}catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.comentarios",re);
		}
		return lista;
	}

	@Override
	public List<Comment> getCommentsByNetwork(long companyId, User user)
			throws ServiceException {
		List<Comment> lista = new ArrayList<Comment>();
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			userRepository = ServiceFactory.getService(UserRepository.class);
			List<Long> network = userRepository.getFriends(user); 
			if(network.size() > 0)
				lista = repository.getCommentsByNetwork(companyId, network, 0, 1000);
		}catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.comentarios",re);
		}
		return lista;
	}

}
