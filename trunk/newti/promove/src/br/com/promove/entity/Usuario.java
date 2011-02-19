package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Usuario implements Serializable{

	@Id
	@SequenceGenerator(name="seq_usuario", sequenceName="seq_usuario")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_usuario")
	private Integer id;
	private Integer codigo;
	private String nome;
	private String senha;
	private String mail;
	
	@OneToOne
	private TipoUsuario tipo;
	
	@OneToOne
	private Filial filial;
	
	public Usuario() {}
	
	public Usuario(Integer codigo, String nome, String senha, String mail,
			TipoUsuario tipo, Filial filial) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.senha = senha;
		this.mail = mail;
		this.tipo = tipo;
		this.filial = filial;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Override
	public String toString() {
		return  nome;
	}
	
	
	
}
