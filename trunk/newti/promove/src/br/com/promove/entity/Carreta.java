package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Carreta implements Serializable{

	@Id
	@SequenceGenerator(name="seq_carreta", sequenceName="seq_carreta")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_carreta")
	private Integer id;
	
	@Column(unique=true)
	private String codigo;
	private String placa;
	private Boolean ativo = true;
	
	public Carreta() {}
	
	public Carreta(String codigo, String placa, Boolean ativo) {
		super();
		this.codigo = codigo;
		this.placa = placa;
		this.ativo = ativo;
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

	@Override
	public String toString() {
		try {
			return placa;
		} catch (Exception e) {
			return "";
		}
	}
}
