package br.com.promove.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;

public class VeiculoDAO extends BaseDAO<Integer, Veiculo>{
	public List<Veiculo> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v left JOIN FETCH v.avarias ");
		return executeQuery(hql.toString(), 0, Integer.MAX_VALUE);
	}

	public List<Veiculo> getByFilter(Veiculo veiculo, Date dtInicio, Date dtFim) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v where 1 = 1 ");
		
		if(veiculo.getChassi() != null && !veiculo.getChassi().equals("")) {
			hql.append(" and v.chassi like :txtchassi ");
			addParamToQuery("txtchassi", "%" + veiculo.getChassi());
		}
		
		if(veiculo.getModelo() != null && veiculo.getModelo().getId() != null) {
			hql.append(" and v.modelo = :txtmodelo ");
			addParamToQuery("txtmodelo", veiculo.getModelo());
		}
		
		if(veiculo.getCor() != null && veiculo.getCor().getId() != null) {
			hql.append(" and v.cor = :txtcor ");
			addParamToQuery("txtcor", veiculo.getCor());
		}
		
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			hql.append(" and  dataCadastro between :dtIni and :dtFim ");
			addParamToQuery("dtIni", dtInicio);
			addParamToQuery("dtFim", dtFim);
		}
		
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
}
