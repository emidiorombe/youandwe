package br.com.promove.dao;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.exception.DAOException;

public class AvariaDAO extends BaseDAO<Integer, Avaria>{
	public List<Avaria> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos");
		return executeQuery(hql.toString(), 1, 100);
	}

	public List<Avaria> getAvariasPorFiltro(String chassi, Avaria av, Date de, Date ate) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos left join fetch av.veiculo veic where 1 = 1 ");
		
		if(chassi != null && !chassi.isEmpty()) {
			hql.append(" and veic.chassi like :txtChassi ");
			addParamToQuery("txtChassi", "%"+ chassi);
		}
		
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
		
		if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
			hql.append(" and  av.dataLancamento between :dtIni and :dtFim ");
			addParamToQuery("dtIni", de);
			addParamToQuery("dtFim", ate);
		}
		
		return executeQuery(hql.toString(), paramsToQuery, 1, Integer.MAX_VALUE);
	}
}
