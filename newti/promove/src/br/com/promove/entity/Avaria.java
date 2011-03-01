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

@Entity
public class Avaria implements Serializable{
	
	@Id
	@SequenceGenerator(name="seq_avaria", sequenceName="seq_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_avaria")
	private Integer id;
	
	@ManyToOne(targetEntity=Veiculo.class)
	private Veiculo veiculo;
	
	@OneToOne
	private TipoAvaria tipo;
	
	@OneToOne
	private LocalAvaria local;
	
	@OneToOne
	private OrigemAvaria origem;
	
	@OneToOne
	private ExtensaoAvaria extensao;
	
	@OneToOne
	private Clima clima;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLancamento = new Date();
	
	@OneToMany(mappedBy="avaria", targetEntity=FotoAvaria.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<FotoAvaria> fotos;
	
	@OneToOne
	private Usuario usuario;
	
	private String observacao;
	
	private String hora;
	
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

	public LocalAvaria getLocal() {
		return local;
	}

	public void setLocal(LocalAvaria local) {
		this.local = local;
	}

	public OrigemAvaria getOrigem() {
		return origem;
	}

	public void setOrigem(OrigemAvaria origem) {
		this.origem = origem;
	}

	public ExtensaoAvaria getExtensao() {
		return extensao;
	}

	public void setExtensao(ExtensaoAvaria extensao) {
		this.extensao = extensao;
	}

	public Clima getClima() {
		return clima;
	}

	public void setClima(Clima clima) {
		this.clima = clima;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<FotoAvaria> getFotos() {
		return fotos;
	}

	public void setFotos(List<FotoAvaria> fotos) {
		this.fotos = fotos;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
