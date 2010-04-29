package br.com.yaw.repository;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;

public class CommentDAO extends BaseDAO<Comment, Key> implements CommentRepository{

	@Override
	public List<Comment> getCommentsByCompany(Company companyId,
			int initPaginacao, int fimPaginacao) {
		return null;
	}

}

