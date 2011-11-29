package br.com.promove.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import br.com.promove.exception.PromoveException;
import br.com.promove.importacao.ImportacaoAvaria;
import br.com.promove.importacao.ImportacaoCAOA;
import br.com.promove.importacao.ImportacaoCtrc;
import br.com.promove.importacao.ImportacaoDeParaAvaria;
import br.com.promove.importacao.ImportacaoTERCA;
import br.com.promove.utils.FileUtils;

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

	@Override
	public void importAvariasDoDiretorio(String config, String dest) throws PromoveException {
		Map<String, Document> xmls;
		
		xmls = FileUtils.readXMlsFromDisk(config);
		
		for (Map.Entry<String, Document> doc : xmls.entrySet()) {
			try {
				importAvaria(doc.getValue().asXML());
				FileUtils.moverXML(dest+doc.getKey(), doc.getValue());
				FileUtils.removeXML(config+doc.getKey());
			} catch (Exception e) {
				throw new PromoveException("Erro no arquivo " + doc.getKey());
			}
		}
		
		//FileUtils.removeXMLs(config);
		
	}

	@Override
	public void importCtrc(String xml) throws PromoveException {
		try {
			ImportacaoCtrc import_ctrc = new ImportacaoCtrc();
			import_ctrc.importar(xml);
		}catch(DocumentException de) {
			throw new PromoveException(de);
		} catch (ParseException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void importarGabardo(String url) throws PromoveException {
		try {
			ImportacaoCtrc import_ctrc = new ImportacaoCtrc();
			import_ctrc.importarGabardo(url);
		}catch(DocumentException de) {
			throw new PromoveException(de);
		} catch (ParseException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public void importDeParaAvaria(String csv) throws PromoveException {
		try {
			ImportacaoDeParaAvaria importacao = new ImportacaoDeParaAvaria();
			List<String> linhas = new ArrayList<String>();
			for(String linha : csv.split("\n")) {
				linhas.add(linha);
			}
			importacao.importar(linhas);
		} catch (PromoveException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public void transfereFotos(String origem, String destino) throws PromoveException {
		FileUtils.moveJPGs(origem, destino);
	}

}
