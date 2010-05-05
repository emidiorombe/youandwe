package br.com.yaw.repository;

import java.util.List;

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
}
