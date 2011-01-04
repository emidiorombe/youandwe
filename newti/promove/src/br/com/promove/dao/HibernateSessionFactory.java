package br.com.promove.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.promove.servlet.listener.GeneralContextListener;

public class HibernateSessionFactory {
	
	private static Logger log = Logger.getLogger(HibernateSessionFactory.class);
	private static SessionFactory factory;
	private static ThreadLocal<Session> tlSession = new ThreadLocal<Session>();
	
	/**
	 * Inicializa o SessionFactory 
	 */
	public static void initializeFactory(){
		Configuration config = new Configuration();
		config.configure();
		
		factory = config.buildSessionFactory();
	}
	
	/**
	 * Retorna a Sessão corrente do usuário
	 * @return
	 */
	public static Session getSession(){
		if(tlSession.get() == null || !tlSession.get().isConnected())
			tlSession.set(factory.openSession());
		return tlSession.get();
	}
	
	
	/**
	 * Remove a Session vinculada a esta transação
	 */
	public static void clear(){
		tlSession.remove();
	}
	
	/**
	 * Método responsável por fechar uma sessão com o Hibernate
	 * @throws HibernateException
	 */
	public static void closeSession() {
        Session s = (Session) tlSession.get();
        if (s != null && s.isOpen()) {
        		s.close();
        }
        tlSession.set(null);
	}
	
}
