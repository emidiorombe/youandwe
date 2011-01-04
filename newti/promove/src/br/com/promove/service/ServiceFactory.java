package br.com.promove.service;

import com.google.inject.Injector;

/**
 * Classe responsável por injetar os serviços
 * @author Rafael Nunes
 *
 */
public class ServiceFactory{
	private ServiceFactory(){}
	private static Injector injector;
	
	public static <T>T getService(Class<T> type){
		return injector.getInstance(type);
	}

	public static void setInjector(Injector inj) {
		injector = inj;
	}

}
