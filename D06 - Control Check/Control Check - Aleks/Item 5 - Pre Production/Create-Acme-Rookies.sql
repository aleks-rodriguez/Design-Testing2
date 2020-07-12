﻿start transaction;
drop database if exists `Acme-Rookies`;
create database `Acme-Rookies`;
use `Acme-Rookies`;
create user 'acme-user'@'%'identified by 'ACME-Us3r-P@ssw0rd';
create user 'acme-manager'@'%' identified by 'ACME-M@n@ger-6874';
grant select, insert, update, delete on `Acme-Rookies`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables,
lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Rookies`.* to 'acme-manager'@'%';
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Rookies
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor_boxes`
--

DROP TABLE IF EXISTS `actor_boxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_boxes` (
  `actor` int(11) NOT NULL,
  `boxes` int(11) NOT NULL,
  UNIQUE KEY `UK_6n6psqivvjho155qcf9kjvv1h` (`boxes`),
  CONSTRAINT `FK_6n6psqivvjho155qcf9kjvv1h` FOREIGN KEY (`boxes`) REFERENCES `box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_boxes`
--

LOCK TABLES `actor_boxes` WRITE;
/*!40000 ALTER TABLE `actor_boxes` DISABLE KEYS */;
INSERT INTO `actor_boxes` VALUES (195,164),(195,165),(195,166),(195,167),(195,168),(196,169),(196,170),(196,171),(196,172),(196,173),(197,174),(197,175),(197,176),(197,177),(197,178),(218,179),(218,180),(218,181),(218,182),(218,183),(219,184),(219,185),(219,186),(219,187),(219,188),(196,189),(229,245),(229,246),(229,247),(229,248),(229,249),(230,250),(230,251),(230,252),(230,253),(230,254),(231,255),(231,256),(231,257),(231,258),(231,259),(232,260),(232,261),(232,262),(232,263),(232,264);
/*!40000 ALTER TABLE `actor_boxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_profiles`
--

DROP TABLE IF EXISTS `actor_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_profiles` (
  `actor` int(11) NOT NULL,
  `profiles` int(11) NOT NULL,
  UNIQUE KEY `UK_hdd4x67ooucg6f2yfe4edbs52` (`profiles`),
  CONSTRAINT `FK_hdd4x67ooucg6f2yfe4edbs52` FOREIGN KEY (`profiles`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_profiles`
--

LOCK TABLES `actor_profiles` WRITE;
/*!40000 ALTER TABLE `actor_profiles` DISABLE KEYS */;
INSERT INTO `actor_profiles` VALUES (195,198),(195,199);
/*!40000 ALTER TABLE `actor_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2a5vcjo3stlfcwadosjfq49l1` (`user_account_id`),
  CONSTRAINT `FK_2a5vcjo3stlfcwadosjfq49l1` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (195,0,'',134,'2018-12-15','John Doe','VISA','1111222233334444','admin<admin@gmail.com>','Admin1','955582516','','\0','Admin1','0.21',190);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `explanation` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (220,0,'explanation','http://www.us.es');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `application_moment` date DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `answer` int(11) DEFAULT NULL,
  `curricula` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `problem` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lvkbgejfw0xfg2m80h1bjjmau` (`answer`),
  KEY `FK_hsw5exxa4qe3jxi8xdhnn0dl5` (`curricula`),
  KEY `FK_cqpb54jon3yjef814oj6g6o4n` (`position`),
  KEY `FK_7gn6fojv7rim6rxyglc6n9mt2` (`problem`),
  KEY `FK_dq1om37bx4hgli24rbkjo2n7` (`rookie`),
  CONSTRAINT `FK_dq1om37bx4hgli24rbkjo2n7` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_7gn6fojv7rim6rxyglc6n9mt2` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_cqpb54jon3yjef814oj6g6o4n` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_hsw5exxa4qe3jxi8xdhnn0dl5` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_lvkbgejfw0xfg2m80h1bjjmau` FOREIGN KEY (`answer`) REFERENCES `answer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (269,0,'2017-12-15','2017-12-15','SUBMITTED',220,222,213,201,218);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `final_mode` bit(1) NOT NULL,
  `moment` date DEFAULT NULL,
  `score` double DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `auditor` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3m6p53wfvy7kcl4f4fvphkh9h` (`auditor`),
  KEY `FK_bumsxo4hc038y25pbfsinc38u` (`position`),
  CONSTRAINT `FK_bumsxo4hc038y25pbfsinc38u` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_3m6p53wfvy7kcl4f4fvphkh9h` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (236,0,'\0','2019-12-15',5,'testingText',229,213),(237,0,'','2018-12-15',7,'testingText2',229,213),(238,0,'','2018-12-15',8,'testingText3',230,217),(239,0,'','2018-12-15',8,'testingText4',229,213),(240,0,'','2018-12-15',5,'testingText5',229,217),(241,0,'','2018-12-15',5,'testingText5',229,217),(242,0,'','2019-03-30',5,'testingText6',229,217);
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_clqcq9lyspxdxcp6o4f3vkelj` (`user_account_id`),
  CONSTRAINT `FK_clqcq9lyspxdxcp6o4f3vkelj` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (229,0,'',182,'2017-12-15','John Doe','MASTERCARD','1111222233334444','auditor1@us.es','auditor1','955582516','','\0','auditor1','12345678Z',225),(230,0,'',182,'2017-12-15','John Doe','MASTERCARD','1111222233334444','auditor2@us.es','auditor2','955582516','','\0','auditor2','12345678Z',226);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `box`
--

DROP TABLE IF EXISTS `box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `box` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `from_system` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `box`
--

LOCK TABLES `box` WRITE;
/*!40000 ALTER TABLE `box` DISABLE KEYS */;
INSERT INTO `box` VALUES (164,0,'','In Box'),(165,0,'','Out Box'),(166,0,'','Spam Box'),(167,0,'','Trash Box'),(168,0,'','Notification Box'),(169,0,'','In Box'),(170,0,'','Out Box'),(171,0,'','Spam Box'),(172,0,'','Trash Box'),(173,0,'','Notification Box'),(174,0,'','In Box'),(175,0,'','Out Box'),(176,0,'','Spam Box'),(177,0,'','Trash Box'),(178,0,'','Notification Box'),(179,0,'','In Box'),(180,0,'','Out Box'),(181,0,'','Spam Box'),(182,0,'','Trash Box'),(183,0,'','Notification Box'),(184,0,'','In Box'),(185,0,'','Out Box'),(186,0,'','Spam Box'),(187,0,'','Trash Box'),(188,0,'','Notification Box'),(189,0,'\0','Box'),(245,0,'','In Box'),(246,0,'','Out Box'),(247,0,'','Spam Box'),(248,0,'','Trash Box'),(249,0,'','Notification Box'),(250,0,'','In Box'),(251,0,'','Out Box'),(252,0,'','Spam Box'),(253,0,'','Trash Box'),(254,0,'','Notification Box'),(255,0,'','In Box'),(256,0,'','Out Box'),(257,0,'','Spam Box'),(258,0,'','Trash Box'),(259,0,'','Notification Box'),(260,0,'','In Box'),(261,0,'','Out Box'),(262,0,'','Spam Box'),(263,0,'','Trash Box'),(264,0,'','Notification Box');
/*!40000 ALTER TABLE `box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `box_boxes`
--

DROP TABLE IF EXISTS `box_boxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `box_boxes` (
  `box` int(11) NOT NULL,
  `boxes` int(11) NOT NULL,
  UNIQUE KEY `UK_q58rwalgem56q7sovn3wx0g8r` (`boxes`),
  KEY `FK_9knmuvy78l7w7af8jp96ntggn` (`box`),
  CONSTRAINT `FK_9knmuvy78l7w7af8jp96ntggn` FOREIGN KEY (`box`) REFERENCES `box` (`id`),
  CONSTRAINT `FK_q58rwalgem56q7sovn3wx0g8r` FOREIGN KEY (`boxes`) REFERENCES `box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `box_boxes`
--

LOCK TABLES `box_boxes` WRITE;
/*!40000 ALTER TABLE `box_boxes` DISABLE KEYS */;
/*!40000 ALTER TABLE `box_boxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  `commercial_name` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6ew1r5yvuegqegy2os5gtp723` (`user_account_id`),
  CONSTRAINT `FK_6ew1r5yvuegqegy2os5gtp723` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (196,0,'',138,'2017-12-15','John Doe','MASTERCARD','1111222233334444','company@us.es','Company1','955582516','','\0','Company1','12345678Z',191,'CommercialName',0),(197,0,'',182,'2017-12-15','John Doe','FLY','1111222233334444','company@us.es','Company2','955875516','','\0','Company2','12345678Z',192,'CommercialName',0);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula`
--

DROP TABLE IF EXISTS `curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `copy` bit(1) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `github_profile` varchar(255) DEFAULT NULL,
  `linked_in_profile` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lq4kfcvf5vufwsng4apko2wd` (`rookie`),
  CONSTRAINT `FK_lq4kfcvf5vufwsng4apko2wd` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula`
--

LOCK TABLES `curricula` WRITE;
/*!40000 ALTER TABLE `curricula` DISABLE KEYS */;
INSERT INTO `curricula` VALUES (221,0,'\0','rookie1 rookie1','http://www.github.com','http://www.linkedin.com','955251415','statement',218),(222,0,'','Rookie1 Rookie1','http://www.github.com','http://www.linkedin.com','955251415','statement',218);
/*!40000 ALTER TABLE `curricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_education_data`
--

DROP TABLE IF EXISTS `curricula_education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_education_data` (
  `curricula` int(11) NOT NULL,
  `education_data` int(11) NOT NULL,
  UNIQUE KEY `UK_r552kg3pwybsy7igk77depn9l` (`education_data`),
  KEY `FK_a133bnrmd36opa9yi2dvx0rly` (`curricula`),
  CONSTRAINT `FK_a133bnrmd36opa9yi2dvx0rly` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_r552kg3pwybsy7igk77depn9l` FOREIGN KEY (`education_data`) REFERENCES `education_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_education_data`
--

LOCK TABLES `curricula_education_data` WRITE;
/*!40000 ALTER TABLE `curricula_education_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula_education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_miscellaneous_data`
--

DROP TABLE IF EXISTS `curricula_miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_miscellaneous_data` (
  `curricula` int(11) NOT NULL,
  `miscellaneous_data` int(11) NOT NULL,
  UNIQUE KEY `UK_12gfi193l4ocrk6r69ckn9ee5` (`miscellaneous_data`),
  KEY `FK_dfsfu2upc4v3tudsfcpnu9whf` (`curricula`),
  CONSTRAINT `FK_dfsfu2upc4v3tudsfcpnu9whf` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_12gfi193l4ocrk6r69ckn9ee5` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_miscellaneous_data`
--

LOCK TABLES `curricula_miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `curricula_miscellaneous_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula_miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_positions_data`
--

DROP TABLE IF EXISTS `curricula_positions_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_positions_data` (
  `curricula` int(11) NOT NULL,
  `positions_data` int(11) NOT NULL,
  UNIQUE KEY `UK_6kc8elssjem0rcj4h0bjxdhpb` (`positions_data`),
  KEY `FK_9ajv9qkalgsqliq5h8cl59ydc` (`curricula`),
  CONSTRAINT `FK_9ajv9qkalgsqliq5h8cl59ydc` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_6kc8elssjem0rcj4h0bjxdhpb` FOREIGN KEY (`positions_data`) REFERENCES `position_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_positions_data`
--

LOCK TABLES `curricula_positions_data` WRITE;
/*!40000 ALTER TABLE `curricula_positions_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula_positions_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system`
--

DROP TABLE IF EXISTS `customisation_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `flat_rate` double DEFAULT NULL,
  `hours_finder` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `phone_prefix` int(11) DEFAULT NULL,
  `result_finder` int(11) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system`
--

LOCK TABLES `customisation_system` WRITE;
/*!40000 ALTER TABLE `customisation_system` DISABLE KEYS */;
INSERT INTO `customisation_system` VALUES (200,0,'https://i.imgur.com/RyOrQZ7.jpg',0.6,24,'Welcome to Acme-Rookies! We\'re IT rookie\'s favourite job marketplace!.  ¡Bienvenidos a Acme-Rookies! ¡Somos el mercado de trabajo favorito de los profesionales de las TICs!',34,10,'Acme-Rookies',0.21);
/*!40000 ALTER TABLE `customisation_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system_spamwords`
--

DROP TABLE IF EXISTS `customisation_system_spamwords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system_spamwords` (
  `customisation_system` int(11) NOT NULL,
  `spamwords` varchar(255) DEFAULT NULL,
  `lang` varchar(255) NOT NULL,
  PRIMARY KEY (`customisation_system`,`lang`),
  CONSTRAINT `FK_a1yl4kwyajuuad7xh2yaxe9gm` FOREIGN KEY (`customisation_system`) REFERENCES `customisation_system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system_spamwords`
--

LOCK TABLES `customisation_system_spamwords` WRITE;
/*!40000 ALTER TABLE `customisation_system_spamwords` DISABLE KEYS */;
INSERT INTO `customisation_system_spamwords` VALUES (200,'sex, cialis, one millon, you´ve been selected, Nigeria\n					','en'),(200,'sexo, un millon, ha sido seleccionado, viagra','es');
/*!40000 ALTER TABLE `customisation_system_spamwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_data`
--

DROP TABLE IF EXISTS `education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `mark` double DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_data`
--

LOCK TABLES `education_data` WRITE;
/*!40000 ALTER TABLE `education_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `max_salary` double DEFAULT NULL,
  `min_salary` double DEFAULT NULL,
  `single_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (223,0,'2019-04-21 23:40:00','2019-04-01',100,0,''),(224,0,'2019-04-10 10:18:00','2019-03-01',100,0,'');
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_positions`
--

DROP TABLE IF EXISTS `finder_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_positions` (
  `finder` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  KEY `FK_3d46gil0nks2dhgg7cyhv2m39` (`positions`),
  KEY `FK_l0b3qg4nly59oeqhe8ig5yssc` (`finder`),
  CONSTRAINT `FK_l0b3qg4nly59oeqhe8ig5yssc` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_3d46gil0nks2dhgg7cyhv2m39` FOREIGN KEY (`positions`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_positions`
--

LOCK TABLES `finder_positions` WRITE;
/*!40000 ALTER TABLE `finder_positions` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_isojc9iaj7goju6s26847atbn` (`provider`),
  CONSTRAINT `FK_isojc9iaj7goju6s26847atbn` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (233,0,'item1','item1',231),(234,0,'item2','item2',231),(235,0,'item3','item3',232);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_pictures`
--

DROP TABLE IF EXISTS `item_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_pictures` (
  `item` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_aur62dcmq5mod3fcwl099dmxi` (`item`),
  CONSTRAINT `FK_aur62dcmq5mod3fcwl099dmxi` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_pictures`
--

LOCK TABLES `item_pictures` WRITE;
/*!40000 ALTER TABLE `item_pictures` DISABLE KEYS */;
INSERT INTO `item_pictures` VALUES (233,'http://www.pictures.com'),(234,'http://www.pictures.com');
/*!40000 ALTER TABLE `item_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_urls`
--

DROP TABLE IF EXISTS `item_urls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_urls` (
  `item` int(11) NOT NULL,
  `urls` varchar(255) DEFAULT NULL,
  KEY `FK_rp081doamal2hwhrn0jjttons` (`item`),
  CONSTRAINT `FK_rp081doamal2hwhrn0jjttons` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_urls`
--

LOCK TABLES `item_urls` WRITE;
/*!40000 ALTER TABLE `item_urls` DISABLE KEYS */;
INSERT INTO `item_urls` VALUES (233,'http://www.url.com'),(234,'http://www.url.com');
/*!40000 ALTER TABLE `item_urls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `momentsent` date DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_box`
--

DROP TABLE IF EXISTS `message_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_box` (
  `message_entity` int(11) NOT NULL,
  `box` int(11) NOT NULL,
  KEY `FK_tirn2sj16untob4m3ygpx1vf9` (`box`),
  KEY `FK_qdktorl3wplbhlgl73e2ljr19` (`message_entity`),
  CONSTRAINT `FK_qdktorl3wplbhlgl73e2ljr19` FOREIGN KEY (`message_entity`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_tirn2sj16untob4m3ygpx1vf9` FOREIGN KEY (`box`) REFERENCES `box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_box`
--

LOCK TABLES `message_box` WRITE;
/*!40000 ALTER TABLE `message_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_entity_tags`
--

DROP TABLE IF EXISTS `message_entity_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_entity_tags` (
  `message_entity` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FK_7gi4116nmc27lgj86gtipek6q` (`message_entity`),
  CONSTRAINT `FK_7gi4116nmc27lgj86gtipek6q` FOREIGN KEY (`message_entity`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_entity_tags`
--

LOCK TABLES `message_entity_tags` WRITE;
/*!40000 ALTER TABLE `message_entity_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_entity_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_receiver`
--

DROP TABLE IF EXISTS `message_receiver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_receiver` (
  `message` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  KEY `FK_5rttfwfwpjo927puhq7wgrhii` (`message`),
  CONSTRAINT `FK_5rttfwfwpjo927puhq7wgrhii` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_receiver`
--

LOCK TABLES `message_receiver` WRITE;
/*!40000 ALTER TABLE `message_receiver` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_receiver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data`
--

DROP TABLE IF EXISTS `miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data`
--

LOCK TABLES `miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data_urls`
--

DROP TABLE IF EXISTS `miscellaneous_data_urls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data_urls` (
  `miscellaneous_data` int(11) NOT NULL,
  `urls` varchar(255) DEFAULT NULL,
  KEY `FK_8as27xnuwxv7832wxl6rhf1r5` (`miscellaneous_data`),
  CONSTRAINT `FK_8as27xnuwxv7832wxl6rhf1r5` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data_urls`
--

LOCK TABLES `miscellaneous_data_urls` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data_urls` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_data_urls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelf`
--

DROP TABLE IF EXISTS `pelf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pelf` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `publication_moment` datetime DEFAULT NULL,
  `audit` int(11) DEFAULT NULL,
  `company` int(11) NOT NULL,
  `ticker` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qn47dqejn1u898c4aes4v3eyb` (`ticker`),
  KEY `FK_gis17mtfxxpkdaj2wdy78iamh` (`audit`),
  KEY `FK_kp0ad61pnq1lsn4rhdicm2f2q` (`company`),
  CONSTRAINT `FK_qn47dqejn1u898c4aes4v3eyb` FOREIGN KEY (`ticker`) REFERENCES `ticker` (`id`),
  CONSTRAINT `FK_gis17mtfxxpkdaj2wdy78iamh` FOREIGN KEY (`audit`) REFERENCES `audit` (`id`),
  CONSTRAINT `FK_kp0ad61pnq1lsn4rhdicm2f2q` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelf`
--

LOCK TABLES `pelf` WRITE;
/*!40000 ALTER TABLE `pelf` DISABLE KEYS */;
INSERT INTO `pelf` VALUES (265,0,'PelfTEST1','','http://www.pelf.com','2019-06-20 00:00:00',237,196,208),(266,0,'PelfTEST2','','http://www.pelf.com','2019-05-10 00:00:00',238,197,209),(267,0,'PelfTEST3','\0','http://www.pelf.com','2019-03-24 00:00:00',237,196,210),(268,0,'PelfTEST4','\0','http://www.pelf.com','2019-06-21 00:00:00',238,197,211);
/*!40000 ALTER TABLE `pelf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancel` bit(1) NOT NULL,
  `deadline` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `profile_required` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `skills_required` varchar(255) DEFAULT NULL,
  `technologies` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  `ticker` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_15390c4j2aeju6ha0iwvi6mc5` (`ticker`),
  KEY `UK_3ln3adscbs87qjic8ce8bxl1l` (`ticker`,`title`,`description`,`deadline`,`profile_required`,`skills_required`,`technologies`),
  KEY `FK_7qlfn4nye234rrm4w83nms1g8` (`company`),
  CONSTRAINT `FK_15390c4j2aeju6ha0iwvi6mc5` FOREIGN KEY (`ticker`) REFERENCES `ticker` (`id`),
  CONSTRAINT `FK_7qlfn4nye234rrm4w83nms1g8` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (213,0,'\0','2019-12-15','ingeniero de software','','lsi',120,'developer','spring','ingeniero de software',196,204),(214,0,'\0','2019-12-15','ingeniero computadores','\0','dte',120,'developer','spring','ingeniero computadores',196,205),(215,0,'\0','2019-12-15','encargado de las redes','\0','atc',150,'developer','spring','redes',197,206),(216,0,'\0','2019-12-20','ingeniero de ti','','lsi',140,'developer','spring','ingeniero de ti',196,207),(217,0,'\0','2019-02-20','ingeniero de control check','','lsi',100,'developer','spring','ingeniero de control check',197,212);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_data`
--

DROP TABLE IF EXISTS `position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_data`
--

LOCK TABLES `position_data` WRITE;
/*!40000 ALTER TABLE `position_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `final_mode` bit(1) DEFAULT NULL,
  `hint` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1dla8eoii1nn6emoo4yv86pgb` (`company`),
  CONSTRAINT `FK_1dla8eoii1nn6emoo4yv86pgb` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (201,0,'','hint','difficult','problem1',196),(202,0,'\0','hint','difficult','problem2',196),(203,0,'','hint','difficult','problem3',197);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_attachments`
--

DROP TABLE IF EXISTS `problem_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_attachments` (
  `problem` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_mhrgqo77annlexxubmtv4qvf7` (`problem`),
  CONSTRAINT `FK_mhrgqo77annlexxubmtv4qvf7` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_attachments`
--

LOCK TABLES `problem_attachments` WRITE;
/*!40000 ALTER TABLE `problem_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `problem_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_position`
--

DROP TABLE IF EXISTS `problem_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_position` (
  `problem` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  KEY `FK_slia2atg2myvm8tyyocd7ix4p` (`position`),
  KEY `FK_sj3o1yj22r6yb63ui168q4ge7` (`problem`),
  CONSTRAINT `FK_sj3o1yj22r6yb63ui168q4ge7` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_slia2atg2myvm8tyyocd7ix4p` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_position`
--

LOCK TABLES `problem_position` WRITE;
/*!40000 ALTER TABLE `problem_position` DISABLE KEYS */;
INSERT INTO `problem_position` VALUES (201,215),(201,216),(201,213),(202,214),(202,215),(202,216),(202,213);
/*!40000 ALTER TABLE `problem_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `social_network_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (198,0,'https://www.linkedin/DP1010','DP1010','LinkedIn'),(199,0,'https://www.tuenti/DP1011','DP1011','Tuenti');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  `provider_make` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b1gwnjqm6ggy9yuiqm0o4rlmd` (`user_account_id`),
  CONSTRAINT `FK_b1gwnjqm6ggy9yuiqm0o4rlmd` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
INSERT INTO `provider` VALUES (231,0,'',182,'2017-12-15','John Doe','MASTERCARD','1111222233334444','provider1@us.es','provider1','955582516','','\0','provider1','12345678Z',227,'provider1'),(232,0,'',182,'2017-12-15','John Doe','MASTERCARD','1111222233334444','provider2@us.es','provider2','955582519','','\0','provider2','12345678Z',228,'provider2');
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie`
--

DROP TABLE IF EXISTS `rookie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  `finder` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ftcwu1b6ip7somp86ptkkwoao` (`user_account_id`),
  KEY `FK_n7rveqfq9lm0cwcxhwyvtyi1g` (`finder`),
  CONSTRAINT `FK_ftcwu1b6ip7somp86ptkkwoao` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_n7rveqfq9lm0cwcxhwyvtyi1g` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie`
--

LOCK TABLES `rookie` WRITE;
/*!40000 ALTER TABLE `rookie` DISABLE KEYS */;
INSERT INTO `rookie` VALUES (218,1,'',138,'2017-12-15','John Doe','MASTERCARD','1111222233334444','crookie@us.es','Rookie1','955582516','','\0','Rookie1','12345678Z',193,223),(219,1,'',138,'2017-12-15','John Doe','MASTERCARD','1111222233334444','crookie@us.es','rookie2','955582516','','','rookie2','12345678Z',194,224);
/*!40000 ALTER TABLE `rookie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `flat_rate` double DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `target` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jnrjojfnyyaie1n4jhsdxjbig` (`position`),
  KEY `FK_dwk5ymekhnr143u957f7gtns6` (`provider`),
  CONSTRAINT `FK_dwk5ymekhnr143u957f7gtns6` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`),
  CONSTRAINT `FK_jnrjojfnyyaie1n4jhsdxjbig` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (243,0,'https://i.imgur.com/VrQpD0U.png',182,'2017-12-15','John Doe','FLY','1111222233334444',0.2,'','https://www.google.com',213,231),(244,0,'https://i.imgur.com/BrAw5PK.png',182,'2017-12-15','John Doe','MASTERCARD','1111222233334444',0.2,'\0','https://www.prueba.com',216,231);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticker`
--

DROP TABLE IF EXISTS `ticker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticker` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gbm6yyxhbstgaw5nrouliulph` (`ticker`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticker`
--

LOCK TABLES `ticker` WRITE;
/*!40000 ALTER TABLE `ticker` DISABLE KEYS */;
INSERT INTO `ticker` VALUES (204,0,'COMM-1234'),(205,0,'COMM-1235'),(206,0,'COMM-1236'),(207,0,'COMM-1237'),(208,0,'190607:LSNWIV'),(209,0,'190607:NFUD6D'),(210,0,'190607:58D5D8'),(211,0,'190607:F8F8E5'),(212,0,'COMM-1658');
/*!40000 ALTER TABLE `ticker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (190,0,'','e00cf25ad42683b3df678c61f42c6bda','admin1'),(191,0,'','df655f976f7c9d3263815bd981225cd9','company1'),(192,0,'','d196a28097115067fefd73d25b0c0be8','company2'),(193,0,'','9701eb1802a4c63f51e1501512fbdd30','rookie1'),(194,0,'','124be4fa2a59341a1d7e965ac49b2923','rookie2'),(225,0,'','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(226,0,'','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(227,0,'','cdb82d56473901641525fbbd1d5dab56','provider1'),(228,0,'','ebfc815ee2cc6a16225105bb7b3e1e53','provider2');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (190,'ADMIN'),(191,'COMPANY'),(192,'COMPANY'),(193,'ROOKIE'),(194,'ROOKIE'),(225,'AUDITOR'),(226,'AUDITOR'),(227,'PROVIDER'),(228,'PROVIDER');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-24 18:05:21
commit;