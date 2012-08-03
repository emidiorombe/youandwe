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
	private HashMap<String, TipoAvaria> tiposDescricao;
	private HashMap<String, LocalAvaria> locaisDescricao;
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
			Avaria av = new Avaria();
			
			String chassi = campos[10].trim();
			
			if (chassi.length() != 17) {
				continue;
			}

			String[] tiposAvaria = campos[9].trim().replaceAll(" \\/ ", " \\| ").split("\\/");
			String[] locaisAvaria = campos[13].trim().replaceAll(" \\/ ", " \\| ").split("\\/");

			for (int cont = 0; cont < (tiposAvaria.length > locaisAvaria.length ? tiposAvaria.length : locaisAvaria.length); cont++) {
				
				try {
					av.setClima(climas.get(new Integer("4")));
					av.setOrigem(origens.get(new Integer("1000")));
					av.setUsuario(user);
					
					String tipoAvaria = tiposAvaria[cont > tiposAvaria.length - 1 ? tiposAvaria.length - 1 : cont].trim().replaceAll(" \\| ", " \\/ ");
					String localAvaria = locaisAvaria[cont > locaisAvaria.length - 1 ? locaisAvaria.length - 1 : cont].trim().replaceAll(" \\| ", " \\/ ");
					
					//av.setTipo(tiposDescricao.get(campos[9].trim()));
					av.setTipo(tiposDescricao.get(tipoAvaria));
					//av.setLocal(locaisDescricao.get(campos[13].trim()));
					av.setLocal(locaisDescricao.get(localAvaria));
					
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
	
					String msgErro = verificaInconsistencias(av, campos, tipoAvaria, localAvaria);
					
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
						if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
							//Ja existe essa avaria
							continue;
						}
		
						avariaService.salvarAvaria(av, true);
					}
				}catch(Exception e) {
					avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage(), null);
				}
			}
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
		tiposDescricao = new HashMap<String, TipoAvaria>();
		List<TipoAvaria> lista = avariaService.buscarTodosTipoAvaria();
		for (TipoAvaria tipo : lista) {
			tiposDescricao.put(tipo.getDescricaoSeguradora(), tipo);
		}
	}
	
	private void loadLocais() throws PromoveException {
		locaisDescricao = new HashMap<String, LocalAvaria>();
		List<LocalAvaria> lista = avariaService.buscarTodosLocaisAvaria();
		for (LocalAvaria local : lista) {
			locaisDescricao.put(local.getDescricaoSeguradora(), local);
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
		
		if (av.getTipo() == null)
			msgErro += "Tipo " + tipoAvaria + " não existe;";
		
		if (av.getLocal() == null)
			msgErro += "Local " + localAvaria + " não existe;";
		
		return msgErro;
	}
}
