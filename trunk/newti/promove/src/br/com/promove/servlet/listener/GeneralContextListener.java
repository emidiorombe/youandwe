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

import br.com.promove.async.EnviarEmailAvariasJob;
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
		
		//Inicializa o 'Scheduler' para execução dos jobs assincronos
		
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
	        Scheduler sched = sf.getScheduler();
	        
	        String cron_scheduler = ctx.getServletContext().getInitParameter("ctrc_job");
	        String url_ctrc = ctx.getServletContext().getInitParameter("ctrc_ws_url");
	        String dest_param = ctx.getServletContext().getInitParameter("destinatarios_mail");
	        String mail_sched = ctx.getServletContext().getInitParameter("mail_scheduler");
	        
			JobDetail ctrcJob = newJob(ImportCTRCJob.class).withIdentity("job_ctrc", "gdefault").build();
			ctrcJob.getJobDataMap().put("url", url_ctrc);
			
			JobDetail mail_job = newJob(EnviarEmailAvariasJob.class).withIdentity("job_mail", "gdefault").build();
			mail_job.getJobDataMap().put("dest", dest_param);
			
			CronTrigger trigger_ctrc  = newTrigger().withIdentity("trigger_ctrc", "gdefault").withSchedule(cronSchedule(cron_scheduler)).build();
			CronTrigger trigger_mail  = newTrigger().withIdentity("trigger_mail", "gdefault").withSchedule(cronSchedule(mail_sched)).build();
			
			sched.scheduleJob(ctrcJob, trigger_ctrc);
			sched.scheduleJob(ctrcJob, trigger_mail);
			
			sched.start();
			log.info(">>>>>>>>>>> Jobs agendados");
		}catch(Exception e) {
			log.error("::::Não foi possivel agendar a importação de CTRC");
		}
		
	}

}
