package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.CompanyService;
import br.com.yaw.servlet.bean.BeanMapper;
import br.com.yaw.utils.StringUtilities;

public class CompanyActionServlet extends BaseActionServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
		CompanyService service = ServiceFactory.getService(CompanyService.class);
		CommentService commentService = ServiceFactory.getService(CommentService.class);
		Gson gson = new Gson();
		
		if("list".equals(action)) {
			try {
				if(tokens[3].equals("all")){
					listAll(response, service);
				}else{
					long companyId = Long.parseLong(tokens[3]);
					Company company = service.getCompanyById(companyId);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/viewCompany.jsp");
					
					request.setAttribute("company", company);
					
					List<Comment> commentsByCompany = null;
					if(request.getParameter("all") == null && request.getSession(false) != null && request.getSession(false).getAttribute(LOGGED_USER) != null)
						commentsByCompany = commentService.getCommentsByNetwork(companyId, (User)request.getSession(false).getAttribute(LOGGED_USER));
					else
						commentsByCompany = commentService.getCommentsByCompany(companyId);
					
					request.setAttribute("c_comments", commentsByCompany);
					request.setAttribute("qtdeComments", commentsByCompany.size());
					request.setAttribute("c_tags", StringUtilities.listTagToString(service.getCompanyTags(company.getKey().getId())));
					
					dispatcher.forward(request, response);
				}
			} catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
			response.getWriter().close();
			
		}else if("add".equals(action)) {
			try {
				if(request.getParameter("edit") == null) {
					RequestDispatcher rd = request.getRequestDispatcher("/pages/edtCompany.jsp");
					rd.forward(request, response);
				}else {
					
					Company c = BeanMapper.createCompany(request);
					
					String id_c = request.getParameter("id_c");
						
					if(id_c == null || "".equals(id_c) ) {
						User user = (User)request.getSession().getAttribute(LOGGED_USER);
						c.setOwner(user.getKey().getId());
						service.addCompany(c);
					}else {
						Integer companyId = Integer.parseInt(id_c != null ? id_c : "0");
						Company fromBase = service.getCompanyById(companyId);
						c.setKey(fromBase.getKey());
						c.setOwner(fromBase.getOwner());
						c.getAddr().setKey(fromBase.getAddr().getKey());
						service.addCompany(c);
						
					}
					
					service.addTags(request.getParameter("category"), c.getKey().getId());
					response.sendRedirect("/company/list/" + c.getKey().getId());
				}
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}catch(Exception e){
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
		}else if("edit".equals(action)) {
			try {
				long companyId = Long.parseLong(tokens[3]);
				Company company = service.getCompanyById(companyId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/edtCompany.jsp");
				request.setAttribute("company", company);
				request.setAttribute("c_tags", StringUtilities.listTagToString(service.getCompanyTags(company.getKey().getId())));
				
				dispatcher.forward(request, response);
				
			} catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
		}else if("delete".equals(action)) {
			try {
				long companyId = Long.parseLong(tokens[3]);
				
				service.remove(companyId);
				response.sendRedirect("/company/list/all");
				
			} catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
		}else if("search".equals(action)) {
			try {
				String query = request.getParameter("txtBusca");
				List<Company> lista = service.findCompanies(query);
				
				RequestDispatcher rd = request.getRequestDispatcher("/pages/listCompanies.jsp");
				request.setAttribute("lista_co", lista);
				
				rd.forward(request, response);
			}catch (Exception e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
		}else {
			response.sendRedirect("/pages/404.jsp");	
		} 
	}

	private void listAll(HttpServletResponse response, CompanyService service)
			throws ServiceException, IOException {
		List<Company> lista = service.getAllCompanies();
		
		for (Company co : lista) {
			response.getWriter().write("<a href='/company/list/" + co.getKey().getId() +"/' />Empresa " + co.getKey().getId() + "</a><br/>");
		}
	}
}
