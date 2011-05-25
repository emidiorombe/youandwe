package br.com.promove.dao;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Ctrc;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.utils.StringUtilities;

public class CtrcDAO extends BaseDAO<Integer, Ctrc>{
	public List<Ctrc> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ct from Ctrc ct");
		return executeQuery(hql.toString(), 0, 100);
	}

	public List<Ctrc> getCtrcPorFiltro(Ctrc ctrc, Date de, Date ate) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ct from Ctrc ct");
		hql.append(" where 1=1");
		if (ctrc.getNumero() != null && ctrc.getNumero() != 0) {
			hql.append(" and ct.numero = :txtNumero");
			addParamToQuery("txtNumero", ctrc.getNumero());
		}
		
		if(ctrc.getTransp() != null && ctrc.getTransp().getId() != null) {
			hql.append(" and ct.transp = :txtTransp ");
			addParamToQuery("txtTransp", ctrc.getTransp());
		}
		
		if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
			hql.append(" and  ct.dataEmissao between :dtIni and :dtFim ");
			addParamToQuery("dtIni", de);
			addParamToQuery("dtFim", ate);
		}
		
		hql.append(" order by ct.dataEmissao, ct.numero");
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Ctrc> getCtrcsDuplicadosPorFiltros(Ctrc ctrc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select c from Ctrc c");
		hql.append(" where c.filial = :txtFilial");
		hql.append(" and c.numero = :txtNumero");
		hql.append(" and c.tipo = :txtTipo");
		hql.append(" and c.serie = :txtSerie");
		hql.append(" and c.transp = :txtTransp");
		addParamToQuery("txtFilial",  ctrc.getFilial());
		addParamToQuery("txtNumero",  ctrc.getNumero());
		addParamToQuery("txtTipo",  ctrc.getTipo());
		addParamToQuery("txtSerie",  ctrc.getSerie());
		addParamToQuery("txtTransp",  ctrc.getTransp());
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}
}
