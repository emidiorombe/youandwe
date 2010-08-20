package br.com.yaw;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="loginMB")
@RequestScoped
public class LoginMB {
	
	private String mail;
	private String senha;
	
	public String autenticar() {
		if(mail.length() == 0 || senha.length() == 0) {
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Preenchimento de e-mail e senha obrigatórios!"));
			return null;
		}
		
		return "autenticado";
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String pass) {
		this.senha = pass;
	}
}
