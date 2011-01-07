package br.com.promove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TipoUsuario {
	@Id
	@SequenceGenerator(name="seq_tipo_usuario", sequenceName="seq_tipo_usuario")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_tipo_usuario")
	private Integer id;
	private String nome;
	
	public TipoUsuario() {}
	
	public TipoUsuario(String nome) {
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
