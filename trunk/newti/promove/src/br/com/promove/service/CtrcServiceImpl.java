package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.promove.dao.CtrcDAO;
import br.com.promove.dao.InconsistenciaCtrcDAO;
import br.com.promove.dao.TransportadoraDAO;
import br.com.promove.dao.VeiculoCtrcDAO;
import br.com.promove.dao.VeiculoDAO;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.Transportadora;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.utils.DateUtils;
import br.com.promove.utils.StringUtilities;

public class CtrcServiceImpl implements CtrcService, Serializable {
	private TransportadoraDAO transpDAO;
	private CtrcDAO ctrcDAO;
	private InconsistenciaCtrcDAO inconsistenciaCtrcDAO;
	private VeiculoCtrcDAO veiculoCtrcDAO;
	private VeiculoDAO veiculoDAO;
	
	CtrcServiceImpl() {
		transpDAO = new TransportadoraDAO();
		ctrcDAO = new CtrcDAO();
		inconsistenciaCtrcDAO = new InconsistenciaCtrcDAO(); 
		veiculoCtrcDAO = new VeiculoCtrcDAO();
		veiculoDAO = new VeiculoDAO();
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
	public void salvarInconsistenciaCtrc(InconsistenciaCtrc inc) throws PromoveException {
		salvarInconsistenciaCtrc(inc, false);
	}

	@Override
	public void salvarInconsistenciaCtrc(InconsistenciaCtrc inc, boolean isFlush) throws PromoveException {
		try {
			if(inc.getId() == null && inconsistenciaCtrcDAO.getInconsistenciasCtrcDuplicadosPorFiltros(inc.getCtrc()).size() > 0)
				throw new IllegalArgumentException("Inconsistência CTRC já cadastrada!");
			
			inconsistenciaCtrcDAO.save(inc);
			if(isFlush)
				inconsistenciaCtrcDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
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
	public void excluirInconsistenciaCtrc(VeiculoCtrc veic) throws PromoveException {
		try {
			inconsistenciaCtrcDAO.delete(inconsistenciaCtrcDAO.getByPrimaryKey(veic.getInconsistencia()));
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
	public List<InconsistenciaCtrc> buscarInconsistenciaCtrcDuplicadoPorFiltros(Ctrc ct) throws PromoveException {
		List<InconsistenciaCtrc> lista = null;
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
			salvarVeiculoCtrc(veic);
			InconsistenciaCtrc inc = inconsistenciaCtrcDAO.getByPrimaryKey(veic.getInconsistencia());
			ok = revalidarInconsistencia(inc, false);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
		return ok;
	}

	@Override
	public boolean revalidarInconsistencia(InconsistenciaCtrc inc, boolean salvarVeics) throws PromoveException {
		boolean ok = false;
		try {
			int veicInvalidos = 0;
			Ctrc ctrc = inc.getCtrc();
			List<VeiculoCtrc> veics = veiculoCtrcDAO.getByInconsistencia(inc.getId());
			
			for (VeiculoCtrc veic : veics) {
				if (salvarVeics && veic.getVeiculo() == null) {
					List<Veiculo> veicsEncontrados = veiculoDAO.getByChassi(veic.getChassiInvalido());
					if(veicsEncontrados.size() > 0) {
						veic.setVeiculo(veicsEncontrados.get(0));
						veic.setModelo(veicsEncontrados.get(0).getModelo().getDescricao());
						veic.setMsgErro("");
						salvarVeiculoCtrc(veic);
					}
				}
				if (veic.getMsgErro() != null && !veic.getMsgErro().isEmpty()) veicInvalidos++;
			}
	
			if(StringUtilities.getTranspFromErrorMessage(inc.getMsgErro()) != null) {
				List<Transportadora> transportadoras = buscaTransportadoraPorCnpj(StringUtilities.getTranspFromErrorMessage(inc.getMsgErro()));
				if((transportadoras.size() > 0)) {
					inc.setTransp(transportadoras.get(0));
					inc.setMsgErro("");
					inconsistenciaCtrcDAO.save(inc);
				}
			}
			
			if (veicInvalidos == 0 && (inc.getMsgErro() == null || inc.getMsgErro().isEmpty())) {
				ok = true;
				ctrcDAO.save(ctrc);
				for (VeiculoCtrc veic : veics) {
					veic.setInconsistencia(null);
					veic.setCtrc(ctrc);
					salvarVeiculoCtrc(veic);
				}
				excluirInconsistenciaCtrc(inc);
			}

		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
		return ok;
	}

	public List<VeiculoCtrc> buscarVeiculosPorInconsistencia(Integer idInc) throws PromoveException {
		List<VeiculoCtrc> lista = null;
		try {
			lista = veiculoCtrcDAO.getByInconsistencia(idInc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
}
