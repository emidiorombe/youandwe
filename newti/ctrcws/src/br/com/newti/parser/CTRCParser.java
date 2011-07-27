package br.com.newti.parser;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CTRCParser {
	public static String mapToXML(Map<String, Object> ctrcs) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("retorno");
		
		for(Map.Entry<String, Object> entry : ctrcs.entrySet()) {
			Element ctrc = root.addElement("ctrc");
			ctrc.addElement(entry.getKey()).addText(entry.getValue().toString());
		}
		
		return xml.asXML();
	}
}
