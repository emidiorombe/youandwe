package br.com.promove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ExtensaoAvaria {
	@Id
	@SequenceGenerator(name="seq_extensao_avaria", sequenceName="seq_extensao_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_extensao_avaria")
	private Integer id;
	private Integer codigo;
	private String descricao;
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
	
	
}
