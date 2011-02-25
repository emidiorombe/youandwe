package br.com.promove.importacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.FotoAvaria;
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

	public void importar(String xml) throws DocumentException, ParseException,
			PromoveException {
		Document doc = DocumentHelper.parseText(xml);
		importTagAvaria(doc);
		importTagMovimento(doc);
	}

	private void importTagAvaria(Document doc) throws ParseException, PromoveException {
		List<Node> avarias = doc.selectNodes("//dados_coletados/avarias");
		for (Node node_av : avarias) {
			Avaria av = new Avaria();
			try {
				av.setClima(avariaService.getById(Clima.class, new Integer(node_av.selectSingleNode("//avarias/concli").getText())));
				av.setExtensao(avariaService.getById(ExtensaoAvaria.class, new Integer(node_av.selectSingleNode("//avarias/gravid").getText())));
				av.setDataLancamento(date_format.parse(node_av.selectSingleNode("//avarias/data").getText()));
				av.setTipo(avariaService.getById(TipoAvaria.class, new Integer(node_av.selectSingleNode("//avarias/tipo").getText())));
				av.setLocal(avariaService.getById(LocalAvaria.class, new Integer(node_av.selectSingleNode("//avarias/local").getText())));
				av.setOrigem(avariaService.getById(OrigemAvaria.class,new Integer(node_av.selectSingleNode("//avarias/origem").getText())));
				av.setUsuario(avariaService.getById(Usuario.class, new Integer(node_av.selectSingleNode("//avarias/origem").getText())));
				av.setObservacao(node_av.selectSingleNode("//avarias/obs").getText());
				
				List<Veiculo> veiculos = cadastroService.buscarVeiculosPorChassi(node_av.selectSingleNode("//avarias/chassi").getText());
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) { 
					throw new Exception("Veiculo " + node_av.selectSingleNode("//avarias/chassi").getText() + " não existe");
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
				avariaService.cleanUpSession();
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage());
			}
		}
	}

	private void importTagMovimento(Document doc) throws ParseException,
			PromoveException {
		List<Node> avarias = doc.selectNodes("//dados_coletados/movto");
		for (Node node_av : avarias) {
			Avaria av = new Avaria();
			av.setClima(new Clima(new Integer(node_av.selectSingleNode("//avarias/concli").getText())));
			av.setExtensao(new ExtensaoAvaria(new Integer(node_av.selectSingleNode("//avarias/gravid").getText())));
			av.setDataLancamento(date_format.parse(node_av.selectSingleNode("//avarias/data").getText()));
			av.setTipo(new TipoAvaria(new Integer(node_av.selectSingleNode("//avarias/tipo").getText())));
			av.setLocal(new LocalAvaria(new Integer(node_av.selectSingleNode("//avarias/local").getText())));
			av.setOrigem(new OrigemAvaria(new Integer(node_av.selectSingleNode("//avarias/origem").getText())));
			av.setObservacao(node_av.selectSingleNode("//avarias/obs").getText());
			// TODO Buscar veiculo

			avariaService.salvarAvaria(av, true);

			List<Node> node_fotos = node_av.selectNodes("//avarias/fotos");
			for (Node node_foto : node_fotos) {
				FotoAvaria foto = new FotoAvaria();
				foto.setAvaria(av);
				Element node_nome = (Element) node_foto.selectSingleNode("//fotos/arq_foto");
				foto.setNome(node_nome.getText());
				avariaService.salvarFotoAvaria(foto, true);
			}
		}
	}

}
