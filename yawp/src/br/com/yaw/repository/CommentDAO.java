package br.com.yaw.repository;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.exception.RepositoryException;

public class CommentDAO extends BaseDAO<Comment, Key> implements CommentRepository{

	@Override
	public List<Comment> getCommentsByCompany(long companyId,
			int initPaginacao, int fimPaginacao)throws RepositoryException {
		StringBuilder jql = new StringBuilder();
		jql.append("select c from Comment c where company = :coId");
		addParamToQuery("coId", KeyFactory.createKey("Company", companyId));
		return executeQuery(jql.toString(), paramsToQuery, initPaginacao, fimPaginacao);
	}

	@Override
	public void addComment(Comment comment) throws RepositoryException {
		save(comment);
		restartTransaction();
		
		Key company = comment.getCompany();
		
		
		CompanyDAO companyDAO = new CompanyDAO();
		Company co = companyDAO.getById(company.getId());
		co.getComments().add(comment.getKey());
		companyDAO.addCompany(co);
	}

}

