package br.com.yaw.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.yaw.async.AsyncJobs;
import br.com.yaw.repository.CompassFactory;
import br.com.yaw.repository.EMFactory;
import br.com.yaw.resource.ResourceBundleFactory;

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
	public void contextInitialized(ServletContextEvent ctxEv) {
		//Cria o EntityManagerFactory
		EMFactory.initialize();
		
		//Cria o indexador para full-text-search
		CompassFactory.initialize(EMFactory.get());
		
		
		//Cria a factory de resourceBundle para I18N, e insere o default no contexto da app
		ResourceBundleFactory.loadBundles("pt", "es", "en");
		ctxEv.getServletContext().setAttribute("msg", ResourceBundleFactory.getBundleByLanguage("pt"));
		
		//Adiciona todas as empresas no Cache
		AsyncJobs.addAllCompaniesToCache();
		
		System.out.println("Inicializou o YaWP");
	}
	
}
