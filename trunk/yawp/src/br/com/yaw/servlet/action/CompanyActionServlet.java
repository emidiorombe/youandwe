package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.async.AsyncJobs;
import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.CompanyService;
import br.com.yaw.servlet.bean.BeanMapper;
import br.com.yaw.utils.States;
import br.com.yaw.utils.StringUtilities;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;

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
				if(request.getSession().getAttribute(LOGGED_USER) == null) {
					response.sendRedirect("/pages/login.jsp");
				}else if(request.getParameter("edit") == null) {
					
					
					RequestDispatcher rd = request.getRequestDispatcher("/pages/edtCompany.jsp");
					request.setAttribute("l_states", States.getListStates());
					rd.forward(request, response);
				}else {
					Company c = BeanMapper.createCompany(request);
					
					String id_c = request.getParameter("id_c");
					
					BlobstoreService blobS = BlobstoreServiceFactory.getBlobstoreService();
					Map<String, BlobKey> uploadedBlobs = blobS.getUploadedBlobs(request);
					
					BlobKey blobKey = uploadedBlobs.get("logo_c");
					
					if(blobKey != null)
						c.setLogo(blobKey.getKeyString());
						
					if(id_c == null || "".equals(id_c) ) {
						User user = (User)request.getSession().getAttribute(LOGGED_USER);
						c.setOwner(user.getKey().getId());
						c.setSearchableName(c.getName().toLowerCase());
						c.getAddr().setSearchableCity(c.getAddr().getCity().toLowerCase());
						service.addCompany(c);
					}else {
						Integer companyId = Integer.parseInt(id_c != null ? id_c : "0");
						Company fromBase = service.getCompanyById(companyId);
						c.setKey(fromBase.getKey());
						c.setOwner(fromBase.getOwner());
						c.getAddr().setSearchableCity(c.getAddr().getCity().toLowerCase());
						c.setSearchableName(c.getName().toLowerCase());
						service.addCompany(c);
						
					}
					
					service.addTags(request.getParameter("category"), c.getKey().getId());
					AsyncJobs.addCompanyToCache(c);
					response.sendRedirect("/company/list/" + c.getKey().getId());
				}
			}catch (ServiceException e) {
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
				request.setAttribute("l_states", States.getListStates());
				
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
				
				Map<String, String> query = new HashMap<String, String>();
				query.put("categoria_ou_nome", request.getParameter("q_cat"));
				query.put("local", request.getParameter("q_onde"));
				
				List<Company> lista = service.findCompanies(query);
				
				RequestDispatcher rd = request.getRequestDispatcher("/pages/listCompanies.jsp");
				request.setAttribute("lista_co", lista);
				
				rd.forward(request, response);
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
		}else if("update_logo".equals(action)) {
			try {
				BlobstoreService blobS = BlobstoreServiceFactory.getBlobstoreService();
				Map<String, BlobKey> blobs = blobS.getUploadedBlobs(request);
				
				BlobKey blobKey = blobs.get("logoFile");
				String idReq = request.getParameter("id_c");
				long id_c = Integer.parseInt(idReq);
				
				Company company = service.getCompanyById(id_c);
				company.setLogo(blobKey.getKeyString());
				service.addCompany(company);
	
			    response.sendRedirect("/company/list/"+idReq);
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
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
