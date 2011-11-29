package br.com.promove.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class VeiculoCtrc implements Serializable{
	@Id
	@SequenceGenerator(name="seq_veiculo_ctrc", sequenceName="seq_veiculo_ctrc")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_veiculo_ctrc")
	private Integer id;
	
	@ManyToOne(targetEntity=Ctrc.class)
	private Ctrc ctrc;
	
	@ManyToOne(targetEntity=Veiculo.class)
	private Veiculo veiculo;
	
	private Integer inconsistencia;
	private String msgErro;
	private String chassiInvalido;
	private String modelo;
	private String numeroNF;
	private String serieNF;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNF = new Date();
	
	private Double valorMercadoria;
	private String chassi;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCtrc(Ctrc ctrc) {
		this.ctrc = ctrc;
	}

	public Ctrc getCtrc() {
		return ctrc;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public void setInconsistencia(Integer inconsistencia) {
		this.inconsistencia = inconsistencia;
	}

	public Integer getInconsistencia() {
		return inconsistencia;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setChassiInvalido(String chassiInvalido) {
		this.chassiInvalido = chassiInvalido;
	}

	public String getChassiInvalido() {
		return chassiInvalido;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setNumeroNF(String numeroNF) {
		this.numeroNF = numeroNF;
	}

	public String getNumeroNF() {
		return numeroNF;
	}

	public void setSerieNF(String serieNF) {
		this.serieNF = serieNF;
	}

	public String getSerieNF() {
		return serieNF;
	}

	public void setDataNF(Date dataNF) {
		this.dataNF = dataNF;
	}

	public Date getDataNF() {
		return dataNF;
	}

	public void setValorMercadoria(Double valorMercadoria) {
		this.valorMercadoria = valorMercadoria;
	}

	public Double getValorMercadoria() {
		return valorMercadoria;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getChassi() {
		return chassi;
	}

}
