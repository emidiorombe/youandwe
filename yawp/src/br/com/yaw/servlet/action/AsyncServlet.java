package br.com.yaw.servlet.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.repository.CompassFactory;

public class AsyncServlet extends BaseActionServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
		
		if("compass_rebuild".equals(action)) {
			CompassFactory.rebuildIndex();
			
		}
	}
	
	

}
