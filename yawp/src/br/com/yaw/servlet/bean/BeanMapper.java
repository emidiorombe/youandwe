package br.com.yaw.servlet.bean;

import javax.servlet.http.HttpServletRequest;

import br.com.yaw.entity.Address;
import br.com.yaw.entity.Company;

/**
 * Faz mapeamento dos parâmetros de request para Java Beans. 
 * @author Rafael Nunes
 *
 */
public class BeanMapper {

	public static Company createCompany(HttpServletRequest request) {
		Company c = new Company();
		
		Address addr = new Address();
		addr.setCity(request.getParameter("city"));
		addr.setCountry(request.getParameter("county"));
		addr.setNumber(Integer.parseInt(request.getParameter("number")));
		addr.setState(request.getParameter("state"));
		addr.setStreet(request.getParameter("street"));
		
		c.setAddr(addr);
		c.setDescription(request.getParameter("desc"));
		c.setLogo(request.getParameter("logo"));
		c.setName(request.getParameter("name"));
		c.setUrl(request.getParameter("site"));
		return c;
	}
	
}
