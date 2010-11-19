package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import br.com.yaw.async.AsyncJobs;
import br.com.yaw.entity.Comment;
import br.com.yaw.entity.User;
import br.com.yaw.exception.SenhaInvalidaException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.exception.UsuarioExistenteException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.EQtalMailService;
import br.com.yaw.service.UserService;
import br.com.yaw.servlet.bean.BeanMapper;
import br.com.yaw.utils.StringUtilities;

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
					String senha = request.getParameter("pass1");
					if(senha == null || senha.length() < 6) {
						throw new SenhaInvalidaException("Senha deve ter no mínimo 6 caracteres!");
					}
					User user = new User();
					user.setContactEmail(request.getParameter("mail"));
					user.setPassword(StringUtilities.createPassword(senha));
					user.setAuthKey(StringUtilities.generateUserAuthKey());
					user.setLastAccess(new Date().getTime());
					user.setTipoCadastro(1);
					service.addUser(user);
					
					AsyncJobs.sendMailUserAdded(user);
					
					request.getSession().setAttribute(LOGGED_USER, user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}else {
					User user = service.getUserByEmail(request.getParameter("contactEmail"));
					user.setName(request.getParameter("name"));
					user.setTwit(request.getParameter("twit"));
					user.setFcbook(request.getParameter("fcbook"));
					user.setOrkut(request.getParameter("orkut"));
					
					BlobstoreService bService = BlobstoreServiceFactory.getBlobstoreService();
					Map<String, BlobKey> blobs = bService.getUploadedBlobs(request);
					BlobKey avatarKey = blobs.get("avatar");
					
					if(avatarKey != null) {
						user.setAvatar(avatarKey.getKeyString());
					}

					service.updateUser(user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
				
			}catch(UsuarioExistenteException uee) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login.jsp");
				request.setAttribute("msgErro", uee.getMessage());
				dispatcher.forward(request, response);
			}catch(SenhaInvalidaException sie) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login.jsp");
				request.setAttribute("msgErro", sie.getMessage());
				dispatcher.forward(request, response);
			}
			
		}else if("list".equals(action)) {
			try {
				long userId = Long.parseLong(tokens[3]);
				User user = service.getUserById(userId);
				User logged = (User) request.getSession().getAttribute(LOGGED_USER);
				if(user != null){
					RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/viewUser.jsp");
					
					request.setAttribute("user", user);
					
					List<Comment> commentsByUser = commentService.getCommentsByUser(user);
					request.setAttribute("c_comments", commentsByUser);
					request.setAttribute("c_comments_size", commentsByUser.size());
					List<User> friends = service.getUserNetwork(user);
					request.setAttribute("c_friends", friends);
					if(logged != null && logged.getContacts() != null)
						request.setAttribute("already_friend", logged.getContacts().contains(userId));
					dispatcher.forward(request, response);
				}else{
					RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/404.jsp");
					dispatcher.forward(request, response);
				}
			}catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
		}else if("add_contact".equals(action)) {
			long userId = Long.parseLong(tokens[3]);
			User logged = (User) request.getSession(false).getAttribute("loggedUser");
			try {
				service.addContact(logged,userId);
				request.getSession(false).setAttribute("loggedUser", logged);
				response.sendRedirect("/user/list/"+ userId);
			}catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
			
			
		}else if("login".equals(action)) {
			try {
				String senha = request.getParameter("password");
				if(senha == null || senha.length() < 6) {
					throw new SenhaInvalidaException("Senha deve ter no mínimo 6 caracteres");
				}
				
				User user = new User();
				user.setContactEmail(request.getParameter("contactEmail"));
				user.setPassword(StringUtilities.createPassword(request.getParameter("password")));
				
				User userAuth = service.authenticate(user.getContactEmail(), user.getPassword());
				
				if(userAuth != null) {
					userAuth.setLastAccess(new Date().getTime());
					service.updateUser(userAuth);
					request.getSession().setAttribute(LOGGED_USER, userAuth);
					response.sendRedirect("/user/list/" + userAuth.getKey().getId());
				}else {
					RequestDispatcher dispatch = request.getRequestDispatcher("/pages/login.jsp");
					dispatch.forward(request, response);
				}
				
			}catch (ServiceException e) {
				response.getWriter().println(e.getMessage());
			}catch (SenhaInvalidaException se) {
				response.getWriter().println(se.getMessage());
			}
		}else if("login_ext".equals(action)) {
			try {
				User user = service.getUserByEmail(request.getUserPrincipal().getName());
				
				if(user != null) {
					user.setLastAccess(new Date().getTime());
					service.updateUser(user);
					
					request.getSession().setAttribute(LOGGED_USER, user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}else {
					//Adiciona usuário pois ainda não existia no cadastro.
					user = new User();
					user.setContactEmail(request.getUserPrincipal().getName());
					user.setTipoCadastro(2);
					user.setLastAccess(new Date().getTime());
					user.setAuthKey(StringUtilities.generateUserAuthKey());
					service.addUser(user);
					
					AsyncJobs.sendMailUserAdded(user);
					
					request.getSession().setAttribute(LOGGED_USER, user);
					response.sendRedirect("/user/list/" + user.getKey().getId());
				}
				
				
				
			}catch (ServiceException e) {
				response.getWriter().println(e.getMessage());
			}
		}else if("edit".equals(action)) {
			try {
				long userId = Long.parseLong(tokens[3]);
				User user = service.getUserById(userId);
				User logged = request.getSession(false) == null ? null : (User) request.getSession(false).getAttribute("loggedUser");
				
				if(logged != null && (logged.getKey().getId() == userId)) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/edtUser.jsp");
					
					request.setAttribute("user", user);
					dispatcher.forward(request, response);
						
				}else {
					response.sendRedirect("/index.jsp");
				}
			}catch(ServiceException se) {
				throw new RuntimeException();
			}
			
			
		
		}else if("search".equals(action)) {
			try {
				String mail = request.getParameter("txtmail");
				String name = request.getParameter("txtBusca");
				List<User> lista = new ArrayList<User>(1);
				
				if(name != null) {
					lista = service.getUserByName(name);
				}
				if(mail != null) {
					User user = service.getUserByEmail(mail);
					lista.add(user);
				}
				
				
				for (User user2 : lista) {
					response.getWriter().println(user2.getContactEmail());
				}
				response.getWriter().close();
			}catch (ServiceException se) {

			}
		}else if("search_adv".equals(action)) {
			String nome_u = request.getParameter("nome_u");
			String mail_u = request.getParameter("mail_u");
			
			try {
				
				
				if(mail_u != null && mail_u.length() > 0) {
					User u = service.getUserByEmail(mail_u);
					request.setAttribute("us_by_mail", u);
				}else if(nome_u != null && nome_u.length() > 0) {
					List<User> users = service.getUserByName(nome_u);
					request.setAttribute("list_users", users);
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("/pages/search_result.jsp");
				rd.forward(request, response);
			} catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
			
		}else if("remove_contact".equals(action)) {
			long userId = Long.parseLong(tokens[3]);
			User logged = (User) request.getSession(false).getAttribute("loggedUser");
			try {
				service.removeContact(logged, userId);
				request.getSession(false).setAttribute("loggedUser", logged);
				response.sendRedirect("/user/list/"+ userId);
			}catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
		
		}else if("send_remember_mail".equals(action)) {
			
			long userId = Long.parseLong(tokens[3]);
			try {
				User user = service.getUserById(userId);
				user.setAuthKey(StringUtilities.generateUserAuthKey());
				service.updateUser(user);
				
				AsyncJobs.sendMailUserAdded(user);
				
				response.sendRedirect("/user/list/"+user.getKey().getId());
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}else if("logout".equals(action)) {
		
				request.getSession().invalidate();
				response.sendRedirect("/geral/index");
			
		}else if("enable".equals(action)) {
				String mail = request.getParameter("u_mail");
				String auth = request.getParameter("enable_key");
				
				try {
					User user = service.getUserByEmail(mail);
					 if(user.getAuthKey().equals(auth)) {
						 user.setApproved(true);
						 service.updateUser(user);
						 
						 request.getSession().setAttribute(LOGGED_USER, user);
						 response.sendRedirect("/user/list/" + user.getKey().getId());
					 }
					 
					
				} catch (ServiceException e) {
					response.sendRedirect("/geral/index");
				}
		}else {
			response.sendRedirect("/pages/404.jsp");	
		} 
		
	}

}