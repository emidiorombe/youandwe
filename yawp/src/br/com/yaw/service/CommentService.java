package br.com.yaw.service;

import java.util.List;

import br.com.yaw.entity.Comment;

/**
 * Serviços de comentários
 * @author rafael
 *
 */
public interface CommentService {
	List<Comment> getCommentsByCompany(long companyId);
}
