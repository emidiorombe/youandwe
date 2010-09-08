package br.com.yaw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.yaw.utils.StringUtilities;

import com.google.appengine.api.datastore.Key;

/**
 * A generic app User.
 * @author Rafael Nunes
 *
 */
@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key ;
	
	private String name;
	
	private String searchableName;
	
	private String password;
	
	private String contactEmail;
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Long> contacts;
	
	private String avatar;
	
	private String twit;
	
	private String fcbook;
	
	private String orkut;
	
	private Integer tipoCadastro;
	
	private String tipoUsuario;
	
	private Integer qtdeContatos;
	
	private Boolean approved;
	
	private String authKey;
	
	private Long lastAccess;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.searchableName = StringUtilities.getSearchableString(name).toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public List<Long> getContacts() {
		if(contacts == null)
			contacts = new ArrayList<Long>();
		return contacts;
	}

	public void setContacts(List<Long> contacts) {
		this.contacts = contacts;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTwit() {
		return twit;
	}

	public void setTwit(String twit) {
		this.twit = twit;
	}

	public String getFcbook() {
		return fcbook;
	}

	public void setFcbook(String fcbook) {
		this.fcbook = fcbook;
	}

	public String getOrkut() {
		return orkut;
	}

	public void setOrkut(String orkut) {
		this.orkut = orkut;
	}

	public Integer getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(Integer tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public int getQtdeContatos() {
		return qtdeContatos == null ? 0 : qtdeContatos;
	}
	

	public String getSearchableName() {
		return searchableName;
	}

	public void setSearchableName(String searchableName) {
		this.searchableName = searchableName;
	}
	
	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
	public Boolean getApproved() {
		return approved == null ? false : approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}

	public void removeContact(long userId) {
		int index = contacts.indexOf(userId);
		if(index >= 0) {
			contacts.remove(index);
			qtdeContatos = getQtdeContatos() - 1;
		}
		
	}

	public void addContact(long contactId) {
		if(!getContacts().contains(contactId)){
			getContacts().add(contactId);
			qtdeContatos = getQtdeContatos() + 1;
		}
	}
	
}
