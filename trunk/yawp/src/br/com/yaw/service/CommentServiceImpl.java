package br.com.yaw.service;

import java.util.ArrayList;
import java.util.Collection;
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
			network.add(user.getKey().getId());
			
			lista = repository.getCommentsByNetwork(companyId, network, 0, 1000);
		}catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.comentarios",re);
		}
		return lista;
	}

	@Override
	public Collection<Comment> getLatestComments(int quantidade) throws ServiceException {
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			Collection<Comment> lat = repository.getLatest(quantidade);
			return lat;
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
	}

	@Override
	public Comment getCommentById(long idComment) throws ServiceException {
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			return repository.getById(idComment);
		}catch(RepositoryException re) {
			throw new ServiceException("impossivel.buscar.comentarios",re);
		}
	}

	@Override
	public void remove(Comment c) throws ServiceException {
		try {
			repository = ServiceFactory.getService(CommentRepository.class);
			repository.remove(c);
		}catch(RepositoryException re) {
			throw new ServiceException("impossivel.remover.comentarios",re);
		}
	}

}
