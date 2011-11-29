package br.com.promove.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import br.com.promove.exception.PromoveException;

public class FileUtils {
	public static Map<String, Document> readXMlsFromDisk(String str_dir) throws PromoveException {
		Map<String, Document> docs = new HashMap<String, Document>();
		String erros = "";
		
		File dir = new File(str_dir);
		File[] files = dir.listFiles(new FileUtils().new FilterXml());
		for (File file : files) {
			Document doc = null;
			try {
				SAXReader reader = new SAXReader();
				reader.setValidation(false);
				doc = reader.read(file);
				docs.put(file.getName(), doc);
			} catch (DocumentException de) {
				erros += file.getName() + "; ";
			}
		}
		if (!erros.isEmpty()) {
			throw new PromoveException("Erro nos arquivos: " + erros);
		}
		
		return docs;
	}
	
	class FilterXml implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith("xml");
		}
	}

	public static void removeXMLs(String str_dir) {
		File dir = new File(str_dir);
		File[] files = dir.listFiles(new FileUtils().new FilterXml());
		for (File file : files) {
			file.delete();
		}
	}

	public static void removeXML(String xml) {
		File file = new File(xml);
		file.delete();
	}

	public static void moverXML(String dest, Document xml) throws IOException {
		XMLWriter writer = new XMLWriter(new FileWriter(dest));
		writer.write(xml);
		writer.close();
	}
	
	class FilterJpg implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith("jpg");
		}
	}

	public static void moveJPGs(String origem, String destino) throws PromoveException {
		File dirOrigem = new File(origem);
		File dirDestino = new File(destino);
		File[] files = dirOrigem.listFiles(new FileUtils().new FilterJpg());
		String erros = "";
		
		for (File fileOrigem : files) {
			File fileDestino = new File(dirDestino, fileOrigem.getName());
			
			if (fileDestino.exists()) {
				fileOrigem.delete();
			} else {
				if (!fileOrigem.renameTo(fileDestino)) {
					erros += "Erro no arquivo: " + fileOrigem.getName() + ";";
				}
			}
		}
		
		if (!erros.isEmpty()) {
			throw new PromoveException(erros);
		}
	}
}
