<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false" pageEncoding="ISO-8859-1"%>
<%@page import="com.google.appengine.api.users.UserService"%>

<%  UserService userServiceG = com.google.appengine.api.users.UserServiceFactory.getUserService(); %>

<!-- Modal Login -->
		<div id="modal-login">
			<div id="osx-modal-title">${msg['login']}</div>
			<div class="close"><a href="#" class="simplemodal-close">X</a></div>
			<div id="osx-modal-data">
				<form action="/user/login" method="post" name="formLogin" id="formLogin">
				<table>
					<tr>
						<td>${msg['mail']}:</td>
						<td><input type="text" name="contactEmail" class="input_login"></input></td>
					</tr>
					<tr>
						<td>${msg['senha']}:</td>
						<td><input type="password" name="password" class="input_login"></input></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="${msg['enviar']}" style="float: right;"></input></td>
					</tr>
				</table>
				<a href="<%= userServiceG.createLoginURL("/user/login_ext") %>">${msg['entre_conta_google']}</a>
				</form>
			</div>
		</div>
		
	<!-- Modal Register -->
		<div id="modal-register">
			<div id="osx-modal-title">${msg['registrar']}</div>
			<div class="close"><a href="#" class="simplemodal-close">X</a></div>
			<div id="osx-modal-data">
				<form action="/user/add" method="post" onsubmit="return validaSenhaRepetida()" name="formReg" id="formReg">
				<table>
					<tr>
						<td>${msg['mail']}:</td>
						<td><input type="text" name="mail" class="input_login"></input></td>
					</tr>
					<tr>
						<td>${msg['senha']}:</td>
						<td><input type="password" name="pass1" class="input_login"></input></td>
					</tr>
					<tr>
						<td>${msg['re_senha']}:</td>
						<td><input type="password" name="pass2" class="input_login"></input></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="${msg['enviar']}" style="float: right;"></input></td>
					</tr>
				</table>
				<a href="<%= userServiceG.createLoginURL("/user/login_ext") %>">${msg['entre_conta_google']}</a>
				</form >			
				</div>
		</div>