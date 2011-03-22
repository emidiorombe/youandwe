package br.com.promove.service;

import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.exception.PromoveException;

/**
 * 
 * @author Rafael Nunes
 *
 */
public interface ExportacaoService {
	
	
	public String exportarCadastrosBasicos() throws PromoveException;

	public String exportarXLSAvarias(List<Avaria> avarias)throws PromoveException;
}
