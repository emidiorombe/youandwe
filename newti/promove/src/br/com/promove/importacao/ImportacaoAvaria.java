package br.com.promove.importacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.NivelAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

public class ImportacaoAvaria {
	private String xmlContent;
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat date_format_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private HashMap<Integer, Clima> climas;
	private HashMap<Integer, ExtensaoAvaria> extensoes;
	private HashMap<Integer, TipoAvaria> tipos;
	private HashMap<Integer, LocalAvaria> locais;
	private HashMap<Integer, OrigemAvaria> origens;
	private HashMap<String, OrigemAvaria> origensTipoFilial;
	private HashMap<Integer, Usuario> usuarios;

	public ImportacaoAvaria() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
	}

	public ImportacaoAvaria(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public void importar(String xml) throws DocumentException, ParseException, PromoveException {
		loadClimas();
		loadExtensoes();
		loadTipos();
		loadLocais();
		loadOrigens();
		loadOrigensTipoFilial();
		loadUsuarios();

		Document doc = DocumentHelper.parseText(xml);
		importTagVistoria(doc);
		importTagAvaria(doc);
		importTagMovimento(doc);
	}

	private void importTagVistoria(Document doc) throws ParseException, PromoveException {
		List<Element> avarias = doc.selectNodes("//dados_coletados/vistorias");
		for (Element node_av : avarias) {
			Avaria av = new Avaria();
			
			try {
				av.setClima(climas.get(new Integer(node_av.element("concli").getText())));
				av.setOrigem(origens.get(new Integer(node_av.element("origem").getText())));
				// TODO temporario
				if (av.getOrigem() == null)
					av.setOrigem(origens.get(new Integer(node_av.element("origem").getText() + "0")));
				av.setUsuario(usuarios.get(new Integer(node_av.element("usuario").getText())));
				
				av.setTipo(tipos.get(new Integer(node_av.element("tipo").getText())));
				av.setLocal(locais.get(new Integer(node_av.element("local").getText())));
				av.setExtensao(extensoes.get(new Integer(node_av.element("gravid").getText())));
				// TODO fixo
				if (!node_av.element("tipo").getText().equals("300"))
					av.setNivel(avariaService.getById(NivelAvaria.class, new Integer(node_av.element("nivel").getText())));
				if (!node_av.element("obs").getText().trim().replaceAll("\r", "").isEmpty())
					av.setObservacao(node_av.element("obs").getText());

				av.setDataLancamento(date_format.parse(node_av.element("data").getText()));
				av.setHora(node_av.element("hora").getText());

				String msgErro = verificaInconsistencias(av, node_av, "vistorias");
				
				String chassi = node_av.element("chassi").getText();
				List<Veiculo> veiculos = null;
				
				if(chassi.contains("000000000")) {
					chassi = chassi.replace("000000000", "");
					
					veiculos = cadastroService.buscarVeiculosPorModeloFZData(chassi, av.getDataLancamento());
				}else {
					veiculos = cadastroService.buscarVeiculosPorChassi(chassi);
				}
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) {
					msgErro += "Veiculo " + node_av.element("chassi").getText() + " não existe!;";
					InconsistenciaAvaria inc = avariaService.salvarInconsistenciaImportAvaria(av, msgErro);
					
					Element node_fotos = ((Element)node_av).element("fotos");
					Iterator it = node_fotos.elementIterator();
					List<FotoAvaria> fotos = new ArrayList<FotoAvaria>();
					while (it.hasNext()) {
						FotoAvaria foto = new FotoAvaria();
						foto.setInconsisctencia(inc.getId());
						Element node_arq = (Element) it.next();
						foto.setNome(node_arq.attributeValue("nome"));
						avariaService.salvarFotoAvaria(foto, true);
					}
					
				}else {
					if (!msgErro.isEmpty()) {
						throw new Exception(msgErro);
					}
					
					av.setVeiculo(veiculos.get(0));
					
					if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
						//Ja existe essa avaria
						continue;
					}
	
					avariaService.salvarAvaria(av, true);
		
					Element node_fotos = ((Element)node_av).element("fotos");
					Iterator it = node_fotos.elementIterator();
					while (it.hasNext()) {
						FotoAvaria foto = new FotoAvaria();
						foto.setAvaria(av);
						Element node_arq = (Element) it.next();
						foto.setNome(node_arq.attributeValue("nome"));
						avariaService.salvarFotoAvaria(foto, true);
					}
				}
			}catch(Exception e) {
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage());
			}
		}
	}

	private void importTagAvaria(Document doc) throws ParseException, PromoveException {
		List<Element> avarias = doc.selectNodes("//dados_coletados/avarias");
		for (Element node_av : avarias) {
			Avaria av = new Avaria();
			
			try {
				String ext = node_av.element("gravid").getText();
				ext  = ext.equals("L") ? "9" : (ext.equals("G") ? "10" : ext); //(ext.equals("0") ? "9999": ext));
				//av.setClima(avariaService.getById(Clima.class, new Integer(node_av.element("concli").getText())));
				//av.setExtensao(avariaService.getById(ExtensaoAvaria.class, new Integer(ext)));
				//av.setTipo(avariaService.getById(TipoAvaria.class, new Integer(node_av.element("tipo").getText())));
				//av.setLocal(avariaService.getById(LocalAvaria.class, new Integer(node_av.element("local").getText())));
				//av.setOrigem(avariaService.getById(OrigemAvaria.class,new Integer(node_av.element("origem").getText())));
				//av.setUsuario(avariaService.getById(Usuario.class, new Integer(node_av.element("usuario").getText())));
				av.setClima(climas.get(new Integer(node_av.element("concli").getText())));
				av.setExtensao(extensoes.get(new Integer(ext)));
				av.setTipo(tipos.get(new Integer(node_av.element("tipo").getText())));
				av.setLocal(locais.get(new Integer(node_av.element("local").getText())));
				av.setOrigem(origens.get(new Integer(node_av.element("origem").getText())));
				// TODO temporario
				if (av.getOrigem() == null)
					av.setOrigem(origens.get(new Integer(node_av.element("origem").getText() + "0")));
				av.setUsuario(usuarios.get(new Integer(node_av.element("usuario").getText())));
				av.setDataLancamento(date_format.parse(node_av.element("data").getText()));
				//av.setHora(node_av.element("hora").getText());
				av.setObservacao(node_av.element("obs").getText());
				
				String msgErro = verificaInconsistencias(av, node_av, "avarias");
				
				String chassi = node_av.element("chassi").getText();
				List<Veiculo> veiculos = null;
				
				if(chassi.contains("000000000")) {
					chassi = chassi.replace("000000000", "");
					
					veiculos = cadastroService.buscarVeiculosPorModeloFZData(chassi, av.getDataLancamento());
				}else {
					veiculos = cadastroService.buscarVeiculosPorChassi(chassi);
				}
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) {
					msgErro += "Veiculo " + node_av.element("chassi").getText() + " não existe!;";
					InconsistenciaAvaria inc = avariaService.salvarInconsistenciaImportAvaria(av, msgErro);
					
					Element node_fotos = ((Element)node_av).element("fotos");
					Iterator it = node_fotos.elementIterator();
					List<FotoAvaria> fotos = new ArrayList<FotoAvaria>();
					while (it.hasNext()) {
						FotoAvaria foto = new FotoAvaria();
						foto.setInconsisctencia(inc.getId());
						Element node_arq = (Element) it.next();
						foto.setNome(node_arq.attributeValue("nome"));
						avariaService.salvarFotoAvaria(foto, true);
					}
					
				}else {
					if (!msgErro.isEmpty()) {
						throw new Exception(msgErro);
					}
					
					av.setVeiculo(veiculos.get(0));
					
					if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
						//Ja existe essa avaria
						continue;
					}
	
					avariaService.salvarAvaria(av, true);
		
					Element node_fotos = ((Element)node_av).element("fotos");
					Iterator it = node_fotos.elementIterator();
					while (it.hasNext()) {
						FotoAvaria foto = new FotoAvaria();
						foto.setAvaria(av);
						Element node_arq = (Element) it.next();
						foto.setNome(node_arq.attributeValue("nome"));
						avariaService.salvarFotoAvaria(foto, true);
					}
				}
			}catch(Exception e) {
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage());
			}
		}
	}

	private void importTagMovimento(Document doc) throws ParseException, PromoveException {
		List<Element> avarias = doc.selectNodes("//dados_coletados/movto");
		for (Element node_av : avarias) {
			Avaria av = new Avaria();
			try {
				//av.setClima(avariaService.getById(Clima.class, new Integer("4")));
				//av.setExtensao(avariaService.getById(ExtensaoAvaria.class, new Integer("9999")));
				//av.setTipo(avariaService.getById(TipoAvaria.class, new Integer("300")));
				//av.setLocal(avariaService.getById(LocalAvaria.class, new Integer("300")));
				//av.setOrigem(avariaService.buscarOrigemPorTipoEFilial(node_av.element("tipo").getText(), node_av.element("filial").getText()));
				//av.setUsuario(avariaService.getById(Usuario.class, new Integer(node_av.element("usuario").getText())));
				av.setClima(climas.get(new Integer("4")));
				av.setExtensao(extensoes.get(new Integer("0")));
				av.setTipo(tipos.get(new Integer("300")));
				av.setLocal(locais.get(new Integer("300")));
				// TODO remover fixos
				av.setOrigem(origensTipoFilial.get(node_av.element("filial").getText() + "_" + node_av.element("tipo").getText()));
				av.setUsuario(usuarios.get(new Integer(node_av.element("usuario").getText())));
				av.setDataLancamento(date_format.parse(node_av.element("data").getText()));
				av.setHora(node_av.element("hora").getText());
				
				String msgErro = verificaInconsistencias(av, node_av, "movto");
				
				String chassi = node_av.element("chassi").getText();
				List<Veiculo> veiculos = null;
				
				if(chassi.contains("000000000")) {
					chassi = chassi.replace("000000000", "");
					
					veiculos = cadastroService.buscarVeiculosPorModeloFZData(chassi, av.getDataLancamento());
				}else {
					veiculos = cadastroService.buscarVeiculosPorChassi(chassi);
				}
				
				//Se não existir o veículo, gravar a inconsistência
				if(veiculos.size() == 0) { 
					msgErro += "Veiculo " + node_av.element("chassi").getText() + " não existe!;";
				}else {
					av.setVeiculo(veiculos.get(0));
					
					if(avariaService.buscarAvariaDuplicadaPorFiltros(veiculos, av).size() > 0) {
						//Ja existe essa avaria
						continue;
					}
					avariaService.salvarAvaria(av, true);
				}
				
				if (!msgErro.isEmpty()) {
					throw new Exception(msgErro);
				}
			}catch(Exception e) {
				avariaService.salvarInconsistenciaImportAvaria(av, e.getMessage());
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
		tipos = new HashMap<Integer, TipoAvaria>();
		List<TipoAvaria> lista = avariaService.buscarTodosTipoAvaria();
		for (TipoAvaria tipo : lista) {
			tipos.put(tipo.getCodigo(), tipo);
		}
	}
	
	private void loadLocais() throws PromoveException {
		locais = new HashMap<Integer, LocalAvaria>();
		List<LocalAvaria> lista = avariaService.buscarTodosLocaisAvaria();
		for (LocalAvaria local : lista) {
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
	
	private void loadOrigensTipoFilial() throws PromoveException {
		origensTipoFilial = new HashMap<String, OrigemAvaria>();
		List<OrigemAvaria> lista = avariaService.buscarTodasOrigensAvaria();
		for (OrigemAvaria origem : lista) {
			if (origem.getFilial() != null && origem.getTipo() != null) {
				origensTipoFilial.put(origem.getFilial().getCodigo() + "_" + origem.getTipo(), origem);
			}
		}
	}
	
	private void loadUsuarios() throws PromoveException {
		usuarios = new HashMap<Integer, Usuario>();
		List<Usuario> lista = cadastroService.buscarTodosUsuarios();
		for (Usuario usuario : lista) {
			usuarios.put(usuario.getCodigo(), usuario);
		}
	}

	private String verificaInconsistencias(Avaria av, Element node_av, String tipo) {
		String msgErro = "";
		
		if (av.getClima() == null)
			msgErro += "Clima " + node_av.element("concli").getText() + " não existe;";

		if (av.getExtensao() == null)
			msgErro += "Extensao " + node_av.element("gravid").getText() + " não existe;";

		if (av.getTipo() == null) {
			msgErro += "Tipo " + node_av.element("tipo").getText() + " não existe;";
			if (tipo == "vistorias" && av.getNivel() == null && !av.getTipo().getMovimentacao().equals(true))
				msgErro += "Nível " + node_av.element("nivel").getText() + " não existe;";
		}
		
		if (av.getLocal() == null)
			msgErro += "Local " + node_av.element("local").getText() + " não existe;";
		
		if (tipo == "avarias" && av.getOrigem() == null)
			msgErro += "Origem " + node_av.element("origem").getText() + " não existe;";
		
		if (tipo == "movto" && av.getOrigem() == null)
			msgErro += "Origem filial " + node_av.element("filial").getText() + " / tipo " + node_av.element("tipo").getText() + " não existe;";
		
		if (av.getUsuario() == null)
			msgErro += "Usuario " + node_av.element("usuario").getText() + " não existe;";
		
			
		return msgErro;
	}

}
