package br.com.yaw.repository;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.User;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.utils.StringUtilities;

public class CommentDAO extends BaseDAO<Comment, Key> implements CommentRepository{

	@Override
	public List<Comment> getCommentsByCompany(long companyId,
			int initPaginacao, int fimPaginacao)throws RepositoryException {
		List<Comment> list = null;
		try {
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select c from Comment c where company = :coId");
			addParamToQuery("coId", companyId);
			list = executeQuery(jql.toString(), paramsToQuery, initPaginacao, fimPaginacao);
			commitTransaction();	
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return list;
	}

	@Override
	public void addComment(Comment comment) throws RepositoryException {
		try {
			beginTransaction();
			save(comment);
			commitTransaction();
			
			beginTransaction();
			Company comp = (Company) getGenericEntityByPrimaryKey(Company.class, comment.getCompany());
			comp.getComments().add(comment.getKey().getId());
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
	}

	@Override
	public List<Comment> getCommentsByUser(User user, int init, int end) throws RepositoryException {
		List<Comment> list = null;
		try {
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select c from Comment c where owner = :owId");
			addParamToQuery("owId", user.getKey().getId());
			list = executeQuery(jql.toString(), paramsToQuery, init, end);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
		return list;
	}

	@Override
	public List<Comment> getCommentsByNetwork(long companyId, List<Long> network,
			int init, int end) throws RepositoryException {
		List<Comment> list = null;
		try {
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select c from Comment c where c.company = :coId and c.owner in " + StringUtilities.listLongToInClause(network)); 
			addParamToQuery("coId", companyId);
			list = executeQuery(jql.toString(), paramsToQuery, init, end);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return list;
	}

	@Override
	public Collection<Comment> getLatest(int quantidade) throws RepositoryException {
	
		try {
			beginTransaction();
			StringBuilder jql = new StringBuilder();
			jql.append("select c from Comment c "); 

			Collection<Comment> list = executeQuery(jql.toString(), paramsToQuery, 0, quantidade);
			commitTransaction();
			return list;
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
	}

	@Override
	public Comment getById(long idComment) throws RepositoryException {
		return getByPrimaryKey(KeyFactory.createKey("Comment", idComment));
		
	}

	@Override
	public void remove(Comment c) throws RepositoryException {
		try {
			beginTransaction();
			delete(c);
			commitTransaction();	
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}

}

