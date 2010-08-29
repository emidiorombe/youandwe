package br.com.yaw.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


import br.com.yaw.utils.StringUtilities;

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
	
	private String searchableName;
	
	private String description;
	
	private String mail;
	
	@Embedded
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Address addr;
	
	private String url;
	
	private String logo;
	
	private List<Long> comments;
	
	private Long owner;

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

	public List<Long> getComments() {
		return comments;
	}

	public void setComments(List<Long> comments) {
		this.comments = comments;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getSearchableName() {
		return searchableName;
	}

	public void setSearchableName(String searchableName) {
		this.searchableName = searchableName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	
	

}
