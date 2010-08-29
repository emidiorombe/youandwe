package br.com.yaw.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.yaw.utils.StringUtilities;

import com.google.appengine.api.datastore.Key;

@Entity
public class CompanyTag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	
	private String name;
	
	private String searchableName;
	
	private Long companyId;

	public CompanyTag(String tag, Long companyId) {
		setName(tag);
		this.companyId = companyId;
	}

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
		this.searchableName = StringUtilities.getSearchableString(name).toString();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getSearchableName() {
		return searchableName;
	}

	public void setSearchableName(String searchableName) {
		this.searchableName = searchableName;
	}

}
