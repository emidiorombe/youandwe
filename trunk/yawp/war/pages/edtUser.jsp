<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%BlobstoreService blobS =  BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
	<head>
	<title>EQtal? - Profile Usuário -</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<h3>Perfil Usuário</h3>
<hr />

<form action=<%=blobS.createUploadUrl("/user/add")%> method="post" enctype="multipart/form-data">
<input type="hidden" name="edit" value="true"/>
<fieldset>
	<table>
	<tr>
		<td><label>Foto:</label></td>
		<td><input type="file" name="avatar"></td>
	</tr>
	<tr>
		<td><label>Nome:</label></td>
		<td><input type="text" name="name" value="${user.name}"/></td>
	</tr>
	
	<tr>
		<td><label>Email:</label></td>
		<td><input type="text" name="contactEmail" value="${user.contactEmail}"  readonly="readonly"/></td>
	</tr>
	<tr>
		<td><label>Twitter:</label></td>
		<td><input type="text" name="twit" value="${user.twit}"/></td>
	</tr>
	<tr>
		<td><label>Orkut:</label></td>
		<td><input type="text" name="fcbook" /></td>
	</tr>
	<tr>
		<td><label>Facebook:</label></td>
		<td><input type="text" name="orkut" /></td>
	</tr>
	<tr>
		<td><label>Tipo:</label></td>
		<td><input type="text" name="tpcad" /></td>
	</tr>
	<tr>
		<td><label></label></td>
		<td><input type="submit" value="Cadastrar" /></td>
	</tr>
</table>
</fieldset>

</form>

<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script>
</html>