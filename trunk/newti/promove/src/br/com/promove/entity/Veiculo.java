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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Veiculo implements Serializable{
	@Id
	@SequenceGenerator(name="seq_veiculo", sequenceName="seq_veiculo")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_veiculo")
	private Integer id;
	
	private Integer codigo;
	
	@OneToOne
	private Modelo modelo;
	
	@OneToOne
	private Cor cor;
	
	private String chassi;
	
	private String codigoInterno;
	
	@Transient
	private List<Avaria> avarias;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro = new Date();
	
	private String chassiOriginal;
	private Integer tipo;
	private String navio;
	
	public Veiculo() {}
	
	public Veiculo(String chassi) {
		this.chassi = chassi;
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

	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public String getChassiOriginal() {
		return chassiOriginal;
	}

	public void setChassiOriginal(String chassiOriginal) {
		this.chassiOriginal = chassiOriginal;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setNavio(String navio) {
		this.navio = navio;
	}

	public String getNavio() {
		return navio;
	}

	public List<Avaria> getAvarias() {
		return avarias;
	}

	public void setAvarias(List<Avaria> avarias) {
		this.avarias = avarias;
	}
	
	
	
	
}
