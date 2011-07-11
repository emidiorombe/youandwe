package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class NivelAvaria implements Serializable{
	@Id
	@SequenceGenerator(name="seq_nivel_avaria", sequenceName="seq_nivel_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_nivel_avaria")
	private Integer id;
	private String nome;
	
	public NivelAvaria() {}
	
	public NivelAvaria(String nome) {
		super();
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
