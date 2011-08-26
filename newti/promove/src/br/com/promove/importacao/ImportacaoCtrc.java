package br.com.promove.importacao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoCtrc {
	private CtrcService ctrcService;
	private CadastroService cadastroService;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat date_format_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	private HashMap<String, Transportadora> transportadoras;
	private String xmlContent;

	public ImportacaoCtrc() {
		ctrcService = ServiceFactory.getService(CtrcService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
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
		for(Element node_ct : ctrcs) {
			Ctrc ct = new Ctrc();
			List<VeiculoCtrc> veiculos = new ArrayList<VeiculoCtrc>();
			
			try {
				int veicInvalidos = 0;
				
				ct.setTransp(transportadoras.get(node_ct.element("cnpj_transportadora").getText()));
				
				if(ct.getTransp() == null)
					throw new Exception("Transportadora " + node_ct.element("cnpj_transportadora").getText() + " não existe;");
				
				ct.setFilial(new Integer(node_ct.element("filial").getText()));
				ct.setNumero(new Integer(node_ct.element("ctrc_numero").getText()));
				ct.setTipo(new Integer(node_ct.element("tipo").getText()));
				ct.setSerie(node_ct.element("ctrc_serie").getText());

				List<Ctrc> ctrc = ctrcService.buscarCtrcDuplicadoPorFiltros(ct);

				if(ctrc.size() > 0) {
					ct = ctrc.get(0);
				}
				
				ct.setPlacaFrota(node_ct.element("placa_frota").getText());
				ct.setPlacaCarreta(node_ct.element("placa_carreta").getText());
				ct.setUfOrigem(node_ct.element("uf_origem").getText());
				ct.setMunicipioOrigem(node_ct.element("municipio_origem").getText());
				ct.setUfDestino(node_ct.element("uf_destino").getText());
				ct.setMunicipioDestino(node_ct.element("municipio_destino").getText());
				ct.setMotorista(node_ct.element("nome_motorista").getText());
				ct.setCancelado(node_ct.element("situacao").getText() == "3" ? true : false);
				
				ct.setTaxaRct(new Double(trataNumero(node_ct.element("taxa_rct").getText())));
				ct.setTaxaRr(new Double(trataNumero(node_ct.element("taxa_rr").getText())));
				ct.setTaxaRcf(new Double(trataNumero(node_ct.element("taxa_rcf").getText())));
				ct.setTaxaFluvial(new Double(trataNumero(node_ct.element("taxa_fluvial").getText())));
				ct.setValorMercadoria(new Double(trataNumero(node_ct.element("valor_mercadoria").getText())));

				ct.setDataEmissao(date_format_hora.parse(node_ct.element("ctrc_data").getText()));

				List<InconsistenciaCtrc> inconsistenciaCtrc = ctrcService.buscarInconsistenciaCtrcDuplicadoPorFiltros(ct);

				if(inconsistenciaCtrc.size() > 0) {
					InconsistenciaCtrc inc = inconsistenciaCtrc.get(0); 
					inc.setCtrc(ct);
					ctrcService.salvarInconsistenciaCtrc(inc, true);
				} else {
					if(ctrc.size() == 0) {
						List<Element> node_veics = node_ct.elements("veiculo");
						for(Element node_veic : node_veics) {
							VeiculoCtrc veic = new VeiculoCtrc();
							try {
								String chassi = node_veic.element("veiculo_chassi").getText();
								if (chassi != null && !chassi.isEmpty()) {
									chassi = chassi.substring(0, 17);
								
									veic.setChassiInvalido(chassi);
									veic.setModelo(node_veic.element("veiculo_modelo").getText());
									
									veic.setNumeroNF(node_veic.element("veiculo_numero_nf").getText());
									veic.setSerieNF(node_veic.element("veiculo_serie_nf").getText());
									veic.setDataNF(date_format_hora.parse(node_veic.element("veiculo_data_nf").getText()));
									veic.setValorMercadoria(new Double(trataNumero(node_veic.element("veiculo_valor_nf").getText())));
									
									List<Veiculo> veicsEncontrados = cadastroService.buscarVeiculosPorChassi(chassi);
									if(veicsEncontrados.size() > 0) {
										veic.setVeiculo(veicsEncontrados.get(0));
										veic.setModelo(veicsEncontrados.get(0).getModelo().getDescricao());
									} else {
										throw new Exception("Veiculo " + chassi + " não existe!;");
									}
								}
							} catch(Exception e) {
								veic.setMsgErro(e.getMessage());
								veicInvalidos++;
							}
							veiculos.add(veic);
						}
					}
					if(veicInvalidos > 0) {
						throw new Exception("");
					} else {
						ctrcService.salvarCtrc(ct, true);
						for(VeiculoCtrc veic: veiculos) {
							veic.setCtrc(ct);
							ctrcService.salvarVeiculoCtrc(veic, true);
						}
					}
				}
				
			} catch(Exception e) {
				InconsistenciaCtrc inc = ctrcService.salvarInconsistenciaImportCtrc(ct, e.getMessage());
				for(VeiculoCtrc veic: veiculos) {
					veic.setInconsistencia(inc.getId());
					ctrcService.salvarVeiculoCtrc(veic, true);
				}
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
	
	public static String trataNumero(String str) {
		if(str == null || str.isEmpty())
			return "0";
		return str;
	}
}
