package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Modelo implements Serializable{

	@Id
	@SequenceGenerator(name="seq_modelo", sequenceName="seq_modelo")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_modelo")
	private Integer id;
	
	@Column(unique=true)
	private String codigo;
	private String descricao;
	private String codigoExternoNacional;
	private String codigoExternoImportacao;
	
	@OneToOne
	private Fabricante fabricante;
	
	public Modelo() {}
	
	public Modelo(String codigo, String descricao, Fabricante fabricante) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.fabricante = fabricante;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public String getCodigoExternoNacional() {
		return codigoExternoNacional;
	}

	public void setCodigoExternoNacional(String codigoExternoNacional) {
		this.codigoExternoNacional = codigoExternoNacional;
	}

	public String getCodigoExternoImportacao() {
		return codigoExternoImportacao;
	}

	public void setCodigoExternoImportacao(String codigoExternoImportacao) {
		this.codigoExternoImportacao = codigoExternoImportacao;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	
	
}
