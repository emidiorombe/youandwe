package br.com.promove.service;

import java.io.Serializable;
import java.util.List;

import br.com.promove.dao.CorDAO;
import br.com.promove.dao.FabricanteDAO;
import br.com.promove.dao.FilialDAO;
import br.com.promove.dao.ModeloDAO;
import br.com.promove.dao.TipoUsuarioDAO;
import br.com.promove.dao.UsuarioDAO;
import br.com.promove.dao.VeiculoDAO;
import br.com.promove.entity.Cor;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Filial;
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
	
	public CadastroServiceImpl() {
		corDAO = new CorDAO();
		modeloDAO = new ModeloDAO();
		fabricanteDAO = new FabricanteDAO();
		filialDAO = new FilialDAO();
		usuarioDAO = new UsuarioDAO();
		tipoUsuarioDAO = new TipoUsuarioDAO();
		veiculoDAO = new VeiculoDAO();
		
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
		try {
			veiculoDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
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
}
