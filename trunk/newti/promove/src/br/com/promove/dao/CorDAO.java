package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.exception.DAOException;

public class CorDAO extends BaseDAO<Integer, Cor> {

	public List<Cor> getByCodigoExterno(String codigo) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select c from Cor c where  ");
		hql.append("c.codigoExterno = :txtCodigo ");

		addParamToQuery("txtCodigo", codigo);

		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

}
