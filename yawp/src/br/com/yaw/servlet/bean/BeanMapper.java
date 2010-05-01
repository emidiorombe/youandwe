package br.com.yaw.servlet.bean;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import br.com.yaw.entity.Address;
import br.com.yaw.entity.Company;

/**
 * Faz mapeamento dos parâmetros de request para Java Beans. 
 * @author Rafael Nunes
 *
 */
public class BeanMapper {

	/*public static Company createCompany(HttpServletRequest request) {
		Company c = new Company();
		for(Object o : request.getParameterMap().entrySet()) {
			Map.Entry e = (Map.Entry) o;
			String key = e.getKey().toString();
			if(key.startsWith("c_")) {
				try {
					PropertyUtils.setSimpleProperty(c, key.substring(2), e.getValue().toString());
				}catch (Exception ex) {
					//TODO log de property não encontrada
					ex.printStackTrace();
				}
			}
		}
		c.setAddr(createAddress(request));
		
		return c;
	}*/
	
	public static Company createCompany(HttpServletRequest request) {
		Company c = new Company();
		HashMap map = new HashMap();
		Enumeration names = request.getParameterNames();
		
		while(names.hasMoreElements()) {
			String name = (String) names.nextElement();
			map.put(name, request.getParameterValues(name));
		}
		
		try {
			BeanUtils.populate(c, map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		c.setAddr(createAddress(request));
		
		return c;
	}
	
	public static Address createAddress(HttpServletRequest request) {
		Address addr = new Address();
		HashMap map = new HashMap();
		Enumeration names = request.getParameterNames();
		
		while(names.hasMoreElements()) {
			String name = (String) names.nextElement();
			map.put(name, request.getParameterValues(name));
		}
		
		try {
			BeanUtils.populate(addr, map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return addr;
	}
	
}
