package br.com.yaw.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Factory for EntityManager's
 * @author Rafael Nunes
 *
 */
public class EMFactory {
	private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");
	
	private EMFactory() {
		
	}
	
	public static EntityManagerFactory get() {
		return emfInstance;
	}
}
