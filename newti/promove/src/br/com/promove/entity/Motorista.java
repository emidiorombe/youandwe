package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Motorista implements Serializable{
	@Id
	@SequenceGenerator(name="seq_motorista", sequenceName="seq_motorista")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_motorista")
	private Integer id;
	
	private Integer codigo;
	private String nome;
	private String cnh;
	private String rg;
	private String cpf;
	private Boolean ativo;

	@OneToOne
	private Frota frota;
	
	@OneToOne
	private Carreta carreta;
	
	public Motorista() {}
	
	public Motorista(Integer id, String nome, String cnh, String rg, String cpf, Boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.cnh = cnh;
		this.rg = rg;
		this.cpf = cpf;
		this.ativo = ativo;
	}
	
	public Motorista(Integer id, String nome, String cnh, String rg, String cpf, Boolean ativo, Frota frota, Carreta carreta) {
		this(id, nome, cnh, rg, cpf, ativo);
		this.setFrota(frota);
		this.setCarreta(carreta);
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

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getCnh() {
		return cnh;
	}

	public void setNome(String nome) {
		this.cnh = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRg() {
		return rg;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setCarreta(Carreta carreta) {
		this.carreta = carreta;
	}

	public Carreta getCarreta() {
		return carreta;
	}

	@Override
	public String toString() {
		try {
			return nome;
		} catch (Exception e) {
			return "";
		}
	}
}
