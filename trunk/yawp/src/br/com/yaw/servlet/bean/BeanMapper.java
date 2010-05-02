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

	public static Company createCompany(HttpServletRequest request) {
		Company c = createObject(Company.class, request);
		c.setAddr(createAddress(request));
		
		return c;
	}
	
	public static Address createAddress(HttpServletRequest request) {
		Address addr = createObject(Address.class, request);
		
		return addr;
	}
	
	private static <T>T createObject(Class<T> klass, HttpServletRequest request){
		T newInstance = null;
		try{
			newInstance = klass.newInstance();
			HashMap map = new HashMap();
			Enumeration names = request.getParameterNames();
			
			while(names.hasMoreElements()) {
				String name = (String) names.nextElement();
				map.put(name, request.getParameterValues(name));
			}
			BeanUtils.populate(newInstance, map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return newInstance;
	}
	
}
