package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class FotoAvaria implements Serializable{
	@Id
	@SequenceGenerator(name="seq_foto_avaria", sequenceName="seq_foto_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_foto_avaria")
	private Integer id;
	
	@ManyToOne(targetEntity=Avaria.class)
	private Avaria avaria;
	
	private String nome;
	
	private Integer inconsisctencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Avaria getAvaria() {
		return avaria;
	}

	public void setAvaria(Avaria avaria) {
		this.avaria = avaria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getInconsisctencia() {
		return inconsisctencia;
	}

	public void setInconsisctencia(Integer inconsisctencia) {
		this.inconsisctencia = inconsisctencia;
	}
	
	
	
}
