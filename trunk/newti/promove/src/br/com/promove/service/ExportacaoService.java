package br.com.promove.service;

import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Resumo;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.Ctrc;
import br.com.promove.exception.PromoveException;

/**
 * 
 * @author Rafael Nunes
 *
 */
public interface ExportacaoService {
	
	
	public String exportarCadastrosBasicos(String novo) throws PromoveException;

	public String exportarXMLAvarias(List<Avaria> avarias) throws PromoveException;

	public String exportarXLSAvarias(List<Avaria> avarias) throws PromoveException;

	public String exportarXLSVeiculos(List<Veiculo> veiculos) throws PromoveException;

	public String exportarXLSCtrcs(List<Ctrc> ctrcs) throws PromoveException;

	public String exportarXLSInconsistenciaAvarias(List<InconsistenciaAvaria> lista) throws PromoveException;

	public String exportarXLSResumo(List<Resumo> resumos, String item, String subitem) throws PromoveException;

}
