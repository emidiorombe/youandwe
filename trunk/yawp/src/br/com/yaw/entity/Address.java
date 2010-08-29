package br.com.yaw.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import br.com.yaw.utils.StringUtilities;


/**
 * The company address
 * @author Rafael Nunes
 *
 */
@Embeddable
public class Address implements Serializable{
	
	private String street;
	
	private String bairro;
	
	private String searchableBairro;
	
	private String city;
	
	private String searchableCity;
	
	private String state;
	
	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
		this.searchableBairro = StringUtilities.getSearchableString(bairro).toLowerCase();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		this.searchableCity = StringUtilities.getSearchableString(city).toLowerCase();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSearchableCity() {
		return searchableCity;
	}

	public void setSearchableCity(String searchableCity) {
		this.searchableCity = searchableCity;
	}

	public String getSearchableBairro() {
		return searchableBairro;
	}

	public void setSearchableBairro(String searchableBairro) {
		this.searchableBairro = searchableBairro;
	}
	
	
	
	
}
