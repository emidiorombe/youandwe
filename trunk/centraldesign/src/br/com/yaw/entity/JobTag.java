package br.com.yaw.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

public class JobTag {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	
	private String tag;
	
	public JobTag() {}
	
	public JobTag(String tag) {
		this.tag = tag;
	}
}
