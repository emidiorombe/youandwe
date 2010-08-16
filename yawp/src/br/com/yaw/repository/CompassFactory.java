package br.com.yaw.repository;

import javax.persistence.EntityManagerFactory;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.core.converter.Converter;
import org.compass.gps.CompassGps;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.impl.SingleCompassGps;

import br.com.yaw.utils.KeyConverter;

public class CompassFactory {
	private static Compass compass;
    private static CompassGps compassGps;
    
    public static void initialize(EntityManagerFactory emfInstance) {
    	compass = new CompassConfiguration().setConnection("gae://index")
    										.setSetting(CompassEnvironment.ExecutorManager.EXECUTOR_MANAGER_TYPE, "disabled")
    										.addScan("br.com.yaw.entity")
    										.registerConverter("keyConverter", com.google.appengine.api.datastore.Key.class, (Converter) (new KeyConverter()))
    										.buildCompass();
    	
    	compassGps = new  SingleCompassGps(compass);
    	compassGps.addGpsDevice(new JpaGpsDevice("appengine", emfInstance));
    	compassGps.start();
    	//compassGps.index(); Está com problemas se tentarmos inicializar unto com o contexto, GAE geralmente retorna uma com.google.apphosting.api.DeadlineExceededException
    }
    
    public static Compass getCompass(){
    	return compass;
    }
    
    public static void index() {
    	compassGps.index();
    }
    
    public static void rebuildIndex() {
    	compassGps.index();
    }
    
}
