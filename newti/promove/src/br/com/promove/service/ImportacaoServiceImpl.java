package br.com.promove.service;

import java.io.BufferedReader;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import br.com.promove.exception.PromoveException;
import br.com.promove.importacao.ImportacaoAvaria;
import br.com.promove.importacao.ImportacaoCAOA;
import br.com.promove.importacao.ImportacaoTERCA;

/**
 * 
 * @author Rafael Nunes
 *
 */
public class ImportacaoServiceImpl implements ImportacaoService, Serializable{

	@Override
	public void importAvaria(String xml) throws PromoveException {
		try {
			ImportacaoAvaria import_avaria = new ImportacaoAvaria();
			import_avaria.importar(xml);
		}catch(DocumentException de) {
			throw new PromoveException(de);
		} catch (ParseException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void importVeiculosNacionais(String csv) throws PromoveException {
		try {
			ImportacaoCAOA import_nac = new ImportacaoCAOA();
			List<String> linhas = new ArrayList<String>();
			for(String linha : csv.split("\n")) {
				linhas.add(linha);
			}
			import_nac.importar(linhas);
		}catch (PromoveException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void importVeiculosImportados(String csv) throws PromoveException {
		try {
			ImportacaoTERCA import_terca = new ImportacaoTERCA();
			List<String> linhas = new ArrayList<String>();
			for(String linha : csv.split("\n")) {
				linhas.add(linha);
			}
			import_terca.importar(linhas);
		} catch (PromoveException e) {
			throw new PromoveException(e);
		}
		
	}

}
