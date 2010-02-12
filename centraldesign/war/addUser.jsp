<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add User</title>
</head>
<body>
<s:actionerror />
<s:actionmessage/>
<h1>Adicionar Usuario</h1>
<s:form action="add" method="post">
	<s:textfield name="name" label="Name"></s:textfield>
	<s:textfield name="desc" label="Description"></s:textfield>
	<s:textfield name="mail" label="E-mail"></s:textfield>
	<s:textfield name="url" label="Site"></s:textfield>
	<s:textfield name="tags" label="Tags"></s:textfield>
	<s:submit name="Salvar" value="Salvar"></s:submit>
</s:form>
</body>
</html>