<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
<h3>Usuario: ${user.name}</h3>
Email: ${user.contactEmail}
<br />
<hr />

<h4>Comentários Feitos(${qtdeComments})</h4>
<c:forEach var="com" items="${c_comments}">
	${com.text.value} / rating: ${com.rating}<br/>
</c:forEach>

<h4>Comentários da Rede(${qtdeComments})</h4>
<c:forEach var="com" items="${c_comments}">
	${com.text.value} / rating: ${com.rating}<br/>
</c:forEach>

</body>
</html>