package br.com.promove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Modelo {

	@Id
	@SequenceGenerator(name="seq_modelo", sequenceName="seq_modelo")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_modelo")
	private Integer id;
	private Integer codigo;
	private String descricao;
	
	@OneToOne
	private Fabricante fabricante;
	
	public Modelo() {}
	
	public Modelo(Integer codigo, String descricao, Fabricante fabricante) {
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

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	
}
