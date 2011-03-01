package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.promove.dao.CorDAO;
import br.com.promove.dao.FabricanteDAO;
import br.com.promove.dao.FilialDAO;
import br.com.promove.dao.InconsistenciaVeiculoDAO;
import br.com.promove.dao.ModeloDAO;
import br.com.promove.dao.TipoUsuarioDAO;
import br.com.promove.dao.UsuarioDAO;
import br.com.promove.dao.VeiculoDAO;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Cor;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Filial;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.InconsistenciaVeiculo;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.TipoUsuario;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;


public class CadastroServiceImpl implements CadastroService, Serializable{
	private CorDAO corDAO;
	private ModeloDAO modeloDAO;
	private FabricanteDAO fabricanteDAO;
	private FilialDAO filialDAO;
	private UsuarioDAO usuarioDAO;
	private TipoUsuarioDAO tipoUsuarioDAO;
	private VeiculoDAO veiculoDAO;
	private InconsistenciaVeiculoDAO inconsistenciaVeiculoDAO;
	
	public CadastroServiceImpl() {
		corDAO = new CorDAO();
		modeloDAO = new ModeloDAO();
		fabricanteDAO = new FabricanteDAO();
		filialDAO = new FilialDAO();
		usuarioDAO = new UsuarioDAO();
		tipoUsuarioDAO = new TipoUsuarioDAO();
		veiculoDAO = new VeiculoDAO();
		inconsistenciaVeiculoDAO = new InconsistenciaVeiculoDAO();
		
	}
	
	@Override
	public void salvarCor(Cor cor) throws PromoveException {
		try {
			corDAO.save(cor);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Cor> buscarTodasCores() throws PromoveException {
		List<Cor> lista = null;
		try {
			lista = corDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void excluirCor(Cor cor) throws PromoveException {
		try {
			corDAO.delete(cor);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void salvarModelo(Modelo bean) throws PromoveException {
		try {
			modeloDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirModelo(Modelo bean) throws PromoveException {
		try {
			modeloDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Modelo> buscarTodosModelos() throws PromoveException {
		List<Modelo> lista = null;
		try {
			lista = modeloDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<Fabricante> buscarTodosFabricantes() throws PromoveException {
		List<Fabricante> lista = null;
		try {
			lista = fabricanteDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarFabricante(Fabricante bean) throws PromoveException {
		try {
			fabricanteDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirFabricante(Fabricante bean) throws PromoveException {
		try {
			fabricanteDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void salvarFilial(Filial bean) throws PromoveException {
		try {
			filialDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirFilial(Filial bean) throws PromoveException {
		try {
			filialDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Filial> buscarTodasFiliais() throws PromoveException {
		List<Filial> lista = null;
		try {
			lista = filialDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarUsuario(Usuario bean) throws PromoveException {
		try {
			usuarioDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void excluirUsuario(Usuario bean) throws PromoveException {
		try {
			usuarioDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Usuario> buscarTodosUsuarios() throws PromoveException {
		List<Usuario> lista = null;
		try {
			lista = usuarioDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;

	}

	@Override
	public List<TipoUsuario> buscarTodosTiposUsuarios() throws PromoveException {
		List<TipoUsuario> lista = null;
		try {
			lista = tipoUsuarioDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarVeiculo(Veiculo bean) throws PromoveException {
		salvarVeiculo(bean, false);
	}

	@Override
	public void excluirVeiculo(Veiculo bean) throws PromoveException {
		try {
			veiculoDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public List<Veiculo> buscarTodosVeiculos() throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getAllCustom();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<Veiculo> buscarVeiculoPorFiltro(Veiculo veiculo, Date dtInicio, Date dtFim) throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getByFilter(veiculo, dtInicio, dtFim);
			for (Veiculo v : lista) {
				for(Avaria av: v.getAvarias()) {
					av.getFotos().size();
				}
			}
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public List<Veiculo> buscarVeiculosPorChassi(String chassi) throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getByChassi(chassi);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void salvarVeiculo(Veiculo veiculo, boolean isFlush)	throws PromoveException {
		try {
			if(veiculoDAO.getByChassi(veiculo.getChassi()).size() > 0)
				throw new IllegalArgumentException("Chassi já cadastrado!");
			
			veiculoDAO.save(veiculo);
			if(isFlush)
				veiculoDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	@Override
	public void salvarInconsistenciaVeiculo(Veiculo v, String message, Integer tipo)throws PromoveException {
		try {
			inconsistenciaVeiculoDAO.save(new InconsistenciaVeiculo(v, message, tipo));
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}
	
	@Override
	public <T> T getById(Class<T> clazz, Integer id) throws PromoveException {
		try {
			return veiculoDAO.getGenericObject(clazz, id);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public List<InconsistenciaVeiculo> buscarTodasInconsistenciasDeVeiculos()throws PromoveException {
		List<InconsistenciaVeiculo> lista = null;
		try {
			lista = inconsistenciaVeiculoDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	@Override
	public void excluirInconsistenciaVeiculo(InconsistenciaVeiculo bean)throws PromoveException {
		try {
			inconsistenciaVeiculoDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	@Override
	public List<Veiculo> buscarVeiculosPorModeloFZ(String chassi)throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getByModeloFZ(chassi);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
}
