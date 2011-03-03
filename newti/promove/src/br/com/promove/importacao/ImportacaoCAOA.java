package br.com.promove.importacao;

import java.util.HashMap;
import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoCAOA {
	private CadastroService cadastro;
	private HashMap<String, Modelo> modelos;
	private HashMap<String, Cor> cores;
	
	public ImportacaoCAOA() {
		cadastro = ServiceFactory.getService(CadastroService.class);
	}
	
	public void importar(List<String> csv) throws PromoveException{
		loadModelos();
		loadCores();
		for (String linha : csv) {
			Veiculo v = new Veiculo();
			try {
				String[] campos = linha.split(";");
				String msgErro = "";
				boolean temErro = false;
				
				if(campos.length != 3 || campos[2].length() < 17)
					continue;
				
				v.setCodigoInterno(campos[0]);
				v.setChassi(campos[2].substring(0, 17));
				
				if(!cores.containsKey(campos[0].substring(campos[0].length()-2))) {
					temErro = true;
					msgErro += "Cor " + campos[0].substring(campos[0].length()-2) + " não existe!; ";
				}else {
					v.setCor(cores.get(campos[0].substring(campos[0].length()-2)));
				}
				
				if(!modelos.containsKey(campos[0].substring(0, 5))) {
					temErro = true;
					msgErro += "Modelo " + campos[0].substring(0, 5) + " não existe!; ";
				}else {
					v.setModelo(modelos.get(campos[0].substring(0, 5)));
				}
				
				if(temErro) {
					throw new Exception(msgErro);
				}
				
				cadastro.salvarVeiculo(v, true);
				
			}catch(IllegalArgumentException ie) {
				ie.printStackTrace();
			}catch(Exception e) {
				cadastro.salvarInconsistenciaVeiculo(v, e.getMessage(), 1);
			}
		}
	}

	private void loadModelos() throws PromoveException {
		modelos = new HashMap<String, Modelo>();
		List<Modelo> lista = cadastro.buscarTodosModelos();
		for (Modelo modelo : lista) {
			if(modelo.getCodigoExternoNacional() != null && !modelo.getCodigoExternoNacional().isEmpty())
				modelos.put(modelo.getCodigoExternoNacional(), modelo);
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
