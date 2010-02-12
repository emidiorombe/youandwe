package br.com.yaw.entity;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.FetchGroup;
import javax.jdo.annotations.Persistent;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;

/**
 * A generic app User.
 * @author Rafael Nunes
 *
 */
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key id;
	
	private String name;
	
	private String password;
	
	private String contactEmail;
	
	private String description;
	
	private String url;
	
	private Portfolio portfolio;
	
	private Integer perfil;
	
	@Persistent
	private List<JobTag> jobTags;
	
	public User() {}
	
	
	
	public User(String name, String password, String contactEmail, String description,
			String url, Integer perfil) {
		super();
		this.name = name;
		this.password = password;
		this.contactEmail = contactEmail;
		this.description = description;
		this.url = url;
		this.perfil = perfil;
	}

	/**
	 * Add tags to the user 
	 * @param tags
	 */
	public void addTags(String tags) {
		if(jobTags == null) {
			jobTags = new ArrayList<JobTag>();
		}
		
		for(String tag : tags.split(" ")) {
			jobTags.add(new JobTag(tag));
		}
	}

	
	/**
	 * @return the id
	 */
	public Key getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Key id) {
		this.id = id;
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

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the portfolio
	 */
	public Portfolio getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio the portfolio to set
	 */
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the perfil
	 */
	public Integer getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the jobTags
	 */
	public List<JobTag> getJobTags() {
		return jobTags;
	}



	/**
	 * @param jobTags the jobTags to set
	 */
	public void setJobTags(List<JobTag> jobTags) {
		this.jobTags = jobTags;
	}
	
	
	
}
