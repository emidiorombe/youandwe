package br.com.promove.service;

import br.com.promove.exception.PromoveException;

/**
 * 
 * @author Rafael Nunes
 *
 */
public interface ImportacaoService {
	public void importAvaria(String xml) throws PromoveException;
}
