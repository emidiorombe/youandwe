package br.com.yaw.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.yaw.repository.CompassFactory;
import br.com.yaw.repository.EMFactory;

/**
 * General initializations
 * @author Rafael Nunes
 *
 */
public class GeneralContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		EMFactory.initialize();
		CompassFactory.initialize(EMFactory.get());
		System.out.println("Inicializou o YaWP");
	}
	
}
