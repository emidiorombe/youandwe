package br.com.yaw.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.blobstore.BlobKey;
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
	
	private List<BlobKey> photos;

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
	
	public List<BlobKey> getPhotos() {
		return photos;
	}

	public void setPhotos(List<BlobKey> photos) {
		this.photos = photos;
	}

	public String getPartialText(){
		String txt = text.getValue();
		if(txt.length() > 140){
			txt = txt.substring(0, 141) + "...";
		}
		return txt;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
}
