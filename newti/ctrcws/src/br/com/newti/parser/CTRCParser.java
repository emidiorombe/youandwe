package br.com.newti.parser;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CTRCParser {
	public static String listMapToXML(List<Map<String, Object>> ctrcByData) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("retorno");
		for(Map<String, Object> ctrc : ctrcByData) {
			Element ctrc_tag = root.addElement("ctrc");
			for(Map.Entry<String, Object> entry : ctrc.entrySet()) {
				try {
					ctrc_tag.addElement(entry.getKey().toLowerCase()).addText(elvis(entry.getValue()));
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
