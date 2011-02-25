package br.com.promove.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class FileUtils {
	public static List<Document> readXMlsFromDisk(String str_dir) throws DocumentException {
		List<Document> docs = new ArrayList<Document>();
		
		File dir = new File(str_dir);
		File[] files = dir.listFiles(new FileUtils().new FilterXml());
		for (File file : files) {
			docs.add(new SAXReader().read(file));
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
}
