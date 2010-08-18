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
	
	String bKey = null;

	if(request.getAttribute("bKey") != null){
		bKey = (String)request.getAttribute("bKey"); 
	}else if(request.getAttribute("uId") != null){
		String uid = (String)request.getAttribute("uId");
		
		UserService uService = ServiceFactory.getService(UserService.class);
		User us = (User)uService.getUserById(Long.parseLong(uid));
		bKey = us.getAvatar();
	}

	

	ImagesService imgS = ImagesServiceFactory.getImagesService();
	String imgSize = (String)request.getAttribute("imgSize");
	
	String url = "";
	if(imgSize != null){
		url = imgS.getServingUrl(new BlobKey(bKey), Integer.parseInt(imgSize), false); 
	}else{
		url = imgS.getServingUrl(new BlobKey(bKey));
	}
	
	request.setAttribute("url", url);
%>
<img src="${url}" />