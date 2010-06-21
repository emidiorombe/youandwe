<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
${msgErro}
<h3>Perfil Usuário</h3>
<hr />

<form action="/user/add" method="post">
<input type="hidden" name="edit" />
<fieldset><legend>Usuário</legend>
	<table>
	<tr>
		<td><label>Nome:</label></td>
		<td><input type="text" name="name" value="${user.name}"/></td>
	</tr>
	
	<c:if test="${user.tipoCadastro != 2}">
	<tr>
		<td><label>Nova Senha:</label></td>
		<td><input type="password" name="password" />(Deixe em branco para manter a atual)</td>
	</tr>
	<tr>
		<td><label>Repita Nova Senha:</label></td>
		<td><input type="password" name="repassword" /></td>
	</tr>
	</c:if>
	<tr>
		<td><label>Email:</label></td>
		<td><input type="text" name="contactEmail" value="${user.contactEmail}"  readonly="readonly"/></td>
	</tr>
	<tr>
		<td><label>Twitter:</label></td>
		<td><input type="text" name="twit" /></td>
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
		<td><label></label></td>
		<td><input type="submit" value="Cadastrar" /></td>
	</tr>
</table>
</fieldset>

</form>

</body>
</html>