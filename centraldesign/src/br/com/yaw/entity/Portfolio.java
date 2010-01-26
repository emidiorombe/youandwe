package br.com.yaw.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

public class Portfolio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key id;
	
	private List<Work> works;

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
	 * @return the works
	 */
	public List<Work> getWorks() {
		return works;
	}

	/**
	 * @param works the works to set
	 */
	public void setWorks(List<Work> works) {
		this.works = works;
	}
	
}
