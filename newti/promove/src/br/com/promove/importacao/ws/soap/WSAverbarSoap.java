/**
 * WSAverbarSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.promove.importacao.ws.soap;

public interface WSAverbarSoap extends java.rmi.Remote {

    /**
     * Registra os Conhecimentos de Transporte
     */
    public void incluiCTRC(java.lang.String codigoCliente, java.lang.String assinaturaDigital, br.com.promove.importacao.ws.soap.CtrcAverbar ctrc, javax.xml.rpc.holders.BooleanHolder incluiCTRCResult, br.com.promove.importacao.ws.soap.RespostaErroHolder oRespostaErro) throws java.rmi.RemoteException;
}
