package br.com.yaw.test;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;



public class DAO {
	private static final Logger log = Logger.getLogger(DAO.class.getName());
	static {
		ObjectifyService.register(President.class);
		ObjectifyService.register(Party.class);
	}
	
//	public static String getPresidents() throws Exception {
//		Objectify ofy = ObjectifyService.begin();
//		List<President> lista = ofy.query(President.class).list();
//		
//		StringBuilder retorno = new StringBuilder("{'total':'" + lista.size()+ "', 'results': ");
//		GsonBuilder g = new GsonBuilder();
//		g.registerTypeAdapter(Key.class, new PartyJsonAdaptaer());
//		Gson gson = g.create(); 
//		//retorno.append(gson.toJson(lista));
//		retorno.append("[");
//		for (President p : lista) {
//			retorno.append(gson.toJson(p));
//			retorno.append(",");
//			
//		}
//		retorno.append("]}");
//		System.out.println(retorno);
//		return retorno.toString();
//	}
	
	public static String getPresidents() throws Exception {
		Objectify ofy = ObjectifyService.begin();
		List<President> lista = ofy.query(President.class).list();
		
		StringBuilder retorno = new StringBuilder("{'total':'" + lista.size()+ "', 'results': ");
		GsonBuilder g = new GsonBuilder();
		g.registerTypeAdapter(Key.class, new PartyJsonAdaptaer());
		Gson gson = g.create(); 
		retorno.append("[");
		for (President p : lista) {
			Party partyByKey = DAO.getPartyByKey(p.getPartyKey());
			retorno.append("{'id':'" + p.getId() +"',"+
						   "'firstName':'" + p.getFirstName() +"',"+ 
						   "'lastName':'" + p.getLastName() +"',"+
						   "'tookOffice':'" + new SimpleDateFormat("dd/mm/yyyy").format(p.getTookOffice())+ "',"+
						   "'leftOffice':'" + new SimpleDateFormat("dd/MM/yyyy").format(p.getLeftOffice())+ "',"+
						   "'income':" + p.getIncome() + ","+
						   "'party':" + partyByKey.getId() + ","+
						   "'pname':'" + partyByKey.getName()+ "'"+
						   "},"
					);
		}
		retorno.append("]}");
		System.out.println(retorno);
		return retorno.toString();
	}
	
//	public static String getPresidents() throws Exception {
//		Objectify ofy = ObjectifyService.begin();
//		List<President> lista = ofy.query(President.class).list();
//		
//		StringBuilder retorno = new StringBuilder("{'total':'" + lista.size()+ "', 'results': ");
//		GsonBuilder g = new GsonBuilder();
//		Gson gson = g.create(); 
//		retorno.append(gson.toJson(PresidentDataView.createFromListPresident(lista)));
//		retorno.append("}");
//		return retorno.toString();
//	}
	
	public static void addParty(String name) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(new Party(name));
	}
	
	public static Party getPartyByKey(Key<Party> k) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(k);
	}
	
	public static Party getParty(Long id) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(Party.class, id);
	}
	
	public static void addPresident(President p) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(p);
	}
	
}
