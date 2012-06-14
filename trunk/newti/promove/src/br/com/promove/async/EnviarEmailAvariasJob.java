package br.com.promove.async;

import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.promove.dao.HibernateSessionFactory;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ImportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.Config;
import br.com.promove.utils.DateUtils;
import br.com.promove.utils.EmailUtils;

public class EnviarEmailAvariasJob implements Job {

	private static Logger log = Logger.getLogger(ImportCTRCJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		ImportacaoService imp = ServiceFactory.getService(ImportacaoService.class);
		CadastroService cadastroService = ServiceFactory.getService(CadastroService.class);
		
		// Vistorias
		try {
			Map<String, String> params = cadastroService.buscarTodosParametrosAsMap();
			
			//JobDataMap data = ctx.getJobDetail().getJobDataMap();
			//String tos[] = data.getString("dest").split(";");
			
			ImportacaoService importService = ServiceFactory.getService(ImportacaoService.class);
			AvariaService avariaService = ServiceFactory.getService(AvariaService.class);
			
			HibernateSessionFactory.getSession().beginTransaction();
			 
			importService.transfereFotos(Config.getConfig("pasta_avaria_xml"), Config.getConfig("pasta_fotos"));
			String conteudo = importService.importAvariasDoDiretorio(Config.getConfig("pasta_avaria_xml"), Config.getConfig("pasta_destino_xml")).replaceAll(";", "<br>");
			if (conteudo.isEmpty()) conteudo = "Nenhum arquivo.";
			conteudo = "<b>Lista de Arquivos importados:</b><br>" + conteudo; 
					
			conteudo += "<br><br>" + avariaService.listarAvariasPT(DateUtils.diaAtual());
			EmailUtils.sendHtml(params.get("smtpEmail"), params.get("emailVistorias").split(";"), "SIGA - Importacao de Vistorias", conteudo);
			
			HibernateSessionFactory.getSession().getTransaction().commit();
			
			log.warn("Importação de avarias realizada com sucesso.");
		} catch (Exception e) {
			log.error("Erro na Importação de avarias " + e.getMessage());
			e.printStackTrace();
		}
		
		/*
		// CTRC
		try {
			HibernateSessionFactory.getSession().beginTransaction();

			JobDataMap data = ctx.getJobDetail().getJobDataMap();
			String url = data.getString("url");
			System.out.println(url);
			
			Calendar now = Calendar.getInstance();
			
			Calendar ontem = Calendar.getInstance();
			ontem.add(Calendar.DATE, -1);
			
			String query = "?dataIni=" + DateUtils.getStringFromDate(ontem.getTime(), null) + "&dataFim=" + DateUtils.getStringFromDate(now.getTime(), null);
			
			String conteudo = "<b>Data de importacao: </b>";
			conteudo = conteudo + DateUtils.getStringFromDate(ontem.getTime(), "dd/mm/yyyy") + "<br><br>";
			conteudo = conteudo + "<b>Quantidade de CTRCs importados: </b>";
			conteudo = conteudo + imp.importarGabardo(url + query) + "<br>"; 
			EmailUtils.sendHtml("sica@promoveseguros.com.br", "daniel@newti.com.br;".split(";"), "SIGA - Importação de CTRC", conteudo);
			
			HibernateSessionFactory.getSession().getTransaction().commit();
			
			log.warn("Importação de CTRC realizada com sucesso.");
		} catch (PromoveException e) {
			log.error("Erro na importação de CTRC " + e.getMessage());
			e.printStackTrace();
		}
		*/
	}

}

