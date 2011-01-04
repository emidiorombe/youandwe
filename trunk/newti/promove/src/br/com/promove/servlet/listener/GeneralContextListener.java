package br.com.promove.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.google.inject.Guice;

import br.com.promove.dao.HibernateSessionFactory;
import br.com.promove.service.ServiceFactory;
import br.com.promove.service.ServiceInjector;

public class GeneralContextListener implements ServletContextListener{
	private static Logger log = Logger.getLogger(GeneralContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		log.warn(">>>>>>>>>>> Iniciando Promove");
		
		ServiceFactory.setInjector(Guice.createInjector(new ServiceInjector()));
		HibernateSessionFactory.initializeFactory();
		
	}

}
