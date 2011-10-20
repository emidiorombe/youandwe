package br.com.promove.dao;

import java.util.List;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.exception.DAOException;

public class InconsistenciaAvariaDAO extends BaseDAO<Integer, InconsistenciaAvaria>{
	
	public List<InconsistenciaAvaria> getInconsistenciaAvariaPorChassi(String chassi) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from InconsistenciaAvaria av");
		hql.append(" where av.chassiInvalido = :txtChassi");
		addParamToQuery("txtChassi", chassi);
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

}
