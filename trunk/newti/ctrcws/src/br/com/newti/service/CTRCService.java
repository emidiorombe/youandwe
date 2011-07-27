package br.com.newti.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import br.com.newti.util.DateUtils;
import br.com.newti.util.SQLCache;

@Path("/ctrc")
public class CTRCService {
	
	@Path("/por-data")
	@GET
	public String getPorDataAlteracao(@QueryParam("data")String data) {
		Date d = null;
		if(data == null || data.isEmpty())
			d = new Date();
		else
			d = DateUtils.parseFromString(data, "yyyy-MM-dd");
		
		return SQLCache.getScript("ctrc");
	}
}
