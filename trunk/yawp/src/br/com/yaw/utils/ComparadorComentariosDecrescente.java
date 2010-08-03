package br.com.yaw.utils;

import java.util.Comparator;

import br.com.yaw.entity.Comment;

public class ComparadorComentariosDecrescente implements Comparator<Comment> {

	@Override
	public int compare(Comment c1, Comment c2) {
		
		return c1.getDtComment() == null || c2.getDtComment() == null ? 1 : c2.getDtComment().compareTo(c1.getDtComment());
	}

}
