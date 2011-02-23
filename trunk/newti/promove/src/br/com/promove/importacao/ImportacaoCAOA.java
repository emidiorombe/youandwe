package br.com.promove.importacao;

import java.util.HashMap;
import java.util.List;

import antlr.collections.impl.LList;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoCAOA {
	private CadastroService cadastro;
	private HashMap<String, Modelo> modelos;
	
	public ImportacaoCAOA() {
		cadastro = ServiceFactory.getService(CadastroService.class);
	}
	
	public void importar(List<String> csv) throws PromoveException{
		try {
			loadModelos();
			for (String linha : csv) {
				String[] campos = linha.split(";");
				Veiculo v = new Veiculo();
				v.setCodigoExterno(campos[0]);
				v.setModelo(modelos.get(campos[1]));
				v.setChassi(campos[2]);
				
				cadastro.salvarVeiculo(v);
				
			}
		}catch(Exception e) {
			throw new PromoveException();
		}
				
	}

	private void loadModelos() throws PromoveException {
		modelos = new HashMap<String, Modelo>();
		List<Modelo> lista = cadastro.buscarTodosModelos();
		for (Modelo modelo : lista) {
			modelos.put(modelo.getDescricao(), modelo);
		}
		
	}

}
