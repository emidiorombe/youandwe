package importacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	private static final  String DEFAULT_DIR = "/home/rafael/bkp_acer/DADOS/Projetos/promove/import/importacao/"; 
	
	public static List<String> loadFile(String file) {
		List<String> linhas = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_DIR + file));
			String line = "";
			while((line = reader.readLine()) != null) {
				linhas.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possível carregar o arquivo!!");
			e.printStackTrace();
		} catch(IOException ioe) {
			System.out.println("Não foi possível carregar o arquivo!!");
			ioe.printStackTrace();
		}
		
		return linhas;
	}

	public static Boolean getBoolean(String b) {
		return b == "S" ? true : false;
	}
}
