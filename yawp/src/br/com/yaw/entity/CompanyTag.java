package br.com.yaw.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

import com.google.appengine.api.datastore.Key;

@Entity
@Searchable
public class CompanyTag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SearchableId
	private Key key;
	
	@SearchableProperty
	private String name;
	
	@SearchableProperty
	private Long companyId;

	public CompanyTag(String tag, Long companyId) {
		name = tag;
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
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	

}
