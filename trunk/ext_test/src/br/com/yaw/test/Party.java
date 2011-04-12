package br.com.yaw.test;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class Party {
	@Id
	private Long id;
	
	private String name;
	
	public Party() {}

	public Party(String name) {
		super();
		this.name = name;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
