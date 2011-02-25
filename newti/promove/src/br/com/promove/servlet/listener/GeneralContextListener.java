package br.com.promove.servlet.listener;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.google.inject.Guice;

import br.com.promove.dao.HibernateSessionFactory;
import br.com.promove.service.ServiceFactory;
import br.com.promove.service.ServiceInjector;
import br.com.promove.utils.Config;

public class GeneralContextListener implements ServletContextListener{
	private static Logger log = Logger.getLogger(GeneralContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

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
		
	}

}
