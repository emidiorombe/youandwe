package br.com.promove.service;

import java.util.List;

import br.com.promove.dao.ExtensaoDAO;
import br.com.promove.dao.LocalAvariaDAO;
import br.com.promove.dao.OrigemAvariaDAO;
import br.com.promove.dao.TipoAvariaDAO;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;

public class AvariaServiceImpl implements AvariaService {
	private TipoAvariaDAO tipoDAO;
	private LocalAvariaDAO localDAO;
	private OrigemAvariaDAO origemDAO;
	private ExtensaoDAO extensaoDAO;

	AvariaServiceImpl() {
		tipoDAO = new TipoAvariaDAO();
		localDAO = new LocalAvariaDAO();
		origemDAO = new OrigemAvariaDAO();
		extensaoDAO = new ExtensaoDAO();
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

}
