-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema jinbu
--

CREATE DATABASE IF NOT EXISTS jinbu;
USE jinbu;

--
-- Definition of table `jinbu`.`auth_group`
--

DROP TABLE IF EXISTS `jinbu`.`auth_group`;
CREATE TABLE  `jinbu`.`auth_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_group`
--

/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
LOCK TABLES `auth_group` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`auth_group_permissions`
--

DROP TABLE IF EXISTS `jinbu`.`auth_group_permissions`;
CREATE TABLE  `jinbu`.`auth_group_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`,`permission_id`),
  KEY `auth_group_permissions_425ae3c4` (`group_id`),
  KEY `auth_group_permissions_1e014c8f` (`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_group_permissions`
--

/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
LOCK TABLES `auth_group_permissions` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`auth_message`
--

DROP TABLE IF EXISTS `jinbu`.`auth_message`;
CREATE TABLE  `jinbu`.`auth_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `message` longtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_message_403f60f` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_message`
--

/*!40000 ALTER TABLE `auth_message` DISABLE KEYS */;
LOCK TABLES `auth_message` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_message` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`auth_permission`
--

DROP TABLE IF EXISTS `jinbu`.`auth_permission`;
CREATE TABLE  `jinbu`.`auth_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `content_type_id` (`content_type_id`,`codename`),
  KEY `auth_permission_1bb8f392` (`content_type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_permission`
--

/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
LOCK TABLES `auth_permission` WRITE;
INSERT INTO `jinbu`.`auth_permission` VALUES  (1,'Can add permission',1,'add_permission'),
 (2,'Can change permission',1,'change_permission'),
 (3,'Can delete permission',1,'delete_permission'),
 (4,'Can add group',2,'add_group'),
 (5,'Can change group',2,'change_group'),
 (6,'Can delete group',2,'delete_group'),
 (7,'Can add user',3,'add_user'),
 (8,'Can change user',3,'change_user'),
 (9,'Can delete user',3,'delete_user'),
 (10,'Can add message',4,'add_message'),
 (11,'Can change message',4,'change_message'),
 (12,'Can delete message',4,'delete_message'),
 (13,'Can add content type',5,'add_contenttype'),
 (14,'Can change content type',5,'change_contenttype'),
 (15,'Can delete content type',5,'delete_contenttype'),
 (16,'Can add session',6,'add_session'),
 (17,'Can change session',6,'change_session'),
 (18,'Can delete session',6,'delete_session'),
 (19,'Can add site',7,'add_site'),
 (20,'Can change site',7,'change_site'),
 (21,'Can delete site',7,'delete_site'),
 (22,'Can add usuario',8,'add_usuario'),
 (23,'Can change usuario',8,'change_usuario'),
 (24,'Can delete usuario',8,'delete_usuario'),
 (25,'Can add empresa',9,'add_empresa'),
 (26,'Can change empresa',9,'change_empresa'),
 (27,'Can delete empresa',9,'delete_empresa'),
 (28,'Can add categoria',10,'add_categoria'),
 (29,'Can change categoria',10,'change_categoria'),
 (30,'Can delete categoria',10,'delete_categoria'),
 (31,'Can add promocao',11,'add_promocao'),
 (32,'Can change promocao',11,'change_promocao'),
 (33,'Can delete promocao',11,'delete_promocao'),
 (34,'Can add oferta',12,'add_oferta'),
 (35,'Can change oferta',12,'change_oferta'),
 (36,'Can delete oferta',12,'delete_oferta'),
 (37,'Can add cupom',13,'add_cupom'),
 (38,'Can change cupom',13,'change_cupom'),
 (39,'Can delete cupom',13,'delete_cupom'),
 (40,'Can add log entry',14,'add_logentry'),
 (41,'Can change log entry',14,'change_logentry'),
 (42,'Can delete log entry',14,'delete_logentry');
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`auth_user`
--

DROP TABLE IF EXISTS `jinbu`.`auth_user`;
CREATE TABLE  `jinbu`.`auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(75) NOT NULL,
  `password` varchar(128) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `last_login` datetime NOT NULL,
  `date_joined` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_user`
--

/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
LOCK TABLES `auth_user` WRITE;
INSERT INTO `jinbu`.`auth_user` VALUES  (1,'rafael','','','rafael@jinbu.com.br','sha1$5b24a$048c67fb99c3c0a866ed2de3ba1be7f4125dec5b',1,1,1,'2011-07-15 09:41:12','2011-03-24 23:50:32'),
 (2,'jinbuser','Jinbu','User','jinbuser@jinbu.com.br','xkx',0,1,0,'2011-07-15 09:51:51','2011-07-15 09:51:51');
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`auth_user_groups`
--

DROP TABLE IF EXISTS `jinbu`.`auth_user_groups`;
CREATE TABLE  `jinbu`.`auth_user_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`group_id`),
  KEY `auth_user_groups_403f60f` (`user_id`),
  KEY `auth_user_groups_425ae3c4` (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_user_groups`
--

/*!40000 ALTER TABLE `auth_user_groups` DISABLE KEYS */;
LOCK TABLES `auth_user_groups` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_user_groups` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`auth_user_user_permissions`
--

DROP TABLE IF EXISTS `jinbu`.`auth_user_user_permissions`;
CREATE TABLE  `jinbu`.`auth_user_user_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`permission_id`),
  KEY `auth_user_user_permissions_403f60f` (`user_id`),
  KEY `auth_user_user_permissions_1e014c8f` (`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`auth_user_user_permissions`
--

/*!40000 ALTER TABLE `auth_user_user_permissions` DISABLE KEYS */;
LOCK TABLES `auth_user_user_permissions` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `auth_user_user_permissions` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_categoria`
--

DROP TABLE IF EXISTS `jinbu`.`core_categoria`;
CREATE TABLE  `jinbu`.`core_categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_categoria`
--

/*!40000 ALTER TABLE `core_categoria` DISABLE KEYS */;
LOCK TABLES `core_categoria` WRITE;
INSERT INTO `jinbu`.`core_categoria` VALUES  (5,'PRODUTO'),
 (4,'SERVICO'),
 (3,'BAR e RESTAURANTE'),
 (1,'LAZER');
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_categoria` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_cupom`
--

DROP TABLE IF EXISTS `jinbu`.`core_cupom`;
CREATE TABLE  `jinbu`.`core_cupom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promocao_id` int(11) NOT NULL,
  `oferta_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `core_cupom_6e19ebbc` (`promocao_id`),
  KEY `core_cupom_66f5f521` (`oferta_id`),
  KEY `core_cupom_363db8b9` (`usuario_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_cupom`
--

/*!40000 ALTER TABLE `core_cupom` DISABLE KEYS */;
LOCK TABLES `core_cupom` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_cupom` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_empresa`
--

DROP TABLE IF EXISTS `jinbu`.`core_empresa`;
CREATE TABLE  `jinbu`.`core_empresa` (
  `user_ptr_id` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  PRIMARY KEY (`user_ptr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_empresa`
--

/*!40000 ALTER TABLE `core_empresa` DISABLE KEYS */;
LOCK TABLES `core_empresa` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_empresa` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_oferta`
--

DROP TABLE IF EXISTS `jinbu`.`core_oferta`;
CREATE TABLE  `jinbu`.`core_oferta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promocao_id` int(11) NOT NULL,
  `data_expiracao` date NOT NULL,
  `adesao_minima` int(11) NOT NULL,
  `empresa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `core_oferta_6e19ebbc` (`promocao_id`),
  KEY `core_oferta_54324052` (`empresa_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_oferta`
--

/*!40000 ALTER TABLE `core_oferta` DISABLE KEYS */;
LOCK TABLES `core_oferta` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_oferta` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_oferta_usuarios`
--

DROP TABLE IF EXISTS `jinbu`.`core_oferta_usuarios`;
CREATE TABLE  `jinbu`.`core_oferta_usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oferta_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oferta_id` (`oferta_id`,`usuario_id`),
  KEY `core_oferta_usuarios_66f5f521` (`oferta_id`),
  KEY `core_oferta_usuarios_363db8b9` (`usuario_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_oferta_usuarios`
--

/*!40000 ALTER TABLE `core_oferta_usuarios` DISABLE KEYS */;
LOCK TABLES `core_oferta_usuarios` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_oferta_usuarios` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_promocao`
--

DROP TABLE IF EXISTS `jinbu`.`core_promocao`;
CREATE TABLE  `jinbu`.`core_promocao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` varchar(255) NOT NULL,
  `data_cadastro` date NOT NULL,
  `local` varchar(255) NOT NULL,
  `user_criacao_id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  `interessados` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `core_promocao_15b80b28` (`user_criacao_id`),
  KEY `core_promocao_64c3c188` (`categoria_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_promocao`
--

/*!40000 ALTER TABLE `core_promocao` DISABLE KEYS */;
LOCK TABLES `core_promocao` WRITE;
INSERT INTO `jinbu`.`core_promocao` VALUES  (5,'Depilação a Laser','2011-07-15','SP',2,'xxx2',4,171,1),
 (4,'Tequila a vontade','2011-07-15','SP',2,'xxx',3,25,1),
 (3,'Corrida de Kart','2011-07-15','SP',2,'xxx',1,30,1),
 (1,'Pular de Paraquedas','2011-07-15','SP',2,'xxx',1,5,1),
 (6,'Escondidinho de Carne Seca','2011-07-15','São Paulo',2,'xxx2',3,42,1),
 (7,'Um dia no SPA','2011-07-15','São Paulo',2,'xxx2',4,22,1),
 (8,'Personal Trainner','2011-07-15','São Paulo',2,'xxx',4,24,1),
 (9,'Cruzeiro Caribe','2011-07-15','São Paulo',2,'yyy',1,80,1),
 (10,'Comida Mexicana','2011-07-15','São Paulo',2,'www',3,10,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_promocao` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`core_usuario`
--

DROP TABLE IF EXISTS `jinbu`.`core_usuario`;
CREATE TABLE  `jinbu`.`core_usuario` (
  `user_ptr_id` int(11) NOT NULL,
  `t` int(11) NOT NULL,
  PRIMARY KEY (`user_ptr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`core_usuario`
--

/*!40000 ALTER TABLE `core_usuario` DISABLE KEYS */;
LOCK TABLES `core_usuario` WRITE;
INSERT INTO `jinbu`.`core_usuario` VALUES  (2,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `core_usuario` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`django_admin_log`
--

DROP TABLE IF EXISTS `jinbu`.`django_admin_log`;
CREATE TABLE  `jinbu`.`django_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) unsigned NOT NULL,
  `change_message` longtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `django_admin_log_403f60f` (`user_id`),
  KEY `django_admin_log_1bb8f392` (`content_type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`django_admin_log`
--

/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
LOCK TABLES `django_admin_log` WRITE;
INSERT INTO `jinbu`.`django_admin_log` VALUES  (1,'2011-07-15 09:54:24',1,11,'5','Pular de Paraquedas',2,'Modificado texto, data_cadastro e user_criacao.'),
 (2,'2011-07-15 09:54:45',1,11,'4','Tequila a vontade',2,'Modificado texto, data_cadastro e user_criacao.'),
 (3,'2011-07-15 09:55:04',1,11,'3','Corrida de Kart',2,'Modificado texto, data_cadastro e user_criacao.'),
 (4,'2011-07-15 09:55:24',1,11,'2','t2',3,''),
 (5,'2011-07-15 09:57:42',1,11,'5','Depilação a Laser',2,'Modificado texto.'),
 (6,'2011-07-15 10:05:22',1,11,'5','Depilação a Laser',2,'Modificado image.'),
 (7,'2011-07-15 10:06:09',1,11,'1','Pular de Paraquedas',2,'Modificado texto, data_cadastro e user_criacao.'),
 (8,'2011-07-15 10:08:02',1,11,'6','Escondidinho de Carne Seca',1,''),
 (9,'2011-07-15 10:08:39',1,11,'7','Um dia no SPA',1,''),
 (10,'2011-07-15 10:09:47',1,11,'8','Personal Trainner',1,''),
 (11,'2011-07-15 10:10:54',1,11,'9','Cruzeiro Caribe',1,''),
 (12,'2011-07-15 10:11:46',1,11,'10','Comida Mexicana',1,'');
UNLOCK TABLES;
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`django_content_type`
--

DROP TABLE IF EXISTS `jinbu`.`django_content_type`;
CREATE TABLE  `jinbu`.`django_content_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_label` (`app_label`,`model`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`django_content_type`
--

/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
LOCK TABLES `django_content_type` WRITE;
INSERT INTO `jinbu`.`django_content_type` VALUES  (1,'permission','auth','permission'),
 (2,'group','auth','group'),
 (3,'user','auth','user'),
 (4,'message','auth','message'),
 (5,'content type','contenttypes','contenttype'),
 (6,'session','sessions','session'),
 (7,'site','sites','site'),
 (8,'usuario','core','usuario'),
 (9,'empresa','core','empresa'),
 (10,'categoria','core','categoria'),
 (11,'promocao','core','promocao'),
 (12,'oferta','core','oferta'),
 (13,'cupom','core','cupom'),
 (14,'log entry','admin','logentry');
UNLOCK TABLES;
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`django_session`
--

DROP TABLE IF EXISTS `jinbu`.`django_session`;
CREATE TABLE  `jinbu`.`django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime NOT NULL,
  PRIMARY KEY (`session_key`),
  KEY `django_session_3da3d3d8` (`expire_date`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`django_session`
--

/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
LOCK TABLES `django_session` WRITE;
INSERT INTO `jinbu`.`django_session` VALUES  ('b376e65e4cf89f3a82a318e5835ce82e','MDU5ZTc4MTc0ZGQ1YzEwZTYxZWJjM2RlYTU5YjkyN2QzNTY4N2IzZTqAAn1xAShVEl9hdXRoX3Vz\nZXJfYmFja2VuZHECVSlkamFuZ28uY29udHJpYi5hdXRoLmJhY2tlbmRzLk1vZGVsQmFja2VuZHED\nVQ1fYXV0aF91c2VyX2lkcQSKAQF1Lg==\n','2011-07-29 09:41:13');
UNLOCK TABLES;
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;


--
-- Definition of table `jinbu`.`django_site`
--

DROP TABLE IF EXISTS `jinbu`.`django_site`;
CREATE TABLE  `jinbu`.`django_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jinbu`.`django_site`
--

/*!40000 ALTER TABLE `django_site` DISABLE KEYS */;
LOCK TABLES `django_site` WRITE;
INSERT INTO `jinbu`.`django_site` VALUES  (1,'example.com','example.com');
UNLOCK TABLES;
/*!40000 ALTER TABLE `django_site` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
