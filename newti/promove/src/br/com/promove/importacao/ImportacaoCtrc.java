package br.com.promove.importacao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Ctrc;
import br.com.promove.entity.Transportadora;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoCtrc {
	private CtrcService ctrcService;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat date_format_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	private HashMap<String, Transportadora> transportadoras;
	private String xmlContent;

	public ImportacaoCtrc() {
		ctrcService = ServiceFactory.getService(CtrcService.class);
	}

	public ImportacaoCtrc(String xmlContent) {
		this.xmlContent = xmlContent;
	}
	
	private String makeRequest(String url) throws IOException {
		GetMethod get = new GetMethod(url);
		HttpClient client = new HttpClient();
		
		client.executeMethod(get);
		
		String response = get.getResponseBodyAsString();
		
		get.releaseConnection();
		
		return response; 
		
	}

	public void importar(String xml) throws DocumentException, ParseException, PromoveException {
		loadTransportadoras();
		
		Document doc = DocumentHelper.parseText(xml);
		importTagCtrc(doc);
	}
	
	
	public void importarGabardo(String url) throws DocumentException, ParseException, PromoveException {
		loadTransportadoras();
		String xml;
		try {
			xml = makeRequest(url);
			Document doc = DocumentHelper.parseText(xml);
			importTagCtrc(doc);
		} catch (IOException e) {
			throw new PromoveException(e);
		}
		
	}

	private void importTagCtrc(Document doc) throws ParseException, PromoveException {
		List<Element> ctrcs = doc.selectNodes("//retorno/ctrc");
		for (Element node_ct : ctrcs) {
			Ctrc ct = new Ctrc();
			
			try {
				ct.setTransp(transportadoras.get(node_ct.element("cnpj_transportadora").getText()));
				
				if (ct.getTransp() == null)
					throw new Exception("Transportadora " + node_ct.element("cnpj_transportadora").getText() + " não existe;");
				
				ct.setFilial(new Integer(node_ct.element("filial").getText()));
				ct.setNumero(new Integer(node_ct.element("ctrc_numero").getText()));
				ct.setTipo(new Integer(node_ct.element("tipo").getText()));
				ct.setSerie(node_ct.element("ctrc_serie").getText());
				ct.setDataEmissao(date_format_hora.parse(node_ct.element("ctrc_data").getText()));
				ct.setPlacaFrota(node_ct.element("placa_frota").getText());
				ct.setPlacaCarreta(node_ct.element("placa_carreta").getText());
				ct.setUfOrigem(node_ct.element("uf_origem").getText());
				ct.setMunicipioOrigem(node_ct.element("municipio_origem").getText());
				ct.setUfDestino(node_ct.element("uf_destino").getText());
				ct.setMunicipioDestino(node_ct.element("municipio_destino").getText());
				
				String taxaRct = node_ct.element("taxa_rct").getText();
				String taxaRr = node_ct.element("taxa_rr").getText();
				String taxaRcf = node_ct.element("taxa_rcf").getText();
				String taxaFluvial = node_ct.element("taxa_fluvial").getText();
				String valorMercadoria = node_ct.element("valor_mercadoria").getText();
				if (taxaRct.equals("")) taxaRct = "0";
				if (taxaRr.equals("")) taxaRr = "0";
				if (taxaRcf.equals("")) taxaRcf = "0";
				if (taxaFluvial.equals("")) taxaFluvial = "0";
				if (valorMercadoria.equals("")) valorMercadoria = "0";
				ct.setTaxaRct(new Double(taxaRct));
				ct.setTaxaRr(new Double(taxaRr));
				ct.setTaxaRcf(new Double(taxaRcf));
				ct.setTaxaFluvial(new Double(taxaFluvial));
				ct.setValorMercadoria(new Double(valorMercadoria));
				
				if (ctrcService.buscarCtrcDuplicadoPorFiltros(ct).size() > 0) {
					continue; //Ja existe esse ctrc
				}

				ctrcService.salvarCtrc(ct, true);
		
			} catch(Exception e) {
				ctrcService.salvarInconsistenciaImportCtrc(ct, e.getMessage());
			}
		}
	}

	private void loadTransportadoras() throws PromoveException {
		transportadoras = new HashMap<String, Transportadora>();
		List<Transportadora> lista = ctrcService.buscarTodasTransportadoras();
		for (Transportadora transp : lista) {
			transportadoras.put(transp.getCnpj(), transp);
		}
	}
	
}
