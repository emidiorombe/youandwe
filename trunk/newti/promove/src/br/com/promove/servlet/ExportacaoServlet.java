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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			ExportacaoService export = ServiceFactory.getService(ExportacaoService.class);
			
			resp.setContentType("text/xml");
			resp.getWriter().print(export.exportarCadastrosBasicos());
		}catch(PromoveException pe) {
			resp.getWriter().write(pe.getMessage());
		}
	}
	
}
