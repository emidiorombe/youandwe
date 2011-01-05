package br.com.promove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Cor {

	@Id
	@SequenceGenerator(name="seq_cor", sequenceName="seq_cor")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_cor")
	private Integer id;
	private Integer codigo;
	private String descricao;
	
	public Cor() {}
	
	public Cor(Integer codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
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
	
	
}