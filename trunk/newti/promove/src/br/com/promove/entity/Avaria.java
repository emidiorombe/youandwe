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
public class Avaria implements Serializable{
	
	@Id
	@SequenceGenerator(name="seq_avaria", sequenceName="seq_avaria")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_avaria")
	private Integer id;
	
	@ManyToOne(targetEntity=Veiculo.class)
	@Fetch(FetchMode.JOIN)
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
	
	@Transient
	private String modelo;
	
	@OneToOne
	private NivelAvaria nivel;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro = new Date();
	
	private String chassiGravado;
	private String etiqueta;
	private String autoDestrutivel;

	@OneToOne
	private Motorista motorista;
	
	@OneToOne
	private Frota frota;
	
	@OneToOne
	private Carreta carreta;
	
	private String chassiOriginal;
	private String arquivo;
	private String nomeMotorista;

	@OneToOne
	private StatusAvaria status;

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

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setNivel(NivelAvaria nivel) {
		this.nivel = nivel;
	}

	public NivelAvaria getNivel() {
		return nivel;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setChassiGravado(String chassiGravado) {
		this.chassiGravado = chassiGravado;
	}

	public String getChassiGravado() {
		return chassiGravado;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setAutoDestrutivel(String autoDestrutivel) {
		this.autoDestrutivel = autoDestrutivel;
	}

	public String getAutoDestrutivel() {
		return autoDestrutivel;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Motorista getMotorista() {
		return motorista;
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

	public void setChassiOriginal(String chassiOriginal) {
		this.chassiOriginal = chassiOriginal;
	}

	public String getChassiOriginal() {
		return chassiOriginal;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}

	public String getNomeMotorista() {
		return nomeMotorista;
	}

	public void setStatus(StatusAvaria status) {
		this.status = status;
	}

	public StatusAvaria getStatus() {
		return status;
	}
}
