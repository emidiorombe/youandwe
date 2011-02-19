package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.exception.DAOException;

public class AvariaDAO extends BaseDAO<Integer, Avaria>{
	public List<Avaria> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos");
		return executeQuery(hql.toString(), 1, 100);
	}
}
