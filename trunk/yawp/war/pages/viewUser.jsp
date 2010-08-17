<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eq" tagdir="/WEB-INF/tags/" %> 

<html>
	<head>
	<title>EQtal? - Profile Usuário -</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	<link type='text/css' href='/media/css/basic.css' rel='stylesheet' media='screen' />
	
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>

<div id="content">
	<div id="uinfo">
		<div id="uphoto">
		<ul>
			<c:if test="${user.avatar == null}">
				<li><img alt="${user.name}" src="/media/img/photo_default.jpg" ></li>
			</c:if>
			<c:if test="${user.avatar != null}">
				<li><img alt="${user.name}" src="/blob/avatar/${user.key.id}" ></li>
			</c:if>
			<c:if test="${loggedUser.key.id == user.key.id}">
				<li><a href="/user/edit/${user.key.id}">${msg['editar_perfil']}</a></li>
			</c:if>
			<c:if test="${loggedUser != null && loggedUser.key.id != user.key.id && !already_friend}">
				<li><a href="/user/add_contact/${user.key.id}">${msg['adicionar_contato']}</a></li>
			</c:if>
			<c:if test="${loggedUser != null && loggedUser.key.id != user.key.id && already_friend}">
				<li><a href="/user/remove_contact/${user.key.id}">${msg['remover_contato']}</a></li>
			</c:if>
			
			
			<li><a href="#" class="basic">${msg['contatos']} (${user.qtdeContatos})</a></li>
		</ul>
		</div>
		<div id="udata">
			<ul id="uname">
				<li><h2>${user.name}</h2></li>
			</ul>
			<ul id="usocial">
				<li>${msg['twitter']}: ${user.twit}</li>
				<li>${msg['orkut']}:</li>
				<li>${msg['fcbook']}:</li>
			</ul>
		</div>
	</div>
	<div id="ucomments">
		<div id="uc_title">
			<h2>${msg['comentarios']}(${c_comments_size})</h2>
		</div>
		<div id="uc_comment">
			<c:forEach var="com" items="${c_comments}">
				<div id="user_c">
					<p>${com.textValue}</p>
					<h5>Sobre <a href='/company/list/${com.company}'><eq:companyName idCompany="${com.company}"/></a></h5>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div id="basic-modal-content">
<h3>Contatos de ${user.name}</h3>
<c:forEach var="f" items="${c_friends}"	>
	Amigo: ${f.name}<br/>
</c:forEach>
</div>

<!-- preload the images -->
<div style='display: none'><img src='/media/img/x.png' alt='' /></div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script>


</html>