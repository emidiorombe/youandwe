package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TipoVeiculo implements Serializable {
	@Id
	@SequenceGenerator(name="seq_tipo_veiculo", sequenceName="seq_tipo_veiculo")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_tipo_veiculo")
	private Integer id;
	private String nome;
	
	public TipoVeiculo() {}
	
	public TipoVeiculo(String nome) {
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
	
}
