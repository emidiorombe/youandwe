package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class VeiculoCtrc implements Serializable{
	@Id
	@SequenceGenerator(name="seq_veiculo_ctrc", sequenceName="seq_veiculo_ctrc")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_veiculo_ctrc")
	private Integer id;
	
	@ManyToOne(targetEntity=Avaria.class)
	private Ctrc ctrc;
	private Integer inconsistencia;
	private String msgErro;
	private String chassiInvalido;

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
	
	
	
}
