<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>EQtal? - Busca</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<div id="content">

	<div id="s_company">
		<h3>Buscar empresa</h3>
		
		<form action="/company/search_adv" method="post">
			<table>
				<tr>
					<td>Nome:</td>
					<td><input type="text" name="nome_c"></td>
				</tr>
				<tr>
					<td>Cidade:</td>
					<td><input type="text" name="cidade_c"></td>
				<tr>
					<td>Bairro:</td>
					<td><input type="text" name="bairro_c"></td>
				<tr>
					<td>Categoria:</td>
					<td><input type="text" name="categoria_c"></td>
				<tr>
					<td></td>
					<td><input type="submit" value="Buscar"></td>
			</table>	
		</form>
	</div>
	
	<div id="s_user">
		<h3>Buscar Pessoa</h3>
		<form action="/user/search_adv">
			
		</form>
		
	</div>
	
</div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script>
</html>