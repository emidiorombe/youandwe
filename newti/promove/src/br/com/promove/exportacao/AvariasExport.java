package br.com.promove.exportacao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Usuario;

public class AvariasExport {
	
	//public static String gerarXmlExportacao(Map<String, List> listas) {
	public static String gerarXmlExportacao(List <Avaria> avarias) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("dados_coletados");
		
		//createTagAvarias(root, listas.get("avarias"));
		createTagAvarias(root, avarias);
		
		return xml.asXML();
	}
	
	private static void createTagAvarias(Element root, List <Avaria> list) {
		for (Avaria avaria : list) {
			Element el_avaria = root.addElement("avarias");
			el_avaria.addElement("chassi").addText(avaria.getVeiculo().getChassi());
			el_avaria.addElement("tipo").addText(avaria.getTipo().getCodigo().toString());
			el_avaria.addElement("local").addText(avaria.getLocal().getCodigo().toString());
			el_avaria.addElement("origem").addText(avaria.getOrigem().getCodigo().toString());
			el_avaria.addElement("concli").addText(avaria.getClima().getCodigo().toString());
			el_avaria.addElement("gravid").addText(avaria.getExtensao().getCodigo().toString());
			el_avaria.addElement("usuario").addText(avaria.getUsuario().getCodigo().toString());
			el_avaria.addElement("data").addText(new SimpleDateFormat("dd/MM/yyyy").format(avaria.getDataLancamento()));
			Element el_fotos = el_avaria.addElement("fotos");
			if (avaria.getObservacao() == null) {
				Element el_obs = el_avaria.addElement("obs");
			} else {
				el_avaria.addElement("obs").addText(avaria.getObservacao());
			}
		}
	}

}
