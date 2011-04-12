package br.com.yaw.test;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class President {
	@Id
	private Long id;
	
	
	private String firstName;
	
	private String lastName;
	
	private Date tookOffice;
	
	private Date leftOffice;
	
	private Double income;
	
	private Key<Party> party;
	
	public President() {}
	
	public President(Party party, String firstName, String lastName,
			Date tookOffice, Date leftOffice, Double income) {
		super();
		this.party = new Key<Party>(Party.class, party.getId());
		this.firstName = firstName;
		this.lastName = lastName;
		this.tookOffice = tookOffice;
		this.leftOffice = leftOffice;
		this.income = income;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getTookOffice() {
		return tookOffice;
	}

	public void setTookOffice(Date tookOffice) {
		this.tookOffice = tookOffice;
	}

	public Date getLeftOffice() {
		return leftOffice;
	}

	public void setLeftOffice(Date leftOffice) {
		this.leftOffice = leftOffice;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Key<Party> getPartyKey() {
		return party;
	}

	public void setParty(Party party) {
		this.party = new Key<Party>(Party.class, party.getId());
	}

	@Override
	public String toString() {
		return "President [firstName=" + firstName + ", lastName=" + lastName
				+ ", tookOffice=" + tookOffice + ", leftOffice=" + leftOffice
				+ ", income=" + income + ", party=" + party + "]";
	}
	
	
	
}
