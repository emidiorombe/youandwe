package br.com.promove.exportacao;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.PieData;

public class GraficoExport {
	public static String gerarXmlExportacao(Map<String, List<PieData>> itens) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("items");
		for (Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
			Element item = root.addElement("item");
			String itemName = entry.getKey();
			Integer itemTotal = 0;
			for (PieData pd : entry.getValue()) {
				item.addElement("subitem")
						.addAttribute("region", pd.getLabel())
						.addAttribute("value", pd.getValue());
				itemTotal += new Integer(pd.getValue());
			}
			item.addAttribute("region", itemName);
			item.addAttribute("value", Integer.toString(itemTotal));
		}
		return xml.asXML();
	}
	
	public static String gerarXmlExportacaoFromList(List<Map<String, List<PieData>>> list_itens) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("items");
		for(Map<String, List<PieData>> itens : list_itens) {
			for (Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
				Element item = root.addElement("item");
				String itemName = entry.getKey();
				Integer itemTotal = 0;
				for (PieData pd : entry.getValue()) {
					item.addElement("subitem")
							.addAttribute("region", pd.getLabel())
							.addAttribute("value", pd.getValue());
					itemTotal += new Integer(pd.getValue());
				}
				item.addAttribute("region", itemName);
				item.addAttribute("value", Integer.toString(itemTotal));
			}
		}
		return xml.asXML();
	}
}
