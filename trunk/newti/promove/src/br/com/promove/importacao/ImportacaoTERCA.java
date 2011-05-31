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
	
	public ImportacaoTERCA() {
		cadastro = ServiceFactory.getService(CadastroService.class);
	}
	
	public void importar(List<String> csv) throws PromoveException{
		loadModelos();
		for (String linha : csv) {
			String[] campos = linha.replaceAll("\r", "; ").split(";");
			Veiculo v = new Veiculo();
			try {
				if(campos[0].length() < 17)
					continue;
				v.setChassi(campos[0]);
				v.setCor(cadastro.getById(Cor.class, new Integer(97)));
				v.setTipo(1);
				
				if(campos[2] != null && !campos[2].trim().equals("")) {
					v.setNavio(campos[2]);
					v.setTipo(2);
				}
				
				if(!modelos.containsKey(campos[1])) {
					throw new Exception("Modelo " + campos[1] + " não existe;");
				}else {
					v.setModelo(modelos.get(campos[1]));
				}
				
				cadastro.salvarVeiculo(v, true);
				
			}catch(IllegalArgumentException ie) {
				ie.printStackTrace();
			}catch(Exception e) {
				cadastro.salvarInconsistenciaVeiculo(v, e.getMessage());
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
	
}
