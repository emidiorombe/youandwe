package br.com.promove.importacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoAvaria {
	private String xmlContent;
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat date_format_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public ImportacaoAvaria() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
	}

	public ImportacaoAvaria(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public void importar(String xml) throws DocumentException, ParseException, PromoveException {
		Document doc = DocumentHelper.parseText(xml);
		importTagAvaria(doc);
		importTagMovimento(doc);
	}

	private void importTagAvaria(Document doc) throws ParseException, PromoveException {
		List<Element> avarias = doc.selectNodes("//dados_coletados/avarias");
		for (Element node_av : avarias) {
			Avaria av = new Avaria();
			
			try {
				av.setClima(avariaService.getById(Clima.class, new Integer(node_av.element("concli").getText())));
				String ext = node_av.element("gravid").getText();
				ext  = ext.equals("L") ? "9"  : (ext.equals("G") ? "10" : (ext.equals("0") ? "9999": ext));
				av.setExtensao(avariaService.getById(ExtensaoAvaria.class, new Integer(ext)));
				av.setDataLancamento(date_format.parse(node_av.element("data").getText()));
				av.setTipo(avariaService.getById(TipoAvaria.class, new Integer(node_av.element("tipo").getText())));
				av.setLocal(avariaService.getById(LocalAvaria.class, new Integer(node_av.element("local").getText())));
				av.setOrigem(avariaService.getById(OrigemAvaria.class,new Integer(node_av.element("origem").getText())));
				av.setUsuario(avariaService.getById(Usuario.class, new Integer(node_av.element("usuario").getText())));
				av.setObservacao(node_av.element("obs").getText());
				
				String chassi = node_av.element("chassi").getText();
				List<Veiculo> veiculos = null;
				
				if(chassi.contains("000000000")) {
					chassi = chassi.replace("000000000", "");
					
					veiculos = cadastroService.buscarVeiculosPorModeloFZData(chassi, av.getDataLancamento());
				}else {
					veiculos = cadastroService.buscarVeiculosPorChassi(chassi);
				}
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) {
					InconsistenciaAvaria inc = avariaService.salvarInconsistenciaImportAvaria(av, "Veiculo " + node_av.element("chassi").getText() + " não existe!;");
					
					Element node_fotos = ((Element)node_av).element("fotos");
					Iterator it = node_fotos.elementIterator();
					List<FotoAvaria> fotos = new ArrayList<FotoAvaria>();
					while (it.hasNext()) {
						FotoAvaria foto = new FotoAvaria();
						foto.setInconsisctencia(inc.getId());
						Element node_arq = (Element) it.next();
						foto.setNome(node_arq.attributeValue("nome"));
						avariaService.salvarFotoAvaria(foto, true);
					}
					
				}else {
					av.setVeiculo(veiculos.get(0));
					
					if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
						//Ja existe essa avaria
						continue;
					}
	
					avariaService.salvarAvaria(av, true);
		
					Element node_fotos = ((Element)node_av).element("fotos");
					Iterator it = node_fotos.elementIterator();
					while (it.hasNext()) {
						FotoAvaria foto = new FotoAvaria();
						foto.setAvaria(av);
						Element node_arq = (Element) it.next();
						foto.setNome(node_arq.attributeValue("nome"));
						avariaService.salvarFotoAvaria(foto, true);
					}
				}
			}catch(Exception e) {
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage());
			}
		}
	}

	private void importTagMovimento(Document doc) throws ParseException, PromoveException {
		List<Element> avarias = doc.selectNodes("//dados_coletados/movto");
		for (Element node_av : avarias) {
			Avaria av = new Avaria();
			try {
				av.setClima(avariaService.getById(Clima.class, new Integer("4")));
				av.setExtensao(avariaService.getById(ExtensaoAvaria.class, new Integer("9999")));
				av.setDataLancamento(date_format.parse(node_av.element("data").getText()));
				av.setTipo(avariaService.getById(TipoAvaria.class, new Integer("300")));
				av.setLocal(avariaService.getById(LocalAvaria.class, new Integer("300")));
				av.setHora(node_av.element("hora").getText());
				av.setOrigem(avariaService.buscarOrigemPorTipoEFilial(node_av.element("tipo").getText(), node_av.element("filial").getText()));
				av.setUsuario(avariaService.getById(Usuario.class, new Integer(node_av.element("usuario").getText())));
				
				String chassi = node_av.element("chassi").getText();
				List<Veiculo> veiculos = null;
				
				if(chassi.contains("000000000")) {
					chassi = chassi.replace("000000000", "");
					
					veiculos = cadastroService.buscarVeiculosPorModeloFZData(chassi, av.getDataLancamento());
				}else {
					veiculos = cadastroService.buscarVeiculosPorChassi(chassi);
				}
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) { 
					throw new Exception("Veiculo " + node_av.element("chassi").getText() + " não existe");
				}else {
					av.setVeiculo(veiculos.get(0));
					
					if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
						//Ja existe essa avaria
						continue;
					}
					avariaService.salvarAvaria(av, true);
				}
			}catch(Exception e) {
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage());
			}
		}
	}

}
