package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.dom4j.Node;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.Cor;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.ResponsabilidadeAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;

/**
 * Serviços de Avarias
 * @author Rafael Nunes
 *
 */
public interface AvariaService extends Serializable{
	
	/**
	 * Cria um novo ou altera o Tipo de Avaria
	 * @param tipo avaria
	 * @throws PromoveException 
	 */
	void salvarTipoAvaria(TipoAvaria tipoa) throws PromoveException;
	
	/**
	 * Busca todos os Tipos de avaria
	 * @return
	 * @throws PromoveException 
	 */
	List<TipoAvaria> buscarTodosTipoAvaria() throws PromoveException;
	
	List<TipoAvaria> buscarTodosTipoAvaria(String sortField) throws PromoveException;

	/**
	 * Remove um Tipo de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	void excluirTipoAvaria(TipoAvaria bean) throws PromoveException;
	
	/**
	 * Busca todos os locais de avaria
	 * @return
	 */
	List<LocalAvaria> buscarTodosLocaisAvaria() throws PromoveException;
	
	List<LocalAvaria> buscarTodosLocaisAvaria(String sortField) throws PromoveException;
	
	/**
	 * Salva o Local de Avaria
	 * @param class1
	 */
	void salvarLocalAvaria(LocalAvaria local) throws PromoveException;
	
	/**
	 * Remove um Local de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	void excluirLocalAvaria(LocalAvaria bean) throws PromoveException;
	
	/**
	 * Salva a origem da Avaria
	 * @param class1
	 */
	void salvarOrigemAvaria(OrigemAvaria bean)  throws PromoveException;
	
	/**
	 * Remove um Origem de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	void excluirOrigemAvaria(OrigemAvaria bean) throws PromoveException;
	
	/**
	 * Busca todos as origens de avaria
	 * @return
	 */
	List<OrigemAvaria> buscarTodasOrigensAvaria() throws PromoveException;
	
	List<OrigemAvaria> buscarTodasOrigensAvaria(String sortField) throws PromoveException;
	
	/**
	 * Salva a Extensao da Avaria
	 * @param bean
	 */
	void salvarExtensaoAvaria(ExtensaoAvaria bean)throws PromoveException;
	
	/**
	 * Remove uma Extensao de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	void excluirExtensaoAvaria(ExtensaoAvaria bean) throws PromoveException;
	
	/**
	 * Busca todos as extensões de avaria
	 * @return
	 */
	List<ExtensaoAvaria> buscarTodasExtensoesAvaria() throws PromoveException;
	
	public List<Clima> buscarTodosClimas()throws PromoveException;

	public void salvarClima(Clima bean)throws PromoveException;

	public void excluirClima(Clima bean)throws PromoveException;

	void salvarAvaria(Avaria bean)throws PromoveException;

	void excluirAvaria(Avaria bean)throws PromoveException;

	List<Avaria> buscarTodasAvarias() throws PromoveException;

	void salvarFotoAvaria(FotoAvaria foto, boolean isFlush) throws PromoveException;

	void salvarAvaria(Avaria av, boolean isFlush)throws PromoveException;

	List<Avaria> buscarAvariaPorFiltros(Avaria avaria, Date de, Date ate, Integer periodo, Boolean movimentacao, Boolean registradas, OrigemAvaria oriAte, ResponsabilidadeAvaria responsabilidade, Fabricante fabricante) throws PromoveException;

	List<ResponsabilidadeAvaria> buscarTodasResponsabilidades() throws PromoveException;

	List<Avaria> buscarAvariaDuplicadaPorFiltros(List<Veiculo> veiculos, Avaria av) throws PromoveException;

	InconsistenciaAvaria salvarInconsistenciaImportAvaria(Avaria avaria, String msgErro)throws PromoveException;

	void cleanUpSession()throws PromoveException;

	<T>T getById(Class<T> clazz, Integer id) throws PromoveException;

	OrigemAvaria buscarOrigemPorTipoEFilial(String tipo_id, String filial_id)throws PromoveException;

	List<InconsistenciaAvaria> buscarTodasInconsistenciasAvaria()throws PromoveException;

	void excluirInconsistenciaImportAvaria(InconsistenciaAvaria inc)throws PromoveException;

	void salvarAvariaDeInconsistencias(InconsistenciaAvaria inc)throws PromoveException;

	List<Avaria> buscarAvariaDuplicadaPorFiltros(Veiculo veiculo, Avaria av)throws PromoveException;
	
	public List<Cor> buscarResumo(Veiculo veiculo, Date de, Date ate, Integer periodo, OrigemAvaria oride, OrigemAvaria oriate, String item, String subitem) throws PromoveException;
}
