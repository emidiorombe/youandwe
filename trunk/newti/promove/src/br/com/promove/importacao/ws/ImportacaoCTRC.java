package br.com.promove.importacao.ws;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import br.com.promove.entity.Ctrc;

public class ImportacaoCTRC {
	
	public void importarGabardo(String url) throws IOException {
		String xml = makeRequest(url);
		List<Ctrc> ctrcs = parseXml(xml);
	}

	private String makeRequest(String url) throws IOException {
		GetMethod get = new GetMethod(url);
		HttpClient client = new HttpClient();
		
		client.executeMethod(get);
		
		String response = get.getResponseBodyAsString();
		
		get.releaseConnection();
		
		return response; 
		
	}
	
	private List<Ctrc> parseXml(String xml) {
		return null;
	}

}
