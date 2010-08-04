package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.entity.Company;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.CompassFactory;
import br.com.yaw.service.CacheService;
import br.com.yaw.service.CompanyService;

public class AsyncServlet extends BaseActionServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
		
		if("compass_rebuild".equals(action)) {
			CompassFactory.rebuildIndex();
			
		}else if("cache_company".equals(action)){
			String idC = tokens[3];
			CompanyService companyService = ServiceFactory.getService(CompanyService.class); 
			try {
				Map<String, Company> companies = CacheService.getCompanies();
				if(companies == null || !companies.containsKey(idC)){
					Company c = companyService.getCompanyById(Long.parseLong(idC));
					CacheService.addCompany(c);
					companies = CacheService.getCompanies();
				}
			}catch(ServiceException se) {
				System.err.println("::::::Não foi possível adicionar Cmopany no cache. COmpany ID => " + idC);
			}
		}else if("cache_companies".equals(action)){
			CompanyService cService = ServiceFactory.getService(CompanyService.class);
			try {
				List<Company> allCompanies = cService.getAllCompanies();
				CacheService.addCompanies(allCompanies);
			} catch (ServiceException e) {
				System.err.println(":::::: Erro ao carregar empresas para o Cache > " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	

}
