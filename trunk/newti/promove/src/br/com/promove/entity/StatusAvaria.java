package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class StatusAvaria implements Serializable {
	@Id
	@SequenceGenerator(name="seq_status_avaria", sequenceName="seq_status_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_status_avaria")
	private Integer id;
	private String nome;
	
	public StatusAvaria() {}
	
	public StatusAvaria(String nome) {
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
