package br.com.promove.dao;

import br.com.promove.entity.Usuario;
import br.com.promove.exception.DAOException;

public class UsuarioDAO extends BaseDAO<Integer, Usuario>{

	public Usuario getByUserAndPass(String user, String password) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select u from Usuario u where");
		hql.append(" u.codigo = :txtnome");
		hql.append(" and u.senha = :txtsenha");
		hql.append(" and u.tipo <> 6");
		addParamToQuery("txtnome",  Integer.parseInt(user));
		addParamToQuery("txtsenha",  password);
		return (Usuario) executeQueryOneResult(hql.toString(), paramsToQuery);
	}

}
