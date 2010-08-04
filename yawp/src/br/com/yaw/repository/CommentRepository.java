package br.com.yaw.repository;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;

public interface CommentRepository {

	/**
	 * Retorna os compentários de uma empresa paginando o resultado pelo inicio e fim informados
	 * @param companyId
	 * @param initPaginacao
	 * @param fimPaginacao
	 * @return
	 */
	List<Comment> getCommentsByCompany(long companyId, int initPaginacao, int fimPaginacao) throws RepositoryException;
	
	void addComment(Comment comment) throws RepositoryException;

	List<Comment> getCommentsByUser(User user, int i, int j) throws RepositoryException;

	List<Comment> getCommentsByNetwork(long companyId, List<Long> list, int init, int end) throws RepositoryException;

	Collection<Comment> getLatest(int quantidade) throws RepositoryException;

	Comment getById(long idComment) throws RepositoryException;

	void remove(Comment c) throws RepositoryException;
}
