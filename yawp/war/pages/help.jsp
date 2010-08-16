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
			<li><a href="#oquee">O que é o EQTal?</a></li>
			<li><a href="#gae">O que é o Google AppEngine?</a></li>
			<li><a href="#maisum">Mais uma rede social?</a></li>
			<li><a href="#oquee">Quem é o culpado?</a></li>
		</ul>
		
		<h4>Como faço?</h4>
		<ul>	
			<li><a href="#auth">Autenticação</a></li>
			<li><a href="#perfil">Criar Perfil</a></li>
			<li><a href="#criar_empresa">Criar Empresa</a></li>
			<li><a href="#busca_empresa">Busca por Empresa</a></li>
			<li><a href="#busca_pessoa">Busca por Pessoas</a></li>
			
		</ul>
		
		<div id="oquee" class="div_text_help">
			<h2>O que é o EQtal?</h2>
			<p>
				"EQtal é uma aplicação web que permite você classificar, dar notas para os lugares que frequenta, compatilhar com seus amigos o que achou e também ver a opinião deles.<br/> 
				 Por exemplo, imagine que você foi em um restaurante do qual gostou muito. Você diz o que achou, o por que gostou, coloca algumas fotos que tirou no lugar e convida seus amigos para irem juntos na próxima semana.<br/>
				 O EQtal também foi criado como o caso de estudo de um <a href="" target="_blank">curso sobre o Google AppEngine</a> utilizando a plataforma Java"
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="gae" class="div_text_help" >
			<h2>O que é o Google AppEngine?</h2>
			<p>
				"Google AppEngine é uma plataforma e/ou serviço(PaaS - Platform as a Service) criado pelo Google que nos permite criar aplicações web e hospedálas utilizando a infraestrutura, bancos de dados e servidores de aplicação do próprio Google.<br/>
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