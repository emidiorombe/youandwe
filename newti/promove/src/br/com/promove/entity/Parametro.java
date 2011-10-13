package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Parametro implements Serializable {
	@Id
	@SequenceGenerator(name="seq_parametro", sequenceName="seq_parametro")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_parametro")
	private Integer id;
	private String chave;
	private String valor;
	
	public Parametro() {}
	
	public Parametro(String chave, String valor) {
		super();
		this.chave = chave;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
