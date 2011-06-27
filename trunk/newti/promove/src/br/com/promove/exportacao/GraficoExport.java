package br.com.promove.exportacao;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Cor;

public class GraficoExport {
	
	public static String gerarXmlExportacao(List <Cor> list) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("items");

		createTagItens(root, list);
		
		return xml.asXML();
	}
	
	private static void createTagItens(Element root, List <Cor> list) {
		String item = "";
		Element el_item = null;
		
		for (Cor cor : list) {
			String descricao = cor.getDescricao().isEmpty() ? "ALL" : cor.getDescricao();
			
			if(item != descricao) {
				el_item = root.addElement("item");
				
				if(cor.getCodigoExterno().isEmpty()) 
					createTagSubitem(el_item, new Cor(descricao, descricao, cor.getCodigo()));
				
				item = descricao;
			}
			
			if(!cor.getCodigoExterno().isEmpty()) {
				createTagSubitem(el_item, cor);
			} else {
				el_item.addAttribute("region", descricao);
				el_item.addAttribute("value", cor.getCodigo().toString());
			}
		}
	}

	private static void createTagSubitem(Element el_item, Cor cor) {
		Element el_subitem = el_item.addElement("subitem");
		el_subitem.addAttribute("region", cor.getCodigoExterno());
		el_subitem.addAttribute("value", cor.getCodigo().toString());
	}
}
