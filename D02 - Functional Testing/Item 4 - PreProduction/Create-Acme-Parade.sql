start transaction;
drop database if exists `Acme-Parade`;
create database `Acme-Parade`;
use `Acme-Parade`;
create user 'acme-user'@'%'identified by 'ACME-Us3r-P@ssw0rd';
create user 'acme-manager'@'%' identified by 'ACME-M@n@ger-6874';
grant select, insert, update, delete on `Acme-Parade`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables,
lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Parade`.* to 'acme-manager'@'%';
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Parade
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
INSERT INTO `actor_boxes` VALUES (17,18),(17,19),(17,20),(17,21),(17,22);
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
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
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
INSERT INTO `administrator` VALUES (17,0,'Adress Admin1','admin1@acme-madruga.es','MiddleNameAdmin1','Admin1',NULL,'https://www.acme.com/admin1','\0','Admin1',16);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area_pictures`
--

DROP TABLE IF EXISTS `area_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area_pictures` (
  `area` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_s2y5bun5v8b608aoptnxfuelm` (`area`),
  CONSTRAINT `FK_s2y5bun5v8b608aoptnxfuelm` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_pictures`
--

LOCK TABLES `area_pictures` WRITE;
/*!40000 ALTER TABLE `area_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `area_pictures` ENABLE KEYS */;
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
INSERT INTO `box` VALUES (18,0,'','In Box'),(19,0,'','Out Box'),(20,0,'','Spam Box'),(21,0,'','Trash Box'),(22,0,'','Notification Box');
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
-- Table structure for table `brotherhood`
--

DROP TABLE IF EXISTS `brotherhood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  `establishment` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `area` int(11) DEFAULT NULL,
  `history` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r6yl5ul2de5phu11js8dwsf2` (`user_account_id`),
  KEY `FK_oku65kpdi3ro8ta0bmmxdkidt` (`area`),
  KEY `FK_5cyh83gj75vp1266mw0hxf8eu` (`history`),
  CONSTRAINT `FK_r6yl5ul2de5phu11js8dwsf2` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_5cyh83gj75vp1266mw0hxf8eu` FOREIGN KEY (`history`) REFERENCES `history` (`id`),
  CONSTRAINT `FK_oku65kpdi3ro8ta0bmmxdkidt` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood`
--

LOCK TABLES `brotherhood` WRITE;
/*!40000 ALTER TABLE `brotherhood` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_floats`
--

DROP TABLE IF EXISTS `brotherhood_floats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_floats` (
  `brotherhood` int(11) NOT NULL,
  `floats` int(11) NOT NULL,
  UNIQUE KEY `UK_1p136su1hlqt726lb0mavn0fc` (`floats`),
  KEY `FK_i9k81m0mstix5xlud1o953snk` (`brotherhood`),
  CONSTRAINT `FK_i9k81m0mstix5xlud1o953snk` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`),
  CONSTRAINT `FK_1p136su1hlqt726lb0mavn0fc` FOREIGN KEY (`floats`) REFERENCES `float` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_floats`
--

LOCK TABLES `brotherhood_floats` WRITE;
/*!40000 ALTER TABLE `brotherhood_floats` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood_floats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_parades`
--

DROP TABLE IF EXISTS `brotherhood_parades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_parades` (
  `brotherhood` int(11) NOT NULL,
  `parades` int(11) NOT NULL,
  UNIQUE KEY `UK_3qb5pu5tlp8g3f4edicibj7k9` (`parades`),
  KEY `FK_aqpfy8n80469jhdj95g8xh9fi` (`brotherhood`),
  CONSTRAINT `FK_aqpfy8n80469jhdj95g8xh9fi` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`),
  CONSTRAINT `FK_3qb5pu5tlp8g3f4edicibj7k9` FOREIGN KEY (`parades`) REFERENCES `parade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_parades`
--

LOCK TABLES `brotherhood_parades` WRITE;
/*!40000 ALTER TABLE `brotherhood_parades` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood_parades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brotherhood_pictures`
--

DROP TABLE IF EXISTS `brotherhood_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brotherhood_pictures` (
  `brotherhood` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_8d0m2wigmw0c32w3yqpgqlpyl` (`brotherhood`),
  CONSTRAINT `FK_8d0m2wigmw0c32w3yqpgqlpyl` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brotherhood_pictures`
--

LOCK TABLES `brotherhood_pictures` WRITE;
/*!40000 ALTER TABLE `brotherhood_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `brotherhood_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `area` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_itfmdve1262u2nn60pws9tck` (`user_account_id`),
  KEY `FK_g1jjg80txjhuvgdkb84om9q9p` (`area`),
  CONSTRAINT `FK_itfmdve1262u2nn60pws9tck` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_g1jjg80txjhuvgdkb84om9q9p` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
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
  `flat_fare` double DEFAULT NULL,
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
INSERT INTO `customisation_system` VALUES (30,0,'https://i.imgur.com/PjcX0fO.jpg',0.5,2,'Welcome to Acme-Madruga! The site to organise your parades! <br> ¡Bienvenidos a Acme-Madruga! Tu sitio para organizar desfiles!',34,10,'Acme-Madruga',0.21);
/*!40000 ALTER TABLE `customisation_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system_bad_words`
--

DROP TABLE IF EXISTS `customisation_system_bad_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system_bad_words` (
  `customisation_system` int(11) NOT NULL,
  `bad_words` varchar(255) DEFAULT NULL,
  KEY `FK_7a95xaw5qq7xld7a6eo6d9l5k` (`customisation_system`),
  CONSTRAINT `FK_7a95xaw5qq7xld7a6eo6d9l5k` FOREIGN KEY (`customisation_system`) REFERENCES `customisation_system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system_bad_words`
--

LOCK TABLES `customisation_system_bad_words` WRITE;
/*!40000 ALTER TABLE `customisation_system_bad_words` DISABLE KEYS */;
INSERT INTO `customisation_system_bad_words` VALUES (30,'not'),(30,'bad'),(30,'horrible'),(30,'average'),(30,'disaster'),(30,'no'),(30,'malo'),(30,'horrible'),(30,'media'),(30,'desstre');
/*!40000 ALTER TABLE `customisation_system_bad_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system_credit_card_makes`
--

DROP TABLE IF EXISTS `customisation_system_credit_card_makes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system_credit_card_makes` (
  `customisation_system` int(11) NOT NULL,
  `credit_card_makes` varchar(255) DEFAULT NULL,
  KEY `FK_n8ocid1al9akjbkb1aumjuf6p` (`customisation_system`),
  CONSTRAINT `FK_n8ocid1al9akjbkb1aumjuf6p` FOREIGN KEY (`customisation_system`) REFERENCES `customisation_system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system_credit_card_makes`
--

LOCK TABLES `customisation_system_credit_card_makes` WRITE;
/*!40000 ALTER TABLE `customisation_system_credit_card_makes` DISABLE KEYS */;
INSERT INTO `customisation_system_credit_card_makes` VALUES (30,'VISA'),(30,'MASTERCARD'),(30,'AMEX'),(30,'DINERS'),(30,'FLY');
/*!40000 ALTER TABLE `customisation_system_credit_card_makes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system_good_words`
--

DROP TABLE IF EXISTS `customisation_system_good_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system_good_words` (
  `customisation_system` int(11) NOT NULL,
  `good_words` varchar(255) DEFAULT NULL,
  KEY `FK_9cld8fahedak69sq167mkf6by` (`customisation_system`),
  CONSTRAINT `FK_9cld8fahedak69sq167mkf6by` FOREIGN KEY (`customisation_system`) REFERENCES `customisation_system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system_good_words`
--

LOCK TABLES `customisation_system_good_words` WRITE;
/*!40000 ALTER TABLE `customisation_system_good_words` DISABLE KEYS */;
INSERT INTO `customisation_system_good_words` VALUES (30,'good'),(30,'fantastic'),(30,'excellent'),(30,'great'),(30,'amazing'),(30,'terrific'),(30,'beatiful'),(30,'bueno'),(30,'fantastico'),(30,'excelente'),(30,'genial'),(30,'asombroso'),(30,'estupendo'),(30,'bonito');
/*!40000 ALTER TABLE `customisation_system_good_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system_priorities`
--

DROP TABLE IF EXISTS `customisation_system_priorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system_priorities` (
  `customisation_system` int(11) NOT NULL,
  `priorities` varchar(255) DEFAULT NULL,
  KEY `FK_q92e7stnqqvd6apkce4kvjfe1` (`customisation_system`),
  CONSTRAINT `FK_q92e7stnqqvd6apkce4kvjfe1` FOREIGN KEY (`customisation_system`) REFERENCES `customisation_system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system_priorities`
--

LOCK TABLES `customisation_system_priorities` WRITE;
/*!40000 ALTER TABLE `customisation_system_priorities` DISABLE KEYS */;
INSERT INTO `customisation_system_priorities` VALUES (30,'HIGH'),(30,'NEUTRAL'),(30,'LOW');
/*!40000 ALTER TABLE `customisation_system_priorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation_system_spam_words`
--

DROP TABLE IF EXISTS `customisation_system_spam_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation_system_spam_words` (
  `customisation_system` int(11) NOT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  KEY `FK_2hmee8sh9enh34bygyfw20r0p` (`customisation_system`),
  CONSTRAINT `FK_2hmee8sh9enh34bygyfw20r0p` FOREIGN KEY (`customisation_system`) REFERENCES `customisation_system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation_system_spam_words`
--

LOCK TABLES `customisation_system_spam_words` WRITE;
/*!40000 ALTER TABLE `customisation_system_spam_words` DISABLE KEYS */;
INSERT INTO `customisation_system_spam_words` VALUES (30,'sex'),(30,'viagra'),(30,'cialis'),(30,'one millon'),(30,'you\'ve been selected'),(30,'Nigeria'),(30,'sexo'),(30,'un millÃ³n'),(30,'ha sido seleccionado');
/*!40000 ALTER TABLE `customisation_system_spam_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrolment`
--

DROP TABLE IF EXISTS `enrolment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrolment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `exit_member` bit(1) DEFAULT NULL,
  `exit_moment` date DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `brotherhood` int(11) NOT NULL,
  `member` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kacft8i7jufll7t0nyk68cptn` (`brotherhood`),
  KEY `FK_o5re2u23cjomuht1q0fjmu09u` (`member`),
  KEY `FK_aopae51comq4kt7iadag2ygye` (`position`),
  CONSTRAINT `FK_aopae51comq4kt7iadag2ygye` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_kacft8i7jufll7t0nyk68cptn` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`),
  CONSTRAINT `FK_o5re2u23cjomuht1q0fjmu09u` FOREIGN KEY (`member`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrolment`
--

LOCK TABLES `enrolment` WRITE;
/*!40000 ALTER TABLE `enrolment` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrolment` ENABLE KEYS */;
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
  `maximum_date` date DEFAULT NULL,
  `minimun_date` date DEFAULT NULL,
  `single_word` varchar(255) DEFAULT NULL,
  `area` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rbjlwj1bksuusd3142rf62xyt` (`area`),
  CONSTRAINT `FK_rbjlwj1bksuusd3142rf62xyt` FOREIGN KEY (`area`) REFERENCES `area` (`id`)
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
-- Table structure for table `finder_parades`
--

DROP TABLE IF EXISTS `finder_parades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_parades` (
  `finder` int(11) NOT NULL,
  `parades` int(11) NOT NULL,
  KEY `FK_a7t9ojmirwd7ijfq42ffessn5` (`parades`),
  KEY `FK_8pff5xgqq7qfh2ciyx24p67sp` (`finder`),
  CONSTRAINT `FK_8pff5xgqq7qfh2ciyx24p67sp` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_a7t9ojmirwd7ijfq42ffessn5` FOREIGN KEY (`parades`) REFERENCES `parade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_parades`
--

LOCK TABLES `finder_parades` WRITE;
/*!40000 ALTER TABLE `finder_parades` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_parades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `float`
--

DROP TABLE IF EXISTS `float`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `float` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `float`
--

LOCK TABLES `float` WRITE;
/*!40000 ALTER TABLE `float` DISABLE KEYS */;
/*!40000 ALTER TABLE `float` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `float_pictures`
--

DROP TABLE IF EXISTS `float_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `float_pictures` (
  `float` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_dp4g3ry840d4yqsjkifnm8q3t` (`float`),
  CONSTRAINT `FK_dp4g3ry840d4yqsjkifnm8q3t` FOREIGN KEY (`float`) REFERENCES `float` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `float_pictures`
--

LOCK TABLES `float_pictures` WRITE;
/*!40000 ALTER TABLE `float_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `float_pictures` ENABLE KEYS */;
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
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `inception_record` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ft70pl0noyt3c2bufgsyepbbe` (`inception_record`),
  CONSTRAINT `FK_ft70pl0noyt3c2bufgsyepbbe` FOREIGN KEY (`inception_record`) REFERENCES `inception_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_legal_record`
--

DROP TABLE IF EXISTS `history_legal_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_legal_record` (
  `history` int(11) NOT NULL,
  `legal_record` int(11) NOT NULL,
  UNIQUE KEY `UK_8b1v2j59r454ufxrpfyowb23e` (`legal_record`),
  KEY `FK_bepydqqkxboxxwrq37si6a46q` (`history`),
  CONSTRAINT `FK_bepydqqkxboxxwrq37si6a46q` FOREIGN KEY (`history`) REFERENCES `history` (`id`),
  CONSTRAINT `FK_8b1v2j59r454ufxrpfyowb23e` FOREIGN KEY (`legal_record`) REFERENCES `legal_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_legal_record`
--

LOCK TABLES `history_legal_record` WRITE;
/*!40000 ALTER TABLE `history_legal_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_legal_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_link_record`
--

DROP TABLE IF EXISTS `history_link_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_link_record` (
  `history` int(11) NOT NULL,
  `link_record` int(11) NOT NULL,
  UNIQUE KEY `UK_iacd6fcdhmkm5wrerng940t10` (`link_record`),
  KEY `FK_mc0acpadhcbodjl08en4vo189` (`history`),
  CONSTRAINT `FK_mc0acpadhcbodjl08en4vo189` FOREIGN KEY (`history`) REFERENCES `history` (`id`),
  CONSTRAINT `FK_iacd6fcdhmkm5wrerng940t10` FOREIGN KEY (`link_record`) REFERENCES `link_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_link_record`
--

LOCK TABLES `history_link_record` WRITE;
/*!40000 ALTER TABLE `history_link_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_link_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_miscellaneous_record`
--

DROP TABLE IF EXISTS `history_miscellaneous_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_miscellaneous_record` (
  `history` int(11) NOT NULL,
  `miscellaneous_record` int(11) NOT NULL,
  UNIQUE KEY `UK_h6paftptcresoq2553eejcs6` (`miscellaneous_record`),
  KEY `FK_5hk57x4gt0hu78sa3h21y1qj` (`history`),
  CONSTRAINT `FK_5hk57x4gt0hu78sa3h21y1qj` FOREIGN KEY (`history`) REFERENCES `history` (`id`),
  CONSTRAINT `FK_h6paftptcresoq2553eejcs6` FOREIGN KEY (`miscellaneous_record`) REFERENCES `miscellaneous_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_miscellaneous_record`
--

LOCK TABLES `history_miscellaneous_record` WRITE;
/*!40000 ALTER TABLE `history_miscellaneous_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_miscellaneous_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_period_record`
--

DROP TABLE IF EXISTS `history_period_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_period_record` (
  `history` int(11) NOT NULL,
  `period_record` int(11) NOT NULL,
  UNIQUE KEY `UK_ocn5e4f74aqswp47vkcu6ega0` (`period_record`),
  KEY `FK_l2kbsu7ruvbbp9xq2iodq5d64` (`history`),
  CONSTRAINT `FK_l2kbsu7ruvbbp9xq2iodq5d64` FOREIGN KEY (`history`) REFERENCES `history` (`id`),
  CONSTRAINT `FK_ocn5e4f74aqswp47vkcu6ega0` FOREIGN KEY (`period_record`) REFERENCES `period_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_period_record`
--

LOCK TABLES `history_period_record` WRITE;
/*!40000 ALTER TABLE `history_period_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_period_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inception_record`
--

DROP TABLE IF EXISTS `inception_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inception_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inception_record`
--

LOCK TABLES `inception_record` WRITE;
/*!40000 ALTER TABLE `inception_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `inception_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inception_record_photos`
--

DROP TABLE IF EXISTS `inception_record_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inception_record_photos` (
  `inception_record` int(11) NOT NULL,
  `photos` varchar(255) DEFAULT NULL,
  KEY `FK_lgtjmb9yttfdlcia3lkdv6fhk` (`inception_record`),
  CONSTRAINT `FK_lgtjmb9yttfdlcia3lkdv6fhk` FOREIGN KEY (`inception_record`) REFERENCES `inception_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inception_record_photos`
--

LOCK TABLES `inception_record_photos` WRITE;
/*!40000 ALTER TABLE `inception_record_photos` DISABLE KEYS */;
/*!40000 ALTER TABLE `inception_record_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legal_record`
--

DROP TABLE IF EXISTS `legal_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legal_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `legal_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legal_record`
--

LOCK TABLES `legal_record` WRITE;
/*!40000 ALTER TABLE `legal_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `legal_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legal_record_laws`
--

DROP TABLE IF EXISTS `legal_record_laws`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legal_record_laws` (
  `legal_record` int(11) NOT NULL,
  `laws` varchar(255) DEFAULT NULL,
  KEY `FK_3e7ckestn2iyfparnrbwpotws` (`legal_record`),
  CONSTRAINT `FK_3e7ckestn2iyfparnrbwpotws` FOREIGN KEY (`legal_record`) REFERENCES `legal_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legal_record_laws`
--

LOCK TABLES `legal_record_laws` WRITE;
/*!40000 ALTER TABLE `legal_record_laws` DISABLE KEYS */;
/*!40000 ALTER TABLE `legal_record_laws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_record`
--

DROP TABLE IF EXISTS `link_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `brotherhood` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m9iy5nonuvly36efe468wojq1` (`brotherhood`),
  CONSTRAINT `FK_m9iy5nonuvly36efe468wojq1` FOREIGN KEY (`brotherhood`) REFERENCES `brotherhood` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_record`
--

LOCK TABLES `link_record` WRITE;
/*!40000 ALTER TABLE `link_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `link_record` ENABLE KEYS */;
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
  `adress` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
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
-- Table structure for table `member_requests`
--

DROP TABLE IF EXISTS `member_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member_requests` (
  `member` int(11) NOT NULL,
  `requests` int(11) NOT NULL,
  UNIQUE KEY `UK_j6fqoub4vhqsqdbgmhe22hbad` (`requests`),
  KEY `FK_pxtv78fqjxxvsxd4dd6x9k37m` (`member`),
  CONSTRAINT `FK_pxtv78fqjxxvsxd4dd6x9k37m` FOREIGN KEY (`member`) REFERENCES `member` (`id`),
  CONSTRAINT `FK_j6fqoub4vhqsqdbgmhe22hbad` FOREIGN KEY (`requests`) REFERENCES `request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_requests`
--

LOCK TABLES `member_requests` WRITE;
/*!40000 ALTER TABLE `member_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_requests` ENABLE KEYS */;
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
-- Table structure for table `miscellaneous_record`
--

DROP TABLE IF EXISTS `miscellaneous_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_record`
--

LOCK TABLES `miscellaneous_record` WRITE;
/*!40000 ALTER TABLE `miscellaneous_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parade`
--

DROP TABLE IF EXISTS `parade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parade` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `moment_organised` date DEFAULT NULL,
  `open_path` bit(1) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `why_rejected` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5yuq215wrvxtjvi0gjynv32g7` (`ticker`),
  KEY `UK_5i3gcvsgrjexjbdu30gncy6q7` (`ticker`,`title`,`description`,`moment_organised`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parade`
--

LOCK TABLES `parade` WRITE;
/*!40000 ALTER TABLE `parade` DISABLE KEYS */;
/*!40000 ALTER TABLE `parade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parade_floats`
--

DROP TABLE IF EXISTS `parade_floats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parade_floats` (
  `parade` int(11) NOT NULL,
  `floats` int(11) NOT NULL,
  KEY `FK_nmgwe8ou0qop1ocyigwv0a2xq` (`floats`),
  KEY `FK_cct51fcgo0xvooajhnwu0txl4` (`parade`),
  CONSTRAINT `FK_cct51fcgo0xvooajhnwu0txl4` FOREIGN KEY (`parade`) REFERENCES `parade` (`id`),
  CONSTRAINT `FK_nmgwe8ou0qop1ocyigwv0a2xq` FOREIGN KEY (`floats`) REFERENCES `float` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parade_floats`
--

LOCK TABLES `parade_floats` WRITE;
/*!40000 ALTER TABLE `parade_floats` DISABLE KEYS */;
/*!40000 ALTER TABLE `parade_floats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parade_requests`
--

DROP TABLE IF EXISTS `parade_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parade_requests` (
  `parade` int(11) NOT NULL,
  `requests` int(11) NOT NULL,
  UNIQUE KEY `UK_ltyqqbcqgeu8v524k6uquou9a` (`requests`),
  KEY `FK_iqrwwm65bxaulovp4iqc0dti` (`parade`),
  CONSTRAINT `FK_iqrwwm65bxaulovp4iqc0dti` FOREIGN KEY (`parade`) REFERENCES `parade` (`id`),
  CONSTRAINT `FK_ltyqqbcqgeu8v524k6uquou9a` FOREIGN KEY (`requests`) REFERENCES `request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parade_requests`
--

LOCK TABLES `parade_requests` WRITE;
/*!40000 ALTER TABLE `parade_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `parade_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `period_record`
--

DROP TABLE IF EXISTS `period_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `period_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `period_record`
--

LOCK TABLES `period_record` WRITE;
/*!40000 ALTER TABLE `period_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `period_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `period_record_photos`
--

DROP TABLE IF EXISTS `period_record_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `period_record_photos` (
  `period_record` int(11) NOT NULL,
  `photos` varchar(255) DEFAULT NULL,
  KEY `FK_64mj91qg7ncf1qybk31wfbqtp` (`period_record`),
  CONSTRAINT `FK_64mj91qg7ncf1qybk31wfbqtp` FOREIGN KEY (`period_record`) REFERENCES `period_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `period_record_photos`
--

LOCK TABLES `period_record_photos` WRITE;
/*!40000 ALTER TABLE `period_record_photos` DISABLE KEYS */;
/*!40000 ALTER TABLE `period_record_photos` ENABLE KEYS */;
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
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (23,0,'President'),(24,0,'Vice President'),(25,0,'Secretary'),(26,0,'Treasurer'),(27,0,'Historian'),(28,0,'Fundraiser'),(29,0,'Officer');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_other_langs`
--

DROP TABLE IF EXISTS `position_other_langs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_other_langs` (
  `position` int(11) NOT NULL,
  `other_langs` varchar(255) DEFAULT NULL,
  KEY `FK_jct88cef9jpmo4hjeoff94w7j` (`position`),
  CONSTRAINT `FK_jct88cef9jpmo4hjeoff94w7j` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_other_langs`
--

LOCK TABLES `position_other_langs` WRITE;
/*!40000 ALTER TABLE `position_other_langs` DISABLE KEYS */;
INSERT INTO `position_other_langs` VALUES (23,'Presidente'),(24,'Vice Presidente'),(25,'Secretario'),(26,'Tesorero'),(27,'Historiador'),(28,'Recaudador'),(29,'Funcionario');
/*!40000 ALTER TABLE `position_other_langs` ENABLE KEYS */;
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
  `final_mode` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `chapter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1k8gj5092fv7q0e4ikqlc30g` (`chapter`),
  CONSTRAINT `FK_1k8gj5092fv7q0e4ikqlc30g` FOREIGN KEY (`chapter`) REFERENCES `chapter` (`id`)
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
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `march_column` int(11) NOT NULL,
  `march_row` int(11) NOT NULL,
  `record` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segment`
--

DROP TABLE IF EXISTS `segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `arrive_time` time DEFAULT NULL,
  `number` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `parade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ah198g3ugn3uw4ajqtdlql0ja` (`parade`),
  CONSTRAINT `FK_ah198g3ugn3uw4ajqtdlql0ja` FOREIGN KEY (`parade`) REFERENCES `parade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segment`
--

LOCK TABLES `segment` WRITE;
/*!40000 ALTER TABLE `segment` DISABLE KEYS */;
/*!40000 ALTER TABLE `segment` ENABLE KEYS */;
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
  `adress` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
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
  `cvv` int(11) NOT NULL,
  `expiration` date DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `linktpage` varchar(255) DEFAULT NULL,
  `url_banner` varchar(255) DEFAULT NULL,
  `parade` int(11) DEFAULT NULL,
  `sponsor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4ngakrolee2xy93wibkm3tr52` (`parade`),
  KEY `FK_huglhkud0ihqdljyou4eshra6` (`sponsor`),
  CONSTRAINT `FK_huglhkud0ihqdljyou4eshra6` FOREIGN KEY (`sponsor`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_4ngakrolee2xy93wibkm3tr52` FOREIGN KEY (`parade`) REFERENCES `parade` (`id`)
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
INSERT INTO `user_account` VALUES (16,0,'','e00cf25ad42683b3df678c61f42c6bda','admin1');
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
INSERT INTO `user_account_authorities` VALUES (16,'ADMIN');
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

-- Dump completed on 2019-03-28 19:58:23
commit;