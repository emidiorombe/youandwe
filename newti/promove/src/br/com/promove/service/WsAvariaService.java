package br.com.promove.service;

import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import br.com.promove.dao.AvariaDAO;
import br.com.promove.entity.Avaria;
import br.com.promove.exception.DAOException;
import br.com.promove.exportacao.AvariasExport;
import br.com.promove.utils.DateUtils;

@Path("/avarias")
public class WsAvariaService {
	private AvariaDAO avariaDAO;

	@Path("/por-data")
	@GET
	public String getAvariaPorData(@QueryParam("dataIni")String dataIni, @QueryParam("dataFim")String dataFim) {
		Date d1 = null;
		Date d2 = null;
		if(dataIni == null || dataFim == null)
			throw new IllegalArgumentException();
		
		avariaDAO = new AvariaDAO();
		
		d1 = DateUtils.parseFromString(dataIni, "yyyy-MM-dd");
		d2 = DateUtils.parseFromString(dataFim, "yyyy-MM-dd");
		
		Date init = DateUtils.montarDataInicialParaHQLQuery(d1); 
		Date fim = DateUtils.montarDataFinalParaHQLQuery(d2); 

		Avaria avaria = new Avaria();
		List<Avaria> avarias = null;
		
		try {
			avarias = avariaDAO.getAvariasPorFiltro(avaria, init, fim, 1, false, false, false, null, null, null);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return AvariasExport.gerarXmlExportacao(avarias);
	}
}
