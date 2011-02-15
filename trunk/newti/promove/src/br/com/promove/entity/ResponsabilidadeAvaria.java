package br.com.promove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ResponsabilidadeAvaria {
	@Id
	@SequenceGenerator(name="seq_responsa_avaria", sequenceName="seq_responsa_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_responsa_avaria")
	private Integer id;
	
	private String nome;

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
