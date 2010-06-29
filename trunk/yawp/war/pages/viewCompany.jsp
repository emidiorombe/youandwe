<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
<h3>Empresa: ${company.name}</h3>
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
<form action="/comment/add" method="post">
	<input type="hidden" name="id_company" value="${company.key.id}">
	<input type="text" name="rating" size="3"><br/>
	<textarea rows="10" cols=50" name="text">
	</textarea>	
	<br/>
	<input type="submit" value="Registrar">
</form>
<h4>Comentários (${qtdeComments})</h4> 
<c:if test="${empty param.all &&  !(empty loggedUser)}">
	<a href="/company/list/${company.key.id}/?all=1">ver todos</a><br/>
</c:if>
<c:if test="${!empty param.all &&  !(empty loggedUser)}">
	<a href="/company/list/${company.key.id}/">ver somente contatos</a><br/>
</c:if>
<c:forEach var="com" items="${c_comments}">
	${com.text.value} / rating: ${com.rating}<br/>
</c:forEach>
</body>
</html>