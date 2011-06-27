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

public class FileUtils {
	public static Map<String, Document> readXMlsFromDisk(String str_dir) throws DocumentException {
		Map<String, Document> docs = new HashMap<String, Document>();
		
		File dir = new File(str_dir);
		File[] files = dir.listFiles(new FileUtils().new FilterXml());
		for (File file : files) {
			docs.put(file.getName(), new SAXReader().read(file));
		}
		
		return docs;
	}
	
	
	class FilterXml implements FileFilter{
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
}
