package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Fabricante implements Serializable {
	
	@Id
	@SequenceGenerator(name="seq_fabricante", sequenceName="seq_fabricante")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_fabricante")
	private Integer id;
	private Integer codigo;
	private String nome;
	
	public Fabricante() {}
	
	public Fabricante(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return codigo + " - " + nome;
	}
	
	
	
	
}
