package br.com.cdesign.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.images.ImagesServicePb.ImagesTransformRequest;



public class ImageHandler extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if("entry".equals(action)){
			InputStream is = req.getInputStream();
			byte bts[] = new byte[is.available()];
			is.read(bts);
			
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Image imgDefault = ImagesServiceFactory.makeImage(bts);
			
			
			Transform peqT = ImagesServiceFactory.makeResize(200, 300);
			Transform medT = ImagesServiceFactory.makeResize(400, 500);
			Transform grdT = ImagesServiceFactory.makeResize(800, 600);
			
			Image imgPeq = imagesService.applyTransform(peqT, imgDefault);
			Image imgMed = imagesService.applyTransform(medT, imgDefault);
			Image imgGrd = imagesService.applyTransform(grdT, imgDefault);
			
		
		}
		
	}
	
}
