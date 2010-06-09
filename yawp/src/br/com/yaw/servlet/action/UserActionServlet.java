package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelServicePb;
import com.google.appengine.api.channel.ChannelServicePb.CreateChannelRequest;


import br.com.yaw.entity.Comment;
import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.exception.UsuarioExistenteException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.UserService;
import br.com.yaw.servlet.bean.BeanMapper;

public class UserActionServlet extends BaseActionServlet{
	private static final Logger log = Logger.getLogger(UserActionServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
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
				
			}catch(UsuarioExistenteException uee) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/addUser.jsp");
				request.setAttribute("msgErro", uee.getMessage());
				dispatcher.forward(request, response);
			}
			
		}else if("list".equals(action)) {
			try {
				long userId = Long.parseLong(tokens[3]);
				User user = service.getUserById(userId);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/viewUser.jsp");
				
				request.setAttribute("user", user);
				
				List<Comment> commentsByUser = commentService.getCommentsByUser(user);
				request.setAttribute("c_comments", commentsByUser);
				List<User> friends = service.getUserNetwork(user);
				request.setAttribute("c_friends", friends);
				dispatcher.forward(request, response);
				
			}catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
		}else if("add_contact".equals(action)) {
			String friendId = tokens[3];
			User logged = (User) request.getSession(false).getAttribute("loggedUser");
			try {
				service.addContact(logged, Long.parseLong(friendId));
				request.getSession(false).setAttribute("loggedUser", logged);
			}catch (Exception se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
			
			
		}else if("login".equals(action)) {
			try {
				User user = BeanMapper.createUser(request);
				User userAuth = service.authenticate(user.getContactEmail(), user.getPassword());
				log.info(":::::::::::::::::::::::LOGIN INFO " + userAuth.getContactEmail());
				log.severe(":::::::::::::::::::::::LOGIN SEVERE " + userAuth.getContactEmail());
				
				if(userAuth != null) {
					request.getSession().setAttribute(LOGGED_USER, userAuth);
				}else {
					RequestDispatcher dispatch = request.getRequestDispatcher("/pages/login.jsp");
					dispatch.forward(request, response);
				}
				
				response.sendRedirect("/index.html");
			}catch (Exception e) {
				response.getWriter().println(e.getMessage());
			}
		}
		
	}

}
