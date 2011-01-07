package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class LocalAvaria implements Serializable{
	
	
	@Id
	@SequenceGenerator(name="seq_local_avaria", sequenceName="seq_local_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_local_avaria")
	private Integer id;
	private Integer codigo;
	private String descricao;
	private Boolean acessorio = false;
	private static final long serialVersionUID = 5053227920409244439L;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Boolean getAcessorio() {
		return acessorio;
	}
	public void setAcessorio(Boolean acessorio) {
		this.acessorio = acessorio;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

}
