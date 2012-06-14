package br.com.promove.importacao;

import java.util.List;

import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoDeParaAvaria {

	private AvariaService avariaService;
	
	public ImportacaoDeParaAvaria() {
		avariaService = ServiceFactory.getService(AvariaService.class);
	}
	
	public void importar(List<String> csv) throws PromoveException{
		for (String linha : csv) {
			String[] campos = linha.replaceAll("\r", "; ").split(";");
			
			String chassiAntigo = campos[0];
			String chassiNovo = campos[1];
			
			System.out.println(chassiNovo);
			
			if(chassiAntigo.length() != 17) continue;
			if(chassiNovo.length() != 17) continue;

			for (InconsistenciaAvaria inc : avariaService.buscarInconsistenciaAvariaPorChassi(chassiAntigo)) {
				inc.setChassiInvalido(chassiNovo);
				avariaService.salvarAvariaDeInconsistencias(inc);
			}
		}
	}
}
