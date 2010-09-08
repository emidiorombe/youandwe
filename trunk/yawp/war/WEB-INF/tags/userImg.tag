<%@tag import="br.com.yaw.entity.User"%>
<%@tag import="br.com.yaw.ioc.ServiceFactory"%>
<%@tag import="br.com.yaw.service.UserService"%>
<%@tag import="com.google.appengine.api.blobstore.BlobKey"%>
<%@tag import="com.google.appengine.api.images.ImagesServiceFactory"%>
<%@tag import="com.google.appengine.api.images.ImagesService"%>
<%@ tag body-content="empty" isELIgnored="false"%>
<%@ attribute name="userImg" required="false" %>
<%@attribute name="size" required="false" %>
<%@attribute name="userId" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="bKey" value="${userImg}" scope="request"/>
<c:set var="imgSize" value="${size}" scope="request"/>
<c:set var="uId" value="${userId}" scope="request"/>

<%
	
	String bKey = request.getAttribute("bKey") != null && request.getAttribute("bKey").toString().length() > 0 ?(String)request.getAttribute("bKey") : null;

	if(bKey == null && request.getAttribute("uId") != null){
		String uid = (String)request.getAttribute("uId");
		
		UserService uService = ServiceFactory.getService(UserService.class);
		User us = (User)uService.getUserById(Long.parseLong(uid));
		bKey = us != null ? us.getAvatar() : null;
	}

	

	ImagesService imgS = ImagesServiceFactory.getImagesService();
	String imgSize = (String)request.getAttribute("imgSize");
	
	String url = "/media/img/photo_default_peq.jpg";
	if(bKey != null && imgSize != null){
		url = imgS.getServingUrl(new BlobKey(bKey), Integer.parseInt(imgSize), false); 
	}else if(bKey != null){
		url = imgS.getServingUrl(new BlobKey(bKey));
	}
	
	request.setAttribute("url", url);
%>
<img src="${url}" />