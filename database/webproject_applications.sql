CREATE DATABASE  IF NOT EXISTS `webproject` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `webproject`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: webproject
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications` (
  `application_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id_fk` bigint NOT NULL,
  `title` varchar(100) COLLATE utf8_bin NOT NULL,
  `application_type` enum('cargo','passenger') COLLATE utf8_bin NOT NULL,
  `date` bigint NOT NULL,
  `cargo_weight` double NOT NULL DEFAULT '0',
  `cargo_volume` double NOT NULL DEFAULT '0',
  `passengers_number` int NOT NULL DEFAULT '0',
  `departure_date` bigint NOT NULL,
  `departure_address` varchar(150) COLLATE utf8_bin NOT NULL,
  `departure_city` varchar(50) COLLATE utf8_bin NOT NULL,
  `arrival_date` bigint NOT NULL,
  `arrival_address` varchar(150) COLLATE utf8_bin NOT NULL,
  `arrival_city` varchar(50) COLLATE utf8_bin NOT NULL,
  `description` longtext COLLATE utf8_bin,
  PRIMARY KEY (`application_id`),
  KEY `fk_applications_users1_idx` (`user_id_fk`),
  CONSTRAINT `fk_applications_users1` FOREIGN KEY (`user_id_fk`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications`
--

LOCK TABLES `applications` WRITE;
/*!40000 ALTER TABLE `applications` DISABLE KEYS */;
INSERT INTO `applications` VALUES (1,18,'Перевозка','passenger',1603579442653,0,0,2,1605474000000,'Некрасова, 15','Минск',1605474000000,'Минская, 56','Бобруйск','Отвезти'),(2,18,'Перевозка груза','cargo',1603659982019,250,30,0,1605700800000,'ул. Якуба Коласа, 17-25','Минск',1605708000000,'ул. Горецкого, 12','Минск','Отвезти корм в приют'),(3,18,'Пункт А - Пункт В','passenger',1603824008916,0,0,3,1604091600000,'Кутузова, 4','Минск',1604091600000,'Купревича, 1/2','Минск','Маршрут'),(5,18,'New app','cargo',1603884174530,14,2,0,1604005200000,'Якуба Коласа, 17-25','Минск',1604091600000,'Минская, 56','Бобруйск',''),(6,18,'Отвезти маски','cargo',1604348669522,120,50,0,1606338000000,'Кутузова, 4','Минск',1606338000000,'Розы Люксембург, 25','Минск','10 коробок масок'),(7,19,'Привезти респираторы','cargo',1604384503673,150,30,0,1605646800000,'пр. Независимости, 132','Минск',1605646800000,'Розы Люксембург, 25','Минск','30 коробок респираторов'),(8,19,'Костюмы','cargo',1604384616160,200,58.6,0,1605819600000,'Независимости, 132','Минск',1605906000000,'Розы Люксембург, 25','Минск','50 комплектов костюмов'),(9,20,'Отвезти людей','passenger',1604388649131,0,0,3,1605819600000,'ул. Н-Неман, 139','Борисов',1605906000000,'Пер. 1й Слуцкий, 48','Бобруйск','Отвезти 3 человек'),(10,20,'Перевозка пассажиров','passenger',1604388791610,0,0,2,1605733200000,'Некрасова, 15','Борисов',1605733200000,'Гагарина, 56','Борисов','Отвезти'),(11,24,'Макулатура','cargo',1604612424216,200,100,0,1605733200000,'Кутузова, 4','Борисов',1605733200000,'Минская, 56','Борисов','200кг макулатуры отвезти из школы в пункт приема. Желательно до 15.00.'),(12,24,'Книги','cargo',1604612485721,55,8,0,1605819600000,'Кутузова, 4','Борисов',1605906000000,'Пер. 1й Слуцкий, 48','Борисов','25 книг'),(14,25,'15 детей','passenger',1604615426592,0,0,15,1606770000000,'Якуба Коласа, 17-25','Минск',1606770000000,'Купревича, 1/2','Минск','Экскурсия для детей. Примерно 6 часов общее время.'),(15,25,'Корм','cargo',1604615497568,80,60,0,1605992400000,'Кольцова, 16','Минск',1605992400000,'Цнянская, 4','Минск','40 упаковок корма для собак'),(16,28,'Вещи','cargo',1604617980463,350,150,0,1606165200000,'ул. Тимирязева, 65','Минск',1606251600000,'ул. Горецкого, 19','Минск','Помочь с переездом'),(17,28,'Вещи в приют','cargo',1604618087986,180,180,0,1607461200000,'ул. Тимирязева, 65','Минск',1607720400000,'Инкубаторная, 56','Борисов','Отвезти вещи в Борисов'),(19,31,'Переезд','cargo',1604755817471,2500,0,0,1605042000000,'Победителей, 14','Минск',1605214800000,'Ложинская, 56','Минск','Необходима помощь в переезде: диван, шкаф и 2 тумбочки'),(20,25,'Одежда','cargo',1604760235038,650,300,0,1605733200000,'Некрасова, 15','Минск',1605819600000,'Горецкого, 12','Минск','Одежда в детский дом.'),(21,31,'Отвезти собак','passenger',1604798910026,0,0,3,1606338000000,'Победителей, 106','Минск',1606338000000,'Илимская, 56','Минск','3 собаки необходимо отвезти в приют');
/*!40000 ALTER TABLE `applications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-10  4:34:02
