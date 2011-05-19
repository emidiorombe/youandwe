package br.com.promove.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class InconsistenciaCtrc implements Serializable{
	
	@Id
	@SequenceGenerator(name="seq_erro_ctrc", sequenceName="seq_erro_ctrc")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_erro_ctrc")
	private Integer id;
	
	private Integer filial;
	private Integer numero;
	private Integer tipo;
	private String serie;
	
	@OneToOne
	private Transportadora transp;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEmissao = new Date();
	
	private String placaFrota;
	private String placaCarreta;
	private String ufOrigem;
	private String municipioOrigem;
	private String ufDestino;
	private String municipioDestino;
	private Double taxaRct;
	private Double taxaRr;
	private Double taxaRcf;
	private Double taxaFluvial;
	private String msgErro;
	
	public InconsistenciaCtrc() {}
	
	public InconsistenciaCtrc(Ctrc ct, String msgErro) {
		this.filial = ct.getFilial();
		this.numero = ct.getNumero();
		this.tipo = ct.getTipo();
		this.serie = ct.getSerie();
		this.transp = ct.getTransp();
		this.dataEmissao = ct.getDataEmissao();
		this.placaFrota = ct.getPlacaFrota();
		this.placaCarreta = ct.getPlacaCarreta();
		this.ufOrigem = ct.getUfOrigem();
		this.municipioOrigem = ct.getMunicipioOrigem();
		this.ufDestino = ct.getUfDestino();
		this.municipioDestino = ct.getMunicipioDestino();
		this.taxaRct = ct.getTaxaRct();
		this.taxaRr = ct.getTaxaRr();
		this.taxaRcf= ct.getTaxaRcf();
		this.taxaFluvial = ct.getTaxaFluvial();
		this.msgErro = msgErro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFilial(Integer filial) {
		this.filial = filial;
	}

	public Integer getFilial() {
		return filial;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getSerie() {
		return serie;
	}

	public void setTransp(Transportadora transp) {
		this.transp = transp;
	}

	public Transportadora getTransp() {
		return transp;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setPlacaFrota(String placaFrota) {
		this.placaFrota = placaFrota;
	}

	public String getPlacaFrota() {
		return placaFrota;
	}

	public void setPlacaCarreta(String placaCarreta) {
		this.placaCarreta = placaCarreta;
	}

	public String getPlacaCarreta() {
		return placaCarreta;
	}

	public void setUfOrigem(String ufOrigem) {
		this.ufOrigem = ufOrigem;
	}

	public String getUfOrigem() {
		return ufOrigem;
	}

	public void setMunicipioOrigem(String municipioOrigem) {
		this.municipioOrigem = municipioOrigem;
	}

	public String getMunicipioOrigem() {
		return municipioOrigem;
	}

	public void setUfDestino(String ufDestino) {
		this.ufDestino = ufDestino;
	}

	public String getUfDestino() {
		return ufDestino;
	}

	public void setMunicipioDestino(String municipioDestino) {
		this.municipioDestino = municipioDestino;
	}

	public String getMunicipioDestino() {
		return municipioDestino;
	}

	public void setTaxaRct(Double taxaRct) {
		this.taxaRct = taxaRct;
	}

	public Double getTaxaRct() {
		return taxaRct;
	}

	public void setTaxaRr(Double taxaRr) {
		this.taxaRr = taxaRr;
	}

	public Double getTaxaRr() {
		return taxaRr;
	}

	public void setTaxaRcf(Double taxaRcf) {
		this.taxaRcf = taxaRcf;
	}

	public Double getTaxaRcf() {
		return taxaRcf;
	}

	public void setTaxaFluvial(Double taxaFluvial) {
		this.taxaFluvial = taxaFluvial;
	}

	public Double getTaxaFluvial() {
		return taxaFluvial;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public Ctrc getCtrc() {
		Ctrc ct = new Ctrc();
		ct.setFilial(filial);
		ct.setNumero(numero);
		ct.setTipo(tipo);
		ct.setSerie(serie);
		ct.setTransp(transp);
		ct.setDataEmissao(dataEmissao);
		ct.setPlacaFrota(placaFrota);
		ct.setPlacaCarreta(placaCarreta);
		ct.setUfOrigem(ufOrigem);
		ct.setMunicipioOrigem(municipioOrigem);
		ct.setUfDestino(ufDestino);
		ct.setMunicipioDestino(municipioDestino);
		ct.setTaxaRct(taxaRct);
		ct.setTaxaRr(taxaRr);
		ct.setTaxaRcf(taxaRcf);
		ct.setTaxaFluvial(taxaFluvial);
		
		return ct;
	}

}
