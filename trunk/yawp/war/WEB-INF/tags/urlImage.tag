<%@tag import="br.com.yaw.ioc.ServiceFactory"%>
<%@tag import="com.google.appengine.api.blobstore.BlobKey"%>
<%@tag import="com.google.appengine.api.images.ImagesServiceFactory"%>
<%@tag import="com.google.appengine.api.images.ImagesService"%>

<%@ tag body-content="empty" isELIgnored="false"%>
<%@ attribute name="blobKey" required="true" %>
<%@attribute name="size" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="bKey" value="${blobKey}" scope="request"/>
<c:set var="imgSize" value="${size}" scope="request"/>

<%
String bKey = request.getAttribute("bKey") != null && request.getAttribute("bKey").toString().length() > 0  ? (String)request.getAttribute("bKey") : null;
String imgSize = (String)request.getAttribute("imgSize");

ImagesService imgS = ImagesServiceFactory.getImagesService();

String url = "/media/img/photo_default_peq.jpg";
if(bKey != null && imgSize != null){
	url = imgS.getServingUrl(new BlobKey(bKey), Integer.parseInt(imgSize), false); 
}else if(bKey != null){
	url = imgS.getServingUrl(new BlobKey(bKey));
}

request.setAttribute("url", url);
%>

${url}