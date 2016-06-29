CREATE DATABASE  IF NOT EXISTS `example_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `example_db`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: example_db
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `level` (
  `levelId` bigint(20) NOT NULL AUTO_INCREMENT,
  `levelName` varchar(255) DEFAULT NULL,
  `seatPrice` double NOT NULL,
  PRIMARY KEY (`levelId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
INSERT INTO `level` VALUES (1,'Orchestra',100),(2,'Main',75),(3,'Balcony 1',50),(4,'Balcony 2',40);
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `levelrow`
--

DROP TABLE IF EXISTS `levelrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `levelrow` (
  `rowId` bigint(20) NOT NULL AUTO_INCREMENT,
  `levelId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rowId`),
  KEY `FKsblmfxkyq7gk7wupguvhfp97s` (`levelId`),
  CONSTRAINT `FKsblmfxkyq7gk7wupguvhfp97s` FOREIGN KEY (`levelId`) REFERENCES `level` (`levelId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `levelrow`
--

LOCK TABLES `levelrow` WRITE;
/*!40000 ALTER TABLE `levelrow` DISABLE KEYS */;
INSERT INTO `levelrow` VALUES (1,1),(2,1),(3,2),(4,3),(11,4);
/*!40000 ALTER TABLE `levelrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `reservationId` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerEmail` varchar(255) DEFAULT NULL,
  `reservationDate` datetime DEFAULT NULL,
  `reservationToken` bigint(20) NOT NULL,
  PRIMARY KEY (`reservationId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (5,'test@exampl.com','2016-06-26 09:42:48',968986222226113),(6,'test2@example.com','2016-06-27 10:07:32',1056895560327641),(7,'test2@example.com','2016-06-28 08:05:33',1135976458933709),(8,'test2@example.com','2016-06-28 08:49:54',1138638046073770),(9,'test2@example.com','2016-06-28 23:50:26',1192774019679748);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seat` (
  `seatId` bigint(20) NOT NULL AUTO_INCREMENT,
  `seatNo` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `rowId` bigint(20) DEFAULT NULL,
  `seatHoldId` bigint(20) DEFAULT NULL,
  `reservationId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`seatId`),
  KEY `FKkhcd39io5v71clq5v5e4vh9wp` (`rowId`),
  KEY `FK1iq73or04nvh2i0num8sc7wak` (`seatHoldId`),
  KEY `FKanflbgxubhslywvup7so1lgvi` (`reservationId`),
  CONSTRAINT `FK1iq73or04nvh2i0num8sc7wak` FOREIGN KEY (`seatHoldId`) REFERENCES `seathold` (`id`),
  CONSTRAINT `FKanflbgxubhslywvup7so1lgvi` FOREIGN KEY (`reservationId`) REFERENCES `reservation` (`reservationId`),
  CONSTRAINT `FKkhcd39io5v71clq5v5e4vh9wp` FOREIGN KEY (`rowId`) REFERENCES `levelrow` (`rowId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
INSERT INTO `seat` VALUES (1,1,2,1,NULL,8),(2,2,0,1,NULL,NULL),(3,11,0,2,NULL,NULL),(4,12,0,2,NULL,NULL),(5,3,2,1,NULL,7),(6,21,2,11,NULL,9);
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seathold`
--

DROP TABLE IF EXISTS `seathold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seathold` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerEmail` varchar(255) DEFAULT NULL,
  `holdDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seathold`
--

LOCK TABLES `seathold` WRITE;
/*!40000 ALTER TABLE `seathold` DISABLE KEYS */;
/*!40000 ALTER TABLE `seathold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e6gkqunxajvyxl5uctpl2vl2p` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-29  0:51:53
