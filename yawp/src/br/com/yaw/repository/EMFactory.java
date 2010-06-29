package br.com.yaw.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.core.converter.Converter;
import org.compass.gps.CompassGps;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import br.com.yaw.utils.KeyConverter;

/**
 * Factory for EntityManager's
 * @author Rafael Nunes
 *
 */
public class EMFactory {
	private static EntityManagerFactory emfInstance;
	
	public static void initialize() {
		emfInstance = Persistence.createEntityManagerFactory("transactions-optional");
	}
	
	private EMFactory() {
		
	}
	
	public static EntityManagerFactory get() {
		return emfInstance;
	}
	
}
