/**
 * CtrcAverbar.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.promove.importacao.ws.soap;

public class CtrcAverbar  implements java.io.Serializable {
    private java.lang.String cnpj_transportadora;

    private int filial;

    private long ctrc_numero;

    private int tipo;

    private java.lang.String ctrc_serie;

    private java.lang.String tipo_nome;

    private java.lang.String nome_emitente;

    private java.lang.String municipio_emitente;

    private java.lang.String uf_emitente;

    private java.util.Calendar ctrc_data;

    private java.util.Calendar registro;

    private java.lang.String codigo_frota;

    private java.lang.String placa_frota;

    private java.lang.String codigo_carreta;

    private java.lang.String placa_carreta;

    private java.lang.String uf_origem;

    private java.lang.String municipio_origem;

    private java.lang.String uf_destino;

    private java.lang.String municipio_destino;

    private java.lang.String cnpj_fabricante;

    private java.lang.String nome_fabricante;

    private java.lang.String cnpj_seguradora;

    private java.lang.String nome_seguradora;

    private double taxa_rct;

    private double taxa_rr;

    private double taxa_rcf;

    private double taxa_fluvial;

    private java.lang.String tipo_seguro;

    private java.lang.String tipo_transporte;

    private double valor_mercadoria;

    public CtrcAverbar() {
    }

    public CtrcAverbar(
           java.lang.String cnpj_transportadora,
           int filial,
           long ctrc_numero,
           int tipo,
           java.lang.String ctrc_serie,
           java.lang.String tipo_nome,
           java.lang.String nome_emitente,
           java.lang.String municipio_emitente,
           java.lang.String uf_emitente,
           java.util.Calendar ctrc_data,
           java.util.Calendar registro,
           java.lang.String codigo_frota,
           java.lang.String placa_frota,
           java.lang.String codigo_carreta,
           java.lang.String placa_carreta,
           java.lang.String uf_origem,
           java.lang.String municipio_origem,
           java.lang.String uf_destino,
           java.lang.String municipio_destino,
           java.lang.String cnpj_fabricante,
           java.lang.String nome_fabricante,
           java.lang.String cnpj_seguradora,
           java.lang.String nome_seguradora,
           double taxa_rct,
           double taxa_rr,
           double taxa_rcf,
           double taxa_fluvial,
           java.lang.String tipo_seguro,
           java.lang.String tipo_transporte,
           double valor_mercadoria) {
           this.cnpj_transportadora = cnpj_transportadora;
           this.filial = filial;
           this.ctrc_numero = ctrc_numero;
           this.tipo = tipo;
           this.ctrc_serie = ctrc_serie;
           this.tipo_nome = tipo_nome;
           this.nome_emitente = nome_emitente;
           this.municipio_emitente = municipio_emitente;
           this.uf_emitente = uf_emitente;
           this.ctrc_data = ctrc_data;
           this.registro = registro;
           this.codigo_frota = codigo_frota;
           this.placa_frota = placa_frota;
           this.codigo_carreta = codigo_carreta;
           this.placa_carreta = placa_carreta;
           this.uf_origem = uf_origem;
           this.municipio_origem = municipio_origem;
           this.uf_destino = uf_destino;
           this.municipio_destino = municipio_destino;
           this.cnpj_fabricante = cnpj_fabricante;
           this.nome_fabricante = nome_fabricante;
           this.cnpj_seguradora = cnpj_seguradora;
           this.nome_seguradora = nome_seguradora;
           this.taxa_rct = taxa_rct;
           this.taxa_rr = taxa_rr;
           this.taxa_rcf = taxa_rcf;
           this.taxa_fluvial = taxa_fluvial;
           this.tipo_seguro = tipo_seguro;
           this.tipo_transporte = tipo_transporte;
           this.valor_mercadoria = valor_mercadoria;
    }


    /**
     * Gets the cnpj_transportadora value for this CtrcAverbar.
     * 
     * @return cnpj_transportadora
     */
    public java.lang.String getCnpj_transportadora() {
        return cnpj_transportadora;
    }


    /**
     * Sets the cnpj_transportadora value for this CtrcAverbar.
     * 
     * @param cnpj_transportadora
     */
    public void setCnpj_transportadora(java.lang.String cnpj_transportadora) {
        this.cnpj_transportadora = cnpj_transportadora;
    }


    /**
     * Gets the filial value for this CtrcAverbar.
     * 
     * @return filial
     */
    public int getFilial() {
        return filial;
    }


    /**
     * Sets the filial value for this CtrcAverbar.
     * 
     * @param filial
     */
    public void setFilial(int filial) {
        this.filial = filial;
    }


    /**
     * Gets the ctrc_numero value for this CtrcAverbar.
     * 
     * @return ctrc_numero
     */
    public long getCtrc_numero() {
        return ctrc_numero;
    }


    /**
     * Sets the ctrc_numero value for this CtrcAverbar.
     * 
     * @param ctrc_numero
     */
    public void setCtrc_numero(long ctrc_numero) {
        this.ctrc_numero = ctrc_numero;
    }


    /**
     * Gets the tipo value for this CtrcAverbar.
     * 
     * @return tipo
     */
    public int getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this CtrcAverbar.
     * 
     * @param tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the ctrc_serie value for this CtrcAverbar.
     * 
     * @return ctrc_serie
     */
    public java.lang.String getCtrc_serie() {
        return ctrc_serie;
    }


    /**
     * Sets the ctrc_serie value for this CtrcAverbar.
     * 
     * @param ctrc_serie
     */
    public void setCtrc_serie(java.lang.String ctrc_serie) {
        this.ctrc_serie = ctrc_serie;
    }


    /**
     * Gets the tipo_nome value for this CtrcAverbar.
     * 
     * @return tipo_nome
     */
    public java.lang.String getTipo_nome() {
        return tipo_nome;
    }


    /**
     * Sets the tipo_nome value for this CtrcAverbar.
     * 
     * @param tipo_nome
     */
    public void setTipo_nome(java.lang.String tipo_nome) {
        this.tipo_nome = tipo_nome;
    }


    /**
     * Gets the nome_emitente value for this CtrcAverbar.
     * 
     * @return nome_emitente
     */
    public java.lang.String getNome_emitente() {
        return nome_emitente;
    }


    /**
     * Sets the nome_emitente value for this CtrcAverbar.
     * 
     * @param nome_emitente
     */
    public void setNome_emitente(java.lang.String nome_emitente) {
        this.nome_emitente = nome_emitente;
    }


    /**
     * Gets the municipio_emitente value for this CtrcAverbar.
     * 
     * @return municipio_emitente
     */
    public java.lang.String getMunicipio_emitente() {
        return municipio_emitente;
    }


    /**
     * Sets the municipio_emitente value for this CtrcAverbar.
     * 
     * @param municipio_emitente
     */
    public void setMunicipio_emitente(java.lang.String municipio_emitente) {
        this.municipio_emitente = municipio_emitente;
    }


    /**
     * Gets the uf_emitente value for this CtrcAverbar.
     * 
     * @return uf_emitente
     */
    public java.lang.String getUf_emitente() {
        return uf_emitente;
    }


    /**
     * Sets the uf_emitente value for this CtrcAverbar.
     * 
     * @param uf_emitente
     */
    public void setUf_emitente(java.lang.String uf_emitente) {
        this.uf_emitente = uf_emitente;
    }


    /**
     * Gets the ctrc_data value for this CtrcAverbar.
     * 
     * @return ctrc_data
     */
    public java.util.Calendar getCtrc_data() {
        return ctrc_data;
    }


    /**
     * Sets the ctrc_data value for this CtrcAverbar.
     * 
     * @param ctrc_data
     */
    public void setCtrc_data(java.util.Calendar ctrc_data) {
        this.ctrc_data = ctrc_data;
    }


    /**
     * Gets the registro value for this CtrcAverbar.
     * 
     * @return registro
     */
    public java.util.Calendar getRegistro() {
        return registro;
    }


    /**
     * Sets the registro value for this CtrcAverbar.
     * 
     * @param registro
     */
    public void setRegistro(java.util.Calendar registro) {
        this.registro = registro;
    }


    /**
     * Gets the codigo_frota value for this CtrcAverbar.
     * 
     * @return codigo_frota
     */
    public java.lang.String getCodigo_frota() {
        return codigo_frota;
    }


    /**
     * Sets the codigo_frota value for this CtrcAverbar.
     * 
     * @param codigo_frota
     */
    public void setCodigo_frota(java.lang.String codigo_frota) {
        this.codigo_frota = codigo_frota;
    }


    /**
     * Gets the placa_frota value for this CtrcAverbar.
     * 
     * @return placa_frota
     */
    public java.lang.String getPlaca_frota() {
        return placa_frota;
    }


    /**
     * Sets the placa_frota value for this CtrcAverbar.
     * 
     * @param placa_frota
     */
    public void setPlaca_frota(java.lang.String placa_frota) {
        this.placa_frota = placa_frota;
    }


    /**
     * Gets the codigo_carreta value for this CtrcAverbar.
     * 
     * @return codigo_carreta
     */
    public java.lang.String getCodigo_carreta() {
        return codigo_carreta;
    }


    /**
     * Sets the codigo_carreta value for this CtrcAverbar.
     * 
     * @param codigo_carreta
     */
    public void setCodigo_carreta(java.lang.String codigo_carreta) {
        this.codigo_carreta = codigo_carreta;
    }


    /**
     * Gets the placa_carreta value for this CtrcAverbar.
     * 
     * @return placa_carreta
     */
    public java.lang.String getPlaca_carreta() {
        return placa_carreta;
    }


    /**
     * Sets the placa_carreta value for this CtrcAverbar.
     * 
     * @param placa_carreta
     */
    public void setPlaca_carreta(java.lang.String placa_carreta) {
        this.placa_carreta = placa_carreta;
    }


    /**
     * Gets the uf_origem value for this CtrcAverbar.
     * 
     * @return uf_origem
     */
    public java.lang.String getUf_origem() {
        return uf_origem;
    }


    /**
     * Sets the uf_origem value for this CtrcAverbar.
     * 
     * @param uf_origem
     */
    public void setUf_origem(java.lang.String uf_origem) {
        this.uf_origem = uf_origem;
    }


    /**
     * Gets the municipio_origem value for this CtrcAverbar.
     * 
     * @return municipio_origem
     */
    public java.lang.String getMunicipio_origem() {
        return municipio_origem;
    }


    /**
     * Sets the municipio_origem value for this CtrcAverbar.
     * 
     * @param municipio_origem
     */
    public void setMunicipio_origem(java.lang.String municipio_origem) {
        this.municipio_origem = municipio_origem;
    }


    /**
     * Gets the uf_destino value for this CtrcAverbar.
     * 
     * @return uf_destino
     */
    public java.lang.String getUf_destino() {
        return uf_destino;
    }


    /**
     * Sets the uf_destino value for this CtrcAverbar.
     * 
     * @param uf_destino
     */
    public void setUf_destino(java.lang.String uf_destino) {
        this.uf_destino = uf_destino;
    }


    /**
     * Gets the municipio_destino value for this CtrcAverbar.
     * 
     * @return municipio_destino
     */
    public java.lang.String getMunicipio_destino() {
        return municipio_destino;
    }


    /**
     * Sets the municipio_destino value for this CtrcAverbar.
     * 
     * @param municipio_destino
     */
    public void setMunicipio_destino(java.lang.String municipio_destino) {
        this.municipio_destino = municipio_destino;
    }


    /**
     * Gets the cnpj_fabricante value for this CtrcAverbar.
     * 
     * @return cnpj_fabricante
     */
    public java.lang.String getCnpj_fabricante() {
        return cnpj_fabricante;
    }


    /**
     * Sets the cnpj_fabricante value for this CtrcAverbar.
     * 
     * @param cnpj_fabricante
     */
    public void setCnpj_fabricante(java.lang.String cnpj_fabricante) {
        this.cnpj_fabricante = cnpj_fabricante;
    }


    /**
     * Gets the nome_fabricante value for this CtrcAverbar.
     * 
     * @return nome_fabricante
     */
    public java.lang.String getNome_fabricante() {
        return nome_fabricante;
    }


    /**
     * Sets the nome_fabricante value for this CtrcAverbar.
     * 
     * @param nome_fabricante
     */
    public void setNome_fabricante(java.lang.String nome_fabricante) {
        this.nome_fabricante = nome_fabricante;
    }


    /**
     * Gets the cnpj_seguradora value for this CtrcAverbar.
     * 
     * @return cnpj_seguradora
     */
    public java.lang.String getCnpj_seguradora() {
        return cnpj_seguradora;
    }


    /**
     * Sets the cnpj_seguradora value for this CtrcAverbar.
     * 
     * @param cnpj_seguradora
     */
    public void setCnpj_seguradora(java.lang.String cnpj_seguradora) {
        this.cnpj_seguradora = cnpj_seguradora;
    }


    /**
     * Gets the nome_seguradora value for this CtrcAverbar.
     * 
     * @return nome_seguradora
     */
    public java.lang.String getNome_seguradora() {
        return nome_seguradora;
    }


    /**
     * Sets the nome_seguradora value for this CtrcAverbar.
     * 
     * @param nome_seguradora
     */
    public void setNome_seguradora(java.lang.String nome_seguradora) {
        this.nome_seguradora = nome_seguradora;
    }


    /**
     * Gets the taxa_rct value for this CtrcAverbar.
     * 
     * @return taxa_rct
     */
    public double getTaxa_rct() {
        return taxa_rct;
    }


    /**
     * Sets the taxa_rct value for this CtrcAverbar.
     * 
     * @param taxa_rct
     */
    public void setTaxa_rct(double taxa_rct) {
        this.taxa_rct = taxa_rct;
    }


    /**
     * Gets the taxa_rr value for this CtrcAverbar.
     * 
     * @return taxa_rr
     */
    public double getTaxa_rr() {
        return taxa_rr;
    }


    /**
     * Sets the taxa_rr value for this CtrcAverbar.
     * 
     * @param taxa_rr
     */
    public void setTaxa_rr(double taxa_rr) {
        this.taxa_rr = taxa_rr;
    }


    /**
     * Gets the taxa_rcf value for this CtrcAverbar.
     * 
     * @return taxa_rcf
     */
    public double getTaxa_rcf() {
        return taxa_rcf;
    }


    /**
     * Sets the taxa_rcf value for this CtrcAverbar.
     * 
     * @param taxa_rcf
     */
    public void setTaxa_rcf(double taxa_rcf) {
        this.taxa_rcf = taxa_rcf;
    }


    /**
     * Gets the taxa_fluvial value for this CtrcAverbar.
     * 
     * @return taxa_fluvial
     */
    public double getTaxa_fluvial() {
        return taxa_fluvial;
    }


    /**
     * Sets the taxa_fluvial value for this CtrcAverbar.
     * 
     * @param taxa_fluvial
     */
    public void setTaxa_fluvial(double taxa_fluvial) {
        this.taxa_fluvial = taxa_fluvial;
    }


    /**
     * Gets the tipo_seguro value for this CtrcAverbar.
     * 
     * @return tipo_seguro
     */
    public java.lang.String getTipo_seguro() {
        return tipo_seguro;
    }


    /**
     * Sets the tipo_seguro value for this CtrcAverbar.
     * 
     * @param tipo_seguro
     */
    public void setTipo_seguro(java.lang.String tipo_seguro) {
        this.tipo_seguro = tipo_seguro;
    }


    /**
     * Gets the tipo_transporte value for this CtrcAverbar.
     * 
     * @return tipo_transporte
     */
    public java.lang.String getTipo_transporte() {
        return tipo_transporte;
    }


    /**
     * Sets the tipo_transporte value for this CtrcAverbar.
     * 
     * @param tipo_transporte
     */
    public void setTipo_transporte(java.lang.String tipo_transporte) {
        this.tipo_transporte = tipo_transporte;
    }


    /**
     * Gets the valor_mercadoria value for this CtrcAverbar.
     * 
     * @return valor_mercadoria
     */
    public double getValor_mercadoria() {
        return valor_mercadoria;
    }


    /**
     * Sets the valor_mercadoria value for this CtrcAverbar.
     * 
     * @param valor_mercadoria
     */
    public void setValor_mercadoria(double valor_mercadoria) {
        this.valor_mercadoria = valor_mercadoria;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtrcAverbar)) return false;
        CtrcAverbar other = (CtrcAverbar) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cnpj_transportadora==null && other.getCnpj_transportadora()==null) || 
             (this.cnpj_transportadora!=null &&
              this.cnpj_transportadora.equals(other.getCnpj_transportadora()))) &&
            this.filial == other.getFilial() &&
            this.ctrc_numero == other.getCtrc_numero() &&
            this.tipo == other.getTipo() &&
            ((this.ctrc_serie==null && other.getCtrc_serie()==null) || 
             (this.ctrc_serie!=null &&
              this.ctrc_serie.equals(other.getCtrc_serie()))) &&
            ((this.tipo_nome==null && other.getTipo_nome()==null) || 
             (this.tipo_nome!=null &&
              this.tipo_nome.equals(other.getTipo_nome()))) &&
            ((this.nome_emitente==null && other.getNome_emitente()==null) || 
             (this.nome_emitente!=null &&
              this.nome_emitente.equals(other.getNome_emitente()))) &&
            ((this.municipio_emitente==null && other.getMunicipio_emitente()==null) || 
             (this.municipio_emitente!=null &&
              this.municipio_emitente.equals(other.getMunicipio_emitente()))) &&
            ((this.uf_emitente==null && other.getUf_emitente()==null) || 
             (this.uf_emitente!=null &&
              this.uf_emitente.equals(other.getUf_emitente()))) &&
            ((this.ctrc_data==null && other.getCtrc_data()==null) || 
             (this.ctrc_data!=null &&
              this.ctrc_data.equals(other.getCtrc_data()))) &&
            ((this.registro==null && other.getRegistro()==null) || 
             (this.registro!=null &&
              this.registro.equals(other.getRegistro()))) &&
            ((this.codigo_frota==null && other.getCodigo_frota()==null) || 
             (this.codigo_frota!=null &&
              this.codigo_frota.equals(other.getCodigo_frota()))) &&
            ((this.placa_frota==null && other.getPlaca_frota()==null) || 
             (this.placa_frota!=null &&
              this.placa_frota.equals(other.getPlaca_frota()))) &&
            ((this.codigo_carreta==null && other.getCodigo_carreta()==null) || 
             (this.codigo_carreta!=null &&
              this.codigo_carreta.equals(other.getCodigo_carreta()))) &&
            ((this.placa_carreta==null && other.getPlaca_carreta()==null) || 
             (this.placa_carreta!=null &&
              this.placa_carreta.equals(other.getPlaca_carreta()))) &&
            ((this.uf_origem==null && other.getUf_origem()==null) || 
             (this.uf_origem!=null &&
              this.uf_origem.equals(other.getUf_origem()))) &&
            ((this.municipio_origem==null && other.getMunicipio_origem()==null) || 
             (this.municipio_origem!=null &&
              this.municipio_origem.equals(other.getMunicipio_origem()))) &&
            ((this.uf_destino==null && other.getUf_destino()==null) || 
             (this.uf_destino!=null &&
              this.uf_destino.equals(other.getUf_destino()))) &&
            ((this.municipio_destino==null && other.getMunicipio_destino()==null) || 
             (this.municipio_destino!=null &&
              this.municipio_destino.equals(other.getMunicipio_destino()))) &&
            ((this.cnpj_fabricante==null && other.getCnpj_fabricante()==null) || 
             (this.cnpj_fabricante!=null &&
              this.cnpj_fabricante.equals(other.getCnpj_fabricante()))) &&
            ((this.nome_fabricante==null && other.getNome_fabricante()==null) || 
             (this.nome_fabricante!=null &&
              this.nome_fabricante.equals(other.getNome_fabricante()))) &&
            ((this.cnpj_seguradora==null && other.getCnpj_seguradora()==null) || 
             (this.cnpj_seguradora!=null &&
              this.cnpj_seguradora.equals(other.getCnpj_seguradora()))) &&
            ((this.nome_seguradora==null && other.getNome_seguradora()==null) || 
             (this.nome_seguradora!=null &&
              this.nome_seguradora.equals(other.getNome_seguradora()))) &&
            this.taxa_rct == other.getTaxa_rct() &&
            this.taxa_rr == other.getTaxa_rr() &&
            this.taxa_rcf == other.getTaxa_rcf() &&
            this.taxa_fluvial == other.getTaxa_fluvial() &&
            ((this.tipo_seguro==null && other.getTipo_seguro()==null) || 
             (this.tipo_seguro!=null &&
              this.tipo_seguro.equals(other.getTipo_seguro()))) &&
            ((this.tipo_transporte==null && other.getTipo_transporte()==null) || 
             (this.tipo_transporte!=null &&
              this.tipo_transporte.equals(other.getTipo_transporte()))) &&
            this.valor_mercadoria == other.getValor_mercadoria();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCnpj_transportadora() != null) {
            _hashCode += getCnpj_transportadora().hashCode();
        }
        _hashCode += getFilial();
        _hashCode += new Long(getCtrc_numero()).hashCode();
        _hashCode += getTipo();
        if (getCtrc_serie() != null) {
            _hashCode += getCtrc_serie().hashCode();
        }
        if (getTipo_nome() != null) {
            _hashCode += getTipo_nome().hashCode();
        }
        if (getNome_emitente() != null) {
            _hashCode += getNome_emitente().hashCode();
        }
        if (getMunicipio_emitente() != null) {
            _hashCode += getMunicipio_emitente().hashCode();
        }
        if (getUf_emitente() != null) {
            _hashCode += getUf_emitente().hashCode();
        }
        if (getCtrc_data() != null) {
            _hashCode += getCtrc_data().hashCode();
        }
        if (getRegistro() != null) {
            _hashCode += getRegistro().hashCode();
        }
        if (getCodigo_frota() != null) {
            _hashCode += getCodigo_frota().hashCode();
        }
        if (getPlaca_frota() != null) {
            _hashCode += getPlaca_frota().hashCode();
        }
        if (getCodigo_carreta() != null) {
            _hashCode += getCodigo_carreta().hashCode();
        }
        if (getPlaca_carreta() != null) {
            _hashCode += getPlaca_carreta().hashCode();
        }
        if (getUf_origem() != null) {
            _hashCode += getUf_origem().hashCode();
        }
        if (getMunicipio_origem() != null) {
            _hashCode += getMunicipio_origem().hashCode();
        }
        if (getUf_destino() != null) {
            _hashCode += getUf_destino().hashCode();
        }
        if (getMunicipio_destino() != null) {
            _hashCode += getMunicipio_destino().hashCode();
        }
        if (getCnpj_fabricante() != null) {
            _hashCode += getCnpj_fabricante().hashCode();
        }
        if (getNome_fabricante() != null) {
            _hashCode += getNome_fabricante().hashCode();
        }
        if (getCnpj_seguradora() != null) {
            _hashCode += getCnpj_seguradora().hashCode();
        }
        if (getNome_seguradora() != null) {
            _hashCode += getNome_seguradora().hashCode();
        }
        _hashCode += new Double(getTaxa_rct()).hashCode();
        _hashCode += new Double(getTaxa_rr()).hashCode();
        _hashCode += new Double(getTaxa_rcf()).hashCode();
        _hashCode += new Double(getTaxa_fluvial()).hashCode();
        if (getTipo_seguro() != null) {
            _hashCode += getTipo_seguro().hashCode();
        }
        if (getTipo_transporte() != null) {
            _hashCode += getTipo_transporte().hashCode();
        }
        _hashCode += new Double(getValor_mercadoria()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtrcAverbar.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "CtrcAverbar"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cnpj_transportadora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "cnpj_transportadora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "filial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctrc_numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "ctrc_numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctrc_serie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "ctrc_serie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo_nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "tipo_nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome_emitente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "nome_emitente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio_emitente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "municipio_emitente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uf_emitente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "uf_emitente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctrc_data");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "ctrc_data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "registro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo_frota");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "codigo_frota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("placa_frota");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "placa_frota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo_carreta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "codigo_carreta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("placa_carreta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "placa_carreta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uf_origem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "uf_origem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio_origem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "municipio_origem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uf_destino");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "uf_destino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio_destino");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "municipio_destino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cnpj_fabricante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "cnpj_fabricante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome_fabricante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "nome_fabricante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cnpj_seguradora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "cnpj_seguradora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome_seguradora");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "nome_seguradora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxa_rct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "taxa_rct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxa_rr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "taxa_rr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxa_rcf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "taxa_rcf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxa_fluvial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "taxa_fluvial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo_seguro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "tipo_seguro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo_transporte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "tipo_transporte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valor_mercadoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "valor_mercadoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
