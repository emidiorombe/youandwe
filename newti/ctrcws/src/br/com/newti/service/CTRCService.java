package br.com.newti.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import br.com.newti.dao.CTRCDAO;
import br.com.newti.parser.CTRCParser;
import br.com.newti.util.DateUtils;
import br.com.newti.util.SQLCache;

@Path("/ctrc")
public class CTRCService {
	
	@Path("/por-data")
	@GET
	public String getPorDataAlteracao(@QueryParam("dataIni")String dataIni, @QueryParam("dataFim")String dataFim) {
		Date d = null;
		if(dataIni == null || dataIni.isEmpty())
			d = new Date();
		else
			d = DateUtils.parseFromString(dataIni, "yyyy-MM-dd");
		
		//TODO Criar a data fim
		List<Map<String, Object>> ctrcByData = CTRCDAO.getByDataModificacao(d, d);
		
		return CTRCParser.listMapToXML(ctrcByData);
	}
}
