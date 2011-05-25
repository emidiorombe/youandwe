package br.com.promove.importacao.ws;

import br.com.promove.importacao.ws.soap.WSAverbarLocator;
import br.com.promove.importacao.ws.soap.WSAverbarSoap;


public class ImportacaoCTRCWSDL {
	public static void main(String[] args) throws Exception {
		WSAverbarLocator loc = new WSAverbarLocator();
		WSAverbarSoap soap = loc.getWSAverbarSoap();
		
		//soap.incluiCTRC(codigoCliente, assinaturaDigital, ctrc, incluiCTRCResult, oRespostaErro)
	}
}
