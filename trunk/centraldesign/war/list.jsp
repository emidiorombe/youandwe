<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listagem</title>
</head>
<body>
<s:actionerror />
<h1>Listagem de Portf�lios</h1>
<s:iterator value="all">
    <s:property value="name"/> is the <s:property value="username"/><br>
</s:iterator>
</body>
</html>