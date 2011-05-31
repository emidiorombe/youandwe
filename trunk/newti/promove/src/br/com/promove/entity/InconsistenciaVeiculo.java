package br.com.promove.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class InconsistenciaVeiculo implements Serializable{
	@Id
	@SequenceGenerator(name="seq_erro_veiculo", sequenceName="seq_erro_veiculo")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_erro_veiculo")
	private Integer id;
	
	private Integer codigo;
	
	@OneToOne
	private Modelo modelo;

	@OneToOne
	private Cor cor;
	
	private String chassi;
	
	private String codigoInterno;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro = new Date();
	
	private String chassiOriginal;
	
	private String msgErro;
	
	private Integer tipo;

	private String corInvalida;

	private String modeloInvalido;
	
	private String navio;
	
	@Transient
	private Veiculo veiculo;
	
	public InconsistenciaVeiculo() {}
	
	public InconsistenciaVeiculo(Veiculo v, String msgErro) {
		this.chassi = v.getChassi();
		this.modelo = v.getModelo();
		this.cor = v.getCor();
		this.codigoInterno = v.getCodigoInterno();
		this.tipo = v.getTipo();
		this.navio = v.getNavio();
		this.msgErro = msgErro;
		
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return chassi;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(String codigoExterno) {
		this.codigoInterno = codigoExterno;
	}

	public String getChassiOriginal() {
		return chassiOriginal;
	}

	public void setChassiOriginal(String chassiOriginal) {
		this.chassiOriginal = chassiOriginal;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setCorInvalida(String corInvalida) {
		this.corInvalida = corInvalida;
	}
	
	public String getCorInvalida() {
		return corInvalida;
	}

	public void setModeloInvalido(String modeloInvalido) {
		this.modeloInvalido = modeloInvalido;
	}
	
	public String getModeloInvalido() {
		return modeloInvalido;
	}

	public void setNavio(String navio) {
		this.navio = navio;
	}

	public String getNavio() {
		return navio;
	}

	public Veiculo getVeiculo() {
		if(this.veiculo == null) {
			Veiculo v = new Veiculo();
			v.setCodigo(codigo);
			v.setModelo(modelo);
			v.setCor(cor);
			v.setChassi(chassi);
			v.setChassiOriginal(chassiOriginal);
			v.setCodigoInterno(codigoInterno);
			v.setDataCadastro(dataCadastro);
			v.setTipo(tipo);
			v.setNavio(navio);
			this.veiculo = v;
		}
		return veiculo;
	}

}
