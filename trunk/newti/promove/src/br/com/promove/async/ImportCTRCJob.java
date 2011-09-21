package br.com.promove.async;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.DateUtils;


public class ImportCTRCJob implements Job{
	private static Logger log = Logger.getLogger(ImportCTRCJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		ImportacaoService imp = ServiceFactory.getService(ImportacaoService.class);
		
		try {
			JobDataMap data = ctx.getJobDetail().getJobDataMap();
			String url = data.getString("url");
			System.out.println(url);
			
			Calendar now = Calendar.getInstance();
			
			Calendar ontem = Calendar.getInstance();
			ontem.add(Calendar.DATE, -1);
			
			String query = "?dataIni=" + DateUtils.getStringFromDate(ontem.getTime(), null) + "&dataFim=" + DateUtils.getStringFromDate(now.getTime(), null);
			imp.importarGabardo(url + query);
			log.warn("Importação de CTRC realizada com sucesso.");
		} catch (PromoveException e) {
			log.error("Erro na importação de CTRC " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
