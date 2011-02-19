package br.com.promove.service;

import com.google.inject.AbstractModule;

/**
 * Cria o bind dos serviços para injetar
 * @author Rafael Nunes
 *
 */
public class ServiceInjector  extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(AvariaService.class).to(AvariaServiceImpl.class);
		bind(CadastroService.class).to(CadastroServiceImpl.class);
		bind(ExportacaoService.class).to(ExportacaoServiceImpl.class);
		bind(ImportacaoService.class).to(ImportacaoServiceImpl.class);
		
	}
}
