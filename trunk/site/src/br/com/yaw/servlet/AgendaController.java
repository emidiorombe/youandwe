package br.com.yaw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgendaController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		StringBuilder sb = new StringBuilder("[ ");
		sb.append("{ \"id\": \"AJABCT06\", \"desc\":\"Academia Java\", \"dt\":\"02&#47;Abr&#47;2011\", \"per\":\"S&#225;bados, das 10 as 19h\", \"link\":\"/treinamentos/academiaJava.html\" }").append(" , ")
			.append("{ \"id\": \"AWABCT02\", \"desc\":\"Academia Web\", \"dt\":\"09&#47;Mai&#47;2011\", \"per\":\"Segundas e Quartas, das 19 as 23h\", \"link\":\"/treinamentos/academiaWeb.html\" }").append(" , ")
			.append("{ \"id\": \"APABCT01\", \"desc\":\"Academia do Programador\", \"dt\":\"14&#47;Mai&#47;2011\", \"per\":\"S&#225;bados, das 10 as 19h\", \"link\":\"/treinamentos/academiaProgramador.html\" }");
		
		sb.append(" ]");
		out.println(sb.toString());
		out.close();
	}
	
}
