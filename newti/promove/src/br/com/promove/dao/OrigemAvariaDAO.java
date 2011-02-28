package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.OrigemAvaria;
import br.com.promove.exception.DAOException;

public class OrigemAvariaDAO extends BaseDAO<Integer, OrigemAvaria> {

	public List<OrigemAvaria> getOrigemPorTipoEFilial(String tipo_id,String filial_id) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ori from OrigemAvaria ori where ");
		hql.append(" ori.tipo = :tpo ");
		hql.append(" and ori.filial.id = :flori ");
		addParamToQuery("tpo",  tipo_id);
		addParamToQuery("flori",  new Integer(filial_id));
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

}
