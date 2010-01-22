package br.com.yaw.struts2.action;

import br.com.yaw.entity.User;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private String username;
	private String password;
	
	private UserService userService;
	
	public String login(){
		if(username.length() > 0 && password.length() > 0){
			userService = ServiceFactory.getService(UserService.class);
			
			User user = new User();
			user.setName(username);
			user.setPassword(password);
			user.setContactEmail("email@yaw.com.br");
			userService.addUser(user);
			
			addActionMessage("Login com sucesso!");
			return SUCCESS;
		}
		addActionError("Usuario/Senha errado.");
		return INPUT;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
