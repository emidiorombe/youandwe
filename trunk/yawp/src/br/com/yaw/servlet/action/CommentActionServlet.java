package br.com.yaw.servlet.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.CompanyService;
import br.com.yaw.servlet.bean.BeanMapper;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.gson.Gson;

public class CommentActionServlet extends BaseActionServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = tokens[2];
		CommentService service = ServiceFactory.getService(CommentService.class);
		CompanyService companyService = ServiceFactory.getService(CompanyService.class);
		
		if("add".equals(action)) {
			try {
				Comment c = new Comment();
				c.setRating(Integer.parseInt(request.getParameter("rating")));
				c.setText(new Text(request.getParameter("text")));
				
				User u = (User) request.getSession(false).getAttribute(LOGGED_USER);
				c.setOwner(u.getKey().getId());
				
				long companyId = Long.parseLong(request.getParameter("id_company"));
				c.setCompany(companyId);
				
				service.addComment(c);
				
				response.sendRedirect("/company/list/" + companyId);
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
		}else {
			response.sendRedirect("/pages/404.jsp");	
		} 
	}
}
