package br.com.promove.service;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Document;

import com.vaadin.ui.Label;

import br.com.promove.dao.ClimaDAO;
import br.com.promove.dao.ExtensaoDAO;
import br.com.promove.dao.LocalAvariaDAO;
import br.com.promove.dao.NivelAvariaDAO;
import br.com.promove.dao.OrigemAvariaDAO;
import br.com.promove.dao.TipoAvariaDAO;
import br.com.promove.dao.UsuarioDAO;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Resumo;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.Ctrc;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.exportacao.AvariasExport;
import br.com.promove.exportacao.CadastrosBasicosExport;
import br.com.promove.utils.Config;
import br.com.promove.utils.FileUtils;

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
	private ExtensaoDAO extensaoDAO;
	private NivelAvariaDAO nivelDAO;
	private NumberFormat formatPercentual = new DecimalFormat("#0'%'"); //("#0.00'%'")
	
	public ExportacaoServiceImpl() {
		tipoDAO = new TipoAvariaDAO();
		localDAO = new LocalAvariaDAO();
		origemDAO = new OrigemAvariaDAO();
		climaDAO = new ClimaDAO();
		usuarioDAO = new UsuarioDAO();
		extensaoDAO = new ExtensaoDAO();
		nivelDAO = new NivelAvariaDAO();
	}

	@Override
	public String exportarCadastrosBasicos(String novo) throws PromoveException {
		try {
			Map<String , List> listas = new HashMap<String, List>();
			listas.put("usuario", usuarioDAO.getAll());
			listas.put("tipo", tipoDAO.getAll("descricao"));
			listas.put("local", localDAO.getAll("descricao"));
			listas.put("origem", origemDAO.getAll("codigo"));
			listas.put("clima", climaDAO.getAll());
			listas.put("extensao", extensaoDAO.getAll());
			listas.put("nivel", nivelDAO.getAll());
			
			return CadastrosBasicosExport.gerarXmlExportacao(listas, novo);
			
		}catch(DAOException de){
			throw new PromoveException();
		}
		
	}

	@Override
	public String exportarXMLAvarias(List<Avaria> avarias) throws PromoveException {
		return AvariasExport.gerarXmlExportacao(avarias);
	}

	@Override
	public String exportarXLSAvarias(List<Avaria> avarias)throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("avarias");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue("VEÍCULO");
		    row_head.createCell(1).setCellValue("MODELO");
		    row_head.createCell(2).setCellValue("DATA");
		    row_head.createCell(3).setCellValue("HORA");
		    row_head.createCell(4).setCellValue("LOCAL");
		    row_head.createCell(5).setCellValue("PEÇA");
		    row_head.createCell(6).setCellValue("TIPO");
		    row_head.createCell(7).setCellValue("EXTENSÃO");
		    row_head.createCell(8).setCellValue("NÍVEL");
		    row_head.createCell(9).setCellValue("FOTOS");
		    row_head.createCell(10).setCellValue("CLIMA");
		    row_head.createCell(11).setCellValue("USUÁRIO");
		    row_head.createCell(12).setCellValue("OBS");

		    
		    for(int i = 0; i < avarias.size(); i++) {
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(avarias.get(i).getVeiculo().getChassi());
			    row.createCell(1).setCellValue(avarias.get(i).getVeiculo().getModelo().getDescricao());
			    row.createCell(2).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(avarias.get(i).getDataLancamento()));
			    row.createCell(3).setCellValue(avarias.get(i).getHora());
			    row.createCell(4).setCellValue(avarias.get(i).getOrigem().getDescricao());
			    row.createCell(5).setCellValue(avarias.get(i).getLocal().getDescricao());
			    row.createCell(6).setCellValue(avarias.get(i).getTipo().getDescricao());
			    row.createCell(7).setCellValue(avarias.get(i).getExtensao().getDescricao());
			    row.createCell(8).setCellValue(avarias.get(i).getNivel().getNome());
			    row.createCell(9).setCellValue(avarias.get(i).getFotos().size());
			    row.createCell(10).setCellValue(avarias.get(i).getClima().getDescricao());
			    row.createCell(11).setCellValue(avarias.get(i).getUsuario().getNome());
			    row.createCell(12).setCellValue(avarias.get(i).getObservacao());
		    }

		    // Write the output to a file/
		    String fileName = Config.getConfig("tmp_dir") + "avarias_" + System.currentTimeMillis()+".xls";
		    FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
		    fileOut.close();
		    
		    return fileName;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PromoveException();
		}
		
	}

	@Override
	public String exportarXLSInconsistenciaAvarias(List<InconsistenciaAvaria> lista) throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("inconsistencias");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue("CHASSI");
		    row_head.createCell(1).setCellValue("DATA");
		    row_head.createCell(2).setCellValue("HORA");
		    row_head.createCell(3).setCellValue("LOCAL");
		    row_head.createCell(4).setCellValue("PEÇA");
		    row_head.createCell(5).setCellValue("TIPO");
		    row_head.createCell(6).setCellValue("EXTENSÃO");
		    row_head.createCell(7).setCellValue("NÍVEL");
		    row_head.createCell(8).setCellValue("CLIMA");
		    row_head.createCell(9).setCellValue("USUÁRIO");
		    row_head.createCell(10).setCellValue("OBS");
		    row_head.createCell(11).setCellValue("MENSAGEM");
		    
		    for(int i = 0; i < lista.size(); i++) {
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(lista.get(i).getChassiInvalido());
			    row.createCell(1).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(lista.get(i).getDataLancamento()));
			    row.createCell(2).setCellValue(lista.get(i).getHora());
			    row.createCell(3).setCellValue(lista.get(i).getOrigem().getDescricao());
			    row.createCell(4).setCellValue(lista.get(i).getLocal().getDescricao());
			    row.createCell(5).setCellValue(lista.get(i).getTipo().getDescricao());
			    row.createCell(6).setCellValue(lista.get(i).getExtensao().getDescricao());
			    row.createCell(7).setCellValue(lista.get(i).getNivel().getNome());
			    row.createCell(8).setCellValue(lista.get(i).getClima().getDescricao());
			    row.createCell(9).setCellValue(lista.get(i).getUsuario().getNome());
			    row.createCell(10).setCellValue(lista.get(i).getObservacao());
			    row.createCell(11).setCellValue(lista.get(i).getMsgErro());
		    }

		    // Write the output to a file/
		    String fileName = Config.getConfig("tmp_dir") + "incavarias_" + System.currentTimeMillis()+".xls";
		    FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
		    fileOut.close();
		    
		    return fileName;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PromoveException();
		}
		
	}

	@Override
	public String exportarXLSVeiculos(List<Veiculo> veiculos)throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("veiculos");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue("CHASSI");
		    row_head.createCell(1).setCellValue("MODELO");
		    row_head.createCell(2).setCellValue("COR");
		    row_head.createCell(3).setCellValue("DATA");
		    row_head.createCell(4).setCellValue("TIPO");
		    row_head.createCell(5).setCellValue("NAVIO");
		    if (veiculos.size() > 0 && veiculos.get(0).getOrigensfaltantes() != null)
		    	row_head.createCell(6).setCellValue("ORIGENS FALTANTES");
		    
		    for(int i = 0; i < veiculos.size(); i++) {
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(veiculos.get(i).getChassi());
			    row.createCell(1).setCellValue(veiculos.get(i).getModelo().getDescricao());
			    row.createCell(2).setCellValue(veiculos.get(i).getCor().getDescricao());
			    row.createCell(3).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(veiculos.get(i).getDataCadastro()));
			    row.createCell(4).setCellValue(veiculos.get(i).getTipo() == 1 ? "Nacional" : "Importado");
			    row.createCell(5).setCellValue(veiculos.get(i).getNavio());
			    if (veiculos.size() > 0 && veiculos.get(i).getOrigensfaltantes() != null)
			    	row.createCell(6).setCellValue(veiculos.get(i).getOrigensfaltantes());
		    }

		    // Write the output to a file/
		    String fileName = Config.getConfig("tmp_dir") + "veiculos_" + System.currentTimeMillis()+".xls";
		    FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
		    fileOut.close();
		    
		    return fileName;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PromoveException();
		}
		
	}

	@Override
	public String exportarXLSCtrcs(List<Ctrc> ctrcs) throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("ctrcs");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue("ID");
		    row_head.createCell(1).setCellValue("FILIAL");
		    row_head.createCell(2).setCellValue("NUMERO");
		    row_head.createCell(3).setCellValue("TIPO");
		    row_head.createCell(4).setCellValue("SERIE");
		    row_head.createCell(5).setCellValue("TRANSPORTADORA");
		    row_head.createCell(6).setCellValue("EMISSAO");
		    row_head.createCell(7).setCellValue("UF ORIGEM");
		    row_head.createCell(8).setCellValue("MUNICIPIO ORIGEM");
		    row_head.createCell(9).setCellValue("UF DESTINO");
		    row_head.createCell(10).setCellValue("MUNICIPIO DESTINO");
		    row_head.createCell(11).setCellValue("TAXA RCT");
		    row_head.createCell(12).setCellValue("TAXA RCF");
		    row_head.createCell(13).setCellValue("TAXA RR");
		    row_head.createCell(14).setCellValue("TAXA FLUVIAL");
		    
		    for(int i = 0; i < ctrcs.size(); i++) {
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(ctrcs.get(i).getId());
			    row.createCell(1).setCellValue(ctrcs.get(i).getFilial());
			    row.createCell(2).setCellValue(ctrcs.get(i).getNumero());
			    row.createCell(3).setCellValue(ctrcs.get(i).getTipo());
			    row.createCell(4).setCellValue(ctrcs.get(i).getSerie());
			    row.createCell(5).setCellValue(ctrcs.get(i).getTransp().getDescricao());
			    row.createCell(6).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(ctrcs.get(i).getDataEmissao()));
			    row.createCell(7).setCellValue(ctrcs.get(i).getUfOrigem());
			    row.createCell(8).setCellValue(ctrcs.get(i).getMunicipioOrigem());
			    row.createCell(9).setCellValue(ctrcs.get(i).getUfDestino());
			    row.createCell(10).setCellValue(ctrcs.get(i).getMunicipioDestino());
			    row.createCell(11).setCellValue(ctrcs.get(i).getTaxaRct());
			    row.createCell(12).setCellValue(ctrcs.get(i).getTaxaRcf());
			    row.createCell(13).setCellValue(ctrcs.get(i).getTaxaRr());
			    row.createCell(14).setCellValue(ctrcs.get(i).getTaxaFluvial());
		    }

		    // Write the output to a file/
		    String fileName = Config.getConfig("tmp_dir") + "ctrcs_" + System.currentTimeMillis()+".xls";
		    FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
		    fileOut.close();
		    
		    return fileName;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PromoveException();
		}
	}

	@Override
	public String exportarXLSResumo(List<Resumo> resumos, String item, String subitem) throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("veiculos");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue(item);
		    row_head.createCell(1).setCellValue("QUANTIDADE");
		    row_head.createCell(2).setCellValue("PERCENTUAL");
		    row_head.createCell(3).setCellValue(subitem);
		    row_head.createCell(4).setCellValue("QUANTIDADE");
		    row_head.createCell(5).setCellValue("PERCENTUAL");
		    
		    int i = 0;
		    int itemTotal = 0;
		    for(i = 0; i < resumos.size(); i++) {
		    	Integer vl = resumos.get(i).getQuantidadeItem();
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(resumos.get(i).getItem());
			    if (resumos.get(i).getQuantidadeItem() != null) {
			    	row.createCell(1).setCellValue(resumos.get(i).getQuantidadeItem());
				    row.createCell(2).setCellValue(formatPercentual.format(resumos.get(i).getPercentualItem()));
			    }
			    row.createCell(3).setCellValue(resumos.get(i).getSubitem());
			    row.createCell(4).setCellValue(resumos.get(i).getQuantidadeSubitem());
			    row.createCell(5).setCellValue(formatPercentual.format(resumos.get(i).getPercentualSubitem()));
			    if (vl != null) itemTotal += vl;
		    }
		    Row row_footer = sheet.createRow(i+1);
		    row_footer.createCell(0).setCellValue("Total");
		    row_footer.createCell(1).setCellValue(itemTotal);

		    // Write the output to a file/
		    String fileName = Config.getConfig("tmp_dir") + "resumo_" + System.currentTimeMillis()+".xls";
		    FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
		    fileOut.close();
		    
		    return fileName;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PromoveException();
		}
	}

}
