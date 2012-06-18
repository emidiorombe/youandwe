package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Frota implements Serializable{

	@Id
	@SequenceGenerator(name="seq_frota", sequenceName="seq_frota")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_frota")
	private Integer id;
	
	@Column(unique=true)
	private String codigo;
	private String placa;
	private Boolean ativo = true;
	
	//@OneToOne
	//private Motorista motorista;
	
	//@OneToOne
	//private Carreta carreta;
	
	public Frota() {}
	
	public Frota(String codigo, String placa, Boolean ativo) { //, Carreta carreta, Motorista motorista
		super();
		this.codigo = codigo;
		this.placa = placa;
		this.ativo = ativo;
		//this.carreta = carreta;
		//this.motorista = motorista;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	/*
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Motorista getMotorista() {
		return motorista;
	}
	
	public void setCarreta(Carreta carreta) {
		this.carreta = carreta;
	}

	public Carreta getCarreta() {
		return carreta;
	}
	 */

	@Override
	public String toString() {
		try {
			return placa;
		} catch (Exception e) {
			return "";
		}
	}

}
