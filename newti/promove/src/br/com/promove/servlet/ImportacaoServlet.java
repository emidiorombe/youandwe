package br.com.promove.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.promove.importacao.ws.ImportacaoCTRCREST;

public class ImportacaoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		ImportacaoCTRCREST imp_ctrc = new ImportacaoCTRCREST();
		imp_ctrc.importarGabardo("http://localhost:8080/ctrcws/ws/ctrc/por-data?dataIni=2005-01-01&dataFim=2011-12-31");
	}

}
