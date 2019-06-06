start transaction;
drop database if exists `DEL5`;
create database `DEL5`;
use `DEL5`;
create user 'acme-user'@'%'identified by 'ACME-Us3r-P@ssw0rd';
create user 'acme-manager'@'%' identified by 'ACME-M@n@ger-6874';
grant select, insert, update, delete on `DEL5`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables,
lock tables, create view, create routine, alter routine, execute, trigger, show view on `DEL5`.* to 'acme-manager'@'%';
		
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: DEL5
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
INSERT INTO `actor_boxes` VALUES (22,23),(22,24),(22,25),(22,26),(22,27);
/*!40000 ALTER TABLE `actor_boxes` ENABLE KEYS */;
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
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
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
INSERT INTO `administrator` VALUES (22,0,'','admin<admin@gmail.com>',NULL,'Admin1','955582516','','Admin1','\0',17);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
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
INSERT INTO `box` VALUES (23,0,'','In Box'),(24,0,'','Out Box'),(25,0,'','Spam Box'),(26,0,'','Trash Box'),(27,0,'','Notification Box');
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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (28,0,'Teachers Proclaims'),(29,0,'Building Infraestructure'),(30,0,'Proclaim'),(31,0,'Teachers Schedule'),(32,0,'Bullying');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_categories`
--

DROP TABLE IF EXISTS `category_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_categories` (
  `category` int(11) NOT NULL,
  `categories` int(11) NOT NULL,
  UNIQUE KEY `UK_7um9d6vh8tpm6caj3ics9jjax` (`categories`),
  KEY `FK_s52fgdy2v2gjx1b795j9l9ua6` (`category`),
  CONSTRAINT `FK_s52fgdy2v2gjx1b795j9l9ua6` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_7um9d6vh8tpm6caj3ics9jjax` FOREIGN KEY (`categories`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_categories`
--

LOCK TABLES `category_categories` WRITE;
/*!40000 ALTER TABLE `category_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collaborator`
--

DROP TABLE IF EXISTS `collaborator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collaborator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `user_account_id` int(11) NOT NULL,
  `comission` int(11) DEFAULT NULL,
  `portfolio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5g1lvr8610rdfgabea2vtnavd` (`user_account_id`),
  KEY `FK_p6tj7xv2yxgqarnw86xicd7jg` (`comission`),
  KEY `FK_n84ob5bykcwmtt2i7uk7q7c01` (`portfolio`),
  CONSTRAINT `FK_5g1lvr8610rdfgabea2vtnavd` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_n84ob5bykcwmtt2i7uk7q7c01` FOREIGN KEY (`portfolio`) REFERENCES `portfolio` (`id`),
  CONSTRAINT `FK_p6tj7xv2yxgqarnw86xicd7jg` FOREIGN KEY (`comission`) REFERENCES `comission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collaborator`
--

LOCK TABLES `collaborator` WRITE;
/*!40000 ALTER TABLE `collaborator` DISABLE KEYS */;
/*!40000 ALTER TABLE `collaborator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comission`
--

DROP TABLE IF EXISTS `comission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comission` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `moment` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `member` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_gjrb3epd9erg3hrtgkltcoq69` (`final_mode`),
  KEY `FK_shpu7vb3dsao5j60ftqwc1bf3` (`member`),
  CONSTRAINT `FK_shpu7vb3dsao5j60ftqwc1bf3` FOREIGN KEY (`member`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comission`
--

LOCK TABLES `comission` WRITE;
/*!40000 ALTER TABLE `comission` DISABLE KEYS */;
/*!40000 ALTER TABLE `comission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `actor` int(11) DEFAULT NULL,
  `proclaim` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ml62wija5bi4u63cnj2xs4yvg` (`proclaim`),
  CONSTRAINT `FK_ml62wija5bi4u63cnj2xs4yvg` FOREIGN KEY (`proclaim`) REFERENCES `proclaim` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
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
  `credit_card_makes` varchar(255) DEFAULT NULL,
  `hours_finder` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `phone_prefix` varchar(255) DEFAULT NULL,
  `result_finder` int(11) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system`
--

LOCK TABLES `customisation_system` WRITE;
/*!40000 ALTER TABLE `customisation_system` DISABLE KEYS */;
INSERT INTO `customisation_system` VALUES (21,0,'https://i.imgur.com/eDCGHR9.png','VISA,MCARD,AMEX,DINNERS,FLY',24,'Welcome to DEL5! The best website for student rights defense. ¡Bienvenidos a DEL5! El mejor lugar para la defensa de los derechos de los estudiantes','34',10,'DEL5-Student Representation');
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
INSERT INTO `customisation_system_spamwords` VALUES (21,'sex, cialis, one millon, you have been selected, Nigeria','en'),(21,'sexo, un millon, ha sido seleccionado, viagra','es');
/*!40000 ALTER TABLE `customisation_system_spamwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `moment` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `collaborator` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ni9m8kcpnqtk2gnajiebcp971` (`final_mode`),
  KEY `FK_p07p32qfh6ia9w2ica6pv3h08` (`collaborator`),
  CONSTRAINT `FK_p07p32qfh6ia9w2ica6pv3h08` FOREIGN KEY (`collaborator`) REFERENCES `collaborator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
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
  `before_or_not` bit(1) NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `registered_date` date DEFAULT NULL,
  `single_key` varchar(255) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n9t1ayk0x7h5vrsfuhygo043j` (`category`),
  CONSTRAINT `FK_n9t1ayk0x7h5vrsfuhygo043j` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_proclaims`
--

DROP TABLE IF EXISTS `finder_proclaims`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_proclaims` (
  `finder` int(11) NOT NULL,
  `proclaims` int(11) NOT NULL,
  KEY `FK_ja4dj9s8vpsh0p1etkbmqept1` (`proclaims`),
  KEY `FK_de3p7lweeimk2vu9wmwgbulc1` (`finder`),
  CONSTRAINT `FK_de3p7lweeimk2vu9wmwgbulc1` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_ja4dj9s8vpsh0p1etkbmqept1` FOREIGN KEY (`proclaims`) REFERENCES `proclaim` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_proclaims`
--

LOCK TABLES `finder_proclaims` WRITE;
/*!40000 ALTER TABLE `finder_proclaims` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_proclaims` ENABLE KEYS */;
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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `user_account_id` int(11) NOT NULL,
  `finder` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mjoa5yw12sbvknx53x7fu5a1g` (`finder`),
  UNIQUE KEY `UK_htdbm82ach0k7expwvbi4k1sv` (`user_account_id`),
  CONSTRAINT `FK_htdbm82ach0k7expwvbi4k1sv` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_mjoa5yw12sbvknx53x7fu5a1g` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
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
-- Table structure for table `miscellaneous_report`
--

DROP TABLE IF EXISTS `miscellaneous_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` date DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_report`
--

LOCK TABLES `miscellaneous_report` WRITE;
/*!40000 ALTER TABLE `miscellaneous_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_report_attachments`
--

DROP TABLE IF EXISTS `miscellaneous_report_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_report_attachments` (
  `miscellaneous_report` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_3ibe2n2d67aoh1mpp699oklmy` (`miscellaneous_report`),
  CONSTRAINT `FK_3ibe2n2d67aoh1mpp699oklmy` FOREIGN KEY (`miscellaneous_report`) REFERENCES `miscellaneous_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_report_attachments`
--

LOCK TABLES `miscellaneous_report_attachments` WRITE;
/*!40000 ALTER TABLE `miscellaneous_report_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_report_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `note` double DEFAULT NULL,
  `actor` int(11) NOT NULL,
  `event` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_geynv1ior96weqluxsc4vdjev` (`event`),
  CONSTRAINT `FK_geynv1ior96weqluxsc4vdjev` FOREIGN KEY (`event`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio`
--

DROP TABLE IF EXISTS `portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portfolio` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio`
--

LOCK TABLES `portfolio` WRITE;
/*!40000 ALTER TABLE `portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio_miscellaneous_report`
--

DROP TABLE IF EXISTS `portfolio_miscellaneous_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portfolio_miscellaneous_report` (
  `portfolio` int(11) NOT NULL,
  `miscellaneous_report` int(11) NOT NULL,
  UNIQUE KEY `UK_s9rbjl2em0m3hvr3p1c5akoa7` (`miscellaneous_report`),
  KEY `FK_iefdv4rt2tjhbrt3b4xbmjmuf` (`portfolio`),
  CONSTRAINT `FK_iefdv4rt2tjhbrt3b4xbmjmuf` FOREIGN KEY (`portfolio`) REFERENCES `portfolio` (`id`),
  CONSTRAINT `FK_s9rbjl2em0m3hvr3p1c5akoa7` FOREIGN KEY (`miscellaneous_report`) REFERENCES `miscellaneous_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio_miscellaneous_report`
--

LOCK TABLES `portfolio_miscellaneous_report` WRITE;
/*!40000 ALTER TABLE `portfolio_miscellaneous_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `portfolio_miscellaneous_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio_study_report`
--

DROP TABLE IF EXISTS `portfolio_study_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portfolio_study_report` (
  `portfolio` int(11) NOT NULL,
  `study_report` int(11) NOT NULL,
  UNIQUE KEY `UK_kj8i3e4l6wbhtsarsnf1j3qng` (`study_report`),
  KEY `FK_mqo6l6dl8pi85d6h1bpu25cdc` (`portfolio`),
  CONSTRAINT `FK_mqo6l6dl8pi85d6h1bpu25cdc` FOREIGN KEY (`portfolio`) REFERENCES `portfolio` (`id`),
  CONSTRAINT `FK_kj8i3e4l6wbhtsarsnf1j3qng` FOREIGN KEY (`study_report`) REFERENCES `study_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio_study_report`
--

LOCK TABLES `portfolio_study_report` WRITE;
/*!40000 ALTER TABLE `portfolio_study_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `portfolio_study_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio_work_report`
--

DROP TABLE IF EXISTS `portfolio_work_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portfolio_work_report` (
  `portfolio` int(11) NOT NULL,
  `work_report` int(11) NOT NULL,
  UNIQUE KEY `UK_im76lucfpmucsuvvqtctn1iyo` (`work_report`),
  KEY `FK_t6cg5bi5m0i9raivl6qedwuyf` (`portfolio`),
  CONSTRAINT `FK_t6cg5bi5m0i9raivl6qedwuyf` FOREIGN KEY (`portfolio`) REFERENCES `portfolio` (`id`),
  CONSTRAINT `FK_im76lucfpmucsuvvqtctn1iyo` FOREIGN KEY (`work_report`) REFERENCES `work_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio_work_report`
--

LOCK TABLES `portfolio_work_report` WRITE;
/*!40000 ALTER TABLE `portfolio_work_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `portfolio_work_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proclaim`
--

DROP TABLE IF EXISTS `proclaim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proclaim` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `closed` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `law` varchar(255) DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `centre` varchar(255) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `student` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_to0l2jdhu5rkdsl4hj6cu2gsb` (`ticker`),
  KEY `UK_566498veb44vtpw9d0cbyhld9` (`title`,`description`,`moment`,`attachments`,`final_mode`),
  KEY `FK_76slt0d477vjxtewfu1d45306` (`category`),
  KEY `FK_rhwio1y7o3e0ha7afpkdhejvg` (`student`),
  CONSTRAINT `FK_to0l2jdhu5rkdsl4hj6cu2gsb` FOREIGN KEY (`ticker`) REFERENCES `ticker` (`id`),
  CONSTRAINT `FK_76slt0d477vjxtewfu1d45306` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_rhwio1y7o3e0ha7afpkdhejvg` FOREIGN KEY (`student`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proclaim`
--

LOCK TABLES `proclaim` WRITE;
/*!40000 ALTER TABLE `proclaim` DISABLE KEYS */;
/*!40000 ALTER TABLE `proclaim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proclaim_members`
--

DROP TABLE IF EXISTS `proclaim_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proclaim_members` (
  `proclaim` int(11) NOT NULL,
  `members` int(11) NOT NULL,
  KEY `FK_keeaydp09exriwq1o1tld8sb4` (`members`),
  KEY `FK_9isk6yhdgh6tcb51ccnk6quxx` (`proclaim`),
  CONSTRAINT `FK_9isk6yhdgh6tcb51ccnk6quxx` FOREIGN KEY (`proclaim`) REFERENCES `proclaim` (`id`),
  CONSTRAINT `FK_keeaydp09exriwq1o1tld8sb4` FOREIGN KEY (`members`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proclaim_members`
--

LOCK TABLES `proclaim_members` WRITE;
/*!40000 ALTER TABLE `proclaim_members` DISABLE KEYS */;
/*!40000 ALTER TABLE `proclaim_members` ENABLE KEYS */;
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
  `actor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (19,1,'https://www.linkedin/DP1010','DP1010','LinkedIn',22),(20,1,'https://www.tuenti/DP1011','DP1011','Tuenti',22);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `user_account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_20xk0ev32hlg96kqynl6laie2` (`user_account_id`),
  CONSTRAINT `FK_20xk0ev32hlg96kqynl6laie2` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
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
  `is_active` bit(1) NOT NULL,
  `target` varchar(255) DEFAULT NULL,
  `event` int(11) DEFAULT NULL,
  `sponsor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kmuj14sjw9vdjuttjpamsh2fv` (`event`),
  KEY `FK_huglhkud0ihqdljyou4eshra6` (`sponsor`),
  CONSTRAINT `FK_huglhkud0ihqdljyou4eshra6` FOREIGN KEY (`sponsor`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_kmuj14sjw9vdjuttjpamsh2fv` FOREIGN KEY (`event`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `user_account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_39b5mf1i5ggh6xowxc8kmf72e` (`user_account_id`),
  CONSTRAINT `FK_39b5mf1i5ggh6xowxc8kmf72e` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_report`
--

DROP TABLE IF EXISTS `study_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `study_report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `average` double DEFAULT NULL,
  `course` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `percentaje_credits` double DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_report`
--

LOCK TABLES `study_report` WRITE;
/*!40000 ALTER TABLE `study_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `study_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swap`
--

DROP TABLE IF EXISTS `swap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `swap` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `comission` int(11) DEFAULT NULL,
  `receiver` int(11) DEFAULT NULL,
  `sender` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ns80sjljkdypaxpnayintxy98` (`comission`),
  KEY `FK_sd5k16jfytm90cxlpltn2f06t` (`receiver`),
  KEY `FK_6oydjd2mwb4r2ym2x1jo3rduf` (`sender`),
  CONSTRAINT `FK_6oydjd2mwb4r2ym2x1jo3rduf` FOREIGN KEY (`sender`) REFERENCES `collaborator` (`id`),
  CONSTRAINT `FK_ns80sjljkdypaxpnayintxy98` FOREIGN KEY (`comission`) REFERENCES `comission` (`id`),
  CONSTRAINT `FK_sd5k16jfytm90cxlpltn2f06t` FOREIGN KEY (`receiver`) REFERENCES `collaborator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swap`
--

LOCK TABLES `swap` WRITE;
/*!40000 ALTER TABLE `swap` DISABLE KEYS */;
/*!40000 ALTER TABLE `swap` ENABLE KEYS */;
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
INSERT INTO `user_account` VALUES (17,0,'','e00cf25ad42683b3df678c61f42c6bda','admin1'),(18,0,'\0','jg9f0d7sn8sn40vjs93vs9vnw9vos387','anonymous');
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
INSERT INTO `user_account_authorities` VALUES (17,'ADMIN'),(18,'ANONYMOUS');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_report`
--

DROP TABLE IF EXISTS `work_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `business_name` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_report`
--

LOCK TABLES `work_report` WRITE;
/*!40000 ALTER TABLE `work_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `work_report` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-06 13:06:21
commit;