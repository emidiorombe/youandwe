package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Transportadora implements Serializable{
	@Id
	@SequenceGenerator(name="seq_transportadora", sequenceName="seq_transportadora")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_transportadora")
	private Integer id;
	
	private Integer codigo;
	private String cnpj;
	private String descricao;
	
	public Transportadora() {}
	
	public Transportadora(Integer id) {
		this.setId(id);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
}
