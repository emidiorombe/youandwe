<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.google.appengine.api.users.UserService"%>

<%  UserService userServiceG = com.google.appengine.api.users.UserServiceFactory.getUserService(); %>

<html>
<head>
	<title>EQtal? - Autentique-se</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<div id="content">
	<div style="margin-left: 470px;">
		<h4>Autentique-se ou Registre-se</h4>
		<br>
		<h5>${msg[msgErro]}</h5>
	</div>
</div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script></html>