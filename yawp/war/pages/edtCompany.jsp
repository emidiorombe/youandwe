<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<html>
<head>
<title>YaWP!</title>
</head>
<body>
<h3>Adicionar Empresa</h3>
<hr />
<form action="/company/add" method="post">
<input type="hidden" name="edit" />
<fieldset><legend>Empresa</legend>
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
		<td><label>Logotipo:</label></td>
		<td><input type="text" name="logo" value="${company.logo}"/></td>
	</tr>
	<tr>
		<td><label>Descricao:</label></td>
		<td><textarea rows="5" cols="17" name="description">${company.description}</textarea>	</td>
	<tr>
		<td><label>Categoria:</label></td>
		<td><input type="text" name="category" value="${c_tags}"/></td>
	</tr>
	</tr>
</table>
</fieldset>
<fieldset><legend>Endereço</legend>
<table>
	<tr>
		<td><label>Rua:</label></td>
		<td><input type="text" name="street" value="${company.addr.street}" /></td>
	</tr>
	<tr>
		<td><label>Numero:</label></td>
		<td><input type="text" name="number" value="${company.addr.number}"/></td>
	</tr>
	<tr>
		<td><label>Bairro:</label></td>
		<td><input type="text" name="bairro" value="${company.addr.bairro}"/></td>
	</tr>
	<tr>
		<td><label>Cidade:</label></td>
		<td><input type="text" name="city" value="${company.addr.city}"/></td>
	</tr>
	<tr>
		<td><label>Estado:</label></td>
		<td><input type="text" name="state" value="${company.addr.state}"/></td>
	</tr>
	<tr>
		<td><label>Pais:</label></td>
		<td><input type="text" name="country" value="${company.addr.country}"/></td>
	</tr>
</table>
</fieldset>
<div align="center"><input type="submit" value="Cadastrar"/></div>
<input type="hidden" name="id_c" value="${company.key.id}">
</form>

</body>
</html>