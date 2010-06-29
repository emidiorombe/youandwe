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
				String paramEdt = request.getParameter("edit");
				
				if(paramEdt == null) {
					User user = new User();
					user.setContactEmail(request.getParameter("mail"));
					user.setPassword(request.getParameter("pass1"));
					service.addUser(user);
					request.getSession().setAttribute(LOGGED_USER, user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}else {
					User user = service.getUserByEmail(request.getParameter("contactEmail"));
					user.setName(request.getParameter("name"));
					service.updateUser(user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
				
			}catch(UsuarioExistenteException uee) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/edtUser.jsp");
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
				
				if(userAuth != null) {
					request.getSession().setAttribute(LOGGED_USER, userAuth);
					response.sendRedirect("/user/list/" + userAuth.getKey().getId());
				}else {
					RequestDispatcher dispatch = request.getRequestDispatcher("/pages/login.jsp");
					dispatch.forward(request, response);
				}
				
			}catch (Exception e) {
				response.getWriter().println(e.getMessage());
			}
		}else if("login_ext".equals(action)) {
			try {
				User user = service.getUserByEmail(request.getUserPrincipal().getName());
				
				if(user != null) {
					request.getSession().setAttribute(LOGGED_USER, user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}else {
					//Adiciona usuário pois ainda não existia no cadastro.
					user = new User();
					user.setContactEmail(request.getUserPrincipal().getName());
					user.setTipoCadastro(2);
					service.addUser(user);
					
					request.getSession().setAttribute(LOGGED_USER, user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}
				
				
				
			}catch (Exception e) {
				response.getWriter().println(e.getMessage());
			}
		}else if("edit".equals(action)) {
			try {
				long userId = Long.parseLong(tokens[3]);
				User user = service.getUserById(userId);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/edtUser.jsp");
				
				request.setAttribute("user", user);
				dispatcher.forward(request, response);
			}catch(ServiceException se) {
				
			}
			
			
		
		}else if("logout".equals(action)) {
				request.getSession(false).invalidate();
				RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
				dispatch.forward(request, response);
			
		}else {
			response.sendRedirect("/pages/404.jsp");	
		} 
		
	}

}
