package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class OrigemAvaria implements Serializable{
	
	@Id
	@SequenceGenerator(name="seq_origem_avaria", sequenceName="seq_origem_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_origem_avaria")
	private Integer id;
	private Integer codigo;
	private String descricao;
	private String tipo;
	
	@OneToOne
	private Filial filial;
	
	@OneToOne
	private ResponsabilidadeAvaria responsabilidade;
	private String sigla;
	
	public OrigemAvaria() {
	}
	
	public OrigemAvaria(Integer id) {
		this.id = id;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public ResponsabilidadeAvaria getResponsabilidade() {
		return responsabilidade;
	}
	public void setResponsabilidade(ResponsabilidadeAvaria responsabilidade) {
		this.responsabilidade = responsabilidade;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}
	
	
}
