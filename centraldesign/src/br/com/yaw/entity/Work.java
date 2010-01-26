package br.com.yaw.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * Work entity
 * @author Rafael Nunes
 *
 */
public class Work {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key id;
	
	private String customer;
	
	private String urlCustomer;
	
	private String workImage;

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
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the urlCustomer
	 */
	public String getUrlCustomer() {
		return urlCustomer;
	}

	/**
	 * @param urlCustomer the urlCustomer to set
	 */
	public void setUrlCustomer(String urlCustomer) {
		this.urlCustomer = urlCustomer;
	}

	/**
	 * @return the workImage
	 */
	public String getWorkImage() {
		return workImage;
	}

	/**
	 * @param workImage the workImage to set
	 */
	public void setWorkImage(String workImage) {
		this.workImage = workImage;
	}
	
	
}
