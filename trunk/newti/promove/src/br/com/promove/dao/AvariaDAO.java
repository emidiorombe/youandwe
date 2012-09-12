package br.com.promove.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.PieData;
import br.com.promove.entity.ResponsabilidadeAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.utils.DateUtils;
import br.com.promove.utils.StringUtilities;

public class AvariaDAO extends BaseDAO<Integer, Avaria>{
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	
	public List<Avaria> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos");
		return executeQuery(hql.toString(), 0, Integer.MAX_VALUE);
	}

	public List<Avaria> getAvariasPorFiltro(Avaria av, Date de, Date ate, Integer periodo, Boolean movimentacao, Boolean registradas, Boolean vistoriaFinal, Boolean posterior, Boolean cancelados, OrigemAvaria oriAte, ResponsabilidadeAvaria responsabilidade, Fabricante fabricante, Usuario user) throws DAOException {
		//if ((oriAte == null || oriAte.getId() == null) &&
		//		av.getOrigem() != null && av.getOrigem().getId() != null) oriAte = av.getOrigem();
		//if ((av.getOrigem() == null || av.getOrigem().getId() == null) && 
		//		oriAte != null && oriAte.getId() != null) av.setOrigem(oriAte);
		
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos left join fetch av.veiculo veic");
		hql.append(" left JOIN FETCH av.tipo tp left JOIN FETCH av.origem ori left JOIN FETCH av.extensao ext left JOIN FETCH av.local loc left JOIN FETCH av.clima cli");
		hql.append(" left JOIN FETCH av.usuario usu left JOIN FETCH usu.tipo tpu left JOIN FETCH veic.modelo mod left JOIN FETCH veic.cor cor left JOIN FETCH mod.fabricante fab");
		hql.append(" left JOIN FETCH ori.responsabilidade resp left JOIN FETCH usu.filial usufil left JOIN FETCH ori.filial orifil");
		hql.append(" where veic.tipo.id <> 9");
		if(av != null) {
			if(av.getNivel() != null && av.getNivel().getId() != null) {
				hql.append(" and av.nivel = :niv");
				addParamToQuery("niv", av.getNivel());
			}
			
			if(av.getTipo() != null && av.getTipo().getId() != null) {
				hql.append(" and av.tipo = :tp");
				addParamToQuery("tp", av.getTipo());
			}
			
			if(av.getLocal() != null && av.getLocal().getId() != null) {
				hql.append(" and av.local = :loc");
				addParamToQuery("loc", av.getLocal());
			}
			
			//if(av.getOrigem() != null && av.getOrigem().getId() != null) {
			//	hql.append(" and av.origem.codigo between :org and :org2");
			//	addParamToQuery("org", av.getOrigem().getCodigo());
			//	addParamToQuery("org2", oriAte.getCodigo());
			//}
	
			if(av.getOrigem() != null && av.getOrigem().getId() != null) {
				hql.append(" and av.origem.codigo >= :org");
				addParamToQuery("org", av.getOrigem().getCodigo());
			}
	
			if(oriAte != null && oriAte.getId() != null) {
				hql.append(" and av.origem.codigo <= :org2");
				addParamToQuery("org2", oriAte.getCodigo());
			}
	
			if(av.getVeiculo() != null) {
				if(av.getVeiculo().getId() != null && av.getVeiculo().getId() != 0) {
					hql.append(" and veic.id = :txtVeic");
					addParamToQuery("txtVeic", av.getVeiculo().getId());
				} else {
					if(av.getVeiculo().getChassi() != null && !av.getVeiculo().getChassi().isEmpty() && !av.getVeiculo().getChassi().equals("")) {
						hql.append(" and veic.chassi like :txtChassi");
						addParamToQuery("txtChassi", "%"+ av.getVeiculo().getChassi());
					}
					
					if(av.getVeiculo().getTipo() != null && av.getVeiculo().getTipo().getId() != null) {
						hql.append(" and veic.tipo = :txtTipo");
						addParamToQuery("txtTipo", av.getVeiculo().getTipo());
					}
				
					if(av.getVeiculo().getModelo() != null && av.getVeiculo().getModelo().getId() != null) {
						hql.append(" and veic.modelo = :txtModelo");
						addParamToQuery("txtModelo", av.getVeiculo().getModelo());
					}
	
					if(av.getVeiculo().getNavio() != null && !av.getVeiculo().getNavio().equals("")) {
						hql.append(" and veic.navio = :txtnavio");
						addParamToQuery("txtnavio", av.getVeiculo().getNavio().substring(0, av.getVeiculo().getNavio().length() - 13));
						try {
							Date dataNavio = date_format.parse(av.getVeiculo().getNavio().substring(av.getVeiculo().getNavio().length() - 10, av.getVeiculo().getNavio().length()));
							
							hql.append(" and veic.dataLancamento between :dtNavioIni and :dtNavioFim");
							addParamToQuery("dtNavioIni", DateUtils.montarDataInicialParaHQLQuery(dataNavio));
							addParamToQuery("dtNavioFim", DateUtils.montarDataFinalParaHQLQuery(dataNavio));
						} catch (ParseException e) {
							e.printStackTrace();
							throw new DAOException("Data do navio inválida");
						}
					}
				}
			}
		}
		
		if (de != null && !de.equals("") && ate != null && !ate.equals("")) {
			if (periodo == 3) hql.append(" and veic.dataLancamento between :dtIni and :dtFim");
			if (periodo == 4) hql.append(" and veic.dataCadastro between :dtIni and :dtFim");
		}

		if (vistoriaFinal && oriAte != null && oriAte.getId() != null) {
			hql.append(" and exists (select av2 from Avaria av2");
			hql.append(" where av2.veiculo = av.veiculo");
			hql.append(" and av2.status.id <> 3");
			if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
				if(periodo == 1) hql.append(" and av2.dataLancamento between :dtIni and :dtFim");
				if(periodo == 2) hql.append(" and av2.dataCadastro between :dtIni and :dtFim");
			}
				
			hql.append(" and av2.origem = :orgFinal)");
			addParamToQuery("orgFinal", oriAte);
		} else { 
			if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
				if(periodo == 1) hql.append(" and av.dataLancamento between :dtIni and :dtFim");
				if(periodo == 2) hql.append(" and av.dataCadastro between :dtIni and :dtFim");
			}
		}

		if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
			addParamToQuery("dtIni", de);
			addParamToQuery("dtFim", ate);
		}
		
		if (posterior) {
			hql.append(" and (tp.movimentacao = true");
			
			hql.append(" or ( (select max(av2a.origem.codigo) from Avaria av2a");
			hql.append(" where av2a.veiculo = av.veiculo");
			hql.append(" and av2a.status.id <> 3");
			hql.append(" and av2a.local = av.local");
			hql.append(" and av2a.tipo = av.tipo)");
			hql.append(" = (select max(av3a.origem.codigo) from Avaria av3a");
			hql.append(" where av3a.veiculo = av.veiculo");
			hql.append(" and av3a.status.id <> 3)");
			
			hql.append(" and (select max(av2.dataLancamento) from Avaria av2");
			hql.append(" where av2.veiculo = av.veiculo");
			hql.append(" and av2.status.id <> 3");
			hql.append(" and av2.local = av.local");
			hql.append(" and av2.tipo = av.tipo");
			hql.append(" and av2.origem.codigo = (select max(av2a.origem.codigo) from Avaria av2a");
			hql.append(" where av2a.veiculo = av.veiculo");
			hql.append(" and av2a.status.id <> 3");
			hql.append(" and av2a.local = av.local");
			hql.append(" and av2a.tipo = av.tipo))");
			hql.append(" = (select max(av3.dataLancamento) from Avaria av3");
			hql.append(" where av3.veiculo = av.veiculo");
			hql.append(" and av3.status.id <> 3");
			hql.append(" and av3.origem.codigo = (select max(av3a.origem.codigo) from Avaria av3a");
			hql.append(" where av3a.veiculo = av.veiculo");
			hql.append(" and av3a.status.id <> 3)) ))");
		}
		
		if (!cancelados) {
			hql.append(" and av.status.id <> 3");
		}
		
		if (user != null) {
			if (user.getTipo().getId() != 1 && user.getTipo().getId() != 2) {
				hql.append(" and av.origem.tipo <> '3'");
			}
		}
	
		if(responsabilidade != null && responsabilidade.getId() != null) {
			hql.append(" and ori.responsabilidade = :txtResponsabilidade");
			addParamToQuery("txtResponsabilidade", responsabilidade);
		}
		
		if(fabricante != null && fabricante.getId() != null) {
			hql.append(" and mod.fabricante = :txtFabricante");
			addParamToQuery("txtFabricante", fabricante);
		}
		
		if(movimentacao) hql.append(" and tp.movimentacao = false");

		if(registradas) {
			//hql.append(" and (");
			//if(!movimentacao) hql.append("tp.movimentacao = true or ");
			//hql.append("not exists (select av2 from Avaria av2");
			//hql.append(" where av2.veiculo = av.veiculo");
			//hql.append(" and av2.status.id <> 3");
			//hql.append(" and av2.tipo = av.tipo and av2.local = av.local");
			//hql.append(" and av2.origem.codigo <= ori.codigo");
			//hql.append(" and (av2.origem.codigo < ori.codigo");
			//hql.append(" or av2.dataLancamento < av.dataLancamento)))");
			
			hql.append(" and (tp.movimentacao = true");
			
			hql.append(" or ( ori.codigo = (select min(av2.origem.codigo) from Avaria av2");
			hql.append(" where av2.veiculo = av.veiculo");
			hql.append(" and av2.status.id <> 3");
			hql.append(" and av2.local = av.local");
			hql.append(" and av2.tipo = av.tipo)");
			
			hql.append(" and av.dataLancamento = (select min(av2.dataLancamento) from Avaria av2");
			hql.append(" where av2.veiculo = av.veiculo");
			hql.append(" and av2.status.id <> 3");
			hql.append(" and av2.local = av.local");
			hql.append(" and av2.tipo = av.tipo");
			hql.append(" and av2.origem.codigo = ori.codigo) ))");
		}

		hql.append(" order by av.origem.codigo, av.dataLancamento");
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Avaria> getAvariasDuplicadasPorFiltro(List<Veiculo> veiculos, Avaria av) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left join fetch av.veiculo veic ");
		hql.append(" where veic.chassi in (:listchassi) ");
		hql.append(" and av.tipo = :tpAv ");
		hql.append(" and av.local = :lcAv ");
		hql.append(" and av.origem = :orAv ");
		hql.append(" and av.dataLancamento = :dtLanc ");

		addParamToQuery("listchassi", StringUtilities.listVeiculoToChassiInClause(veiculos));
		addParamToQuery("tpAv", av.getTipo());
		addParamToQuery("lcAv", av.getLocal());
		addParamToQuery("orAv", av.getOrigem());
		addParamToQuery("dtLanc", av.getDataLancamento());
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Avaria> getAvariasDuplicadasPorFiltro(Veiculo veiculo, Avaria av) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left join fetch av.veiculo veic ");
		hql.append(" where veic.chassi = :txtchassi ");
		hql.append(" and av.tipo = :tpAv ");
		hql.append(" and av.local = :lcAv ");
		hql.append(" and av.origem = :orAv ");
		hql.append(" and av.dataLancamento = :dataAv ");

		addParamToQuery("txtchassi", veiculo.getChassi());
		addParamToQuery("tpAv", av.getTipo());
		addParamToQuery("lcAv", av.getLocal());
		addParamToQuery("orAv", av.getOrigem());
		addParamToQuery("dataAv", av.getDataLancamento());
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Avaria> getAvariasDuplicadasPorData(List<Veiculo> veiculos, Avaria av) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left join fetch av.veiculo veic ");
		hql.append(" where veic.chassi in (:listchassi) ");
		hql.append(" and av.origem = :orAv ");
		hql.append(" and av.dataLancamento <> :dtLanc ");

		addParamToQuery("listchassi", StringUtilities.listVeiculoToChassiInClause(veiculos));
		addParamToQuery("orAv", av.getOrigem());
		addParamToQuery("dtLanc", av.getDataLancamento());
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Avaria> getAvariasDuplicadasPorData(Veiculo veiculo, Avaria av) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left join fetch av.veiculo veic ");
		hql.append(" where veic.chassi = :txtchassi ");
		hql.append(" and av.origem = :orAv ");
		hql.append(" and av.dataLancamento <> :dataAv ");

		addParamToQuery("txtchassi", veiculo.getChassi());
		addParamToQuery("orAv", av.getOrigem());
		addParamToQuery("dataAv", av.getDataLancamento());
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public Map<String, List<PieData>> buscarResumo(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo, OrigemAvaria oriInicio, OrigemAvaria oriFim, String item, String subitem, Boolean posterior, Boolean cancelados) throws DAOException {
		//if ((oriFim == null || oriFim.getId() == null) &&
		//		oriInicio != null && oriInicio.getId() != null) oriFim = oriInicio;
		//if ((oriInicio == null || oriInicio.getId() == null) &&
		//		oriFim != null && oriFim.getId() != null) oriInicio = oriFim;
		
		String nomeItem = (item.isEmpty() ? "cast('Total' as text)" : item + (item.equals("fabricante") ? ".nome" : ".descricao"));
		String nomeSubitem = (subitem.isEmpty() ? "cast('' as text)" : subitem + (subitem.equals("fabricante") ? ".nome" : ".descricao"));
		String idItem = (item.isEmpty() ? "" : (item.equals("fabricante") ? "modelo." : (item.equals("modelo") ? "veiculo." : "avaria.")) + item.replaceAll("avaria", "") + "_id");
		String idSubitem = (subitem.isEmpty() ? "" : (subitem.equals("fabricante") ? "modelo." : (subitem.equals("modelo") ? "veiculo." : "avaria.")) + subitem.replaceAll("avaria", "") + "_id");
		StringBuilder sql = new StringBuilder();
		
		sql.append("select " + nomeItem + " as item, " + nomeSubitem + " as subitem, cast(count(*) as integer)");
		sql.append(" from avaria, origemavaria, tipoavaria, veiculo");
		if (!item.isEmpty() && !item.equals("origemavaria") && !item.equals("tipoavaria")) sql.append(", " + item);
		if (!subitem.isEmpty() && !subitem.equals("origemavaria") && !subitem.equals("tipoavaria")) sql.append(", " + subitem);
		if ((!item.isEmpty() && item.equals("fabricante") && !subitem.isEmpty() && !subitem.equals("modelo")) ||
				(!subitem.isEmpty() && subitem.equals("fabricante") && !item.isEmpty() && !item.equals("modelo")))
			sql.append(", modelo");
		
		sql.append(" where tipoavaria.movimentacao = false");
		sql.append(" and veiculo.tipo_id <> 9");
		sql.append(" and avaria.origem_id = origemavaria.id");
		sql.append(" and avaria.tipo_id = tipoavaria.id");
		sql.append(" and avaria.veiculo_id = veiculo.id");
		if (!item.isEmpty() && !item.equals("origemavaria") && !item.equals("tipoavaria")) sql.append(" and " + idItem + " = " + item + ".id");
		if (!subitem.isEmpty() && !subitem.equals("origemavaria") && !subitem.equals("tipoavaria")) sql.append(" and " + idSubitem + " = " + subitem + ".id");
		if ((!item.isEmpty() && item.equals("fabricante") && !subitem.isEmpty() && !subitem.equals("modelo")) ||
				(!subitem.isEmpty() && subitem.equals("fabricante") && !item.isEmpty() && !item.equals("modelo")))
			sql.append(" and veiculo.modelo_id = modelo.id");
		
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			if(periodo == 1) sql.append(" and avaria.dataLancamento between '" + dtInicio +"' and '" + dtFim +"'");
			if(periodo == 2) sql.append(" and avaria.dataCadastro between '" + dtInicio +"' and '" + dtFim +"'");
			if(periodo == 3) sql.append(" and veiculo.dataLancamento between '" + dtInicio +"' and '" + dtFim +"'");
			if(periodo == 4) sql.append(" and veiculo.dataCadastro between '" + dtInicio +"' and '" + dtFim +"'");
		}

		if(veiculo.getNavio() != null && !veiculo.getNavio().equals("")) {
			sql.append(" and veiculo.navio = '" + veiculo.getNavio().substring(0, veiculo.getNavio().length() - 13) + "'");
			try {
				Date dataNavio = date_format.parse(veiculo.getNavio().substring(veiculo.getNavio().length() - 10, veiculo.getNavio().length()));
				
				sql.append(" and veiculo.dataLancamento between '" + DateUtils.montarDataInicialParaHQLQuery(dataNavio) + "'");
				sql.append(" and '" + DateUtils.montarDataFinalParaHQLQuery(dataNavio) + "'");
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DAOException("Data do navio inválida");
			}
		}

		if(veiculo.getTipo() != null && veiculo.getTipo().getId() != null) 
			sql.append(" and veiculo.tipo_id = " + veiculo.getTipo().getId());

		if (oriInicio != null && oriInicio.getId() != null)
			sql.append(" and origemavaria.codigo >= " + oriInicio.getCodigo().toString());
		if (oriFim != null && oriFim.getId() != null)
			sql.append(" and origemavaria.codigo <= " + oriFim.getCodigo().toString());
		
		sql.append(" and not exists (select * from avaria av2, origemavaria ori2");
		sql.append(" where av2.origem_id = ori2.id");
		sql.append(" and av2.veiculo_id = avaria.veiculo_id");
		sql.append(" and av2.status_id <> 3");
		sql.append(" and av2.tipo_id = avaria.tipo_id and av2.local_id = avaria.local_id");
		sql.append(" and (ori2.codigo < origemavaria.codigo");
		sql.append("      or (ori2.codigo = origemavaria.codigo");
		sql.append("          and av2.dataLancamento < avaria.dataLancamento)))");
		
		if (posterior) {
			sql.append(" and (select max(to_char(ori2.codigo, '000000')||to_char(av2.dataLancamento,'yyyymmdd'))");
			sql.append(" from avaria av2, origemavaria ori2");
			sql.append(" where av2.origem_id = ori2.id");
			sql.append(" and av2.veiculo_id = avaria.veiculo_id");
			sql.append(" and av2.status_id <> 3");
			sql.append(" and av2.local_id = avaria.local_id");
			sql.append(" and av2.tipo_id = avaria.tipo_id)");
			sql.append(" = (select max(to_char(ori3.codigo, '000000')||to_char(av3.dataLancamento,'yyyymmdd'))");
			sql.append(" from avaria av3, origemavaria ori3");
			sql.append(" where av3.origem_id = ori3.id");
			sql.append(" and av3.veiculo_id = avaria.veiculo_id");
			sql.append(" and av3.status_id <> 3)");
		}
		
		if (!cancelados) {
			sql.append(" and avaria.status_id <> 3");
		}
		
		if (!item.isEmpty() || !subitem.isEmpty()) {
			sql.append(" group by");
			if (!item.isEmpty()) sql.append(" " + nomeItem);
			if (!item.isEmpty() && !subitem.isEmpty()) sql.append(","); 
			if (!subitem.isEmpty()) sql.append(" " + nomeSubitem);
			sql.append(" order by");
			if (subitem.isEmpty()) sql.append(" count(*) desc,");
			if (!item.isEmpty()) sql.append(" " + nomeItem);
			if (!item.isEmpty() && !subitem.isEmpty()) sql.append(",");
			if (!subitem.isEmpty()) sql.append(" count(*) desc,");
			if (!subitem.isEmpty()) sql.append(" " + nomeSubitem);
		}

		List lista = executeSQLQuery(sql.toString());
		Map<String, List<PieData>> itens = new HashMap<String, List<PieData>>();
		
		for(Object obj : lista) {
			montaListaPorItem((Object[])obj, itens);
		}
		return itens;
	}
	
	private void montaListaPorItem(Object[] obj, Map<String, List<PieData>> itens) {
		List<PieData> lista = itens.get(obj[0]);
		if(lista == null) {
			lista = new ArrayList<PieData>();
		}
		lista.add(new PieData(obj[1].toString(), obj[2].toString()));
		itens.put(obj[0].toString(), lista);
	}
}
