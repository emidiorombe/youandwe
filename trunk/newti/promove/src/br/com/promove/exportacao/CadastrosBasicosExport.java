package br.com.promove.exportacao;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Carreta;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.Frota;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.Motorista;
import br.com.promove.entity.NivelAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;

public class CadastrosBasicosExport {
	
	public static String gerarXmlExportacao(Map<String, List> listas, String novo) {
		Document xml = DocumentHelper.createDocument();
		
		if (novo.equals("1")) {
			Element root = xml.addElement("dados_pre_req"); //cadastros
			createUsuario(root, listas.get("usuario"));
			createPatio(root);
			createOrigemAvaria(root, listas.get("origem"));
			createLocalAvaria(root, listas.get("local"));
			createTipoAvaria(root, listas.get("tipo"));
			createClima(root, listas.get("clima"));
			createFrota(root, listas.get("frota"));
			createCarreta(root, listas.get("carreta"));
			createMotorista(root, listas.get("motorista"));
			//createExtensaoAvaria(root, listas.get("extensao"));
			//createNivelAvaria(root, listas.get("nivel"));
		} else {
			Element root = xml.addElement("dados_pre_req");
			createUsuario(root, listas.get("usuario"));
			createPatio(root);
			createOldOrigem(root, listas.get("origem"));
			createOldLocal(root, listas.get("local"));
			createOldTipo(root, listas.get("tipo"));
			createClima(root, listas.get("clima"));
		}
		
		return xml.asXML();
	}

	private static void createClima(Element root, List<Clima> list) {
		for (Clima clima : list) {
			Element el_clima = root.addElement("climat"); // clima
			el_clima.addAttribute("codigo", clima.getCodigo().toString());
			el_clima.addElement("descricao").addText(clima.getDescricao());
		}
	}

	private static void createExtensaoAvaria(Element root, List<ExtensaoAvaria> list) {
		for (ExtensaoAvaria ext : list) {
			Element el_origem = root.addElement("extensao_avaria");
			el_origem.addAttribute("codigo", ext.getId().toString());
			el_origem.addElement("descricao").addText(ext.getDescricao());
		}
	}

	private static void createLocalAvaria(Element root, List<LocalAvaria> list) {
		for (LocalAvaria loc : list) {
			if (loc.getCodigo() != 300 && loc.getCodigo() != 1000) {
				//TODO remover 300 fixo
				Element el_local = root.addElement("local_avaria"); // peca
				el_local.addAttribute("codigo", loc.getCodigo().toString());
				el_local.addElement("descricao").addText(loc.getDescricao());
			}
		}
	}

	private static void createNivelAvaria(Element root, List<NivelAvaria> list) {
		for (NivelAvaria niv : list) {
			Element el_origem = root.addElement("nivel_avaria");
			el_origem.addAttribute("codigo", niv.getId().toString());
			el_origem.addElement("descricao").addText(niv.getNome());
		}
	}

	private static void createOrigemAvaria(Element root, List<OrigemAvaria> list) {
		for (OrigemAvaria org : list) {
			Element el_origem = root.addElement("origem_avaria"); // local_vistoria
			el_origem.addAttribute("codigo", org.getCodigo().toString());
			el_origem.addElement("descricao").addText(org.getDescricao());
			el_origem.addElement("filial").addText(org.getFilial().getCodigo().toString());
		}
	}

	private static void createTipoAvaria(Element root, List<TipoAvaria> list) { 
		for (TipoAvaria tipo : list) {
			//if (tipo.getMovimentacao() == false) {
			if (tipo.getCodigo() != 300 && tipo.getCodigo() != 1000) {
				Element el_tipo = root.addElement("tipo_avaria");
				el_tipo.addAttribute("codigo", tipo.getCodigo().toString());
				el_tipo.addElement("descricao").addText(tipo.getDescricao());
			}
		}
	}

	private static void createUsuario(Element root, List<Usuario> list) {
		for (Usuario us : list) {
			Element el_user = root.addElement("usuario");
			el_user.addAttribute("codigo", us.getCodigo().toString());
			el_user.addElement("nome").addText(us.getNome());
			el_user.addElement("senha").addText(us.getSenha());
			el_user.addElement("patio").addText("1");
			el_user.addElement("filial").addText(us.getFilial().getCodigo().toString());
		}
	}
	
	private static void createPatio(Element root) {
		Element el_user = root.addElement("patio");
		el_user.addAttribute("codigo", "1");
		el_user.addElement("descricao").addText("PATIO");
	}

	private static void createFrota(Element root, List<Frota> list) {
		for (Frota fr : list) {
			if (fr.getAtivo()) {
				Element el_user = root.addElement("frota");
				el_user.addAttribute("codigo", fr.getCodigo());
				el_user.addElement("placa").addText(fr.getPlaca());
			}
		}
	}

	private static void createCarreta(Element root, List<Carreta> list) {
		for (Carreta ca : list) {
			if (ca.getAtivo()) {
				Element el_user = root.addElement("carreta");
				el_user.addAttribute("codigo", ca.getCodigo());
				el_user.addElement("placa").addText(ca.getPlaca());
			}
		}
	}

	private static void createMotorista(Element root, List<Motorista> list) {
		for (Motorista mo : list) {
			if (mo.getAtivo()) {
				Element el_user = root.addElement("motorista");
				el_user.addAttribute("codigo", mo.getCodigo().toString());
				el_user.addElement("nome").addText(mo.getNome());
				el_user.addElement("cnh").addText(mo.getCnh());
				el_user.addElement("cpf").addText(mo.getCpf());
				el_user.addElement("rg").addText(mo.getRg());
				if (mo.getFrota() == null) {
					el_user.addElement("frota");
				} else {
					el_user.addElement("frota").addText(mo.getFrota().getCodigo());
				}
				if (mo.getCarreta() == null) {
					el_user.addElement("carreta");
				} else {
					el_user.addElement("carreta").addText(mo.getCarreta().getCodigo());
				}
			}
		}
	}

	private static void createOldLocal(Element root, List<LocalAvaria> list) {
		for (LocalAvaria loc : list) {
			Element el_local = root.addElement("local_avaria");
			el_local.addAttribute("codigo", loc.getCodigo().toString());
			el_local.addElement("descricao").addText(loc.getDescricao());
		}
	}

	private static void createOldOrigem(Element root, List<OrigemAvaria> list) {
		for (OrigemAvaria org : list) {
			Element el_origem = root.addElement("origem_avaria");
			el_origem.addAttribute("codigo", org.getCodigo().toString());
			el_origem.addElement("descricao").addText(org.getDescricao());
		}
	}

	private static void createOldTipo(Element root, List<TipoAvaria> list) { 
		for (TipoAvaria tipo : list) {
			Element el_tipo = root.addElement("tipo_avaria");
			el_tipo.addAttribute("codigo", tipo.getCodigo().toString());
			el_tipo.addElement("descricao").addText(tipo.getDescricao());
		}
	}
}
