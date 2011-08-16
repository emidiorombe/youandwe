package br.com.newti.parser;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CTRCParser {
	public static String listMapToXML(Map<Integer, Map<String, Object>> ctrcByData) {
		//Integer id = 0;
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("retorno");
		//Element ctrc_tag = null;
		
		for(Map.Entry<Integer, Map<String, Object>> ctrc : ctrcByData.entrySet()) {
			Element ctrc_tag = root.addElement("ctrc");
			for(Map.Entry<String, Object> entry : ctrc.getValue().entrySet()) {
				try {
					if (entry.getValue() instanceof List<?>) {
						for(Map<String, Object> item : (List<Map<String, Object>>)entry.getValue()) {
							Element item_tag = ctrc_tag.addElement(entry.getKey());
							for(Map.Entry<String, Object> entryItem : item.entrySet()) {
								item_tag.addElement(entryItem.getKey()).addText(elvis(entryItem.getValue()));
							}
						}
					} else {
						ctrc_tag.addElement(entry.getKey()).addText(elvis(entry.getValue()));
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return xml.asXML();
	}
	
	public static String elvis(Object obj) {
		if(obj == null)
			return "";
		return obj.toString();
	}
}
