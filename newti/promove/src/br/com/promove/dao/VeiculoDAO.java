package br.com.promove.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.PieData;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.utils.DateUtils;

public class VeiculoDAO extends BaseDAO<Integer, Veiculo>{
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	
	public List<Veiculo> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v ");
		return executeQuery(hql.toString(), 0, Integer.MAX_VALUE);
	}

	public List<Veiculo> getByFilter(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo) throws DAOException {
		return getByFilter(veiculo, dtInicio, dtFim, periodo, "v.chassi"); 
	}

	public List<Veiculo> getByFilter(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo, String sort) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v left JOIN FETCH v.modelo mod");
		hql.append(" where v.tipo.id <> 9");
		
		if(veiculo.getChassi() != null && !veiculo.getChassi().equals("")) {
			hql.append(" and v.chassi like :txtchassi");
			addParamToQuery("txtchassi", "%" + veiculo.getChassi());
		}
		
		if(veiculo.getModelo() != null && veiculo.getModelo().getId() != null) {
			hql.append(" and v.modelo = :txtmodelo");
			addParamToQuery("txtmodelo", veiculo.getModelo());
		} else if(veiculo.getModelo() != null && veiculo.getModelo().getFabricante() != null) {
			hql.append(" and mod.fabricante = :txtfabricante");
			addParamToQuery("txtfabricante", veiculo.getModelo().getFabricante());
		}
		
		if(veiculo.getCor() != null && veiculo.getCor().getId() != null) {
			hql.append(" and v.cor = :txtcor");
			addParamToQuery("txtcor", veiculo.getCor());
		}
		
		if(veiculo.getTipo() != null && veiculo.getTipo().getId() != null) {
			hql.append(" and v.tipo = :txttipo");
			addParamToQuery("txttipo", veiculo.getTipo());
		}
		
		if(veiculo.getNavio() != null && !veiculo.getNavio().equals("")) {
			hql.append(" and v.navio = :txtnavio");
			addParamToQuery("txtnavio", veiculo.getNavio().substring(0, veiculo.getNavio().length() - 13));
			try {
				Date dataNavio = date_format.parse(veiculo.getNavio().substring(veiculo.getNavio().length() - 10, veiculo.getNavio().length()));
				
				hql.append(" and v.dataLancamento between :dtNavioIni and :dtNavioFim");
				addParamToQuery("dtNavioIni", DateUtils.montarDataInicialParaHQLQuery(dataNavio));
				addParamToQuery("dtNavioFim", DateUtils.montarDataFinalParaHQLQuery(dataNavio));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DAOException("Data do navio inválida");
			}
		}
		
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			if (periodo == 3) hql.append(" and v.dataLancamento between :dtIni and :dtFim");
			if (periodo == 4) hql.append(" and v.dataCadastro between :dtIni and :dtFim");

			addParamToQuery("dtIni", dtInicio);
			addParamToQuery("dtFim", dtFim);
		}
		
		hql.append(" order by " + sort);
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Veiculo> getByChassi(String chassi) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v where ");
		hql.append(" v.chassi = :txtchassi ");
		addParamToQuery("txtchassi",  chassi);
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Veiculo> getByModeloFZAndData(String chassi, Date data) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v where ");
		hql.append(" v.chassi like :txtchassi ");
		hql.append(" and v.modelo.codigoExternoImportacao = :txtmod ");
		hql.append(" and v.dataLancamento between :dataini and :datafim ");
		addParamToQuery("txtchassi",  "%"+chassi.substring(2));
		addParamToQuery("txtmod",  chassi.substring(0, 2));
		
		Calendar calIni = Calendar.getInstance();
		Calendar calFim = Calendar.getInstance();
		
		calIni.setTime(data);
		calFim.setTime(data);
		
		calIni.set(Calendar.DAY_OF_MONTH, -90);
		calFim.set(Calendar.DAY_OF_MONTH, +90);
		
		addParamToQuery("dataini",  calIni.getTime());
		addParamToQuery("datafim",  calFim.getTime());
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Veiculo> getByFz(String fz) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v where ");
		hql.append(" v.chassi like :txtchassi ");
		addParamToQuery("txtchassi",  "%" + fz);
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public static List<String> buscarTodosNavios() throws DAOException {
		StringBuilder origens = new StringBuilder();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select navio||' - '||to_char(data, 'dd/mm/yyyy') from (");
		sql.append("select distinct navio, cast(dataLancamento as date) as data");
		sql.append(" from veiculo where tipo_id = 2 and navio is not null");
		sql.append(" order by data desc) as lista");

		List lista = executeSQLQuery(sql.toString());
		
		return lista;
	}

	public List<Veiculo> buscarVeiculosAuditoria(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo, OrigemAvaria oriInicio, OrigemAvaria oriFim) throws DAOException {
		//if ((oriFim == null || oriFim.getId() == null) &&
		//		oriInicio != null && oriInicio.getId() != null) oriFim = oriInicio;
		//if ((oriInicio == null || oriInicio.getId() == null) &&
		//		oriFim != null && oriFim.getId() != null) oriInicio = oriFim;
		
		StringBuilder origens = new StringBuilder();
		
		StringBuilder hql = new StringBuilder();
		hql.append("select ori from OrigemAvaria ori where 1=1");
		if (oriInicio != null && oriInicio.getId() != null) {
			hql.append(" and ori.codigo >= :oriDe");
			addParamToQuery("oriDe", oriInicio.getCodigo());
		}
		if (oriFim != null && oriFim.getId() != null) {
			hql.append(" and ori.codigo <= :oriAte");
			addParamToQuery("oriAte", oriFim.getCodigo());
		}
		hql.append(" order by ori.codigo");
		
		List<OrigemAvaria> listaOr = executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);

		for (OrigemAvaria or : listaOr) {
			origens.append(or.getDescricao() + ";");
		}

		hql = new StringBuilder();
		hql.append("select v from Veiculo v");
		hql.append(" where v.tipo.id <> 9");
		hql.append(" and not exists (select av2 from Avaria av2");
		hql.append(" where av2.veiculo = v)");
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			hql.append(" and v.dataCadastro between :dtIni and :dtFim");
			addParamToQuery("dtIni", dtInicio);
			addParamToQuery("dtFim", dtFim);
		}
		
		if(veiculo.getTipo() != null && veiculo.getTipo().getId() != null) {
			hql.append(" and v.tipo = :txttipo");
			addParamToQuery("txttipo", veiculo.getTipo());
		}
		
		if(veiculo.getNavio() != null && !veiculo.getNavio().equals("")) {
			hql.append(" and v.navio = :txtnavio");
			addParamToQuery("txtnavio", veiculo.getNavio().substring(0, veiculo.getNavio().length() - 13));
			try {
				Date dataNavio = date_format.parse(veiculo.getNavio().substring(veiculo.getNavio().length() - 10, veiculo.getNavio().length()));
				
				hql.append(" and v.dataLancamento between :dtNavioIni and :dtNavioFim");
				addParamToQuery("dtNavioIni", DateUtils.montarDataInicialParaHQLQuery(dataNavio));
				addParamToQuery("dtNavioFim", DateUtils.montarDataFinalParaHQLQuery(dataNavio));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DAOException("Data do navio inválida");
			}
		}
		
		hql.append(" order by v.chassi");

		List<Veiculo> listaVe = executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
		List<Veiculo> lista = new ArrayList<Veiculo>();
		
		for (Veiculo ve : listaVe) {
			ve.setOrigensfaltantes(origens.toString());
			lista.add(ve);
		}
		
		hql = new StringBuilder();
		hql.append("select av from Avaria av inner join fetch av.veiculo v");
		hql.append(" where v.tipo.id <> 9");
		
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			if (periodo == 3) hql.append(" and v.dataLancamento between :dtIni and :dtFim");
			if (periodo == 4) hql.append(" and v.dataCadastro between :dtIni and :dtFim");
			addParamToQuery("dtIni", dtInicio);
			addParamToQuery("dtFim", dtFim);
		}
		
		if(veiculo.getTipo() != null && veiculo.getTipo().getId() != null) {
			hql.append(" and v.tipo = :txttipo");
			addParamToQuery("txttipo", veiculo.getTipo());
		}
		
		if(veiculo.getNavio() != null && !veiculo.getNavio().equals("")) {
			hql.append(" and v.navio = :txtnavio");
			addParamToQuery("txtnavio", veiculo.getNavio().substring(0, veiculo.getNavio().length() - 13));
			try {
				Date dataNavio = date_format.parse(veiculo.getNavio().substring(veiculo.getNavio().length() - 10, veiculo.getNavio().length()));
				
				hql.append(" and v.dataLancamento between :dtNavioIni and :dtNavioFim");
				addParamToQuery("dtNavioIni", DateUtils.montarDataInicialParaHQLQuery(dataNavio));
				addParamToQuery("dtNavioFim", DateUtils.montarDataFinalParaHQLQuery(dataNavio));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DAOException("Data do navio inválida");
			}
		}
		
		hql.append(" order by v.chassi");

		List<Avaria> listaAv = executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
		
		Veiculo ve = null;
		
		for (Avaria av : listaAv) {
			if (ve == null || !ve.equals(av.getVeiculo())) {
				if (ve != null && !ve.getOrigensfaltantes().isEmpty()) lista.add(ve);  
				ve = av.getVeiculo();
				ve.setOrigensfaltantes(origens.toString());
			}
			ve.setOrigensfaltantes(ve.getOrigensfaltantes().replaceAll(av.getOrigem().getDescricao()+";", ""));
		}
		if (ve != null && !ve.getOrigensfaltantes().isEmpty()) lista.add(ve);
		
		return lista;
	}

	public Map<String, List<PieData>> buscarAnaliseResultado(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo, OrigemAvaria oriInicio, OrigemAvaria oriFim, String item, Boolean vistoriaFinal, Boolean posterior, Boolean cancelados) throws DAOException {
		//if ((oriFim == null || oriFim.getId() == null) &&
		//		oriInicio != null && oriInicio.getId() != null) oriFim = oriInicio;
		//if ((oriInicio == null || oriInicio.getId() == null) &&
		//		oriFim != null && oriFim.getId() != null) oriInicio = oriFim;
		
		String nomeItem = (item.isEmpty() ? "cast('Total' as text)" : item + (item.equals("fabricante") ? ".nome" : ".descricao"));
		String idItem = (item.isEmpty() ? "" : (item.equals("fabricante") ? "modelo." : (item.equals("modelo") ? "veiculo." : "avaria.")) + item.replaceAll("avaria", "") + "_id");
		
		StringBuilder subsql = new StringBuilder();
		StringBuilder subsqlA = new StringBuilder();
		StringBuilder subsqlB = new StringBuilder();
		
		subsqlA.append(" select " + nomeItem + " as item, cast(':tipo' as text) as tipo");
		subsqlA.append(" from veiculo, modelo, avaria, origemavaria");
		if (item.equals("origemavaria")) subsqlA.append(", tipoavaria");
		if (item.equals("fabricante")) subsqlA.append(", fabricante");
		subsqlA.append(" where veiculo.tipo_id <> 9");
		subsqlA.append(" and veiculo.modelo_id = modelo.id");
		if (item.equals("fabricante")) subsqlA.append(" and fabricante.id = modelo.fabricante_id");
		subsqlA.append(" and veiculo.id = avaria.veiculo_id");
		subsqlA.append(" and avaria.origem_id = origemavaria.id");
		if (item.equals("origemavaria")) {
			subsqlA.append(" and avaria.tipo_id = tipoavaria.id");
			subsqlA.append(" and tipoavaria.movimentacao = :opcao");
		}
		if ((periodo == 1 || periodo == 2) && dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			subsqlA.append(" and " + (periodo == 1 ? "avaria.dataLancamento" : "avaria.dataCadastro"));
			subsqlA.append(" between '" + dtInicio + "'");
			subsqlA.append(" and '" + dtFim + "'");
			
			subsqlB.append(subsqlA.toString());
			
			if (oriInicio != null && oriInicio.getId() != null)
				subsqlB.append(" and origemavaria.codigo >= " + oriInicio.getCodigo().toString());
			if (oriFim != null && oriFim.getId() != null)
				subsqlB.append(" and origemavaria.codigo <= " + oriFim.getCodigo().toString());
		} else {
			subsqlB.append(" select " + nomeItem + " as item, cast(':tipo' as text) as tipo");
			subsqlB.append(" from veiculo, modelo");
			subsqlB.append(" where veiculo.tipo_id <> 9");
			subsqlB.append(" and veiculo.modelo_id = modelo.id");
		}
		
		if (item.equals("origemavaria")) {
			if (oriInicio != null && oriInicio.getId() != null)
				subsqlA.append(" and origemavaria.codigo >= " + oriInicio.getCodigo().toString());
			if (oriFim != null && oriFim.getId() != null)
				subsqlA.append(" and origemavaria.codigo <= " + oriFim.getCodigo().toString());
		} else {
			if (oriFim != null && oriFim.getId() != null)
				subsqlA.append(" and origemavaria.codigo = " + oriFim.getCodigo().toString());
		}

		if ((periodo == 3 || periodo == 4) && dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			subsql.append(" and veiculo." + (periodo == 3 ? "dataLancamento" : "dataCadastro"));
			subsql.append(" between '" + dtInicio + "'");
			subsql.append(" and '" + dtFim + "'");
		}
		
		if(veiculo.getNavio() != null && !veiculo.getNavio().equals("")) {
			subsql.append(" and veiculo.navio = '" + veiculo.getNavio().substring(0, veiculo.getNavio().length() - 13) + "'");
			try {
				Date dataNavio = date_format.parse(veiculo.getNavio().substring(veiculo.getNavio().length() - 10, veiculo.getNavio().length()));
				
				subsql.append(" and veiculo.dataLancamento between '" + DateUtils.montarDataInicialParaHQLQuery(dataNavio) + "'");
				subsql.append(" and '" + DateUtils.montarDataFinalParaHQLQuery(dataNavio) + "'");
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DAOException("Data do navio inválida");
			}
		}
		
		if(veiculo.getTipo() != null && veiculo.getTipo().getId() != null) 
			subsql.append(" and veiculo.tipo_id = " + veiculo.getTipo().getId());
		
		subsqlA.append(subsql.toString());
		subsqlB.append(subsql.toString());

		if (!item.equals("origemavaria")) {
			subsqlA.append(" and :existe (select avaria.id from avaria, tipoavaria, origemavaria");
			subsqlA.append(" where veiculo.id = avaria.veiculo_id");
			subsqlA.append(" and avaria.tipo_id = tipoavaria.id");
			subsqlA.append(" and tipoavaria.movimentacao = false");
			if ((periodo == 1 || periodo == 2) && dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
				subsqlA.append(" and avaria." + (periodo == 1 ? "dataLancamento" : "dataCadastro"));
				subsqlA.append(" between '" + dtInicio + "'");
				subsqlA.append(" and '" + dtFim + "'");
			}
			if (oriInicio != null && oriInicio.getId() != null)
				subsqlA.append(" and origemavaria.codigo >= " + oriInicio.getCodigo().toString());
			if (oriFim != null && oriFim.getId() != null)
				subsqlA.append(" and origemavaria.codigo <= " + oriFim.getCodigo().toString());
			subsqlA.append(" and avaria.origem_id = origemavaria.id)");
			
			if (posterior) {
				subsqlA.append(" and (select max(av2.dataLancamento) from avaria av2, origemavaria ori2");
				subsqlA.append(" where av2.origem_id = ori2.id");
				subsqlA.append(" and av2.veiculo_id = avaria.veiculo_id");
				subsqlA.append(" and av2.local_id = avaria.local_id");
				subsqlA.append(" and av2.tipo_id = avaria.tipo_id");
				subsqlA.append(" and ori2.codigo = (select max(ori2a.codigo) from avaria av2a, origemavaria ori2a");
				subsqlA.append(" where av2a.origem_id = ori2a.id");
				subsqlA.append(" and av2a.veiculo_id = avaria.veiculo_id");
				subsqlA.append(" and av2a.local_id = avaria.local_id");
				subsqlA.append(" and av2a.tipo_id = avaria.tipo_id))");
				subsqlA.append(" = (select max(av3.dataLancamento) from avaria av3, origemavaria ori3");
				subsqlA.append(" where av3.origem_id = ori3.id");
				subsqlA.append(" and av3.veiculo_id = avaria.veiculo_id)");
				subsqlA.append(" and ori3.codigo = (select max(ori3a.codigo) from avaria av3a, origemavaria ori3a");
				subsqlA.append(" where av3a.origem_id = ori3a.id");
				subsqlA.append(" and av3a.veiculo_id = avaria.veiculo_id))");
			}
			
			if (!cancelados) {
				subsqlA.append(" and avaria.status_id <> 3");
			}
			
		}
		subsqlA.append(" group by veiculo.id, " + nomeItem);
		
		subsqlB.append(" and :existe (select avaria.id from avaria, origemavaria");
		subsqlB.append(" where veiculo.id = avaria.veiculo_id");
		if(periodo != 3 && dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			subsqlB.append(" and " + (periodo == 1 ? "avaria.dataLancamento" : "avaria.dataCadastro"));
			subsqlB.append(" between '" + dtInicio + "'");
			subsqlB.append(" and '" + dtFim + "'");
		}
		if (oriFim != null && oriFim.getId() != null)
			subsqlB.append(" and origemavaria.codigo = " + oriFim.getCodigo().toString());
		subsqlB.append(" and avaria.origem_id = origemavaria.id)");
		subsqlB.append(" group by veiculo.id, " + nomeItem);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select item, tipo, cast(count(*) as integer) from (");
		if (item.equals("origemavaria")) {
			StringBuilder subsqlC = new StringBuilder();
			subsqlC.append(" and not exists (select av2.id from avaria av2, origemavaria ori2");
			subsqlC.append(" where av2.origem_id = ori2.id");
			subsqlC.append(" and av2.veiculo_id = avaria.veiculo_id");
			subsqlC.append(" and av2.tipo_id = avaria.tipo_id and av2.local_id = avaria.local_id");
			subsqlC.append(" and (ori2.codigo < origemavaria.codigo");
			subsqlC.append("      or (ori2.codigo = origemavaria.codigo");
			subsqlC.append("          and av2.dataLancamento < avaria.dataLancamento)))");
			
			sql.append(subsqlA.toString().replaceAll(":tipo", "Avariados").replaceAll(":opcao", "true" + subsqlC.toString()));
			sql.append(" UNION ALL");
			sql.append(subsqlA.toString().replaceAll(":tipo", "Não avariados").replaceAll(":opcao", "false"));
		} else {
			sql.append(subsqlA.toString().replaceAll(":tipo", "Avariados").replaceAll(":existe", "exists"));
			sql.append(" UNION ALL");
			sql.append(subsqlA.toString().replaceAll(":tipo", "Não avariados").replaceAll(":existe", "not exists"));
			if(vistoriaFinal) {
				sql.append(" UNION ALL");
				sql.append(subsqlB.toString().replaceAll(":tipo", "Em processo").replaceAll(":existe", "not exists"));
			}
		}
		sql.append(") as subconsulta");
		sql.append(" group by tipo, item");
		sql.append(" order by tipo");

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
