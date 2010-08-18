package br.com.yaw.entity;

import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

/**
 * A generic app User.
 * @author Rafael Nunes
 *
 */
@Entity
@Searchable
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SearchableId
	private Key key ;
	
	@SearchableProperty
	private String name;
	
	private String password;
	
	@SearchableProperty
	private String contactEmail;
	
	private String description;
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Long> contacts;
	
	private String avatar;
	
	private String twit;
	
	private String fcbook;
	
	private String orkut;
	
	private Integer tipoCadastro;
	
	private String tipoUsuario;
	
	private Integer qtdeContatos;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getContacts() {
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

	public void removeContact(long userId) {
		int index = contacts.indexOf(userId);
		if(index >= 0) {
			contacts.remove(index);
			qtdeContatos--;
		}
		
	}

	public void addContact(long contactId) {
		if(!contacts.contains(contactId)){
			contacts.add(contactId);
			qtdeContatos++;
		}
	}
	
}
