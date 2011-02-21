package br.com.promove.importacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoAvaria {
	private String xmlContent;
	private AvariaService avariaService;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	
	public ImportacaoAvaria() {
		avariaService = ServiceFactory.getService(AvariaService.class);
	}
	
	public ImportacaoAvaria(String xmlContent) {
		this.xmlContent = xmlContent;
	}



	public void importar(String xml) throws DocumentException, ParseException, PromoveException {
		Document doc = DocumentHelper.parseText(xml);
		List<Node> avarias = doc.selectNodes("//dados_coletados/avarias");
		for (Node node_av : avarias) {
			Avaria av = new Avaria();
			av.setClima(new Clima(new Integer(node_av.selectSingleNode("//avarias/concli").getText())));
			av.setExtensao(new ExtensaoAvaria(new Integer(node_av.selectSingleNode("//avarias/gravid").getText())));
			av.setDataLancamento(date_format.parse(node_av.selectSingleNode("//avarias/data").getText()));
			av.setTipo(new TipoAvaria(new Integer(node_av.selectSingleNode("//avarias/tipo").getText())));
			av.setLocal(new LocalAvaria(new Integer(node_av.selectSingleNode("//avarias/local").getText())));
			av.setOrigem(new OrigemAvaria(new Integer(node_av.selectSingleNode("//avarias/origem").getText())));
			av.setObservacao(node_av.selectSingleNode("//avarias/obs").getText());
			//TODO Buscar veiculo
			
			avariaService.salvarAvaria(av, true);
			
			List<Node> node_fotos = node_av.selectNodes("//avarias/fotos");
			for (Node node_foto : node_fotos) {
				FotoAvaria foto = new FotoAvaria();
				foto.setAvaria(av);
				Node node_nome = node_foto.selectSingleNode("//fotos/arq_foto");
				foto.setNome(node_nome.getText());
				avariaService.salvarFotoAvaria(foto, true);
			}
		}
	}
	
	
}
