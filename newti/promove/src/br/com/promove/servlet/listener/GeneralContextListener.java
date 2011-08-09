package br.com.promove.servlet.listener;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import br.com.promove.async.ImportCTRCJob;
import br.com.promove.dao.HibernateSessionFactory;
import br.com.promove.service.ServiceFactory;
import br.com.promove.service.ServiceInjector;
import br.com.promove.utils.Config;

import com.google.inject.Guice;

public class GeneralContextListener implements ServletContextListener{
	private static Logger log = Logger.getLogger(GeneralContextListener.class);
	private Scheduler sched;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			sched.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		log.warn(">>>>>>>>>>> Iniciando SICA");
		
		ServiceFactory.setInjector(Guice.createInjector(new ServiceInjector()));
		HibernateSessionFactory.initializeFactory();
		
		Enumeration names = ctx.getServletContext().getInitParameterNames();
		while(names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Config.setConfig(name, ctx.getServletContext().getInitParameter(name));
		}
		
		//Inicializa o 'Scheduler' para importação de CTRC
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
	        Scheduler sched = sf.getScheduler();
	        
	        String cron_scheduler = ctx.getServletContext().getInitParameter("ctrc_job");
	        String url_ctrc = ctx.getServletContext().getInitParameter("ctrc_ws_url");
	        
			JobDetail ctrcJob = newJob(ImportCTRCJob.class).withIdentity("job_ctrc", "gdefault").build();
			ctrcJob.getJobDataMap().put("url", url_ctrc);
			
			CronTrigger trigger  = newTrigger().withIdentity("trigger_ctrc", "gdefault").withSchedule(cronSchedule(cron_scheduler)).build();
			sched.scheduleJob(ctrcJob, trigger);
			sched.start();
			log.error(">>>>>>>>>>> Importacao de CTRC Agendada");
		}catch(Exception e) {
			log.error("::::Não foi possivel agendar a importação de CTRC");
		}
		
	}

}
