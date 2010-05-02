package br.com.yaw.servlet.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.CompanyService;
import br.com.yaw.service.UserService;
import br.com.yaw.servlet.bean.BeanMapper;

public class UserActionServlet extends BaseActionServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = tokens[2];
		CompanyService companyService = ServiceFactory.getService(CompanyService.class);
		CommentService commentService = ServiceFactory.getService(CommentService.class);
		UserService service = ServiceFactory.getService(UserService.class);
		
		if("add".equals(action)) {
			try {
				String param = request.getParameter("edit");
				
				if(param == null) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/addUser.jsp");
					dispatcher.forward(request, response);
				}else {
					User user = BeanMapper.createUser(request); 
					service.addUser(user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
		}
		
	}
	
}
