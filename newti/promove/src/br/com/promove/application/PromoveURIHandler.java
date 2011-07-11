package br.com.promove.application;

import java.io.ByteArrayInputStream;
import java.net.URL;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;

import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.URIHandler;

public class PromoveURIHandler implements URIHandler{

	@Override
	public DownloadStream handleURI(URL context, String relativeUri) {
		 if (relativeUri.contains("export")) {
			 try {
				 ExportacaoService exportService = ServiceFactory.getService(ExportacaoService.class);
	             DownloadStream ds = new DownloadStream(new ByteArrayInputStream(exportService.exportarCadastrosBasicos("1").getBytes()), "application/octet-stream", "cadastro.xml");
	             return ds;
			 }catch(PromoveException pe) {
	        	   return null;
	          }
         }else if (relativeUri.contains("import")) {
        	 try {
				 ExportacaoService exportService = ServiceFactory.getService(ExportacaoService.class);
	             DownloadStream ds = new DownloadStream(new ByteArrayInputStream(exportService.exportarCadastrosBasicos("1").getBytes()), "application/octet-stream", "cadastro.xml");
	             return ds;
			 }catch(PromoveException pe) {
	        	   return null;
	          }
		 }else {
             return null;
         }
	}

}
