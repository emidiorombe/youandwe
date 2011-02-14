package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;

public class VeiculoDAO extends BaseDAO<Integer, Veiculo>{

	public List<Veiculo> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from Veiculo v");
		return executeQuery(hql.toString(), 1, 200);
	}
	
}
