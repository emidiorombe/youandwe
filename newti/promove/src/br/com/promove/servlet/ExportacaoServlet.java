package br.com.promove.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.Config;

public class ExportacaoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("cadastro".equals(action)) {
			try {
				ExportacaoService export = ServiceFactory.getService(ExportacaoService.class);
				
				response.setContentType("applicaton/xml");
				response.setHeader("Content-Disposition", "attachment; filename=GSPPRE.xml");
				response.getWriter().print(export.exportarCadastrosBasicos());
			}catch(PromoveException pe) {
				response.getWriter().write(pe.getMessage());
			}
		}else if("avgabardo".equals(action)) {
			
		}else if("avterca".equals(action)) {
			
		}else if("avkuhlmann".equals(action)) {
			
		}else if("export_excel".equals(action)) {
			String filePath = request.getParameter("file");
			String fileName = request.getParameter("fileName");
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			try {
				File f = new File(filePath);
				FileInputStream file = new FileInputStream(f);
				
				int b = -1;
				while((b = file.read()) != -1){
					response.getOutputStream().write(b);
				}
				response.getOutputStream().close();
				f.delete();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}else if("foto".equals(action)) {
			String nome = request.getParameter("name");
			
			FileInputStream fis = new FileInputStream(getServletContext().getInitParameter("pasta_fotos")+nome);
			
			int b = -1;
			while((b=fis.read()) != -1) {
				response.getOutputStream().write(b);
			}
			
		}
	}
	
}
