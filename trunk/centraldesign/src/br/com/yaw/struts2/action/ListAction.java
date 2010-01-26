package br.com.yaw.struts2.action;

import java.util.List;

import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.UserService;


public class ListAction extends BaseAction{
	private UserService userService;
	private List<User> all;

	public String listAll() {
		String result = null;
		userService = ServiceFactory.getService(UserService.class);
		try {
			all = userService.getAll();
			result = SUCCESS;
		}catch (ServiceException se) {
			addActionError("Erro na listagem.");
			result  = "error";
			se.printStackTrace();
		}
		return result;
	}


	/**
	 * @return the all
	 */
	public List<User> getAll() {
		return all;
	}

	
}
