package br.com.yaw.servlet.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.User;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.service.CacheService;
import br.com.yaw.service.CommentService;
import br.com.yaw.service.CompanyService;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Text;

public class CommentActionServlet extends BaseActionServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String tokens[] = request.getRequestURI().split("/");
		String action = tokens[2];
		CommentService service = ServiceFactory.getService(CommentService.class);
		CompanyService companyService = ServiceFactory.getService(CompanyService.class);
		
		if("add".equals(action)) {
			try {
				Comment c = new Comment();
				c.setRating(Integer.parseInt(request.getParameter("rating")));
				c.setText(new Text(request.getParameter("text")));
				c.setDtComment(new Date());
				
				User u = (User) request.getSession(false).getAttribute(LOGGED_USER);
				c.setOwner(u.getKey().getId());
				
				long companyId = Long.parseLong(request.getParameter("id_company"));
				c.setCompany(companyId);
				
				BlobstoreService blobS = BlobstoreServiceFactory.getBlobstoreService();
				Map<String, BlobKey> uploadedBlobs = blobS.getUploadedBlobs(request);
				List<BlobKey> photos = new ArrayList<BlobKey>();
				
				for (Map.Entry<String, BlobKey> entry: uploadedBlobs.entrySet()) {
					photos.add(entry.getValue());
				}
				
				if(photos.size() > 0) {
					c.setPhotos(photos);
				}
				
				service.addComment(c);
				CacheService.addComment(c);
				
				response.sendRedirect("/company/list/" + companyId);
			}catch (ServiceException e) {
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
			
		}else if("latest".equals(action)){
			try {
				Collection<Comment> latestComments = CacheService.getLatestComments();
				if(latestComments == null || latestComments.size() == 0){
					latestComments = service.getLatestComments(100);
					CacheService.addComment(latestComments);
				}
				
				for (Comment comment : latestComments) {
					response.getWriter().write(comment.getTextValue());
				}
			}catch(ServiceException se) {
				throw new RuntimeException();
			}
		}else if("remove".equals(action)){
			try {
				long idComment = Long.parseLong(tokens[3]);
				Comment c = service.getCommentById(idComment);
				String companyId = c.getCompany().toString();
				service.remove(c);
				CacheService.removeComment(c);
				response.sendRedirect("/company/list/"+companyId);
			}catch (ServiceException se) {
				response.getWriter().write(se.getMessage());
				se.printStackTrace();
			}
		}else {
					response.sendRedirect("/pages/404.jsp");	
		} 
	}
}
