package br.com.yaw.struts2.action;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private String username;
	private String password;

	public String login(){
		if(username.equals("rafa") && password.equals("el")){
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
