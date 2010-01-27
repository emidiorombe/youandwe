package br.com.yaw.struts2.action;

import br.com.yaw.entity.User;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.UserService;
import br.com.yaw.util.UserUtilities;

/**
 * Actions for Users
 * @author Rafael Nunes
 *
 */
public class UserAction extends BaseAction {
	private String photoUrl;
	private String name;
	private String desc;
	private String mail;
	private String url;
	private String tags;
	private String password;
	
private UserService userService;
	
	public String login(){
		if(mail.length() > 0 && mail.length() > 0){
			userService = ServiceFactory.getService(UserService.class);
			
			User user = new User();
			user.setContactEmail(mail);
			user.setPassword(password);
			user.setContactEmail("email@yaw.com.br");
			//userService.addUser(user);
			
			addActionMessage("Login com sucesso!");
			return SUCCESS;
		}
		addActionError("Usuario/Senha errado.");
		return INPUT;
	}
	
	public void addUser() {
		User user = new User(name, UserUtilities.generatePassword(), mail, desc, url, 1);
		//user.setPortfolio(new Portfolio());
		user.addTags(tags);
		userService = ServiceFactory.getService(UserService.class);
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
