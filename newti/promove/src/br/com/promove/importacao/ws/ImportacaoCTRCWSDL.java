package br.com.promove.importacao.ws;

import br.com.promove.importacao.ws.soap.CtrcAverbar;
import br.com.promove.importacao.ws.soap.WSAverbarLocator;
import br.com.promove.importacao.ws.soap.WSAverbarSoap;


public class ImportacaoCTRCWSDL {
	public static void main(String[] args) throws Exception {
		WSAverbarLocator loc = new WSAverbarLocator();
		WSAverbarSoap soap = loc.getWSAverbarSoap();
		// c = new CtrcAverbar(cnpj_transportadora, filial, ctrc_numero, tipo, ctrc_serie, tipo_nome, nome_emitente, municipio_emitente, uf_emitente, ctrc_data, registro, codigo_frota, placa_frota, codigo_carreta, placa_carreta, uf_origem, municipio_origem, uf_destino, municipio_destino, cnpj_fabricante, nome_fabricante, cnpj_seguradora, nome_seguradora, taxa_rct, taxa_rr, taxa_rcf, taxa_fluvial, tipo_seguro, tipo_transporte, valor_mercadoria)
		
		//soap.incluiCTRC(codigoCliente, assinaturaDigital, ctrc, incluiCTRCResult, oRespostaErro)
	}
}
