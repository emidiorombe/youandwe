package br.com.promove.importacao;

import java.util.HashMap;
import java.util.List;

import br.com.promove.entity.Modelo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoTERCA {

	private CadastroService cadastro;
	private HashMap<String, Modelo> modelos;
	
	public ImportacaoTERCA() {
		cadastro = ServiceFactory.getService(CadastroService.class);
	}
	
	public void importar(List<String> csv) throws PromoveException{
		try {
			loadModelos();
			for (String linha : csv) {
				String[] campos = linha.split(";");
				Veiculo v = new Veiculo();
				v.setModelo(modelos.get(campos[1]));
				v.setChassi(campos[0]);
				
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
