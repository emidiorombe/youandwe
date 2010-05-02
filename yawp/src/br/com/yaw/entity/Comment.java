package br.com.yaw.entity;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	
	private User owner;
	
	private Text text;
	
	private int rating;
	
	private Key company;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Key getCompany() {
		return company;
	}

	public void setCompany(Key company) {
		this.company = company;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	
	
}
