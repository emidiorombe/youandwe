package br.com.promove.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.promove.dao.AvariaDAO;
import br.com.promove.dao.ClimaDAO;
import br.com.promove.dao.ExtensaoDAO;
import br.com.promove.dao.LocalAvariaDAO;
import br.com.promove.dao.OrigemAvariaDAO;
import br.com.promove.dao.TipoAvariaDAO;
import br.com.promove.dao.UsuarioDAO;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.exportacao.CadastrosBasicosExport;

/**
 * 
 * @author Rafael Nunes
 *
 */
public class ExportacaoServiceImpl implements ExportacaoService, Serializable{
	private UsuarioDAO usuarioDAO;
	private LocalAvariaDAO localDAO;
	private OrigemAvariaDAO origemDAO;
	private TipoAvariaDAO tipoDAO;
	private ClimaDAO climaDAO;
	
	public ExportacaoServiceImpl() {
		tipoDAO = new TipoAvariaDAO();
		localDAO = new LocalAvariaDAO();
		origemDAO = new OrigemAvariaDAO();
		climaDAO = new ClimaDAO();
		usuarioDAO = new UsuarioDAO();
	}

	@Override
	public String exportarCadastrosBasicos() throws PromoveException {
		try {
			Map<String , List> listas = new HashMap<String, List>();
			listas.put("usuario", usuarioDAO.getAll());
			listas.put("tipo", tipoDAO.getAll());
			listas.put("local", localDAO.getAll());
			listas.put("origem", origemDAO.getAll());
			listas.put("clima", climaDAO.getAll());
			
			return CadastrosBasicosExport.gerarXmlExportacao(listas);
			
			
		}catch(DAOException de){
			throw new PromoveException();
		}
		
	}
}
