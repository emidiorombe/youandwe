package br.com.newti.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import br.com.newti.dao.ConnectionManager;
import br.com.newti.exception.DAOException;
import br.com.newti.util.SQLCache;

public class GeneralContextListener implements ServletContextListener{
	
	private static final Logger log = Logger.getLogger(GeneralContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		log.info(":::Inicializando CTRC WS");
		
		String scripts = evt.getServletContext().getInitParameter("scripts");
		String[] tokens = scripts.split(";");
		
		//Adicionando scripts SQL ao cache
		for (String t : tokens) {
			try {
				InputStream is = evt.getServletContext().getResourceAsStream("WEB-INF/scripts/" + t + ".sql");
				BufferedReader bf = new BufferedReader(new InputStreamReader(is));
				String line = null;
				String sql = "";
				while((line = bf.readLine()) != null) {
					sql += line;
				}
				SQLCache.addScript(t, sql);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		
		//Inicializando o Pool de Conexões
		try {
			ConnectionManager.initialize(evt.getServletContext().getInitParameter("db_driver"), evt.getServletContext().getInitParameter("db_url"), 
										 evt.getServletContext().getInitParameter("db_user"), evt.getServletContext().getInitParameter("db_pass"));
		} catch (DAOException e) {
			throw new RuntimeException("Impossível inicializar a aplicacao: " + e.getMessage());
		}
		
	}

}
