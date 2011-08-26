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
		hql.append("select v from VeiculoCtrc v");
		if (veic.getCtrc() != null) {
			hql.append(" where v.ctrc = :txtCtrc");
			addParamToQuery("txtCtrc", veic.getCtrc());
		} else {
			hql.append(" where v.inconsistencia = :txtInconsistencia");
			addParamToQuery("txtInconsistencia", veic.getInconsistencia());
		}
		if (veic.getVeiculo() != null) {
			hql.append(" and v.veiculo.chassi = :txtChassi");
			addParamToQuery("txtChassi", veic.getVeiculo().getChassi());
		} else {
			hql.append(" and v.chassiInvalido = :txtChassiInvalido");
			addParamToQuery("txtChassiInvalido", veic.getChassiInvalido());
		}
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<VeiculoCtrc> getByInconsistencia(Integer idInc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from VeiculoCtrc v");
		hql.append(" where v.inconsistencia = :txtInconsistencia");
		addParamToQuery("txtInconsistencia", idInc);
		return executeQuery(hql.toString(), paramsToQuery, 0, 100);
	}

	public List<VeiculoCtrc> getTodasInconsistencias() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select v from VeiculoCtrc v");
		hql.append(" where v.inconsistencia > 0");
		hql.append(" and v.msgErro is not null");
		return executeQuery(hql.toString(), paramsToQuery, 0, 100);
	}
}
