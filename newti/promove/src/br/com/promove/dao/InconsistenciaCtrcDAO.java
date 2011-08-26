package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.exception.DAOException;

public class InconsistenciaCtrcDAO extends BaseDAO<Integer, InconsistenciaCtrc>{
	public List<InconsistenciaCtrc> getInconsistenciasCtrcDuplicadosPorFiltros(Ctrc ctrc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ct from InconsistenciaCtrc ct");
		hql.append(" where ct.filial = :txtFilial");
		hql.append(" and ct.numero = :txtNumero");
		hql.append(" and ct.tipo = :txtTipo");
		hql.append(" and ct.serie = :txtSerie");
		hql.append(" and ct.transp = :txtTransp");
		addParamToQuery("txtFilial",  ctrc.getFilial());
		addParamToQuery("txtNumero",  ctrc.getNumero());
		addParamToQuery("txtTipo",  ctrc.getTipo());
		addParamToQuery("txtSerie",  ctrc.getSerie());
		addParamToQuery("txtTransp",  ctrc.getTransp());
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

}
