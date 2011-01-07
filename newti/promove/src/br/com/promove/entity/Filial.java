package br.com.promove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Filial {

	@Id
	@SequenceGenerator(name="seq_filial", sequenceName="seq_filial")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_filial")
	private Integer id;
	private Integer codigo;
	private String nome;
	private String sigla;
	private Integer codigoExterno;
	
	public Filial() {}
	
	
	public Filial(Integer codigo, String nome, String sigla,
			Integer codigoExterno) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.sigla = sigla;
		this.codigoExterno = codigoExterno;
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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Integer getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(Integer codigoExterno) {
		this.codigoExterno = codigoExterno;
	}


	@Override
	public String toString() {
		return codigo + " - " + nome ;
	}
	
	
	
	
}
