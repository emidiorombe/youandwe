package br.com.promove.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.promove.dao.CarretaDAO;
import br.com.promove.dao.CorDAO;
import br.com.promove.dao.FabricanteDAO;
import br.com.promove.dao.FilialDAO;
import br.com.promove.dao.FrotaDAO;
import br.com.promove.dao.InconsistenciaVeiculoDAO;
import br.com.promove.dao.ModeloDAO;
import br.com.promove.dao.MotoristaDAO;
import br.com.promove.dao.ParametroDAO;
import br.com.promove.dao.TipoUsuarioDAO;
import br.com.promove.dao.TipoVeiculoDAO;
import br.com.promove.dao.UsuarioDAO;
import br.com.promove.dao.VeiculoDAO;
import br.com.promove.entity.Carreta;
import br.com.promove.entity.Cor;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Filial;
import br.com.promove.entity.Frota;
import br.com.promove.entity.InconsistenciaVeiculo;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.Motorista;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.Parametro;
import br.com.promove.entity.PieData;
import br.com.promove.entity.TipoUsuario;
import br.com.promove.entity.TipoVeiculo;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.utils.DateUtils;
import br.com.promove.utils.StringUtilities;

public class CadastroService implements Serializable {
	private CorDAO corDAO;
	private ModeloDAO modeloDAO;
	private FabricanteDAO fabricanteDAO;
	private FilialDAO filialDAO;
	private UsuarioDAO usuarioDAO;
	private ParametroDAO parametroDAO;
	private TipoUsuarioDAO tipoUsuarioDAO;
	private TipoVeiculoDAO tipoVeiculoDAO;
	private VeiculoDAO veiculoDAO;
	private MotoristaDAO motoristaDAO;
	private FrotaDAO frotaDAO;
	private CarretaDAO carretaDAO;
	private InconsistenciaVeiculoDAO inconsistenciaVeiculoDAO;
	
	public CadastroService() {
		corDAO = new CorDAO();
		modeloDAO = new ModeloDAO();
		fabricanteDAO = new FabricanteDAO();
		filialDAO = new FilialDAO();
		usuarioDAO = new UsuarioDAO();
		parametroDAO = new ParametroDAO();
		tipoUsuarioDAO = new TipoUsuarioDAO();
		tipoVeiculoDAO = new TipoVeiculoDAO();
		veiculoDAO = new VeiculoDAO();
		inconsistenciaVeiculoDAO = new InconsistenciaVeiculoDAO();
		motoristaDAO = new MotoristaDAO();
		frotaDAO = new FrotaDAO();
		carretaDAO = new CarretaDAO();
	}
	
	public void salvarCor(Cor cor) throws PromoveException {
		try {
			corDAO.save(cor);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public List<Cor> buscarTodasCores() throws PromoveException {
		return buscarTodasCores("descricao");
	}

	public List<Cor> buscarTodasCores(String sortField) throws PromoveException {
		List<Cor> lista = null;
		try {
			lista = corDAO.getAll(sortField);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void excluirCor(Cor cor) throws PromoveException {
		try {
			corDAO.delete(cor);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void salvarModelo(Modelo bean) throws PromoveException {
		try {
			modeloDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirModelo(Modelo bean) throws PromoveException {
		try {
			modeloDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Modelo> buscarTodosModelos() throws PromoveException {
		return buscarTodosModelos("descricao");
	}
	
	public List<Modelo> buscarTodosModelos(String sortField) throws PromoveException {
		List<Modelo> lista = null;
		try {
			lista = modeloDAO.getAll(sortField);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Fabricante> buscarTodosFabricantes() throws PromoveException {
		List<Fabricante> lista = null;
		try {
			lista = fabricanteDAO.getAll("codigo");
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarFabricante(Fabricante bean) throws PromoveException {
		try {
			fabricanteDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirFabricante(Fabricante bean) throws PromoveException {
		try {
			fabricanteDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void salvarFilial(Filial bean) throws PromoveException {
		try {
			filialDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void excluirFilial(Filial bean) throws PromoveException {
		try {
			filialDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public List<Filial> buscarTodasFiliais() throws PromoveException {
		List<Filial> lista = null;
		try {
			lista = filialDAO.getAll("codigo");
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarUsuario(Usuario bean) throws PromoveException {
		try {
			usuarioDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void excluirUsuario(Usuario bean) throws PromoveException {
		try {
			usuarioDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public List<Usuario> buscarTodosUsuarios() throws PromoveException {
		List<Usuario> lista = null;
		try {
			lista = usuarioDAO.getAll("codigo");
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarMotorista(Motorista bean) throws PromoveException {
		try {
			motoristaDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirMotorista(Motorista bean) throws PromoveException {
		try {
			motoristaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Motorista> buscarTodosMotoristas() throws PromoveException {
		return buscarTodosMotoristas("codigo");
	}
	
	public List<Motorista> buscarTodosMotoristas(String sortField) throws PromoveException {
		List<Motorista> lista = null;
		try {
			lista = motoristaDAO.getAll(sortField);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarFrota(Frota bean) throws PromoveException {
		try {
			frotaDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirFrota(Frota bean) throws PromoveException {
		try {
			frotaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Frota> buscarTodasFrotas() throws PromoveException {
		return buscarTodasFrotas("codigo");
	}
	
	public List<Frota> buscarTodasFrotas(String sortField) throws PromoveException {
		List<Frota> lista = null;
		try {
			lista = frotaDAO.getAll(sortField);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarCarreta(Carreta bean) throws PromoveException {
		try {
			carretaDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirCarreta(Carreta bean) throws PromoveException {
		try {
			carretaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Carreta> buscarTodasCarretas() throws PromoveException {
		return buscarTodasCarretas("codigo");
	}
	
	public List<Carreta> buscarTodasCarretas(String sortField) throws PromoveException {
		List<Carreta> lista = null;
		try {
			lista = carretaDAO.getAll(sortField);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarParametro(Parametro bean) throws PromoveException {
		try {
			parametroDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirParametro(Parametro bean) throws PromoveException {
		try {
			parametroDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Parametro> buscarTodosParametros() throws PromoveException {
		List<Parametro> lista = null;
		try {
			lista = parametroDAO.getAll("chave");
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;

	}

	public List<TipoUsuario> buscarTodosTiposUsuarios() throws PromoveException {
		List<TipoUsuario> lista = null;
		try {
			lista = tipoUsuarioDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<String> buscarTodosNavios() throws PromoveException {
		List<String> lista = null;
		try {
			lista = VeiculoDAO.buscarTodosNavios();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<TipoVeiculo> buscarTodosTiposVeiculos() throws PromoveException {
		List<TipoVeiculo> lista = null;
		try {
			lista = tipoVeiculoDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarVeiculo(Veiculo bean) throws PromoveException {
		salvarVeiculo(bean, false);
	}

	public void excluirVeiculo(Veiculo bean) throws PromoveException {
		try {
			veiculoDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Veiculo> buscarTodosVeiculos() throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getAllCustom();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Veiculo> buscarVeiculoPorFiltro(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo, Fabricante fabricante, String sort) throws PromoveException {
		List<Veiculo> lista = null;
		try {
			Date init = DateUtils.montarDataInicialParaHQLQuery(dtInicio); 
			Date fim = DateUtils.montarDataFinalParaHQLQuery(dtFim); 
			
			lista = veiculoDAO.getByFilter(veiculo, init, fim, periodo, fabricante, sort);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Veiculo> buscarVeiculosPorChassi(String chassi) throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getByChassi(chassi);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarVeiculo(Veiculo veiculo, boolean isFlush)	throws PromoveException {
		try {
			if(veiculo.getId() == null && veiculoDAO.getByChassi(veiculo.getChassi()).size() > 0)
				throw new IllegalArgumentException("Chassi já cadastrado!");
			
			veiculoDAO.save(veiculo);
			if(isFlush)
				veiculoDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void salvarInconsistenciaVeiculo(Veiculo v, String message) throws PromoveException {
		try {
			InconsistenciaVeiculo inc = new InconsistenciaVeiculo(v, message);
			//if(v.getTipo() == 1) {
			//	inc.setCorInvalida(StringUtilities.getCorFromErrorMessage(message));
			//	inc.setModeloInvalido(StringUtilities.getModeloFromErrorMessage(message));
			//}else if(v.getTipo() == 2) {
				inc.setModeloInvalido(StringUtilities.getModeloFromErrorMessage(message));
			//}
			inconsistenciaVeiculoDAO.save(inc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}
	
	public <T> T getById(Class<T> clazz, Integer id) throws PromoveException {
		try {
			return veiculoDAO.getGenericObject(clazz, id);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<InconsistenciaVeiculo> buscarTodasInconsistenciasDeVeiculos()throws PromoveException {
		List<InconsistenciaVeiculo> lista = null;
		try {
			lista = inconsistenciaVeiculoDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void excluirInconsistenciaVeiculo(InconsistenciaVeiculo bean)throws PromoveException {
		try {
			inconsistenciaVeiculoDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public List<Veiculo> buscarVeiculosPorModeloFZData(String chassi, Date data)throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getByModeloFZAndData(chassi, data);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Cor> buscaCorPorCodigoExterno(String codigo)
			throws PromoveException {
		List<Cor> lista = null;
		try {
			lista = corDAO.getByCodigoExterno(codigo);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Modelo> buscarModeloPorCodigoOuDescricao(String codigo,
			String desc) throws PromoveException {
		List<Modelo> lista = null;
		try {
			lista = modeloDAO.getByCodigoOrDescricao(codigo, desc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public Usuario autenticarUsuario(String user, String password)throws PromoveException {
		try {
			return usuarioDAO.getByUserAndPass(user, password);
		} catch (DAOException e) {
			throw new PromoveException("Erro durante a autenticação");
		}
	}

	public List<Veiculo> buscarVeiculosPorFZ(String fz) throws PromoveException {
		List<Veiculo> lista = null;
		try {
			if (fz.contains("000000000"))
			    fz = fz.replace("000000000", "");
			lista = veiculoDAO.getByFz(fz.substring(fz.length()-6));
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Veiculo> buscarVeiculosAuditoria(Veiculo veiculo, Date de, Date ate, Integer periodo, OrigemAvaria oride, OrigemAvaria oriate, Boolean vistoriaFinal) throws PromoveException {
		List<Veiculo> lista = null;
		try {
			Date init = DateUtils.montarDataInicialParaHQLQuery(de); 
			Date fim = DateUtils.montarDataFinalParaHQLQuery(ate); 
			
			lista = veiculoDAO.buscarVeiculosAuditoria(veiculo, init, fim, periodo, oride, oriate, vistoriaFinal);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public Map<String, List<PieData>> buscarAnaliseResultado(Veiculo veiculo, Date de, Date ate, Integer periodo, OrigemAvaria oride, OrigemAvaria oriate, String item, Boolean vistoriaFinal, Boolean posterior, Boolean cancelados) throws PromoveException {
		Map<String, List<PieData>> lista = null;
		try {
			Date init = DateUtils.montarDataInicialParaSQLQuery(de); 
			Date fim = DateUtils.montarDataFinalParaSQLQuery(ate);
			
			lista = veiculoDAO.buscarAnaliseResultado(veiculo, init, fim, periodo, oride, oriate, item, vistoriaFinal, posterior, cancelados);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public Map<String, String> buscarTodosParametrosAsMap()
			throws PromoveException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for(Object o : parametroDAO.getAll("chave")) {
				Parametro p = (Parametro) o;
				map.put(p.getChave(), p.getValor());
			}
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return map;
	}

}
