package br.com.promove.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.exception.SQLGrammarException;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Cor;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.ResponsabilidadeAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.utils.StringUtilities;

public class AvariaDAO extends BaseDAO<Integer, Avaria>{
	public List<Avaria> getAllCustom() throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos");
		return executeQuery(hql.toString(), 0, 100);
	}

	public List<Avaria> getAvariasPorFiltro(Avaria av, Date de, Date ate, Integer periodo, Boolean movimentacao, Boolean registradas, OrigemAvaria oriAte, ResponsabilidadeAvaria responsabilidade, Fabricante fabricante) throws DAOException {
		StringBuilder hql = new StringBuilder();
		hql.append("select av from Avaria av left JOIN FETCH av.fotos left join fetch av.veiculo veic");
		hql.append(" left JOIN FETCH av.tipo tp left JOIN FETCH av.origem ori left JOIN FETCH av.extensao ext left JOIN FETCH av.local loc left JOIN FETCH av.clima cli");
		hql.append(" left JOIN FETCH av.usuario usu left JOIN FETCH usu.tipo tpu left JOIN FETCH veic.modelo mod left JOIN FETCH veic.cor cor left JOIN FETCH mod.fabricante fab");
		hql.append(" left JOIN FETCH ori.responsabilidade resp left JOIN FETCH usu.filial usufil left JOIN FETCH ori.filial orifil");
		hql.append(" where 1=1");
		if(av != null) {
			if(av.getTipo() != null && av.getTipo().getId() != null) {
				hql.append(" and av.tipo = :tp");
				addParamToQuery("tp", av.getTipo());
			}
			
			if(av.getOrigem() != null && av.getOrigem().getId() != null) {
				hql.append(" and av.origem between :org and :org2");
				addParamToQuery("org", av.getOrigem());
				addParamToQuery("org2", oriAte.getId() == null ? av.getOrigem() : oriAte);
			}
	
			if(av.getLocal() != null && av.getLocal().getId() != null) {
				hql.append(" and av.local = :loc");
				addParamToQuery("loc", av.getLocal());
			}
			
			if(av.getVeiculo() != null) {
				if(av.getVeiculo().getChassi() != null && !av.getVeiculo().getChassi().isEmpty() && !av.getVeiculo().getChassi().equals("")) {
					hql.append(" and veic.chassi like :txtChassi");
					addParamToQuery("txtChassi", "%"+ av.getVeiculo().getChassi());
				}
				
				if(av.getVeiculo().getTipo() != null && av.getVeiculo().getTipo() != 0) {
					hql.append(" and veic.tipo = :txtTipo");
					addParamToQuery("txtTipo", av.getVeiculo().getTipo());
				}
			
				if(av.getVeiculo().getModelo() != null && av.getVeiculo().getModelo().getId() != null) {
					hql.append(" and veic.modelo = :txtModelo");
					addParamToQuery("txtModelo", av.getVeiculo().getModelo());
				}
			}
		}
		
		if(de != null && !de.equals("") && ate != null && !ate.equals("")) {
			if (periodo == 1) hql.append(" and av.dataLancamento between :dtIni and :dtFim");
			else hql.append(" and veic.dataCadastro between :dtIni and :dtFim");
			addParamToQuery("dtIni", de);
			addParamToQuery("dtFim", ate);
		}
		
		if(responsabilidade != null && responsabilidade.getId() != null) {
			hql.append(" and ori.responsabilidade = :txtResponsabilidade");
			addParamToQuery("txtResponsabilidade", responsabilidade);
		}
		
		if(fabricante != null && fabricante.getId() != null) {
			hql.append(" and mod.fabricante = :txtFabricante");
			addParamToQuery("txtFabricante", fabricante);
		}
		
		if(movimentacao) hql.append(" and tp.movimentacao = false");

		if(registradas) {
			hql.append(" and (");
			if(!movimentacao) hql.append("tp.movimentacao = true or ");
			hql.append("not exists (select av2 from Avaria av2");
			hql.append(" where av2.veiculo = av.veiculo");
			hql.append(" and av2.tipo.id = av.tipo.id and av2.local.id = av.local.id");
			hql.append(" and av2.origem.codigo < av.origem.codigo))");
		}

		hql.append(" order by av.origem.codigo");
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

	public List<Cor> buscarResumo(Veiculo veiculo, Date dtInicio, Date dtFim, Integer periodo, OrigemAvaria oriInicio, OrigemAvaria oriFim, String item, String subitem) throws DAOException {
		if (item.isEmpty() && !subitem.isEmpty()) item = subitem;
		if (subitem.equals(item)) subitem = "";
		
		String nomeItem = (item.isEmpty() ? "cast('' as text)" : item + (item.equals("fabricante") ? ".nome" : ".descricao"));
		String nomeSubitem = (subitem.isEmpty() ? "cast('' as text)" : subitem + (subitem.equals("fabricante") ? ".nome" : ".descricao"));
		String idItem = (item.isEmpty() ? "" : (item.equals("fabricante") ? "modelo." : (item.equals("modelo") ? "veiculo." : "avaria.")) + item.replaceAll("avaria", "") + "_id");
		String idSubitem = (subitem.isEmpty() ? "" : (subitem.equals("fabricante") ? "modelo." : (subitem.equals("modelo") ? "veiculo." : "avaria.")) + subitem.replaceAll("avaria", "") + "_id");
		
		StringBuilder sql = new StringBuilder();
		sql.append("select " + nomeItem + " as item, " + nomeSubitem + " as subitem, cast(count(*) as integer)");
		sql.append(" from avaria, origemavaria, tipoavaria, veiculo");
		if (!item.isEmpty() && !item.equals("origemavaria") && !item.equals("tipoavaria")) sql.append(", " + item);
		if (!subitem.isEmpty() && !subitem.equals("origemavaria") && !subitem.equals("tipoavaria")) sql.append(", " + subitem);
		if ((!item.isEmpty() && item.equals("fabricante") && !subitem.isEmpty() && !subitem.equals("modelo")) ||
				(!subitem.isEmpty() && subitem.equals("fabricante") && !item.isEmpty() && !item.equals("modelo")))
			sql.append(", modelo");
		
		sql.append(" where " + (periodo == 1 ? "avaria.datalancamento" : "veiculo.datacadastro"));
		sql.append(" between '" + new SimpleDateFormat("yyyy-MM-dd").format(dtInicio) +"'");
		sql.append(" and '" + new SimpleDateFormat("yyyy-MM-dd").format(dtFim) +"'");

		if(veiculo.getTipo() != null && veiculo.getTipo() != 0) 
			sql.append(" and veiculo.tipo = " + veiculo.getTipo().toString());

		sql.append(" and origemavaria.codigo");
		sql.append(" between " + oriInicio.getCodigo().toString());
		sql.append(" and " + oriFim.getCodigo().toString());
		
		sql.append(" and not exists (select * from avaria av2, origemavaria ori2");
		sql.append(" where av2.origem_id = ori2.id");
		sql.append(" and av2.veiculo_id = avaria.veiculo_id");
		sql.append(" and av2.tipo_id = avaria.tipo_id and av2.local_id = avaria.local_id");
		sql.append(" and ori2.codigo < origemavaria.codigo)");
		
		sql.append(" and tipoavaria.movimentacao = false");
		sql.append(" and avaria.origem_id = origemavaria.id");
		sql.append(" and avaria.tipo_id = tipoavaria.id");
		sql.append(" and avaria.veiculo_id = veiculo.id");
		if (!item.isEmpty() && !item.equals("origemavaria") && !item.equals("tipoavaria")) sql.append(" and " + idItem + " = " + item + ".id");
		if (!subitem.isEmpty() && !subitem.equals("origemavaria") && !subitem.equals("tipoavaria")) sql.append(" and " + idSubitem + " = " + subitem + ".id");
		if ((!item.isEmpty() && item.equals("fabricante") && !subitem.isEmpty() && !subitem.equals("modelo")) ||
				(!subitem.isEmpty() && subitem.equals("fabricante") && !item.isEmpty() && !item.equals("modelo")))
			sql.append(" and veiculo.modelo_id = modelo.id");
		
		if (!item.isEmpty() || !subitem.isEmpty()) {
			sql.append(" group by");
			if (!item.isEmpty()) sql.append(" " + nomeItem);
			if (!item.isEmpty() && !subitem.isEmpty()) sql.append(","); 
			if (!subitem.isEmpty()) sql.append(" " + nomeSubitem);
			sql.append(" order by");
			if (subitem.isEmpty()) sql.append(" count(*) desc,");
			if (!item.isEmpty()) sql.append(" " + nomeItem);
			if (!item.isEmpty() && !subitem.isEmpty()) sql.append(",");
			if (!subitem.isEmpty()) sql.append(" count(*) desc,");
			if (!subitem.isEmpty()) sql.append(" " + nomeSubitem);
		}

		List lista = executeSQLQuery(sql.toString());
		List<Cor> cores = new ArrayList<Cor>();

		List<Cor> coresSort = new ArrayList<Cor>();
		String itemAnt = "";
		Integer total = 0;
		for(Object obj : lista) {
			if(total > 0 && !itemAnt.equals((String)(((Object[])obj)[0]))) {
				coresSort.add(new Cor(itemAnt, "", total));
				total = 0;
			}
			
			if(item.isEmpty() || subitem.isEmpty())
				cores.add(new Cor((String)((Object[])obj)[0], (String)((Object[])obj)[1], (Integer)((Object[])obj)[2]));
			
			total += (Integer)((Object[])obj)[2];
			itemAnt = (String)(((Object[])obj)[0]);
		}
		coresSort.add(new Cor(itemAnt, "", total));
		
		if(!item.isEmpty() && !subitem.isEmpty()) {
			Cor corMaior;
			while(coresSort.size() > 0) {
				corMaior = new Cor("", "", 0);
				for(Cor cor : coresSort) {
					if(cor.getCodigo() > corMaior.getCodigo())
						corMaior = cor;
				}
				coresSort.remove(corMaior);
				
				for(Object obj : lista) {
					if(corMaior.getDescricao().equals((String)((Object[])obj)[0]))
						cores.add(new Cor((String)((Object[])obj)[0], (String)((Object[])obj)[1], (Integer)((Object[])obj)[2]));
				}
				cores.add(corMaior);
			}
		}
		
		return cores;
	}
}
