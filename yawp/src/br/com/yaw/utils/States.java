package br.com.yaw.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

public class States {
	private static Map<String, String> listStates;
	
	static {
		listStates = new TreeMap<String, String>();
		listStates.put("AC", "Acre");
		listStates.put("AL", "Alagoas");
		listStates.put("AP", "Amapá");
		listStates.put("AM", "Amazonas");
		listStates.put("BA", "Bahia");
		listStates.put("CE", "Ceará");
		listStates.put("DF", "Distrito Federal");
		listStates.put("GO", "Goiás");
		listStates.put("ES", "Espírito Santo");
		listStates.put("MA", "Maranhão");
		listStates.put("MT", "Mato Grosso");
		listStates.put("MS", "Mato Grosso do Sul");
		listStates.put("MG", "Minas Gerais");
		listStates.put("PA", "Pará");
		listStates.put("PB", "Paraíba");
		listStates.put("PR", "Paraná");
		listStates.put("PE", "Pernambuco");
		listStates.put("PI", "Piauí");
		listStates.put("RJ", "Rio de Janeiro");
		listStates.put("RN", "Rio Grande do norte");
		listStates.put("RS", "Rio Grande do Sul");
		listStates.put("RO", "Rondônia");
		listStates.put("RR", "Roraima");
		listStates.put("SC", "Santa Catarina");
		listStates.put("SP", "São Paulo");
		listStates.put("SE", "Sergipe");
		listStates.put("TO", "Tocantins");
		
	}

	public static Map<String, String> getListStates() {
		return listStates;
	}
	
	}
