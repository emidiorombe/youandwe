<%@ page language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>EQtal? - Ajuda</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<div id="content_help">
	<div id="faq_title">
		<h4>Sobre</h4>
		<ul>
			<li><a href="#oquee">O que � o EQTal?</a></li>
			<li><a href="#gae">O que � o Google AppEngine?</a></li>
			<li><a href="#social">Mais uma rede social?</a></li>
			<li><a href="#dif">E no que ela se difere das outras?</a></li>
			<li><a href="#culpado">Quem s�o os respons�veis?</a></li>
			<li><a href="#contato">Como eu entro em contato?</a></li>
		</ul>
		
		<h4>Como fa�o?</h4>
		<ul>	
			<li><a href="#perfil">Registrar-se/Criar Perfil</a></li>
			<li><a href="#auth">Autentica��o</a></li>
			<li><a href="#criar_empresa">Criar Empresa</a></li>
			<li><a href="#busca_empresa">Busca por Empresa</a></li>
			<li><a href="#busca_pessoa">Busca por Pessoas</a></li>
			
		</ul>
		
		<div id="oquee" class="div_text_help">
			<h2>O que � o EQtal?</h2>
			<p>
				"EQtal � uma aplica��o web que permite voc� recomendar, classificar os lugares que frequenta, compatilhar com seus amigos o que achou e tamb�m ver a opini�o deles.<br/> 
				 Por exemplo, imagine que voc� foi em um restaurante do qual gostou muito. Voc� diz o que achou, o por que gostou, coloca algumas fotos que tirou no lugar e convida seus amigos para irem juntos na pr�xima semana.<br/>
				 O EQtal tamb�m foi criado como o caso de estudo de um <a href="" target="_blank">curso sobre o Google AppEngine</a> utilizando a plataforma Java"
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="gae" class="div_text_help" >
			<h2>O que � o Google AppEngine?</h2>
			<p>
				"Google AppEngine � uma plataforma e/ou servi�o(PaaS - Platform as a Service) criado pelo Google que nos permite criar aplica��es web e hosped�las utilizando a infraestrutura, bancos de dados e servidores de aplica��o do pr�prio Google.<br/>
				 Se tiver mais interesse procure em: http://www.yaw.com.br/articles/gae"
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="social" class="div_text_help" >
			<h2>Mais uma rede social?</h2>
			<p>
				Sim. :-)
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="dif" class="div_text_help" >
			<h2>E no que ela se difere das outras?</h2>
			<p>
				Em primeiro lugar foco, ela � destinada para um tipo de servi�o espec�fico e n�o algo t�o abrangente como Orkut/Facebook.
				E tamb�m voc� por padr�o viasualiza somente coment�rios de pessoas que confia.
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="culpado" class="div_text_help" >
			<h2>Quem s�o os respons�veis?</h2>
			<p>
				<a href="http://www.yaw.com.br" target="_blank">YaW Tecnologia�</a>
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="contato" class="div_text_help" >
			<h2>Como eu entro em contato?</h2>
			<p>
				Cr�ticas, d�vidas e/ou sugest�es: www.eqtal.com.br/contato
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		
		<div id="perfil" class="div_text_help" >
			<h2>Registrar-se ou Criar um perfil</h2>
			<p>
				Basta clicar no link 'Registrar' em qualquer p�gina e informar seu usu�rio e senha. A sua conta � criada por�m encontra-se desabilitada. Ap�s o registro � enviado um e-mail para o endere�o cadastrado com um link para que seja habilitada sua conta.<br/>
				Todavia, h� a possibilidade de voc� se registrar utilizando a sua conta no Google, caso n�o queira decorar mais uma senha.<br/>
				As suas informa��es pessoais(foto pessoal, twitter, orkut, facebook) s�o inclu�dos ap�s o registro.
				
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="auth" class="div_text_help" >
			<h2>Autentica��o</h2>
			<p>
				H� duas formas:
				A primeira delas como em toda aplica��o, voc� pode autenticar-se com seu usu�rio(e-mail) e senha que registrou anteriormente.
				Ou ent�o pode autenticar-se com sua conta Google(a autentica��o � feita pelo pr�prio Google, em nenhum momento sua senha � transmitida para nossa aplica��o)
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		
	</div>
	
</div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script>
</html>