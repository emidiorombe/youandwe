package br.com.promove.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
	public void importVeiculosImportados(String csv, Date data, Integer tipo) throws PromoveException {
		ImportacaoTERCA import_terca = new ImportacaoTERCA();
		List<String> linhas = new ArrayList<String>();
		
		for(String linha : csv.split("\n")) {
			linhas.add(linha);
		}
		
		if (linhas.size() == 0) {
			throw new PromoveException("Arquivo vazio");
		}
		
		String[] cabecalho = linhas.get(0).replaceAll("\r", "; ; ; ; ").split(";");
		
		if (tipo == 1) {
			if (!cabecalho[0].toUpperCase().trim().equals("CHASSI") || 
					!cabecalho[1].toUpperCase().trim().equals("MODELO") ||
					!cabecalho[2].toUpperCase().trim().isEmpty()) {
				throw new PromoveException("Arquivo com colunas incorretas (Chassi, Modelo)");
			}
		}
		
		if (tipo == 2) {
			if (!cabecalho[0].toUpperCase().trim().equals("CHASSI") || 
					!cabecalho[1].toUpperCase().trim().equals("MODELO") ||
					!cabecalho[2].toUpperCase().trim().equals("NAVIO") ||
					!cabecalho[3].toUpperCase().substring(0, 5).equals("VALOR")) {
				throw new PromoveException("Arquivo com colunas incorretas (Chassi, Modelo, Navio, Valor)");
			}
			
		}
		
		import_terca.importar(linhas, data);
	}

	@Override
	public String importAvariasDoDiretorio(String origem, String destino) throws PromoveException {
		StringBuilder lista = new StringBuilder();
		Map<String, Document> xmls;
		
		xmls = FileUtils.readXMlsFromDisk(origem);
		
		for (Map.Entry<String, Document> doc : xmls.entrySet()) {
			try {
				importAvaria(doc.getValue().asXML());
				FileUtils.moverXML(destino+doc.getKey(), doc.getValue());
				FileUtils.removeXML(origem+doc.getKey());
				lista.append(doc.getKey() + ";");
			} catch (Exception e) {
				throw new PromoveException("Erro no arquivo " + doc.getKey());
			}
		}
		
		//FileUtils.removeXMLs(config);
		return lista.toString();
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
	public String importarGabardo(String url) throws PromoveException {
		try {
			ImportacaoCtrc import_ctrc = new ImportacaoCtrc();
			return import_ctrc.importarGabardo(url);
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
				System.out.println(linha);
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
