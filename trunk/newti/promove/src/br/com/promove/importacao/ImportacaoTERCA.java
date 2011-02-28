package br.com.promove.importacao;

import java.util.HashMap;
import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoTERCA {

	private CadastroService cadastro;
	private HashMap<String, Modelo> modelos;
	private HashMap<String, Cor> cores;
	
	public ImportacaoTERCA() {
		cadastro = ServiceFactory.getService(CadastroService.class);
	}
	
	public void importar(List<String> csv) throws PromoveException{
		loadModelos();
		loadCores();
		for (String linha : csv) {
			String[] campos = linha.split(";");
			Veiculo v = new Veiculo();
			try {
				v.setChassi(campos[0]);
				v.setCor(cadastro.getById(Cor.class, new Integer(97)));
				
				if(!modelos.containsKey(campos[1])) {
					throw new Exception("Modelo " + campos[1] + "não existe.");
				}
				
				cadastro.salvarVeiculo(v, true);
				
			}catch(Exception e) {
				cadastro.salvarInconsistenciaVeiculo(v, e.getMessage(), 2);
			}
		}
	}

	private void loadModelos() throws PromoveException {
		modelos = new HashMap<String, Modelo>();
		List<Modelo> lista = cadastro.buscarTodosModelos();
		for (Modelo modelo : lista) {
			modelos.put(modelo.getDescricao(), modelo);
		}
		
	}
	
	private void loadCores() throws PromoveException {
		cores = new HashMap<String, Cor>();
		List<Cor> lista = cadastro.buscarTodasCores();
		for (Cor cor : lista) {
			if(cor.getCodigoExterno() != null && !cor.getCodigoExterno().isEmpty())
				cores.put(cor.getCodigoExterno(), cor);
		}
		
	}

}
