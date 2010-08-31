<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"  %>
<%@ taglib prefix="eq" tagdir="/WEB-INF/tags/" %> 

<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%BlobstoreService blobS =  BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
	<head>
	<title>EQtal? - Profile Empresa -</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
	<link type='text/css' href='/media/css/basic2.css' rel='stylesheet' media='screen' />
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<div id="content_custom">
<h3>
	Empresa: ${company.name}<br/>
	<img src='<eq:urlImage blobKey="${company.logo}" size="220"/>' >
</h3>
Site: ${company.url}
<br />
Description: ${company.description}
<br />
Categorias: ${c_tags}
<br/>
<c:if test="${loggedUser != null && company.owner == loggedUser.key.id}">
 <a href="/company/edit/${company.key.id}">Editar</a>
</c:if> 
<br/>
<c:if test="${loggedUser != null && company.owner == loggedUser.key.id}">
 <a href="/company/delete/${company.key.id}">Remover</a>
</c:if> 


<hr />

<c:if test="${loggedUser != null && loggedUser.approved}">
<form action=<%=blobS.createUploadUrl("/comment/add")%> method="post" enctype="multipart/form-data">
	<input type="hidden" name="id_company" value="${company.key.id}">
	<input type="hidden" id="rating" name="rating" value="0">
	Avalie: <div id="star"></div>
	<textarea rows="10" cols=50" name="text"></textarea><br/>
	<div id="com_p">
		<a href="#" id="add_com_p">Adicionar photo</a>
	</div>
	<br/>
	<input type="submit" value="Registrar">
</form>
</c:if>
<h4>Comentários (${qtdeComments})</h4> 
<c:if test="${empty param.all &&  !(empty loggedUser)}">
	<a href="/company/list/${company.key.id}/?all=1">ver todos</a><br/>
</c:if>
<c:if test="${!empty param.all &&  !(empty loggedUser)}">
	<a href="/company/list/${company.key.id}/">ver somente contatos</a><br/>
</c:if>
<c:forEach var="com" items="${c_comments}">
	<div id="list_c">
		<div id="star_${com.key.id}">
			<script type="text/javascript">
				jQuery(function ($){
					$('#star_${com.key.id}').raty({
						path: '/media/img/',
						hintList:     ['Nao recomendo', 'Regular', 'Bom', 'Muito Bom', 'Recomendadissimo'],
						readOnly:     true, 
						start: ${com.rating}
					});
				});
			</script>
		</div>
		${com.text.value} / <c:if test="${com.owner == loggedUser.key.id}"><a href="/comment/remove/${com.key.id}">${msg['remover']}</a></c:if>
		<div id="photog">
			<c:forEach var="ph" items="${com.photos}">
				<a href='<eq:urlImage blobKey="${ph.keyString}" size="720"/>' class="preview"><img src='<eq:urlImage blobKey="${ph.keyString}" size="64"/>'></a> 
			</c:forEach>
		</div>
	</div>
</c:forEach>
</div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
</html>