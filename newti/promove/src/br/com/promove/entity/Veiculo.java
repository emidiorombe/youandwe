package br.com.promove.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Veiculo implements Serializable{
	@Id
	@SequenceGenerator(name="seq_veiculo", sequenceName="seq_veiculo")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_veiculo")
	private Integer id;
	
	@OneToOne
	private Modelo modelo;
	
	@OneToMany(mappedBy="veiculo", targetEntity=Avaria.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Avaria> avarias;
	
	@OneToOne
	private Cor cor;
	
	private String chassi;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Avaria> getAvarias() {
		return avarias;
	}

	public void setAvarias(List<Avaria> avarias) {
		this.avarias = avarias;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	
}
