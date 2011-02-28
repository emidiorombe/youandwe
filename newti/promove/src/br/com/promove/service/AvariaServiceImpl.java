package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.promove.dao.AvariaDAO;
import br.com.promove.dao.BaseDAO;
import br.com.promove.dao.ClimaDAO;
import br.com.promove.dao.ExtensaoDAO;
import br.com.promove.dao.FotoAvariaDAO;
import br.com.promove.dao.InconsistenciaAvariaDAO;
import br.com.promove.dao.LocalAvariaDAO;
import br.com.promove.dao.OrigemAvariaDAO;
import br.com.promove.dao.ResponsabilidadeDAO;
import br.com.promove.dao.TipoAvariaDAO;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.ResponsabilidadeAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;

public class AvariaServiceImpl implements AvariaService, Serializable {
	private TipoAvariaDAO tipoDAO;
	private LocalAvariaDAO localDAO;
	private OrigemAvariaDAO origemDAO;
	private ExtensaoDAO extensaoDAO;
	private ClimaDAO climaDAO;
	private AvariaDAO avariaDAO;
	private FotoAvariaDAO fotoDAO;
	private ResponsabilidadeDAO responsaDAO;
	private InconsistenciaAvariaDAO inconsistenciaAvariaDAO;
	AvariaServiceImpl() {
		tipoDAO = new TipoAvariaDAO();
		localDAO = new LocalAvariaDAO();
		origemDAO = new OrigemAvariaDAO();
		extensaoDAO = new ExtensaoDAO();
		climaDAO = new ClimaDAO();
		avariaDAO = new AvariaDAO();
		fotoDAO = new FotoAvariaDAO();
		responsaDAO = new ResponsabilidadeDAO();
		inconsistenciaAvariaDAO = new InconsistenciaAvariaDAO(); 
	}

	@Override
	public void salvarTipoAvaria(TipoAvaria tipoa) throws PromoveException {
		try {
			tipoDAO.save(tipoa);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
	}

	@Override
	public List<TipoAvaria> buscarTodosTipoAvaria() throws PromoveException {
		List<TipoAvaria> lista = null;
		try {
			lista = tipoDAO.getAll();
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	@Override
	public void excluirTipoAvaria(TipoAvaria bean) throws PromoveException {
		try {
			tipoDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}

	}

	@Override
	public List<LocalAvaria> buscarTodosLocaisAvaria() throws PromoveException {
		List<LocalAvaria> lista = null;
		try {
			lista = localDAO.getAll();
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	@Override
	public void salvarLocalAvaria(LocalAvaria local) throws PromoveException {
		try {
			localDAO.save(local);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	@Override
	public void excluirLocalAvaria(LocalAvaria bean) throws PromoveException {
		try {
			localDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	@Override
	public void salvarOrigemAvaria(OrigemAvaria bean) throws PromoveException {
		try {
			origemDAO.save(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	@Override
	public void excluirOrigemAvaria(OrigemAvaria bean) throws PromoveException {
		try {
			origemDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	@Override
	public List<OrigemAvaria> buscarTodasOrigensAvaria() throws PromoveException {
		List<OrigemAvaria> lista = null;
		try {
			lista = origemDAO.getAll();
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	@Override
	public void salvarExtensaoAvaria(ExtensaoAvaria bean)
			throws PromoveException {
		try {
			extensaoDAO.save(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	@Override
	public void excluirExtensaoAvaria(ExtensaoAvaria bean)
			throws PromoveException {
		try {
			extensaoDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	@Override
	public List<ExtensaoAvaria> buscarTodasExtensoesAvaria()
			throws PromoveException {
		List<ExtensaoAvaria> lista = null;
		try {
			lista = extensaoDAO.getAll();
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}
	

	@Override
	public List<Clima> buscarTodosClimas() throws PromoveException {
		List<Clima> lista = null;
		try {
			lista = climaDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarClima(Clima bean) throws PromoveException {
		try {
			climaDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirClima(Clima bean) throws PromoveException {
		try {
			climaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void salvarAvaria(Avaria bean) throws PromoveException {
		salvarAvaria(bean, false);		
	}
	
	@Override
	public void salvarAvaria(Avaria bean, boolean isFlush)
			throws PromoveException {
		try {
			avariaDAO.save(bean);
			if(isFlush)
				avariaDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirAvaria(Avaria bean) throws PromoveException {
		try {
			avariaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}
	
	@Override
	public List<Avaria> buscarTodasAvarias() throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAllCustom();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarFotoAvaria(FotoAvaria foto, boolean isFlush) throws PromoveException {
		try {
			fotoDAO.save(foto);
			if(isFlush)
				fotoDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Avaria> buscarAvariaPorFiltros(String chassi, Avaria av, Date de, Date ate)throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAvariasPorFiltro(chassi, av, de, ate);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<ResponsabilidadeAvaria> buscarTodasResponsabilidades() throws PromoveException {
		List<ResponsabilidadeAvaria> lista = null;
		try {
			lista = responsaDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<Avaria> buscarAvariaDuplicadaPorFiltros(List<Veiculo> veiculos,	Avaria av) throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAvariasDuplicadasPorFiltro(veiculos, av);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarInconsistenciaImportAvaria(Avaria avaria, String msgErro)throws PromoveException {
		try {
			inconsistenciaAvariaDAO.save(new InconsistenciaAvaria(avaria, msgErro));
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void cleanUpSession() throws PromoveException {
		try {
			inconsistenciaAvariaDAO.rebuildSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public <T> T getById(Class<T> clazz, Integer id) throws PromoveException {
		try {
			return avariaDAO.getGenericObject(clazz, id);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public OrigemAvaria buscarOrigemPorTipoEFilial(String tipo_id, String filial_id) throws PromoveException {
		try {
			List<OrigemAvaria> lista = origemDAO.getOrigemPorTipoEFilial(tipo_id, filial_id);
			if(lista.size() > 0)
				return lista.get(0);
			else
				return null;
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}
}
