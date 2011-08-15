package br.com.promove.dao;

import java.util.List;

import br.com.promove.entity.Ctrc;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.DAOException;

public class VeiculoCtrcDAO extends BaseDAO<Integer, VeiculoCtrc> {
	
	public List<VeiculoCtrc> getByCtrc(Ctrc ctrc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select veic from VeiculoCtrc veic");
		hql.append(" where veic.ctrc = :txtctrc");
		addParamToQuery("txtctrc", ctrc);
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<VeiculoCtrc> getVeiculosCtrcDuplicadosPorFiltros(VeiculoCtrc veic) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from VeiculoCtrc v left join fetch v.ctrc ct");
		hql.append(" where ct.filial = :txtFilial");
		hql.append(" and ct.numero = :txtNumero");
		hql.append(" and ct.tipo = :txtTipo");
		hql.append(" and ct.serie = :txtSerie");
		hql.append(" and ct.transp = :txtTransp");
		hql.append(" and v.veiculo.chassi = :txtChassi");
		addParamToQuery("txtFilial", veic.getCtrc().getFilial());
		addParamToQuery("txtNumero", veic.getCtrc().getNumero());
		addParamToQuery("txtTipo", veic.getCtrc().getTipo());
		addParamToQuery("txtSerie", veic.getCtrc().getSerie());
		addParamToQuery("txtTransp", veic.getCtrc().getTransp());
		addParamToQuery("txtChassi", veic.getVeiculo().getChassi());
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<VeiculoCtrc> getByInconsistencia(Integer idInc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select veic from VeiculoCtrc veic where veic.inconsisctencia = :txtinc");
		addParamToQuery("txtinc", idInc);
		return executeQuery(hql.toString(), paramsToQuery, 0, 100);
	}
}
