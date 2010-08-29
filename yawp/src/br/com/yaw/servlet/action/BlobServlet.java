package br.com.yaw.servlet.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.UserService;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

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
		UserService userService = ServiceFactory.getService(UserService.class);
		if("img".equals(action)) {
			String key = tokens[3];
			BlobstoreService blobS = BlobstoreServiceFactory.getBlobstoreService();
			blobS.serve(new BlobKey(key), response);

		}else if("avatar".equals(action)){
			try {
				String uid = tokens[3];
				
				ImagesService imagesService = ImagesServiceFactory.getImagesService();
				String url = imagesService.getServingUrl(new BlobKey(uid));
				
				response.getWriter().println(url+"=120s");
				response.getWriter().close();
				
				
			}catch (Exception e) {
				// TODO return foto default
			}
		}
	}
	

}
