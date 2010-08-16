<%@ page contentType="text/html; charset=ISO-8859-1" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>EQtal? - Ajuda</title>
	<jsp:include page="/pages/template/styles.jsp"></jsp:include>
	<link rel="stylesheet" href="/media/css/user.css" type="text/css" media='screen'/>
	</head>
<body>
<jsp:include page="/pages/template/head.jsp"></jsp:include>
<div id="content_help">
	<div id="faq_title">
		<h4>Sobre</h4>
		<ul>
			<li><a href="#oquee">O que � o EQTal?</a></li>
			<li><a href="#gae">O que � o Google AppEngine?</a></li>
			<li><a href="#maisum">Mais uma rede social?</a></li>
			<li><a href="#oquee">Quem � o culpado?</a></li>
		</ul>
		
		<h4>Como fa�o?</h4>
		<ul>	
			<li><a href="#auth">Autentica��o</a></li>
			<li><a href="#perfil">Criar Perfil</a></li>
			<li><a href="#criar_empresa">Criar Empresa</a></li>
			<li><a href="#busca_empresa">Busca por Empresa</a></li>
			<li><a href="#busca_pessoa">Busca por Pessoas</a></li>
			
		</ul>
		
		<div id="oquee" class="div_text_help">
			<h2>O que � o EQtal?</h2>
			<p>
				"EQtal � uma aplica��o web que permite voc� classificar, dar notas para os lugares que frequenta, compatilhar com seus amigos o que achou e tamb�m ver a opini�o deles.<br/> 
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
	</div>
	
</div>
<jsp:include page="/pages/template/foot.jsp"></jsp:include>
</body>
<jsp:include page="/pages/template/scripts.jsp"></jsp:include>
<script type='text/javascript' src='/media/js/basic.js'></script>
</html>