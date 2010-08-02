<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%  com.google.appengine.api.users.UserService userServiceG = com.google.appengine.api.users.UserServiceFactory.getUserService(); %>

<html>
<jsp:include page="/pages/template/header.jsp"></jsp:include>
<body>
	<div id="header">
		<div id="img_logo">
			<a title="Página Inicial" href="/"><img src="/media/img/logo7.png" ></a>
		</div>
		<c:if test="${loggedUser == null}">
			<div id="dvlog">
					<ul>
						<li><a href="#" class='logmodal'>${msg['login']} </a> / <a href="#" class="regmodal">${msg['registrar']} </a> </li>
					</ul>
			</div>
		</c:if>
		<c:if test="${loggedUser != null}">
			<div id="dvlog">
				<h2>${loggedUser.name == null ? loggedUser.contactEmail : loggedUser.name} / <a href="<%= userServiceG.createLogoutURL("/user/logout")%>" style="font-size: 0.7em;">logout</a></h2>
			</div>
		</c:if>
	</div>
	
	<div id="nav">
		<div id="nav_search">
			<form action="/company/search" method="post">
			<ul>
				<li><input type="text" name="q_cat" class="input_search" value="ex: Empresa, Categoria" onclick="cleanSearchBox(this)"></li>
				<li>${msg['em']}</li>
				<li><input type="text" name="q_onde" class="input_search" value="ex: São Paulo, Curitiba" onclick="cleanSearchBox(this)"></li>
			</ul>
			</form>
		</div>
		<div id="nav_link">
			<ul>
				<li><a href="/user/list/${loggedUser != null ? loggedUser.key.id : 0}">${msg['perfil']}</a></li>
				<li><a href="/company/add">${msg['cadastrar_empresa']}</a></li>
				<li><a href="/geral/help">${msg['socorro']}</a></li>
			</ul>
		</div>
	</div>
	<div id="center">
		<div id="home_center">
			<div id="home_center_head">
				<div id="sp1">
					<h3>${msg['gostou_recomenda']}</h3>
				</div>
				<div id="sp2">
					<h3>${msg['conta_pra_gente']}</h3>
				</div>
			</div>
			<div id="home_center_body">
				
			</div>
		</div>
	</div>
	<div id="latest">
			<div id="head_latest">
				<h3 onclick="loadLatestComments()">Últimos comentários</h3>
			</div>
			<div id="body_latest">
				
			</div>
	</div>
	
	<div id="foot">
		<hr/>
		<a href="http://www.yaw.com.br" target="_blank"><img alt="YaW Tecnologia" src="/media/img/yaw_logo_p1.png"></a>
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
					<li><a href="<%= userServiceG.createLoginURL("/user/login_ext") %>">Ou entre utilizando sua conta Google</a></li>
				</ul>
				</form >			
				</div>
		</div>
		<div id="footer">
			<jsp:include page="/pages/template/footer.jsp"></jsp:include>
		</div>		
</body>
</html>
