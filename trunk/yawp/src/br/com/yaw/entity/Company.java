package br.com.yaw.entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.Persistent;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.appengine.api.datastore.Key;

/**
 * A company
 * @author Rafael Nunes
 *
 */
@Entity
public class Company implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	
	private String name;
	
	private String description;
	
	private String mail;
	
	@Persistent
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Address addr;
	
	private String url;
	
	private String logo;
	
	private List<Key> comments;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Address getAddr() {
		return addr;
	}

	public void setAddr(Address addr) {
		this.addr = addr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<Key> getComments() {
		return comments;
	}

	public void setComments(List<Key> comments) {
		this.comments = comments;
	}
	
}
