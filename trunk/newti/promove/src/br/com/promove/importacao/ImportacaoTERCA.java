package br.com.promove.importacao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.TipoVeiculo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoTERCA {

	private CadastroService cadastroService;
	private HashMap<String, Modelo> modelos;
	
	public ImportacaoTERCA() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
	}
	
	public void importar(List<String> csv, Date data) throws PromoveException{
		loadModelos();
		for (String linha : csv) {
			String[] campos = linha.replaceAll("\r", "; ; ").split(";");
			Veiculo v = new Veiculo();
			try {
				if(campos[0].length() != 17) {
					continue;
				}
				
				v.setChassi(campos[0].toUpperCase());
				v.setCor(cadastroService.getById(Cor.class, new Integer(97)));
				v.setTipo(cadastroService.getById(TipoVeiculo.class, 1));
				if (data != null) {
					v.setDataCadastro(data);
				}
				
				if(campos[2] != null && !campos[2].trim().equals("")) {
					v.setNavio(campos[2]);
					v.setTipo(cadastroService.getById(TipoVeiculo.class, 2));
					
					if(campos[3] != null && !campos[3].trim().equals("")) {
						String valor = campos[3];
						String separadorDecimal = valor.substring(valor.length() - 3, valor.length() - 2);
						if (separadorDecimal.equals(",")) {
							valor = valor.replaceAll("\\.", "");
							valor = valor.replaceAll(",", "\\.");
						} else {
							valor = valor.replaceAll(",", "");
						}
						v.setValorMercadoria(new Double(valor));
					}
				}
				
				if(!modelos.containsKey(campos[1])) {
					throw new Exception("Modelo " + campos[1] + " não existe;");
				}else {
					v.setModelo(modelos.get(campos[1]));
				}
				
				cadastroService.salvarVeiculo(v, true);
				
			}catch(IllegalArgumentException ie) {
				ie.printStackTrace();
			}catch(Exception e) {
				cadastroService.salvarInconsistenciaVeiculo(v, e.getMessage());
			}
		}
	}

	private void loadModelos() throws PromoveException {
		modelos = new HashMap<String, Modelo>();
		List<Modelo> lista = cadastroService.buscarTodosModelos();
		for (Modelo modelo : lista) {
			modelos.put(modelo.getDescricao(), modelo);
		}
		
	}
	
}
