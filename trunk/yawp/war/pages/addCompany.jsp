<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
<h3>Adicionar Empresa</h3>
<hr />
<form action="/company/add" method="post"><input type="hidden"
	name="edit" />
<fieldset><legend>Empresa</legend>
<table>
	<tr>
		<td><label>Nome:</label></td>
		<td><input type="text" name="name" /></td>
	</tr>
	<tr>
		<td><label>Site:</label></td>
		<td><input type="text" name="url" /></td>
	</tr>
	<tr>
		<td><label>Logotipo:</label></td>
		<td><input type="text" name="logo" /></td>
	</tr>
	<tr>
		<td><label>Descricao:</label></td>
		<td><textarea rows="5" cols="17" name="description">
							</textarea></td>
	<tr>
		<td><label>Categoria:</label></td>
		<td><input type="text" name="category" /></td>
	</tr>
	</tr>
</table>
</fieldset>
<fieldset><legend>Endereço</legend>
<table>
	<tr>
		<td><label>Rua:</label></td>
		<td><input type="text" name="street" /></td>
	</tr>
	<tr>
		<td><label>Numero:</label></td>
		<td><input type="text" name="number" /></td>
	</tr>
	<tr>
		<td><label>Bairro:</label></td>
		<td><input type="text" name="bairro" /></td>
	</tr>
	<tr>
		<td><label>Cidade:</label></td>
		<td><input type="text" name="city" /></td>
	</tr>
	<tr>
		<td><label>Estado:</label></td>
		<td><input type="text" name="state" /></td>
	</tr>
	<tr>
		<td><label>Pais:</label></td>
		<td><input type="text" name="country" /></td>
	</tr>
</table>
</fieldset>
<div align="center"><input type="submit" value="Cadastrar"/></div>
</form>

</body>
</html>