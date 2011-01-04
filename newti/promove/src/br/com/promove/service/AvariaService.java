package br.com.promove.service;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
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
	
	/**
	 * Salva o Locald e Avaria
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
	
}
