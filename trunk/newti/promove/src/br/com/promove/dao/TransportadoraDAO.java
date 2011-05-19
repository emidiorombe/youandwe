package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Transportadora;
import br.com.promove.exception.DAOException;

public class TransportadoraDAO extends BaseDAO<Integer, Transportadora>{

	public List<Transportadora> getByCnpj(String cnpj) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select t from Transp t where ");
		hql.append("c.cnpj = :txtCnpj");

		addParamToQuery("txtCodigo", cnpj);

		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}
}
