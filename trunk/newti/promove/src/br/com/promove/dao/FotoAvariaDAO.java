package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.FotoAvaria;
import br.com.promove.exception.DAOException;

public class FotoAvariaDAO extends BaseDAO<Integer, FotoAvaria>{

	public List<FotoAvaria> getByInconsistencia(Integer idInc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ft from FotoAvaria ft where ft.inconsisctencia = :txtinc");
		addParamToQuery("txtinc", idInc);
		return executeQuery(hql.toString(), paramsToQuery, 0, 100);
	}

}
