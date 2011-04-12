package br.com.yaw.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class PresidentDataView {
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String tookOffice;
	
	private String leftOffice;
	
	private Double income;
	
	private Long party;
	
	private String pname;
	
	public PresidentDataView() {}
	
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

	public String getTookOffice() {
		return tookOffice;
	}

	public void setTookOffice(String tookOffice) {
		this.tookOffice = tookOffice;
	}

	public String getLeftOffice() {
		return leftOffice;
	}

	public void setLeftOffice(String leftOffice) {
		this.leftOffice = leftOffice;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}
	
	public Long getParty() {
		return party;
	}

	public void setParty(Long party) {
		this.party = party;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public static PresidentDataView createFromPresident(President p) {
		PresidentDataView dv = new PresidentDataView();
		dv.id = p.getId();
		dv.firstName = p.getFirstName();
		dv.lastName = p.getLastName();
		dv.tookOffice = new SimpleDateFormat("dd/MM/yyyy").format(p.getTookOffice());
		dv.leftOffice = new SimpleDateFormat("dd/MM/yyyy").format(p.getLeftOffice());
		dv.income = p.getIncome();
		Party partyByKey = DAO.getPartyByKey(p.getPartyKey());
		dv.party = partyByKey.getId();
		dv.pname = partyByKey.getName();
		return dv;
	}
	
	public static List<PresidentDataView> createFromListPresident(List<President> lista){
		List<PresidentDataView> retorno = new ArrayList<PresidentDataView>();
		for(President p : lista) {
			retorno.add(PresidentDataView.createFromPresident(p));
		}
		return retorno;
	}
}
