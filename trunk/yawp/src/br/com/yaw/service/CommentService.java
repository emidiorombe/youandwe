package br.com.yaw.service;

import java.util.List;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;

/**
 * Serviços de comentários
 * @author rafael
 *
 */
public interface CommentService {
	List<Comment> getCommentsByCompany(long companyId) throws ServiceException;
	
	void addComment(Comment comment) throws ServiceException;

	List<Comment> getCommentsByUser(User user) throws ServiceException;

	List<Comment> getCommentsByNetwork(long companyId, User attribute) throws ServiceException;
}
