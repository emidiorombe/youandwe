package br.com.yaw.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * The company address
 * @author Rafael Nunes
 *
 */
@Embeddable
public class Address implements Serializable{
	
	private String street;
	
	private int number;
	
	private String bairro;
	
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setNumber(String number) {
		this.number = Integer.parseInt(number);
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
	
	
	
	
}
