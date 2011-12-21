package br.com.promove.service;

import br.com.promove.exception.PromoveException;

/**
 * 
 * @author Rafael Nunes
 *
 */
public interface ImportacaoService {
	public void importAvaria(String xml) throws PromoveException;

	public void importCtrc(String xml) throws PromoveException;

	public void importVeiculosNacionais(String csv) throws PromoveException;

	public void importVeiculosImportados(String csv) throws PromoveException;

	public String importAvariasDoDiretorio(String origem, String destino) throws PromoveException;

	public void importarGabardo(String url) throws PromoveException;

	public void importDeParaAvaria(String csv) throws PromoveException;

	public void transfereFotos(String origem, String destino) throws PromoveException;

}
