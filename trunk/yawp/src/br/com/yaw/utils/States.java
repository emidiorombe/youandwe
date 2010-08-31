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
		/*listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");
		listStates.put("", "");*/
		listStates.put("SP", "São Paulo");/*
		listStates.put("", "");
		listStates.put("", "");*/
		
	}

	public static Map<String, String> getListStates() {
		return listStates;
	}
	
	}
