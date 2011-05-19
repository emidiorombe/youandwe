package br.com.promove.dao;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.utils.StringUtilities;

public class AvariaDAO extends BaseDAO<Integer, Avaria>{
	public List<Avaria> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos");
		return executeQuery(hql.toString(), 0, 100);
	}

	public List<Avaria> getAvariasPorFiltro(Avaria av, Date de, Date ate) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos left join fetch av.veiculo veic ");
		hql.append(" left JOIN FETCH av.tipo tp left JOIN FETCH av.origem ori left JOIN FETCH av.extensao ext left JOIN FETCH av.local loc left JOIN FETCH av.clima cli ");
		hql.append(" left JOIN FETCH av.usuario usu left JOIN FETCH usu.tipo tpu left JOIN FETCH veic.modelo mod left JOIN FETCH veic.cor cor left JOIN FETCH mod.fabricante fab ");
		hql.append(" left JOIN FETCH ori.responsabilidade resp left JOIN FETCH usu.filial usufil left JOIN FETCH ori.filial orifil  ");
		hql.append(" where 1=1 ");
		if(av.getVeiculo() != null && !av.getVeiculo().getChassi().isEmpty()) {
			hql.append(" and veic.chassi like :txtChassi ");
			addParamToQuery("txtChassi", "%"+ av.getVeiculo().getChassi());
		}
		
		if(av != null) {
			if(av.getTipo() != null && av.getTipo().getId() != null) {
				hql.append(" and av.tipo = :tp ");
				addParamToQuery("tp", av.getTipo());
			}
			
			if(av.getOrigem() != null && av.getOrigem().getId() != null) {
				hql.append(" and av.origem = :org ");
				addParamToQuery("org", av.getOrigem());
			}
	
			
			if(av.getLocal() != null && av.getLocal().getId() != null) {
				hql.append(" and av.local = :loc ");
				addParamToQuery("loc", av.getLocal());
			}
		}
		
		if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
			hql.append(" and  av.dataLancamento between :dtIni and :dtFim ");
			addParamToQuery("dtIni", de);
			addParamToQuery("dtFim", ate);
		}
		
		hql.append(" order by av.origem.id");
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}

	public List<Avaria> getAvariasDuplicadasPorFiltro(List<Veiculo> veiculos,Avaria av) throws DAOException{
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left join fetch av.veiculo veic ");
		hql.append(" where veic.chassi in (:listchassi) ");
		hql.append(" and av.tipo = :tpAv ");
		hql.append(" and av.local = :lcAv ");
		hql.append(" and av.origem = :orAv ");

		addParamToQuery("listchassi", StringUtilities.listVeiculoToChassiInClause(veiculos));
		addParamToQuery("tpAv", av.getTipo());
		addParamToQuery("lcAv", av.getLocal());
		addParamToQuery("orAv", av.getOrigem());
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);

	}

	public List<Avaria> getAvariasDuplicadasPorFiltro(Veiculo veiculo, Avaria av) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left join fetch av.veiculo veic ");
		hql.append(" where veic.chassi = :txtchassi ");
		hql.append(" and av.tipo = :tpAv ");
		hql.append(" and av.local = :lcAv ");
		hql.append(" and av.origem = :orAv ");

		addParamToQuery("txtchassi", veiculo.getChassi());
		addParamToQuery("tpAv", av.getTipo());
		addParamToQuery("lcAv", av.getLocal());
		addParamToQuery("orAv", av.getOrigem());
		
		return executeQuery(hql.toString(), paramsToQuery, 0, Integer.MAX_VALUE);
	}
}
