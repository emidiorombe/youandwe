<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%  com.google.appengine.api.users.UserService userServiceG = com.google.appengine.api.users.UserServiceFactory.getUserService(); %>

<html>
<head>
	<title></title>
	<link rel="stylesheet" href="/media/css/home.css" type="text/css" media='screen'/>
	<link rel="stylesheet" href="/media/css/custom_classes.css" type="text/css" media='screen'/>
	<link type='text/css' href='/media/css/osx.css' rel='stylesheet' media='screen' />
</head>

<body>
	<div id="header">
		<div id="img_logo">
			<img alt="" src="/media/img/eqtal_logo1_q.png">
		</div>
		<c:if test="${loggedUser == null}">
			<div id="dvlog">
				<form action="user/login" method="POST">
					<ul>
						<li><a href="#" class='logmodal'>Login</a> / <a href="#" class="regmodal">Registrar</a> </li>
					</ul>
				</form>
			</div>
		</c:if>
		<c:if test="${loggedUser != null}">
			<div id="dvlog">
				<h2>Olá ${loggedUser.name == null ? loggedUser.contactEmail : loggedUser.name} <a href="<%= userServiceG.createLogoutURL("/user/logout")%>">logout</a></h2>
			</div>
		</c:if>
	</div>
	<div id="nav">
		<div id="nav_search">
			<form action="/company/search" method="post">
				<input type="text" name="txtBusca" class="input_search">
				<input type="image" src="/media/img/search.gif" style="width: 50px; height: 50px;">
			</form>
		</div>
		<div id="nav_link">
			<ul>
				<li><a href="/company/add">Cadastrar Empresa</a></li>
				<li><a href="/company/list/all">Listar Empresas</a></li>
				<li><a href="#nogo">Item three</a></li>
				<li><a href="#nogo">Item four</a></li>
				<li><a href="#nogo">Item five</a></li>
				<li><a href="#nogo">Item six</a></li>
	
			</ul>
		</div>
	</div>
	<div id="center">
		<div id="home_center">
			<div id="home_center_head">
				<div id="sp1">
					<h4>Gostou? Recomenda?</h4>
					
				</div>
				<div id="sp2">
					<h4>Conta pra gente!!!</h4>
				</div>
			</div>
			<div id="home_center_body">
				<img alt="" src="/media/img/personagem_01.jpg" style="width:220px; height:340;z-index:2;">
				<img alt="" src="/media/img/personagem_02.jpg" style="width:220px; height:340;z-index:3;">
				<img alt="" src="/media/img/personagem_03.jpg" style="width:220px; height:340;z-index:4;">
			</div>
		</div>
	</div>
	<div id="latest" style="border: 1px; border-color: #FFFFFF;">
			<div id="head_latest">
			</div>
			<div id="body_latest">
			</div>
		</div>
	<div id="foot">
	</div>

	<!-- Modal Login -->
		<div id="modal-login">
			<div id="osx-modal-title">Login</div>
			<div class="close"><a href="#" class="simplemodal-close">X</a></div>
			<div id="osx-modal-data">
				<form action="/user/login" method="post" name="formLogin" id="formLogin">
				<ul>
					<li>E_mail: <input type="text" name="contactEmail"></input></li>
					<li>Senha: <input type="password" name="password"></input></li>
					<li><input type="submit" value="Enviar"></input></li>
					<li><a href="<%= userServiceG.createLoginURL("/user/login_ext") %>">Ou entre utilizando sua conta Google</a></li>
				</ul>
				</form>
			</div>
		</div>
		
	<!-- Modal Register -->
		<div id="modal-register">
			<div id="osx-modal-title">Registro</div>
			<div class="close"><a href="#" class="simplemodal-close">X</a></div>
			<div id="osx-modal-data">
				<form action="/user/add" method="post" onsubmit="return validaSenhaRepetida()" name="formReg" id="formReg">
				<ul>
					<li>E_mail: <input type="text" name="mail"></input></li>
					<li>Senha: <input type="password" name="pass1"></input></li>
					<li>Repita a Senha: <input type="password" name="pass2"></input></li>
					<li><input type="submit" value="Enviar"></input></li>
				</ul>
				</form >			
				</div>
		</div>
		
</body>
<script type="text/javascript" src="/media/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/media/js/jquery.simplemodal-1.3.5.min.js"></script>
<script type="text/javascript" src="/media/js/modal_login_osx.js"></script>
<script type="text/javascript" src="/media/js/modal_register_osx.js"></script>
<script type="text/javascript" src="/media/js/validators.js"></script>
</html>
