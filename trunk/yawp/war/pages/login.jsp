<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
	<form action="/user/login" method="post">
		<label>Email: </label><input type="text" name="contactEmail"><br/>
		<label>Senha:</label><input type="password" name="password"><br/>
		<input type="submit" value="Enviar">
	</form>
</body>
</html>