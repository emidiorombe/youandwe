package br.com.promove.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import br.com.promove.dao.AvariaDAO;
import br.com.promove.dao.ClimaDAO;
import br.com.promove.dao.ExtensaoDAO;
import br.com.promove.dao.FotoAvariaDAO;
import br.com.promove.dao.InconsistenciaAvariaDAO;
import br.com.promove.dao.LocalAvariaDAO;
import br.com.promove.dao.NivelAvariaDAO;
import br.com.promove.dao.OrigemAvariaDAO;
import br.com.promove.dao.ResponsabilidadeDAO;
import br.com.promove.dao.StatusAvariaDAO;
import br.com.promove.dao.TipoAvariaDAO;
import br.com.promove.dao.VeiculoDAO;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.NivelAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.PieData;
import br.com.promove.entity.ResponsabilidadeAvaria;
import br.com.promove.entity.StatusAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.utils.DateUtils;
import br.com.promove.utils.StringUtilities;

/**
 * Serviços de Avarias
 * @author Rafael Nunes
 *
 */
public class AvariaService implements Serializable {
	private TipoAvariaDAO tipoDAO;
	private LocalAvariaDAO localDAO;
	private OrigemAvariaDAO origemDAO;
	private ExtensaoDAO extensaoDAO;
	private ClimaDAO climaDAO;
	private AvariaDAO avariaDAO;
	private FotoAvariaDAO fotoDAO;
	private ResponsabilidadeDAO responsaDAO;
	private NivelAvariaDAO nivelDAO;
	private InconsistenciaAvariaDAO inconsistenciaAvariaDAO;
	private VeiculoDAO veiculoDAO;
	private StatusAvariaDAO statusAvariaDAO;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	
	AvariaService() {
		tipoDAO = new TipoAvariaDAO();
		localDAO = new LocalAvariaDAO();
		origemDAO = new OrigemAvariaDAO();
		extensaoDAO = new ExtensaoDAO();
		climaDAO = new ClimaDAO();
		avariaDAO = new AvariaDAO();
		fotoDAO = new FotoAvariaDAO();
		responsaDAO = new ResponsabilidadeDAO();
		inconsistenciaAvariaDAO = new InconsistenciaAvariaDAO(); 
		veiculoDAO = new VeiculoDAO();
		nivelDAO = new NivelAvariaDAO();
		statusAvariaDAO = new StatusAvariaDAO();
	}

	/**
	 * Cria um novo ou altera o Tipo de Avaria
	 * @param tipo avaria
	 * @throws PromoveException 
	 */
	public void salvarTipoAvaria(TipoAvaria tipoa) throws PromoveException {
		try {
			tipoDAO.save(tipoa);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
	}

	/**
	 * Busca todos os Tipos de avaria
	 * @return
	 * @throws PromoveException 
	 */
	public List<TipoAvaria> buscarTodosTipoAvaria() throws PromoveException {
		return buscarTodosTipoAvaria("descricao");
	}
	
	public List<TipoAvaria> buscarTodosTipoAvaria(String sortField) throws PromoveException {
		List<TipoAvaria> lista = null;
		try {
			lista = tipoDAO.getAll(sortField);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	/**
	 * Remove um Tipo de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	public void excluirTipoAvaria(TipoAvaria bean) throws PromoveException {
		try {
			tipoDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}

	}

	/**
	 * Busca todos os locais de avaria
	 * @return
	 */
	public List<LocalAvaria> buscarTodosLocaisAvaria() throws PromoveException {
		return buscarTodosLocaisAvaria("descricao");
	}
	
	public List<LocalAvaria> buscarTodosLocaisAvaria(String sortField) throws PromoveException {
		List<LocalAvaria> lista = null;
		try {
			lista = localDAO.getAll(sortField);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	/**
	 * Salva o Local de Avaria
	 * @param class1
	 */
	public void salvarLocalAvaria(LocalAvaria local) throws PromoveException {
		try {
			localDAO.save(local);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	/**
	 * Remove um Local de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	public void excluirLocalAvaria(LocalAvaria bean) throws PromoveException {
		try {
			localDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	/**
	 * Salva a origem da Avaria
	 * @param class1
	 */
	public void salvarOrigemAvaria(OrigemAvaria bean) throws PromoveException {
		try {
			origemDAO.save(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
	}

	/**
	 * Remove um Origem de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	public void excluirOrigemAvaria(OrigemAvaria bean) throws PromoveException {
		try {
			origemDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	/**
	 * Busca todos as origens de avaria
	 * @return
	 */
	public List<OrigemAvaria> buscarTodasOrigensAvaria() throws PromoveException {
		return buscarTodasOrigensAvaria("codigo");
	}
	
	public List<OrigemAvaria> buscarTodasOrigensAvaria(String sortField) throws PromoveException {
		List<OrigemAvaria> lista = null;
		try {
			lista = origemDAO.getAll(sortField);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

	/**
	 * Salva a Extensao da Avaria
	 * @param bean
	 */
	public void salvarExtensaoAvaria(ExtensaoAvaria bean)
			throws PromoveException {
		try {
			extensaoDAO.save(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	/**
	 * Remove uma Extensao de Avaria
	 * @param bean
	 * @throws PromoveException
	 */
	public void excluirExtensaoAvaria(ExtensaoAvaria bean)
			throws PromoveException {
		try {
			extensaoDAO.delete(bean);
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		
	}

	/**
	 * Busca todos as extensões de avaria
	 * @return
	 */
	
	public List<ExtensaoAvaria> buscarTodasExtensoesAvaria()
			throws PromoveException {
		List<ExtensaoAvaria> lista = null;
		try {
			lista = extensaoDAO.getAll("codigo");
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}
	

	public List<Clima> buscarTodosClimas() throws PromoveException {
		List<Clima> lista = null;
		try {
			lista = climaDAO.getAll("codigo");
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarClima(Clima bean) throws PromoveException {
		try {
			climaDAO.save(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void excluirClima(Clima bean) throws PromoveException {
		try {
			climaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void salvarAvaria(Avaria bean) throws PromoveException {
		salvarAvaria(bean, false);
	}
	
	public void salvarAvaria(Avaria bean, boolean isFlush)
			throws PromoveException {
		try {
			
			//verifica se chassi é válido
			if(bean.getVeiculo() != null && bean.getVeiculo().getId() == null) {
				List<Veiculo> vs = veiculoDAO.getByChassi(bean.getVeiculo().getChassi());
				if(vs.size() == 0)
					throw new IllegalArgumentException("Chassi Inválido!");
				else
					bean.setVeiculo(vs.get(0));
			}
			
			//verifica duplicidade para avarias novas
			if(bean.getId() == null) {
				List<Avaria> list = buscarAvariaDuplicadaPorFiltros(bean.getVeiculo(), bean);
				if(list.size() > 0)
					throw new IllegalArgumentException("Avaria já cadastrada");
				
				if (bean.getStatus() == null) bean.setStatus(this.getById(StatusAvaria.class, 5));
				avariaDAO.save(bean);
			}else {
				avariaDAO.saveWithId(bean);
			}
			
			if(isFlush)
				avariaDAO.flushSession();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new PromoveException(e);
		}
	}

	public void excluirAvaria(Avaria bean) throws PromoveException {
		try {
			avariaDAO.delete(bean);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}
	
	public List<Avaria> buscarTodasAvarias() throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAllCustom();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void salvarFotoAvaria(FotoAvaria foto, boolean isFlush) throws PromoveException {
		try {
			fotoDAO.save(foto);
			if(isFlush)
				fotoDAO.flushSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public List<Avaria> buscarAvariaPorFiltros(Avaria av, Date de, Date ate, Integer periodo, Boolean movimentacao, Boolean registradas, Boolean vistoriaFinal, Boolean posterior, Boolean cancelados, OrigemAvaria oriAte, ResponsabilidadeAvaria responsabilidade, Fabricante fabricante, Usuario user) throws PromoveException {
		List<Avaria> lista = null;
		try {
			Date init = DateUtils.montarDataInicialParaHQLQuery(de); 
			Date fim = DateUtils.montarDataFinalParaHQLQuery(ate); 
			
			lista = avariaDAO.getAvariasPorFiltro(av, init, fim, periodo, movimentacao, registradas, vistoriaFinal, posterior, cancelados, oriAte, responsabilidade, fabricante, user);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<ResponsabilidadeAvaria> buscarTodasResponsabilidades() throws PromoveException {
		List<ResponsabilidadeAvaria> lista = null;
		try {
			lista = responsaDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Avaria> buscarAvariaDuplicadaPorFiltros(List<Veiculo> veiculos,	Avaria av) throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAvariasDuplicadasPorFiltro(veiculos, av);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
	
	public List<Avaria> buscarAvariaDuplicadaPorFiltros(Veiculo veiculo, Avaria av) throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAvariasDuplicadasPorFiltro(veiculo, av);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<Avaria> buscarAvariaDuplicadaPorData(List<Veiculo> veiculos, Avaria av) throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAvariasDuplicadasPorData(veiculos, av);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
	
	public List<Avaria> buscarAvariaDuplicadaPorData(Veiculo veiculo, Avaria av) throws PromoveException {
		List<Avaria> lista = null;
		try {
			lista = avariaDAO.getAvariasDuplicadasPorData(veiculo, av);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public void cleanUpSession() throws PromoveException {
		try {
			inconsistenciaAvariaDAO.rebuildSession();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public <T> T getById(Class<T> clazz, Integer id) throws PromoveException {
		try {
			return avariaDAO.getGenericObject(clazz, id);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

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

	public List<InconsistenciaAvaria> buscarTodasInconsistenciasAvaria()throws PromoveException {
		List<InconsistenciaAvaria> lista = null;
		try {
			lista = inconsistenciaAvariaDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
	
	public List<InconsistenciaAvaria> buscarInconsistenciaAvariaPorChassi(String chassi) throws PromoveException {
		List<InconsistenciaAvaria> lista = null;
		try {
			lista = inconsistenciaAvariaDAO.getInconsistenciaAvariaPorChassi(chassi);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}
	
	public InconsistenciaAvaria salvarInconsistenciaImportAvaria(Avaria avaria, String msgErro, Element node_av) throws PromoveException {
		try {
			InconsistenciaAvaria inc = new InconsistenciaAvaria(avaria, msgErro);
			inc.setChassiInvalido(StringUtilities.getChassiFromErrorMessage(msgErro));
			if (node_av != null && node_av.element("motorista_nome") != null) inc.setNomeMotorista(node_av.element("motorista_nome").getText());
			if(avaria.getVeiculo() != null) inc.setChassiInvalido(avaria.getVeiculo().getChassi());
 			inconsistenciaAvariaDAO.save(inc);
			return inc;
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public void excluirInconsistenciaImportAvaria(InconsistenciaAvaria inc)throws PromoveException {
		try {
			inconsistenciaAvariaDAO.delete(inc);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		
	}

	public void salvarAvariaDeInconsistencias(InconsistenciaAvaria inc, Boolean verificaData) throws PromoveException {
		try {
			if (inc.getLocal() == null || inc.getTipo() == null)  {
				inconsistenciaAvariaDAO.save(inc);
			} else {
				Avaria avaria = inc.getAvaria();
				List<Veiculo> list = null;
				String chassi = avaria.getVeiculo() != null ? avaria.getVeiculo().getChassi() : 
					(inc.getChassiInvalido() != null ? inc.getChassiInvalido() : StringUtilities.getChassiFromErrorMessage(inc.getMsgErro()));
				
				if (chassi.contains("000000000")) {
					chassi = chassi.replace("000000000", "");
					list = veiculoDAO.getByModeloFZAndData(chassi, avaria.getDataLancamento());
				} else {
					list = veiculoDAO.getByChassi(chassi);
				}
				
				if (list.size() > 0) {
					if (verificaData && this.buscarAvariaDuplicadaPorData(list, avaria).size() > 0) {
						inc.setMsgErro("Existe vistoria em outra data!;");
						inconsistenciaAvariaDAO.save(inc);
					} else {
						if (buscarAvariaDuplicadaPorFiltros(list, avaria).size() == 0) {
							avaria.setStatus(this.getById(StatusAvaria.class, 5));
							avaria.setVeiculo(list.get(0));
							avariaDAO.save(avaria);
							List<FotoAvaria> fotos = fotoDAO.getByInconsistencia(inc.getId());
							for (FotoAvaria foto : fotos) {
								foto.setAvaria(avaria);
								fotoDAO.saveWithId(foto);
							}
						}
						inconsistenciaAvariaDAO.delete(inc);
					}
				}
			}
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
	}

	public Map<String, List<PieData>> buscarResumo(Veiculo veiculo, Date de, Date ate, Integer periodo, OrigemAvaria oride, OrigemAvaria oriate, String item, String subitem, Boolean posterior, Boolean cancelados) throws PromoveException {
		Map<String, List<PieData>> lista = null;
		try {
			Date init = DateUtils.montarDataInicialParaSQLQuery(de); 
			Date fim = DateUtils.montarDataFinalParaSQLQuery(ate); 
			
			lista = avariaDAO.buscarResumo(veiculo, init, fim, periodo, oride, oriate, item, subitem, posterior, cancelados);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<NivelAvaria> buscarTodosNiveisAvaria() throws PromoveException {
		List<NivelAvaria> lista = null;
		try {
			lista = nivelDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public List<StatusAvaria> buscarTodosStatusAvaria() throws PromoveException {
		List<StatusAvaria> lista = null;
		try {
			lista = statusAvariaDAO.getAll();
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		return lista;
	}

	public String listarAvariasPT(Date date) throws PromoveException {
		List<Avaria> lista = null;
		try {
			Avaria av = new Avaria();
			av.setNivel(this.getById(NivelAvaria.class, 4));
			lista = avariaDAO.getAvariasPorFiltro(av, date, date, 2, false, false, false, false, false, null, null, null, null);
		} catch (DAOException e) {
			throw new PromoveException(e);
		}
		StringBuilder conteudo = new StringBuilder();
		conteudo.append("<b>Lista de Possivel PT:</b><br>");
		if (lista.size() == 0) {
			conteudo.append("Sem ocorrencias.");
		} else {
			conteudo.append("<table style=\"FONT-FAMILY: 'Arial','sans-serif'; FONT-SIZE: 10pt\">");
			conteudo.append("<tr><b>");
			conteudo.append("<td>Data da Vistoria</td>");
			conteudo.append("<td>Chassi</td>");
			conteudo.append("<td>Modelo</td>");
			conteudo.append("<td>Local da Vistoria</td>");
			conteudo.append("<td>Peca Avariada</td>");
			conteudo.append("<td>Tipo de Avaria</td>");
			conteudo.append("</b></tr>");
			for (Avaria av : lista) {
				conteudo.append("<tr>");
				conteudo.append("<td>" + date_format.format(av.getDataLancamento()) + "</td>");
				conteudo.append("<td>" + av.getVeiculo().getChassi() + "</td>");
				conteudo.append("<td>" + av.getVeiculo().getModelo().getDescricao() + "</td>");
				conteudo.append("<td>" + av.getOrigem().getDescricao() + "</td>");
				conteudo.append("<td>" + av.getLocal().getDescricao() + "</td>");
				conteudo.append("<td>" + av.getTipo().getDescricao() + "</td>");
				conteudo.append("</tr>");
			}
			conteudo.append("</table><br>");
		}
		return conteudo.toString();
	}
}
