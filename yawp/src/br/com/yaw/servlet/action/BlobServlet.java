package br.com.yaw.servlet.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobServlet extends BaseActionServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
		
		if("img".equals(action)) {
			String key = tokens[3];
			BlobstoreService blobS = BlobstoreServiceFactory.getBlobstoreService();
			blobS.serve(new BlobKey(key), response);

		}else if("search_log".equals(action)){
			
		}
	}
	

}
