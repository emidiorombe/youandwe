<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
<h3>Adicionar Usuário</h3>
<hr />

<form action="/user/add" method="post">
<input type="hidden" name="edit" />
<fieldset><legend>Usuário</legend>
	<table>
	<tr>
		<td><label>Nome:</label></td>
		<td><input type="text" name="name" /></td>
	</tr>
	<tr>
		<td><label>Senha:</label></td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td><label>Repete Senha:</label></td>
		<td><input type="password" name="repassword" /></td>
	</tr>
	<tr>
		<td><label>Email:</label></td>
		<td><input type="text" name="contactEmail" /></td>
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