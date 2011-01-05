package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Avaria implements Serializable{
	
	@Id
	@SequenceGenerator(name="seq_avaria", sequenceName="seq_avaria")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_avaria")
	private Integer id;
	
	@ManyToOne(targetEntity=Veiculo.class)
	private Veiculo veiculo;
	
	@OneToOne
	private TipoAvaria tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoAvaria getTipo() {
		return tipo;
	}

	public void setTipo(TipoAvaria tipo) {
		this.tipo = tipo;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	
}
