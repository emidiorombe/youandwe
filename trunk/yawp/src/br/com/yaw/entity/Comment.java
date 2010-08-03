package br.com.yaw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
public class Comment implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	
	private Long owner;
	
	private Text text;
	
	private int rating;
	
	private Long company;
	
	private Date dtComment;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	public Date getDtComment() {
		return dtComment;
	}

	public void setDtComment(Date dtComment) {
		this.dtComment = dtComment;
	}
	
	public String getTextValue(){
		return text.getValue();
	}
	
	public String getPartialText(){
		String txt = text.getValue();
		if(txt.length() > 120){
			txt = txt.substring(0, 121);
		}
		return txt;
	}

}
