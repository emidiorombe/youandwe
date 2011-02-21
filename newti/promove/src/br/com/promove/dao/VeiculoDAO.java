package br.com.promove.dao;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;

public class VeiculoDAO extends BaseDAO<Integer, Veiculo>{
	public List<Veiculo> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v left JOIN FETCH v.avarias ");
		return executeQuery(hql.toString(), 1, 100);
	}

	public List<Veiculo> getByFilter(String chassi, Date dtInicio, Date dtFim) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v where 1 = 1 ");
		
		if(chassi != null && !chassi.equals("")) {
			hql.append(" and v.chassi like :txtchassi ");
			addParamToQuery("txtchassi", chassi);
		}
		
		if(dtInicio != null && !dtInicio.equals("") && dtFim != null && !dtFim.equals("")) {
			hql.append(" and  dataCadastro between :dtIni and :dtFim ");
			addParamToQuery("dtIni", dtInicio);
			addParamToQuery("dtFim", dtFim);
		}
		
		return executeQuery(hql.toString(), paramsToQuery, 0, 100);
	}
}
