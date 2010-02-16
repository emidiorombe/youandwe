package br.com.yaw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class BlobstoreServlet extends HttpServlet{
	
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		
		if("upload".equals(action)) {
			Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
			BlobKey bKey = blobs.get("photoUrl");
			
			response.sendRedirect("blobstore-servlet?action=getPhoto&blob-key="+bKey.getKeyString());
		}else if("getPhoto".equals(action)) {
			BlobKey blobKey = new BlobKey(request.getParameter("blob-key"));
			
			blobstoreService.serve(blobKey, response);
		}else if("resize".equals(action)) {
			ImagesService imgService = ImagesServiceFactory.getImagesService();
			InputStream stream = request.getInputStream();
			ArrayList<Byte> bytes = new ArrayList<Byte>();
			int b = 0;
			while((b = stream.read()) != -1){
				bytes.add((byte)b);
			}
			
			byte img[] = new byte[bytes.size()];
			for (int i = 0; i < bytes.size(); i++) {
				img[i] = bytes.get(i);
			}
			
			Image oldImage = ImagesServiceFactory.makeImage(img);
			Transform resize = ImagesServiceFactory.makeResize(340, 226);
			
			Image imgNew = imgService.applyTransform(resize, oldImage);
			
			response.getOutputStream().write(imgNew.getImageData());
			response.getOutputStream().close();
		}
		
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPut(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

}
