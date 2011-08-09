package br.com.promove.async;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;


public class ImportCTRCJob implements Job{
	private static Logger log = Logger.getLogger(ImportCTRCJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		ImportacaoService imp = ServiceFactory.getService(ImportacaoService.class);
		
		try {
			JobDataMap data = ctx.getJobDetail().getJobDataMap();
			String url = data.getString("url");
		
			imp.importarGabardo(url + "?dataIni=2005-01-01&dataFim=2011-12-31");
			log.error("Importação de CTRC realizada com sucesso.");
		} catch (PromoveException e) {
			log.error("Erro na importação de CTRC " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
