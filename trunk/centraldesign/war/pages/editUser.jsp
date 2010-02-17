<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit User</title>
</head>
<body>
<s:actionerror />
<s:actionmessage/>
<s:bean name="br.com.yaw.entity.User" id="user"></s:bean>
<h1>Editar Usuario</h1>
<s:form action="update" method="post">
	<s:textfield name="name" label="Name" value="%{user.name}"></s:textfield>
	<s:textfield name="desc" label="Description" value="%{user.description}"></s:textfield>
	<s:textfield name="mail" label="E-mail" value="%{user.contactEmail}"></s:textfield>
	<s:textfield name="url" label="Site" value="%{user.url}"></s:textfield>
	<s:textfield name="tags" label="Tags" value="%{user.tags}"></s:textfield>
	<s:hidden name="id" value="%{user.key.id}"></s:hidden>
	<s:submit name="Salvar" value="Salvar"></s:submit>
</s:form>
</body>
</html>