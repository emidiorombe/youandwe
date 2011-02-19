package br.com.promove.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.promove.exception.PromoveException;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;

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
				response.setHeader("Content-Disposition", "attachment; filename=cadastro.xml");
				response.getWriter().print(export.exportarCadastrosBasicos());
			}catch(PromoveException pe) {
				response.getWriter().write(pe.getMessage());
			}
		}else if("avgabardo".equals(action)) {
			
		}else if("avterca".equals(action)) {
			
		}else if("avkuhlmann".equals(action)) {
			
		}
	}
	
}
