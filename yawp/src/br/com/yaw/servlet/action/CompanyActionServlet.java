package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.yaw.entity.Address;
import br.com.yaw.entity.Company;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CompanyService;

public class CompanyActionServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = tokens[2];
		CompanyService service = ServiceFactory.getService(CompanyService.class);
		
		if("list".equals(action)) {
			try {
				if(tokens[3].equals("all")){
					listAll(response, service);
				}else{
					Company c = service.getCompanyById(Long.parseLong(tokens[3]));
					Gson gson = new Gson();
					c.getAddr(); //Appengine não suportar JOIN en queries nem relacionamentos EAGER 
					String jsonString = gson.toJson(c);
					response.getWriter().write(jsonString);
				}
			} catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
			response.getWriter().close();
			
		}else if("add".equals(action)) {
			try {
				Company c = new Company();
				
				Address addr = new Address();
				addr.setBairro("BAIRRO");
				addr.setCity("CIDADE");
				addr.setCountry("PAIS");
				addr.setNumber(666);
				addr.setState("ESTADO");
				addr.setStreet("RUA");
				
				c.setAddr(addr);
				c.setDescription("DESCRICAO");
				c.setLogo("URLLOGO");
				c.setMail("EMAIL");
				c.setName("NOMEEMPRESA");
				c.setUrl("SITEEMPRESA");
				
				service.addCompany(c);
				response.sendRedirect("/company/list/all");
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
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
