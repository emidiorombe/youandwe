package br.com.promove.service;

import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.Ctrc;
import br.com.promove.exception.PromoveException;

/**
 * 
 * @author Rafael Nunes
 *
 */
public interface ExportacaoService {
	
	
	public String exportarCadastrosBasicos() throws PromoveException;

	public String exportarXLSAvarias(List<Avaria> avarias)throws PromoveException;

	public String exportarXLSVeiculos(List<Veiculo> veiculos)throws PromoveException;

	public String exportarXLSCtrcs(List<Ctrc> ctrcs)throws PromoveException;
}
