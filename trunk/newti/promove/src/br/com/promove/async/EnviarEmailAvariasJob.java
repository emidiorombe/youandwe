package br.com.promove.async;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.promove.service.AvariaService;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.Config;
import br.com.promove.utils.DateUtils;
import br.com.promove.utils.EmailUtils;

public class EnviarEmailAvariasJob implements Job {

	private static Logger log = Logger.getLogger(ImportCTRCJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		try {
			//JobDataMap data = ctx.getJobDetail().getJobDataMap();
			//String tos[] = data.getString("dest").split(";");
			
			ImportacaoService importService = ServiceFactory.getService(ImportacaoService.class);
			AvariaService avariaService = ServiceFactory.getService(AvariaService.class);
			 
			importService.transfereFotos(Config.getConfig("pasta_avaria_xml"), Config.getConfig("pasta_fotos"));
			String conteudo = importService.importAvariasDoDiretorio(Config.getConfig("pasta_avaria_xml"), Config.getConfig("pasta_destino_xml")).replaceAll(";", "<br>");
			if (conteudo.isEmpty()) conteudo = "Nenhum arquivo.";
			conteudo = "<b>Lista de Arquivos importados:</b><br>" + conteudo; 
					
			
			conteudo += "<br><br>" + avariaService.listarAvariasPT(DateUtils.diaAtual());
			EmailUtils.sendHtml("sica@promoveseguros.com.br", "daniel@newti.com.br;".split(";"), "SICA - Importação de Vistorias", conteudo);
		} catch (Exception e) {
			log.error("Erro no envio de e-mail de avarias " + e.getMessage());
			e.printStackTrace();
		}
	}

}

