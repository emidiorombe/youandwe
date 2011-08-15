package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.promove.dao.CtrcDAO;
import br.com.promove.dao.InconsistenciaCtrcDAO;
import br.com.promove.dao.TransportadoraDAO;
import br.com.promove.dao.VeiculoCtrcDAO;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.Transportadora;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.utils.DateUtils;

public class CtrcServiceImpl implements CtrcService, Serializable {
	private TransportadoraDAO transpDAO;
	private CtrcDAO ctrcDAO;
	private VeiculoCtrcDAO veiculoCtrcDAO;
	private InconsistenciaCtrcDAO inconsistenciaCtrcDAO;
	
	CtrcServiceImpl() {
		transpDAO = new TransportadoraDAO();
		ctrcDAO = new CtrcDAO();
		veiculoCtrcDAO = new VeiculoCtrcDAO();
		inconsistenciaCtrcDAO = new InconsistenciaCtrcDAO(); 
	}

	@Override
	public void salvarTransportadora(Transportadora transp) throws PromoveException {
		try {
			transpDAO.save(transp);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
	}

	@Override
	public List<Transportadora> buscarTodasTransportadoras() throws PromoveException {
		List<Transportadora> lista = null;
		try {
			lista = transpDAO.getAll();
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	@Override
	public void excluirTransportadora(Transportadora bean) throws PromoveException {
		try {
			transpDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
	}

	@Override
	public void salvarCtrc(Ctrc bean) throws PromoveException {
		salvarCtrc(bean, false);
	}

	@Override
	public void salvarCtrc(Ctrc ctrc, boolean isFlush)throws PromoveException {
		try {
			if(ctrc.getId() == null && ctrcDAO.getCtrcsDuplicadosPorFiltros(ctrc).size() > 0)
				throw new IllegalArgumentException("CTRC já cadastrado!");
			
			ctrcDAO.save(ctrc);
			if(isFlush)
				ctrcDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirCtrc(Ctrc bean) throws PromoveException {
		try {
			ctrcDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public List<Ctrc> buscarTodosCtrcs() throws PromoveException {
		List<Ctrc> lista = null;
		try {
			lista = ctrcDAO.getAllCustom();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<Ctrc> buscarCtrcPorFiltro(Ctrc ctrc, Date dtInicio, Date dtFim, String chassi) throws PromoveException {
		List<Ctrc> lista = null;
		try {
			Date init = DateUtils.montarDataInicialParaHQLQuery(dtInicio); 
			Date fim = DateUtils.montarDataFinalParaHQLQuery(dtFim); 
			
			lista = ctrcDAO.getCtrcPorFiltro(ctrc, init, fim, chassi);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<Ctrc> buscarCtrcDuplicadoPorFiltros(Ctrc ct) throws PromoveException {
		List<Ctrc> lista = null;
		try {
			lista = ctrcDAO.getCtrcsDuplicadosPorFiltros(ct);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public InconsistenciaCtrc salvarInconsistenciaImportCtrc(Ctrc ctrc, String msgErro)throws PromoveException {
		try {
			InconsistenciaCtrc inc = new InconsistenciaCtrc(ctrc, msgErro);
			inconsistenciaCtrcDAO.save(inc);
			return inc;
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<InconsistenciaCtrc> buscarTodasInconsistenciasCtrc()throws PromoveException {
		List<InconsistenciaCtrc> lista = null;
		try {
			lista = inconsistenciaCtrcDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
	
	@Override
	public void excluirInconsistenciaCtrc(InconsistenciaCtrc inc) throws PromoveException {
		try {
			inconsistenciaCtrcDAO.delete(inc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Transportadora> buscaTransportadoraPorCnpj(String cnpj)
			throws PromoveException {
		List<Transportadora> lista = null;
		try {
			lista = transpDAO.getByCnpj(cnpj);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void cleanUpSession() throws PromoveException {
		try {
			inconsistenciaCtrcDAO.rebuildSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public <T> T getById(Class<T> clazz, Integer id) throws PromoveException {
		try {
			return ctrcDAO.getGenericObject(clazz, id);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public List<VeiculoCtrc> buscarVeiculosPorCtrc(Ctrc ctrc) throws PromoveException {
		List<VeiculoCtrc> lista = null;
		try {
			lista = veiculoCtrcDAO.getByCtrc(ctrc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarVeiculoCtrc(VeiculoCtrc bean) throws PromoveException {
		salvarVeiculoCtrc(bean, false);
	}

	@Override
	public void salvarVeiculoCtrc(VeiculoCtrc veic, boolean isFlush) throws PromoveException {
		try {
			if(veic.getId() == null && veiculoCtrcDAO.getVeiculosCtrcDuplicadosPorFiltros(veic).size() > 0)
				throw new IllegalArgumentException("Veículo do CTRC já cadastrado!");
			
			veiculoCtrcDAO.save(veic);
			if(isFlush)
				veiculoCtrcDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirVeiculoCtrc(VeiculoCtrc bean) throws PromoveException {
		try {
			veiculoCtrcDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public List<Ctrc> buscarInconsistenciaCtrcDuplicadoPorFiltros(Ctrc ct) throws PromoveException {
		List<Ctrc> lista = null;
		try {
			lista = inconsistenciaCtrcDAO.getInconsistenciasCtrcDuplicadosPorFiltros(ct);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<VeiculoCtrc> buscarVeiculoCtrcDuplicadoPorFiltros(VeiculoCtrc veic) throws PromoveException {
		List<VeiculoCtrc> lista = null;
		try {
			lista = veiculoCtrcDAO.getVeiculosCtrcDuplicadosPorFiltros(veic);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public boolean salvarVeiculoCtrcDeInconsistencia(VeiculoCtrc veic) throws PromoveException {
		boolean ok = false;
		try {
			int veicInvalidos = 0;
			salvarVeiculoCtrc(veic);
			InconsistenciaCtrc inc = inconsistenciaCtrcDAO.getByPrimaryKey(veic.getInconsistencia());
			Ctrc ctrc = inc.getCtrc();
			List<VeiculoCtrc> veiculos = veiculoCtrcDAO.getByInconsistencia(veic.getInconsistencia());
			
			for (VeiculoCtrc veiculo : veiculos) {
				if (veiculo.getVeiculo() == null) veicInvalidos++;
			}

			if (veicInvalidos == 0) {
				ok = true;
				ctrcDAO.save(ctrc);
				for (VeiculoCtrc veiculo : veiculos) {
					veiculo.setInconsistencia(null);
					veiculo.setCtrc(ctrc);
					salvarVeiculoCtrc(veiculo);
				}
				this.excluirInconsistenciaCtrc(inc);
			}
			
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
		return ok;
	}

	public List<VeiculoCtrc> buscarVeiculosPorInconsistencias(Integer idInc) throws PromoveException {
		List<VeiculoCtrc> lista = null;
		try {
			lista = veiculoCtrcDAO.getByInconsistencia(idInc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
}
