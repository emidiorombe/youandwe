<%@ page language="java" isELIgnored="false" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EQtal? Listagem de empresas</title>
</head>
<body>
	<c:if test="${empty lista_co}">
		<h2>Não há empresas para a busca informada!</h2>
	</c:if>
	<c:forEach var="com" items="${lista_co}">
		<a href="/company/list/${com.key.id}">${com.name}</a>		
	</c:forEach>
</body>
</html>