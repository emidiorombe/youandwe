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
<c:forEach var="com" items="${c_comments}">
	${com.text.value} / rating: ${com.rating}<br/>
</c:forEach>
</body>
</html>