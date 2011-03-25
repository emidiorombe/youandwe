package br.com.promove.service;

import java.io.FileOutputStream;
import java.io.Serializable;
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

import br.com.promove.dao.ClimaDAO;
import br.com.promove.dao.LocalAvariaDAO;
import br.com.promove.dao.OrigemAvariaDAO;
import br.com.promove.dao.TipoAvariaDAO;
import br.com.promove.dao.UsuarioDAO;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;
import br.com.promove.exportacao.CadastrosBasicosExport;
import br.com.promove.utils.Config;

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

	@Override
	public String exportarXLSAvarias(List<Avaria> avarias)throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("avarias");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue("ID");
		    row_head.createCell(1).setCellValue("VEÍCULO");
		    row_head.createCell(2).setCellValue("MODELO");
		    row_head.createCell(3).setCellValue("TIPO");
		    row_head.createCell(4).setCellValue("LOCAL");
		    row_head.createCell(5).setCellValue("ORIGEM");
		    row_head.createCell(6).setCellValue("EXTENSÃO");
		    row_head.createCell(7).setCellValue("CLIMA");
		    row_head.createCell(8).setCellValue("DATA LANCAMENTO");
		    row_head.createCell(9).setCellValue("HORA");
		    row_head.createCell(10).setCellValue("FOTOS");
		    row_head.createCell(11).setCellValue("USUÁRIO");
		    row_head.createCell(12).setCellValue("OBS");

		    
		    for(int i = 0; i < avarias.size(); i++) {
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(avarias.get(i).getId());
			    row.createCell(1).setCellValue(avarias.get(i).getVeiculo().getChassi());
			    row.createCell(2).setCellValue(avarias.get(i).getVeiculo().getModelo().getDescricao());
			    row.createCell(3).setCellValue(avarias.get(i).getTipo().getDescricao());
			    row.createCell(4).setCellValue(avarias.get(i).getLocal().getDescricao());
			    row.createCell(5).setCellValue(avarias.get(i).getOrigem().getDescricao());
			    row.createCell(6).setCellValue(avarias.get(i).getExtensao().getDescricao());
			    row.createCell(7).setCellValue(avarias.get(i).getClima().getDescricao());
			    row.createCell(8).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(avarias.get(i).getDataLancamento()));
			    row.createCell(9).setCellValue(avarias.get(i).getHora());
			    row.createCell(10).setCellValue(avarias.get(i).getFotos().size());
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
	public String exportarXLSVeiculos(List<Veiculo> veiculos)throws PromoveException {
		try {
			Workbook wb = new HSSFWorkbook();
		    CreationHelper createHelper = wb.getCreationHelper();
		    Sheet sheet = wb.createSheet("veiculos");
		    
		    //Cabeçalho
		    Row row_head = sheet.createRow(0);
		    row_head.createCell(0).setCellValue("ID");
		    row_head.createCell(1).setCellValue("CHASSI");
		    row_head.createCell(2).setCellValue("MODELO");
		    row_head.createCell(3).setCellValue("COR");
		    row_head.createCell(4).setCellValue("DATA CADASTRO");
		    
		    for(int i = 0; i < veiculos.size(); i++) {
			    Row row = sheet.createRow(i+1);
			    row.createCell(0).setCellValue(veiculos.get(i).getId());
			    row.createCell(1).setCellValue(veiculos.get(i).getChassi());
			    row.createCell(2).setCellValue(veiculos.get(i).getModelo().getDescricao());
			    row.createCell(3).setCellValue(veiculos.get(i).getCor().getDescricao());
			    row.createCell(4).setCellValue(veiculos.get(i).getDataCadastro());
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

}
