package br.com.promove.exportacao;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Clima;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;

public class CadastrosBasicosExport {
	
	public static String gerarXmlExportacao(Map<String, List> listas) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("dados_pre_req");
		
		createTagUsuario(root, listas.get("usuario"));
		createTagPatio(root);
		createTagOrigem(root, listas.get("origem"));
		createLocalAvaria(root, listas.get("local"));
		createTipoAvaria(root, listas.get("tipo"));
		createClima(root, listas.get("clima"));
		return xml.asXML();
	}

	private static void createClima(Element root, List<Clima> list) {
		for (Clima clima : list) {
			Element el_clima = root.addElement("climat");
			el_clima.addAttribute("codigo", clima.getCodigo().toString());
			el_clima.addElement("descricao").addText(clima.getDescricao());
		}
		
	}

	private static void createTipoAvaria(Element root, List<TipoAvaria> list) { 
		for (TipoAvaria tipo : list) {
			Element el_tipo = root.addElement("tipo_avaria");
			el_tipo.addAttribute("codigo", tipo.getCodigo().toString());
			el_tipo.addElement("descricao").addText(tipo.getDescricao());
		}
	}

	private static void createLocalAvaria(Element root, List<LocalAvaria> list) {
		for (LocalAvaria loc : list) {
			Element el_local = root.addElement("local_avaria");
			el_local.addAttribute("codigo", loc.getCodigo().toString());
			el_local.addElement("descricao").addText(loc.getDescricao());
		}
		
	}

	private static void createTagOrigem(Element root, List<OrigemAvaria> list) {
		for (OrigemAvaria org : list) {
			Element el_origem = root.addElement("origem_avaria");
			el_origem.addAttribute("codigo", org.getCodigo().toString());
			el_origem.addElement("descricao").addText(org.getDescricao());
		} 
		
	}

	private static void createTagPatio(Element root) {
		Element el_user = root.addElement("patio");
		el_user.addAttribute("codigo", "1");
		el_user.addElement("descricao").addText("PATIO");
	}

	private static void createTagUsuario(Element root, List<Usuario> list) {
		for (Usuario us : list) {
			Element el_user = root.addElement("usuario");
			el_user.addAttribute("codigo", us.getCodigo().toString());
			el_user.addElement("nome").addText(us.getNome());
			el_user.addElement("senha").addText(us.getSenha());
			el_user.addElement("patio").addText("1");
			el_user.addElement("filial").addText(us.getFilial().getCodigo().toString());
		}
	}
	
}
