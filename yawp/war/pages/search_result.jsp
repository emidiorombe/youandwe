<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eq" tagdir="/WEB-INF/tags/" %> 

<html>
<head>
	<title>EQtal? - Resultado Busca</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<div id="content">
	
	<c:if test="${us_by_mail != null}">
		<div>
			<eq:userImg userImg="${us_by_mail.avatar}" size="120"/>
			<a href="/user/list/${us_by_mail.key.id}">${us_by_mail.name == null ? us_by_mail.contactEmail : us_by_mail.name}</a>
		</div>
	</c:if>
	
	
	<c:forEach var="com" items="${lista_co}">
		<div>
			<img src="<eq:urlImage blobKey="${com.logo}" size="120" />" />
			<a href="/company/list/${com.key.id}">${com.name}</a><br/>
		</div>
	</c:forEach>
	
	
	<c:forEach var="us" items="${list_users}">
		<div>
			<eq:userImg userImg="${us.avatar}" size="120"/>
			<a href="/user/list/${us.key.id}">${us.name == null ? us.contactEmail : us.name}</a><br/>
		</div>
	</c:forEach>
	
	
	

</div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
</html>
