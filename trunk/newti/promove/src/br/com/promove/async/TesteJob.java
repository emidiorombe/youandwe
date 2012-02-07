package br.com.promove.async;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.promove.application.PromoveApplication;
import br.com.promove.dao.HibernateSessionFactory;
import br.com.promove.entity.Filial;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;


public class TesteJob implements Job, Serializable {
	private static Logger log = Logger.getLogger(TesteJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		//Enumeration names = ctx.getServletContext().getInitParameterNames();
		//while(names.hasMoreElements()) {
		//	String name = (String) names.nextElement();
		//	Config.setConfig(name, ctx.getServletContext().getInitParameter(name));
		//}
		
		CadastroService cad = ServiceFactory.getService(CadastroService.class);
		
		try {
			HibernateSessionFactory.getSession();
			
			List<Filial> lista = cad.buscarTodasFiliais();
			
			for (Filial filial : lista) {
				if (filial.getCodigo() == 5555) System.out.println("ANTES " + filial.getNome());
			}
			
			Filial fil = new Filial();
			fil.setCodigo(5555);
			fil.setNome("TESTE");
			cad.salvarFilial(fil);

			lista = cad.buscarTodasFiliais();
			
			for (Filial filial : lista) {
				if (filial.getCodigo() == 5555) System.out.println("DEPOIS " + filial.getNome());
			}
			
			log.warn("Teste realizada com sucesso.");
		} catch (PromoveException e) {
			log.error("Erro no Teste " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Erro no Teste " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
