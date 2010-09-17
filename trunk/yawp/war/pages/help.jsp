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
			<li><a href="#oquee">O que é o EQTal?</a></li>
			<li><a href="#gae">O que é o Google AppEngine?</a></li>
			<li><a href="#social">Mais uma rede social?</a></li>
			<li><a href="#dif">E no que ela se difere das outras?</a></li>
			<li><a href="#culpado">Quem são os responsáveis?</a></li>
			<li><a href="#contato">Como eu entro em contato?</a></li>
		</ul>
		
		<h4>Como faço?</h4>
		<ul>	
			<li><a href="#perfil">Registrar-se/Criar Perfil</a></li>
			<li><a href="#auth">Autenticação</a></li>
			<li><a href="#criar_empresa">Criar Empresa</a></li>
			<li><a href="#busca_empresa">Busca por Empresa</a></li>
			<li><a href="#busca_pessoa">Busca por Pessoas</a></li>
			
		</ul>
		
		<div id="oquee" class="div_text_help">
			<h2>O que é o EQtal?</h2>
			<p>
				"EQtal é uma aplicação web que permite você recomendar, classificar os lugares que frequenta, compatilhar com seus amigos o que achou e também ver a opinião deles.<br/> 
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
				Em primeiro lugar foco, ela é destinada para um tipo de serviço específico e não algo tão abrangente como Orkut/Facebook.
				E também você por padrão viasualiza somente comentários de pessoas que confia.
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="culpado" class="div_text_help" >
			<h2>Quem são os responsáveis?</h2>
			<p>
				<a href="http://www.yaw.com.br" target="_blank">YaW Tecnologia®</a>
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="contato" class="div_text_help" >
			<h2>Como eu entro em contato?</h2>
			<p>
				Críticas, dúvidas e/ou sugestões: www.eqtal.com.br/contato
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		
		<div id="perfil" class="div_text_help" >
			<h2>Registrar-se ou Criar um perfil</h2>
			<p>
				Basta clicar no link 'Registrar' em qualquer página e informar seu usuário e senha. A sua conta é criada porém encontra-se desabilitada. Após o registro é enviado um e-mail para o endereço cadastrado com um link para que seja habilitada sua conta.<br/>
				Todavia, há a possibilidade de você se registrar utilizando a sua conta no Google, caso não queira decorar mais uma senha.<br/>
				As suas informações pessoais(foto pessoal, twitter, orkut, facebook) são incluídos após o registro.
				
			</p>
			<a href="#faq_title">Topo</a>
		</div>
		
		<div id="auth" class="div_text_help" >
			<h2>Autenticação</h2>
			<p>
				Há duas formas:
				A primeira delas como em toda aplicação, você pode autenticar-se com seu usuário(e-mail) e senha que registrou anteriormente.
				Ou então pode autenticar-se com sua conta Google(a autenticação é feita pelo próprio Google, em nenhum momento sua senha é transmitida para nossa aplicação)
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