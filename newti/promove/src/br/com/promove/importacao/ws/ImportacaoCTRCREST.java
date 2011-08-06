package br.com.promove.importacao.ws;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Ctrc;

public class ImportacaoCTRCREST {
	
	public void importarGabardo(String url) throws IOException {
		
		try {
			List<Ctrc> ctrcs = parseXml(null);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}

	
	
	private List<Ctrc> parseXml(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		
		Iterator<Element> it = root.elementIterator("CTRC");
		while(it.hasNext()) {
			Element ctrc_tag = it.next();
			System.out.println(ctrc_tag.asXML());
		}
		
		return null;
	}

}
