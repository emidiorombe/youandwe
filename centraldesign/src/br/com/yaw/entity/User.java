package br.com.yaw.entity;

import java.util.ArrayList;
import java.util.Iterator;
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
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	private Key key;
	
	private String name;
	
	private String password;
	
	private String contactEmail;
	
	private String description;
	
	private String url;
	
	private Portfolio portfolio;
	
	private Integer perfil;
	
	@Persistent
	@OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	private List<JobTag> jobTags;
	
	@Transient
	private String tags;
	
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
		
		for(String tag : tags.split(",")) {
			jobTags.add(new JobTag(tag.trim()));
		}
	}
	
	public String getTags() {
		String tags = "";
		for (int i = 0; i < jobTags.size(); i++) {
			tags += jobTags.get(i).getTag();
			if(i < jobTags.size() -1) {
				tags += ",";
			}
		}
		return tags;
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

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<JobTag> getJobTags() {
		return jobTags;
	}

	public void setJobTags(List<JobTag> jobTags) {
		this.jobTags = jobTags;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
 
	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
