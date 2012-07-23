package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



@Entity
public class TipoAvaria implements Serializable{
	@Id
	@SequenceGenerator(name="seq_tipo_avaria", sequenceName="seq_tipo_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_tipo_avaria")
	private Integer id;
	private Integer codigo;
	private String descricao;
	private Boolean falta = false;
	private Boolean perdaTotal = false;
	private Boolean movimentacao = false;
	private String descricaoSeguradora;

	public TipoAvaria(){}
	
	public TipoAvaria(Integer codigo, String descricao, Boolean falta,
			Boolean perdaTotal, Boolean movimentacao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.falta = falta;
		this.perdaTotal = perdaTotal;
		this.movimentacao = movimentacao;
	}
	
	
	public TipoAvaria(Integer id, Integer codigo, String descricao,
			Boolean falta, Boolean perdaTotal, Boolean movimentacao) {
		this(codigo, descricao, falta, perdaTotal, movimentacao);
		this.id = id;
		
	}

	public TipoAvaria(Integer id) {
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
	public Boolean getFalta() {
		return falta;
	}
	public void setFalta(Boolean falta) {
		this.falta = falta;
	}
	public Boolean getPerdaTotal() {
		return perdaTotal;
	}
	public void setPerdaTotal(Boolean perdaTotal) {
		this.perdaTotal = perdaTotal;
	}

	public void setMovimentacao(Boolean movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Boolean getMovimentacao() {
		return movimentacao;
	}

	public void setDescricaoSeguradora(String descricaoSeguradora) {
		this.descricaoSeguradora = descricaoSeguradora;
	}

	public String getDescricaoSeguradora() {
		return descricaoSeguradora;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	
	
}
