package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Cor implements Serializable{

	@Id
	@SequenceGenerator(name="seq_cor", sequenceName="seq_cor")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_cor")
	private Integer id;
	
	@Column(unique=true)
	private Integer codigo;
	private String descricao;
	private String codigoExterno;
	
	public Cor() {}
	
	public Cor(Integer codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Cor(String descricao, String codigoExterno, Integer codigo) {
		this(codigo, descricao);
		this.codigoExterno = codigoExterno; 
	}
	
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

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	
	
	
}
