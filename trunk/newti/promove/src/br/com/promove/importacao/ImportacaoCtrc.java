package br.com.promove.importacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	private String xmlContent;
	private CtrcService ctrcService;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat date_format_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private HashMap<String, Transportadora> transportadoras;

	public ImportacaoCtrc() {
		ctrcService = ServiceFactory.getService(CtrcService.class);
	}

	public ImportacaoCtrc(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public void importar(String xml) throws DocumentException, ParseException, PromoveException {
		loadTransportadoras();

		Document doc = DocumentHelper.parseText(xml);
		importTagCtrc(doc);
	}

	private void importTagCtrc(Document doc) throws ParseException, PromoveException {
		List<Element> ctrcs = doc.selectNodes("//ctrc");
		for (Element node_ct : ctrcs) {
			Ctrc ct = new Ctrc();
			
			try {
				ct.setTransp(transportadoras.get(node_ct.element("cnpj_transportadora").getText()));
				ct.setFilial(new Integer(node_ct.element("filial").getText()));
				ct.setNumero(new Integer(node_ct.element("ctrc_numero").getText()));
				ct.setTipo(new Integer(node_ct.element("tipo").getText()));
				ct.setSerie(node_ct.element("ctrc_serie").getText());
				ct.setDataEmissao(date_format.parse(node_ct.element("ctrc_data").getText()));
				ct.setPlacaFrota(node_ct.element("placa_frota").getText());
				ct.setPlacaCarreta(node_ct.element("placa_carreta").getText());
				ct.setUfOrigem(node_ct.element("uf_origem").getText());
				ct.setMunicipioOrigem(node_ct.element("municipio_origem").getText());
				ct.setUfDestino(node_ct.element("uf_destino").getText());
				ct.setMunicipioDestino(node_ct.element("municipio_destino").getText());
				ct.setTaxaRct(new Double(node_ct.element("taxa_rct").getText()));
				ct.setTaxaRr(new Double(node_ct.element("taxa_rr").getText()));
				ct.setTaxaRcf(new Double(node_ct.element("taxa_rcf").getText()));
				ct.setTaxaFluvial(new Double(node_ct.element("taxa_fluvial").getText()));
				
				if (ct.getTransp() == null)
					throw new Exception("Transportadora " + node_ct.element("cnpj_transportadora").getText() + " não existe;");
				
				if (ctrcService.buscarCtrcDuplicadoPorFiltros(ct).size() > 0) {
					//Ja existe esse ctrc
					continue;
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
