package br.com.promove.dao;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.TipoVeiculo;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.DAOException;

public class CtrcDAO extends BaseDAO<Integer, Ctrc>{
	public List<Ctrc> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ct from Ctrc ct");
		return executeQuery(hql.toString(), 0, 100);
	}

	public List<Ctrc> getCtrcPorFiltro(Ctrc ctrc, Date de, Date ate, String chassi, Boolean veics) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ct from Ctrc ct");
		if (veics) hql.append(" left JOIN FETCH ct.veiculos");
		hql.append(" where 1=1");
		
		if (ctrc.getNumero() != null && ctrc.getNumero() != 0) {
			hql.append(" and ct.numero = :txtNumero");
			addParamToQuery("txtNumero", ctrc.getNumero());
		}
		
		if (ctrc.getTransp() != null && ctrc.getTransp().getId() != null) {
			hql.append(" and ct.transp = :txtTransp ");
			addParamToQuery("txtTransp", ctrc.getTransp());
		}
		
		if (de != null && !de.equals("") && ate != null && !ate.equals("")) {
			hql.append(" and  ct.dataEmissao between :dtIni and :dtFim ");
			addParamToQuery("dtIni", de);
			addParamToQuery("dtFim", ate);
		}
		
		if (chassi != null && !chassi.isEmpty()) {
			hql.append(" and exists (select v from VeiculoCtrc v");
			hql.append(" where v.ctrc = ct");
			hql.append(" and v.veiculo.chassi like :txtChassi)");
			addParamToQuery("txtChassi", "%" + chassi);
		}

		if (ctrc.getCancelado() == null || ctrc.getCancelado() == false) {
			hql.append(" and ct.cancelado = false");
		}
		
		if (ctrc.getInconsistente() == null || ctrc.getInconsistente() == false) {
			hql.append(" and ct.inconsistente = false");
		}
		
		hql.append(" order by ct.dataEmissao, ct.numero");
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
		
		/*
		if(incons) {
			hql = new StringBuilder();
			hql.append("select ct from InconsistenciaCtrc ct");
			hql.append(" where 1=1");
			
			if (ctrc.getNumero() != null && ctrc.getNumero() != 0) {
				hql.append(" and ct.numero = :txtNumero");
				addParamToQuery("txtNumero", ctrc.getNumero());
			}
			
			if (ctrc.getTransp() != null && ctrc.getTransp().getId() != null) {
				hql.append(" and ct.transp = :txtTransp ");
				addParamToQuery("txtTransp", ctrc.getTransp());
			}
			
			if (de != null && !de.equals("") && ate != null && !ate.equals("")) {
				hql.append(" and  ct.dataEmissao between :dtIni and :dtFim ");
				addParamToQuery("dtIni", de);
				addParamToQuery("dtFim", ate);
			}
			
			if (chassi != null && !chassi.isEmpty()) {
				hql.append(" and exists (select v from VeiculoCtrc v");
				hql.append(" where v.ctrc = ct");
				hql.append(" and v.veiculo.chassi like :txtChassi)");
				addParamToQuery("txtChassi", "%" + chassi);
			}

			if (ctrc.getCancelado() == null || ctrc.getCancelado() == false) {
				hql.append(" and ct.cancelado = false");
			}
			hql.append(" order by ct.dataEmissao, ct.numero");
			List<InconsistenciaCtrc> incCtrcs = executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
			
			for (InconsistenciaCtrc incCtrc : incCtrcs) {
				VeiculoCtrcDAO veiculoCtrcDAO = new VeiculoCtrcDAO();
				List<VeiculoCtrc> veicsCtrc = veiculoCtrcDAO.getByInconsistencia(incCtrc.getId());
				
				for (VeiculoCtrc veicCtrc : veicsCtrc) {
					if (veicCtrc.getVeiculo() == null || veicCtrc.getVeiculo().getId() == null) {
						Veiculo veiculo = new Veiculo();
						veiculo.setChassi(veicCtrc.getChassiInvalido());
						veiculo.setCor(this.getGenericObject(Cor.class, new Integer(97)));
						veiculo.setTipo(this.getGenericObject(TipoVeiculo.class, new Integer(9)));
						veiculo.setValorMercadoria(veicCtrc.getValorMercadoria());
						
						Modelo m = new Modelo();
						m.setDescricao(veicCtrc.getModelo());
						veiculo.setModelo(m);
						
						veicCtrc.setVeiculo(veiculo);
					}
				}
				ctrcs.add(incCtrc.getCtrc());
			}
			
		}
		
		return ctrcs;
		*/
	}

	public List<Ctrc> getCtrcsDuplicadosPorFiltros(Ctrc ctrc) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select ct from Ctrc ct");
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
