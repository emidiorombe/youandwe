<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%BlobstoreService blobS =  BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>EQtal? - Editar Empresa</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<h3>Editar Empresa</h3>
<hr />
<form action="/company/add" method="post" enctype="multipart/form-data">
<input type="hidden" name="edit" />
<table>
	<tr>
		<td><label>Nome:</label></td>
		<td><input type="text" name="name" value="${company.name}"/></td>
	</tr>
	<tr>
		<td><label>Site:</label></td>
		<td><input type="text" name="url" value="${company.url}"/></td>
	</tr>
	<tr>
		<td><label>Logo:</label></td>
		<td><input type="file" name="logo_c" /></td>
	</tr>
	<tr>
		<td><label>Descricao:</label></td>
		<td><textarea rows="5" cols="17" name="description">${company.description}</textarea></td>
	</tr>
	<tr>
		<td><label>Categoria:</label></td>
		<td><input type="text" name="category" value="${c_tags}"/></td>
	</tr>
</table>
<table>
	<tr>
		<td><label>Pais:</label></td>
		<td><input type="text" name="country" value="${company.addr.country != null ? company.addr.country : "Brasil"}"/></td>
	</tr>
	<tr>
		<td><label>Estado:</label></td>
		<td>
			<select name="state">
				<option value="0">Selecione...</option>
				<c:forEach var="state" items="${l_states}">
					<option value="${state.key}" ${company.addr.state == state.key ? "selected" : company.addr.state}>${state.value}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td><label>Cidade:</label></td>
		<td><input type="text" name="city" value="${company.addr.city}"/></td>
	</tr>
	<tr>
		<td><label>Bairro:</label></td>
		<td><input type="text" name="bairro" value="${company.addr.bairro}"/></td>
	</tr>
	<tr>
		<td><label>Logradouro:</label></td>
		<td><input type="text" name="street" value="${company.addr.street}" /></td>
	</tr>
</table>
<div align="center"><input type="submit" value="Cadastrar"/></div>
<input type="hidden" name="id_c" value="${company.key.id}">
</form>

<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script>
</html>