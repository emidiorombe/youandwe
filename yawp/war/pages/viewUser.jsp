<%@ page language="java" isELIgnored="false" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.yaw.entity.User"%>
<%@page import="com.google.appengine.api.images.ImagesService"%>
<%@page import="com.google.appengine.api.blobstore.BlobKey"%>
<%@page import="com.google.appengine.api.images.ImagesServiceFactory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eq" tagdir="/WEB-INF/tags/" %> 

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>EQtal? - Profile Usuário -</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	<link type='text/css' href='/media/css/basic.css' rel='stylesheet' media='screen' />
	<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
	<script type='text/javascript' src='/media/js/basic.js'></script>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>

<%ImagesService imgS = ImagesServiceFactory.getImagesService(); 
User us1 =  (User)request.getAttribute("user");	

%>

<div id="content_user">
	<div id="uinfo">
		<div id="uphoto">
		<ul>
			<c:if test="${user.avatar == null}">
				<li><img alt="${user.name}" src="/media/img/photo_default.jpg" ></li>
			</c:if>
			<c:if test="${user.avatar != null}">
				<li><eq:userImg userImg="${user.avatar}" size="120" /></li>
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
				<li>${msg['twitter']}: <a href="http://twitter.com/${user.twit}" target="_blank">${user.twit}</a></li>
				<li>${msg['orkut']}:</li>
				<li>${msg['fcbook']}:</li>
			</ul>
		</div>
		<c:if test="${loggedUser != null && loggedUser.key == user.key && !loggedUser.approved}">
			<h4>${msg['remember_mail_enable']}</h4>
			<h4><a href="/user/send_remember_mail/${loggedUser.key.id}" id="link_remember">${msg['send_remember_mail_enable']}</a></h4>
		</c:if>
		
	</div>
	<div id="ucomments">
		<div id="uc_title">
			<h2>${msg['comentarios']}(${c_comments_size})</h2>
		</div>
		<div id="uc_comment">
			<c:forEach var="com" items="${c_comments}">
				<div class="user_c">
					<h3>Sobre <a href='/company/list/${com.company}'><eq:companyName idCompany="${com.company}"/></a></h3>
					<div id="star_${com.key.id}" style="margin-top: -15px;">
					<script type="text/javascript">
						jQuery(function ($){
							$('#star_${com.key.id}').raty({
								path: '/media/img/',
								hintList:     ['Nao recomendo', 'Regular', 'Bom', 'Muito Bom', 'Recomendadissimo'],
								readOnly:     true, 
								start: ${com.rating}
							});
						});
					</script>
				</div>
					<p>${com.textValue}</p>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div id="basic-modal-content">
<h3>Contatos de ${user.name}</h3>
<c:forEach var="f" items="${c_friends}"	>
	<a href="/user/list/${f.key.id}"><eq:userImg userId="${f.key.id}" size="64" /></a>
	<a href="/user/list/${f.key.id}">${f.name}</a><br/>
</c:forEach>
</div>

<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>

</html>