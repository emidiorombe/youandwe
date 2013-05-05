package br.com.promove.importacao;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.StatusAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoSinistro {
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");

	private CadastroService cadastroService;
	private AvariaService avariaService;
	
	private HashMap<Integer, Clima> climas;
	private HashMap<Integer, ExtensaoAvaria> extensoes;
	//private HashMap<String, TipoAvaria> tiposDescricao;
	private HashMap<Integer, TipoAvaria> tipos;
	//private HashMap<String, LocalAvaria> locaisDescricao;
	private HashMap<Integer, LocalAvaria> locais;
	private HashMap<Integer, OrigemAvaria> origens;
	
	public ImportacaoSinistro() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		avariaService = ServiceFactory.getService(AvariaService.class);
	}
	
	public void importar(List<String> csv, Usuario user, String nomeArquivo) throws PromoveException {
		loadClimas();
		loadExtensoes();
		loadTipos();
		loadLocais();
		loadOrigens();
		
		for (String linha : csv) {
			String[] campos = linha.replaceAll("\r", "; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ").split(";");
			
			String chassi = campos[10].trim();
			
			if (chassi.length() != 17) {
				continue;
			}

			//String[] tiposAvaria = campos[9].trim().replaceAll(" \\/ ", " \\| ").split("\\/");
			//String[] locaisAvaria = campos[13].trim().replaceAll(" \\/ ", " \\| ").split("\\/");

			//for (int cont = 0; cont < (tiposAvaria.length > locaisAvaria.length ? tiposAvaria.length : locaisAvaria.length); cont++) {
			Avaria av = new Avaria();
			
			try {
				av.setClima(climas.get(new Integer("4")));
				av.setOrigem(origens.get(new Integer("1000")));
				av.setUsuario(user);
				
				//String tipoAvaria = tiposAvaria[cont > tiposAvaria.length - 1 ? tiposAvaria.length - 1 : cont].trim().replaceAll(" \\| ", " \\/ ");
				//String localAvaria = locaisAvaria[cont > locaisAvaria.length - 1 ? locaisAvaria.length - 1 : cont].trim().replaceAll(" \\| ", " \\/ ");
				
				//av.setTipo(tiposDescricao.get(campos[9].trim()));
				//av.setTipo(tiposDescricao.get(tipoAvaria));
				av.setTipo(tipos.get(new Integer("1000")));
				
				//av.setLocal(locaisDescricao.get(campos[13].trim()));
				//av.setLocal(locaisDescricao.get(localAvaria));
				av.setLocal(locais.get(new Integer("1000")));
				
				av.setExtensao(extensoes.get(new Integer("0")));
				
				av.setChassiOriginal(chassi);
				av.setArquivo(nomeArquivo);
				av.setStatus(avariaService.getById(StatusAvaria.class, 4));
				
				av.setDnConcessionaria(new Integer(campos[4].trim()));
				av.setNomeConcessionaria(campos[7].trim());
				
				av.setNumeroSinistro(new Long(campos[0].trim()));
				av.setNotaFiscal(new Integer(campos[14].trim()));
				av.setNumeroCtrc(new Integer(campos[16].trim()));
				
				av.setDataLancamento(date_format.parse(campos[2].trim()));
				av.setDataSinistro(date_format.parse(campos[3].trim()));
				//av.setHora("00:00");

				//String msgErro = verificaInconsistencias(av, campos, tipoAvaria, localAvaria);
				String msgErro = "";
				
				List<Veiculo> veiculos = null;
				
				veiculos = cadastroService.buscarVeiculosPorChassi(chassi);
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) {
					msgErro += "Veiculo " + chassi + " não existe!;";
					InconsistenciaAvaria inc = avariaService.salvarInconsistenciaImportAvaria(av, msgErro, null);
				} else {
					av.setVeiculo(veiculos.get(0));
					
					if (!msgErro.isEmpty()) {
						throw new Exception(msgErro);
					}
					
					if(avariaService.buscarAvariaDuplicadaPorData(veiculos, av).size() > 0) {
						throw new Exception("Existe vistoria em outra data!;");
					}
					
					if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
						//Ja existe essa avaria
						continue;
					}
	
					avariaService.salvarAvaria(av, true);
				}
			}catch(Exception e) {
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage(), null);
			}
			//}
		}
	}

	private void loadClimas() throws PromoveException {
		climas = new HashMap<Integer, Clima>();
		List<Clima> lista = avariaService.buscarTodosClimas();
		for (Clima clima : lista) {
			climas.put(clima.getCodigo(), clima);
		}
	}
	
	private void loadExtensoes() throws PromoveException {
		extensoes = new HashMap<Integer, ExtensaoAvaria>();
		List<ExtensaoAvaria> lista = avariaService.buscarTodasExtensoesAvaria();
		for (ExtensaoAvaria extensao : lista) {
			extensoes.put(extensao.getCodigo(), extensao);
		}
	}
	
	private void loadTipos() throws PromoveException {
		//tiposDescricao = new HashMap<String, TipoAvaria>();
		tipos = new HashMap<Integer, TipoAvaria>();
		List<TipoAvaria> lista = avariaService.buscarTodosTipoAvaria();
		for (TipoAvaria tipo : lista) {
			//if (tipo.getDescricaoSeguradora() != null && !tipo.getDescricaoSeguradora().isEmpty()) {
			//	for (String descricao : tipo.getDescricaoSeguradora().split(";")) {
			//		tiposDescricao.put(descricao, tipo);
			//	}
			//}
			tipos.put(tipo.getCodigo(), tipo);
		}
	}
	
	private void loadLocais() throws PromoveException {
		//locaisDescricao = new HashMap<String, LocalAvaria>();
		locais = new HashMap<Integer, LocalAvaria>();
		List<LocalAvaria> lista = avariaService.buscarTodosLocaisAvaria();
		for (LocalAvaria local : lista) {
			//if (local.getDescricaoSeguradora() != null && !local.getDescricaoSeguradora().isEmpty()) {
			//	for (String descricao : local.getDescricaoSeguradora().split(";")) {
			//		locaisDescricao.put(descricao, local);
			//	}
			//}
			locais.put(local.getCodigo(), local);
		}
	}
	
	private void loadOrigens() throws PromoveException {
		origens = new HashMap<Integer, OrigemAvaria>();
		List<OrigemAvaria> lista = avariaService.buscarTodasOrigensAvaria();
		for (OrigemAvaria origem : lista) {
			origens.put(origem.getCodigo(), origem);
		}
	}
	
	private String verificaInconsistencias(Avaria av, String[] campos, String tipoAvaria, String localAvaria) {
		String msgErro = "";
		
		if (av.getTipo() == null || av.getTipo().getCodigo() == null)
			msgErro += "Tipo " + tipoAvaria + " não existe;";
		
		if (av.getLocal() == null || av.getLocal().getCodigo() == null)
			msgErro += "Local " + localAvaria + " não existe;";
		
		return msgErro;
	}
}
