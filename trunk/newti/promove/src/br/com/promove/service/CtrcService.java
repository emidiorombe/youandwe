package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.Transportadora;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.PromoveException;

/**
 * Serviços de Ctrcs
 * @author Rafael Nunes
 *
 */
public interface CtrcService extends Serializable{
	
	/**
	 * Cria um novo ou altera a Transportadora
	 * @param transportadora
	 * @throws PromoveException 
	 */
	void salvarTransportadora(Transportadora transp) throws PromoveException;
	
	/**
	 * Busca todas as Transportadoras
	 * @return
	 * @throws PromoveException 
	 */
	List<Transportadora> buscarTodasTransportadoras() throws PromoveException;
	
	/**
	 * Remove uma Transportadora
	 * @param bean
	 * @throws PromoveException
	 */
	void excluirTransportadora(Transportadora bean) throws PromoveException;
	
	void salvarCtrc(Ctrc bean) throws PromoveException;

	void salvarCtrc(Ctrc ctrc, boolean isFlush) throws PromoveException;
	
	void excluirCtrc(Ctrc bean)throws PromoveException;

	List<Ctrc> buscarTodosCtrcs() throws PromoveException;

	public List<Ctrc> buscarCtrcPorFiltro(Ctrc ctrc, Date dtInicio, Date dtFim, String chassi, Boolean veics) throws PromoveException;

	List<Ctrc> buscarCtrcDuplicadoPorFiltros(Ctrc ctrc) throws PromoveException;

	void salvarInconsistenciaCtrc(InconsistenciaCtrc inc) throws PromoveException;
	
	void salvarInconsistenciaCtrc(InconsistenciaCtrc inc, boolean isFlush) throws PromoveException;
	
	InconsistenciaCtrc salvarInconsistenciaImportCtrc(Ctrc ctrc, String msgErro) throws PromoveException;

	List<InconsistenciaCtrc> buscarTodasInconsistenciasCtrc() throws PromoveException;

	void excluirInconsistenciaCtrc(InconsistenciaCtrc inc) throws PromoveException;

	void excluirInconsistenciaCtrc(VeiculoCtrc veic) throws PromoveException;

	List<Transportadora> buscaTransportadoraPorCnpj(String cnpj) throws PromoveException;

	void cleanUpSession()throws PromoveException;
	
	<T>T getById(Class<T> clazz, Integer id) throws PromoveException;

	public List<VeiculoCtrc> buscarVeiculosPorCtrc(Ctrc ctrc) throws PromoveException;

	void salvarVeiculoCtrc(VeiculoCtrc bean) throws PromoveException;

	void salvarVeiculoCtrc(VeiculoCtrc veic, boolean isFlush) throws PromoveException;
	
	void excluirVeiculoCtrc(VeiculoCtrc bean)throws PromoveException;

	List<InconsistenciaCtrc> buscarInconsistenciaCtrcDuplicadoPorFiltros(Ctrc ctrc) throws PromoveException;

	List<VeiculoCtrc> buscarVeiculoCtrcDuplicadoPorFiltros(VeiculoCtrc veic) throws PromoveException;

	boolean salvarVeiculoCtrcDeInconsistencia(VeiculoCtrc veic) throws PromoveException;

	boolean revalidarInconsistencia(InconsistenciaCtrc inc, boolean salvarVeics) throws PromoveException;
	
	List<VeiculoCtrc> buscarVeiculosPorInconsistencia(Integer idInc) throws PromoveException;

	List<VeiculoCtrc> buscarTodasInconsistenciasCtrcVeiculo() throws PromoveException;
}
