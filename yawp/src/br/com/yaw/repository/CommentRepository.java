package br.com.yaw.repository;

import java.util.List;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;

public interface CommentRepository {

	/**
	 * Retorna os compentários de uma empresa paginando o resultado pelo inicio e fim informados
	 * @param companyId
	 * @param initPaginacao
	 * @param fimPaginacao
	 * @return
	 */
	List<Comment> getCommentsByCompany(Company company, int initPaginacao, int fimPaginacao);
}
