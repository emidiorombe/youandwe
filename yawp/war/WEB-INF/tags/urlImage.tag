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
String bKey = (String)request.getAttribute("bKey");
String imgSize = (String)request.getAttribute("imgSize");

ImagesService imgS = ImagesServiceFactory.getImagesService();

String url = "";
if(imgSize != null){
	url = imgS.getServingUrl(new BlobKey(bKey), Integer.parseInt(imgSize), false); 
}else{
	url = imgS.getServingUrl(new BlobKey(bKey));
}

request.setAttribute("url", url);
%>

${url}