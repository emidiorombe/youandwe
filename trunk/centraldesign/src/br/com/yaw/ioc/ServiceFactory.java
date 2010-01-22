package br.com.yaw.ioc;

import java.util.Hashtable;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Create Services to inject
 * @author Rafael Nunes
 *
 */
public class ServiceFactory {
	private static Map<String, Object> serviceCache;
	private static Injector injector;
	
	static {
		serviceCache = new Hashtable<String, Object>();
		injector = Guice.createInjector(new IoCBinder());
	}
	
	public static <T>T getService(Class<T> service){
		if(!serviceCache.containsKey(service.getName())) {
			Object serviceImpl =  injector.getInstance(service);
			serviceCache.put(service.getName(), serviceImpl);
		}
		
		return (T)serviceCache.get(service.getName());
	}
}
