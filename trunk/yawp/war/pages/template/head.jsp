<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="br.com.yaw.entity.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%  UserService userServiceG = com.google.appengine.api.users.UserServiceFactory.getUserService(); %>

<link rel="stylesheet" href="/media/css/head_style.css" type="text/css" media='screen'/>

<div id="head_nav">
	<div id="head_nav_logo">
		<img src="/media/img/logo_peq1.jpg">
	</div>
	<div id="head_nav_auth">
		<c:if test="${loggedUser == null}">
		<ul>
			<li><a href="#" class='logmodal'>${msg['login']} </a> / <a href="#" class="regmodal">${msg['registrar']} </a> </li>
		</ul>
		</c:if>
		<c:if test="${loggedUser != null}">
			<ul>
				<li><h3 class='userLabel'>${loggedUser.name == null ? loggedUser.contactEmail : loggedUser.name} / <a href="<%= userServiceG.createLogoutURL("/user/logout")%>" >${msg['logout']} </a> </h3></li>
			</ul>
		</c:if>
	</div>
	<div id="head_nav_bar">
		<div id="head_nav_bar_links">
			<ul>
				<li><a href="/">${msg['home']}</a></li>
				<li><a href='<%=session.getAttribute("loggedUser") == null ? "#" : "/user/list/"+((User)session.getAttribute("loggedUser")).getKey().getId()%>'>${msg['perfil']}</a></li>
				<li><a href="/company/add">${msg['cadastrar_empresa']}</a></li>
				<li><a href="/geral/busca">${msg['buscar']}</a></li>
				<li><a href="/geral/help">${msg['socorro']}</a></li>
			</ul>
		</div>
	</div>
</div>

<jsp:include page="/pages/template/modal_login_register.jsp"></jsp:include>