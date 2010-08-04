package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.entity.Comment;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CacheService;
import br.com.yaw.service.CommentService;
import br.com.yaw.utils.ComparadorComentariosDecrescente;

public class GeneralActionServlet extends BaseActionServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
		CommentService com_service = ServiceFactory.getService(CommentService.class);
		
		if("help".equals(action)) {
			response.sendRedirect("/pages/help.jsp");
			
		}else if("busca".equals(action)) {
			response.sendRedirect("/pages/search.jsp");
		}else if("index".equals(action)) {
			try {
				Collection<Comment> latestComments = CacheService.getLatestComments();
				if(latestComments == null || latestComments.size() == 0){
					latestComments = com_service.getLatestComments(100);
					CacheService.addComment(latestComments);
				}
				
				List<Comment> orderedComments = new ArrayList<Comment>(latestComments);
				Collections.sort(orderedComments, new ComparadorComentariosDecrescente());
				RequestDispatcher rd = request.getRequestDispatcher("/pages/index.jsp");
				request.setAttribute("lt_comment", orderedComments);
				rd.forward(request, response);
			}catch(ServiceException se) {
				throw new RuntimeException();
			}
			
		}
	}
	
	

}
