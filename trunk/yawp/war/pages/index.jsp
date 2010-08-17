<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="br.com.yaw.entity.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%@ taglib prefix="eq" tagdir="/WEB-INF/tags/" %> 

<%  UserService userServiceG = com.google.appengine.api.users.UserServiceFactory.getUserService(); %>

<html>
<head>
	<title>EQTAL?</title>
	<jsp:include page="/pages/template/home_style.jsp"></jsp:include>
</head>
<body>
	<div id="header">
		<div id="img_logo">
			<a title="P�gina Inicial" href="/"><img src="/media/img/logo7.png" ></a>
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
				<h2>${loggedUser.name == null ? loggedUser.contactEmail : loggedUser.name} / <a href="<%= userServiceG.createLogoutURL("/user/logout")%>" style="font-size: 0.7em;">${msg['logout']}</a></h2>
			</div>
		</c:if>
	</div>
	
	<div id="nav">
		<div id="nav_search">
			<form action="/company/search" method="post">
			<ul>
				<li><input type="text" name="q_cat" class="input_search" value="${msg['txt_busca_empresa']}" onclick="cleanSearchBox(this)"></li>
				<li>${msg['em']}</li>
				<li><input type="text" name="q_onde" class="input_search" value="${msg['txt_busca_cidade']}" onclick="cleanSearchBox(this)"></li>
			</ul>
			</form>
		</div>
		<div id="nav_link">
			<ul>
				<li><a href='<%=session.getAttribute("loggedUser") == null ? "#" : "/user/list/"+((User)session.getAttribute("loggedUser")).getKey().getId()%>'>${msg['perfil']}</a></li>
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
				<img alt="" src="/media/img/personagem_01.jpg" style="width:220px; height:340;z-index:2;">
				<img alt="" src="/media/img/personagem_02.jpg" style="width:220px; height:340;z-index:3;">
				<img alt="" src="/media/img/personagem_03.jpg" style="width:220px; height:340;z-index:4;">
			</div>
		</div>
	</div>
	<div id="latest">
			<div id="head_latest">
				<h3 onclick="loadLatestComments()">${msg['ultimos_coments']}</h3>
			</div>
			<div id="body_latest">
				<c:forEach var="c" items="${lt_comment}">
						<div style="border-bottom: 1px solid #CCC; padding-bottom: 20px; margin-top: 5px;">
							<div>
								<a href="#"><img src="/blob/avatar/1" style="width: 50px; height: 50px; margin-left: 5px;"></a>
							</div>
							<div style="margin-left: 59px; margin-top: -75px;">
								<h5 style="font-family: 'Trebuchet MS'; font-weight: normal; color: #303030">${c.textValue}</h5>
								<h6>${msg['em']} <fmt:formatDate value="${c.dtComment}" type="date" pattern="dd/MM/yyyy"/> sobre <a href='/company/list/${c.company}'><eq:companyName idCompany="${c.company}"/></a></h6>
							</div>
						</div>
				</c:forEach>
			</div>
	</div>
	
	<jsp:include page="/pages/template/modal_login_register.jsp"></jsp:include>
	
	<jsp:include page="/pages/template/foot.jsp"></jsp:include>
	
	
		<jsp:include page="/pages/template/home_scripts.jsp"></jsp:include>
</body>

</html>
