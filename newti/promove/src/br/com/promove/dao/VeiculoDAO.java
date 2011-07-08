package br.com.promove.dao;

import java.sql.Array;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Cor;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.PieData;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;

public class VeiculoDAO extends BaseDAO<Integer, Veiculo>{
	public List<Veiculo> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v ");
		return executeQuery(hql.toString(), 0, Integer.MAX_VALUE);
	}

	public List<Veiculo> getByFilter(Veiculo veiculo, Date dtInicio, Date dtFim) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v left JOIN FETCH v.modelo mod where 1 = 1 ");
		
		if(veiculo.getChassi() != null && !veiculo.getChassi().equals("")) {
			hql.append(" and v.chassi like :txtchassi ");
			addParamToQuery("txtchassi", "%" + veiculo.getChassi());
		}
		
		if(veiculo.getModelo() != null && veiculo.getModelo().getId() != null) {
			hql.append(" and v.modelo = :txtmodelo ");
			addParamToQuery("txtmodelo", veiculo.getModelo());
		} else if(veiculo.getModelo() != null && veiculo.getModelo().getFabricante() != null) {
			hql.append(" and mod.fabricante = :txtfabricante ");
			addParamToQuery("txtfabricante", veiculo.getModelo().getFabricante());
		}
		
		if(veiculo.getCor() != null && veiculo.getCor().getId() != null) {
			hql.append(" and v.cor = :txtcor ");
			addParamToQuery("txtcor", veiculo.getCor());
		}
		
		if(veiculo.getTipo() != null && veiculo.getTipo() != 0) {
			hql.append(" and v.tipo = :txttipo ");
			addParamToQuery("txttipo", veiculo.getTipo());
		}
		
		if(veiculo.getNavio() != null && !veiculo.getNavio().equals("")) {
			hql.append(" and v.navio = :txtnavio ");
			addParamToQuery("txtnavio", veiculo.getNavio());
		}
		
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			hql.append(" and dataCadastro between :dtIni and :dtFim ");
			addParamToQuery("dtIni", dtInicio);
			addParamToQuery("dtFim", dtFim);
		}
		
		hql.append(" order by v.chassi ");
		
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
		hql.append(" and v.dataCadastro between :dataini and :datafim ");
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

	public List<Veiculo> buscarVeiculosAuditoria(Veiculo veiculo, Date dtInicio, Date dtFim, OrigemAvaria oriInicio, OrigemAvaria oriFim) throws DAOException {
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
		hql.append(" where not exists (select av2 from Avaria av2");
		hql.append(" where av2.veiculo = v)");
		hql.append(" and v.dataCadastro between :dtIni and :dtFim");
		addParamToQuery("dtIni", dtInicio);
		addParamToQuery("dtFim", dtFim);
		
		if(veiculo.getTipo() != null && veiculo.getTipo() != 0) {
			hql.append(" and v.tipo = :txttipo");
			addParamToQuery("txttipo", veiculo.getTipo());
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
		hql.append(" where v.dataCadastro between :dtIni and :dtFim");
		addParamToQuery("dtIni", dtInicio);
		addParamToQuery("dtFim", dtFim);
		
		if(veiculo.getTipo() != null && veiculo.getTipo() != 0) { 
			hql.append(" and v.tipo = :txttipo");
			addParamToQuery("txttipo", veiculo.getTipo());
		}
		
		/*
		for (Integer i = oriInicio.getCodigo(); i <= oriFim.getCodigo(); i++) {
			if (i == oriInicio.getCodigo()) hql.append(" and ( ");
			else hql.append(" or ");
			hql.append(" not exists (select av2 from Avaria av2 ");
			hql.append(" where av2.veiculo = v ");
			hql.append(" and av2.origem.codigo = :origem" + i + ") ");
			addParamToQuery("origem" + i, i);
			if (i == oriFim.getCodigo()) hql.append(" ) ");
		}
		*/
		
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

	public Map<String, List<PieData>> buscarAnaliseResultado(Veiculo veiculo, Date dtInicio, Date dtFim, OrigemAvaria oriInicio, OrigemAvaria oriFim) throws DAOException {
		//if ((oriFim == null || oriFim.getId() == null) &&
		//		oriInicio != null && oriInicio.getId() != null) oriFim = oriInicio;
		//if ((oriInicio == null || oriInicio.getId() == null) &&
		//		oriFim != null && oriFim.getId() != null) oriInicio = oriFim;
		
		StringBuilder subsql = new StringBuilder();
		subsql.append(" select cast(':tipo' as text) as tipo, modelo.descricao as descricao");
		subsql.append(" from veiculo, modelo");
		
		subsql.append(" where veiculo.datacadastro");
		subsql.append(" between '" + dtInicio + "'");
		subsql.append(" and '" + dtFim + "'");
		if(veiculo.getTipo() != null && veiculo.getTipo() != 0) 
			subsql.append(" and veiculo.tipo = " + veiculo.getTipo().toString());
		
		subsql.append(" and veiculo.modelo_id = modelo.id");
		
		subsql.append(" and :existe (select avaria.id from avaria, tipoavaria, origemavaria");
		subsql.append(" where tipoavaria.movimentacao = false");
		if (oriInicio != null && oriInicio.getId() != null)
			subsql.append(" and origemavaria.codigo >= " + oriInicio.getCodigo().toString());
		if (oriFim != null && oriFim.getId() != null)
			subsql.append(" and origemavaria.codigo <= " + oriFim.getCodigo().toString());
		
		subsql.append(" and veiculo.id = avaria.veiculo_id");
		subsql.append(" and avaria.origem_id = origemavaria.id");
		subsql.append(" and avaria.tipo_id = tipoavaria.id)");

		
		StringBuilder sql = new StringBuilder();
		sql.append("select tipo, descricao, cast(count(*) as integer) from ("); 
		sql.append(subsql.toString().replaceAll(":tipo", "Avariados").replaceAll(":existe", "exists"));
		sql.append(" UNION ALL");
		sql.append(subsql.toString().replaceAll(":tipo", "Nao avariados").replaceAll(":existe", "not exists"));
		sql.append(" ) as subconsulta");
		sql.append(" group by tipo, descricao");
		sql.append(" order by tipo, count(*) desc, descricao");

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
