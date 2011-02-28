package br.com.promove.dao;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;

public class VeiculoDAO extends BaseDAO<Integer, Veiculo>{
	public List<Veiculo> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v left JOIN FETCH v.avarias ");
		return executeQuery(hql.toString(), 1, Integer.MAX_VALUE);
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
}