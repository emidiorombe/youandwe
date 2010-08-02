package br.com.yaw.servlet.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.UserService;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageServlet extends BaseActionServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = getAction(tokens);
		UserService userS;
		
		if("split".equals(action)){
			String blobKey = request.getParameter("img");
			ImagesService imgS = ImagesServiceFactory.getImagesService();
			Image orig = ImagesServiceFactory.makeImageFromBlob(new BlobKey(blobKey));
			
			Transform resize = ImagesServiceFactory.makeResize(200, 300);
			
			Image newImg = imgS.applyTransform(resize, orig);
			
			userS = ServiceFactory.getService(UserService.class);
			
			//userS.addUserPhoto();
			
		}
	}
}
