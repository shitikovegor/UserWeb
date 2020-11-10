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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone` bigint NOT NULL,
  `role` enum('administrator','driver','client','guest') NOT NULL,
  `subject` enum('individual','organization') NOT NULL,
  `blocked` tinyint NOT NULL DEFAULT '0',
  `activated` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`) /*!80000 INVISIBLE */,
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Administrator','$2a$10$/FLoanUr2q6VKKDB8.ftj.IvT7K95iAKv0t9iwIvm8bBKoq/vigSS','Егор','Шитиков','shitikov.egor@gmail.comm',375292511741,'administrator','individual',0,1),(17,'User123','$2a$10$vY08Rude11MgWqKJSbkOb.w.73lHY3iliYC.5.N/QdqZDqivrFJH2','Виталий','Петров','petrov@gmail.ce',375292511746,'driver','individual',0,1),(18,'New-user','$2a$10$BTGgxi4S8HaOIbNM0AfJCOsbGx3aBeJCiOjM6ZfEBctkPE2zb7tB.','Егор','Шитиков','shitikov@gmail.com',375292511740,'client','individual',0,1),(19,'Hospital4','$2a$10$OUY4SwR0D6v2ctXQdHJjnu.7KZjVtdIaIrDrWBl7aO.klz0JWahu2','Иван','Иванов','Hospital4@gmail.com',375445874567,'client','organization',0,1),(20,'Bobruisk','$2a$10$JzsX0HShf2ceSNqTDELqTunuUK9GlwK4DoA.l.lB3ngcwAD0R6GJm','Андрей','Петров','bobruisk324@mail.ru',375291115588,'client','individual',0,1),(21,'Driver1','$2a$10$Y4785HIJYAmnSAHzYDkXP.M/2pnmvQKJldkvPecMsIP69cmw/tXNm','Игорь','Романцев','driverForHelp@gmail.com',375295685655,'driver','individual',1,1),(22,'Driver2','$2a$10$hsOpYpOTETv9ct6UgzpaIe37JNNB6TmPoInvGmF87qHWMosUA3l1K','Георгий','Донской','driver2@list.ru',375445215486,'driver','individual',0,1),(23,'Client1','$2a$10$U.O/dV9mreLpbZKUaelaSe6uJtfSiMOaHgyP5a5ZxdIEXenwUNnjy','Сергей','Красовский','client1@tut.by',375295621144,'client','individual',0,1),(24,'Client2','$2a$10$WiG6Hg5SPlz1U./FoSa33.V5O.y6QRYt2d2SveIUr64zkmmjnzNeK','Ян','Черемшин','client2@tut.com',375441225555,'client','individual',0,1),(25,'Client3','$2a$10$DK/wEggbRQ5iE1q4XkBSKe2lmX0va03galohToIayhDJakDiEZJkO','Людмила','Колосова','client3@tut.com',375295226466,'client','organization',0,1),(26,'Driver3','$2a$10$LH7aVEJKEyeqgDLl8/DCM.CivopvsJQ3f9sCbJ0CbeZ98G8hWp6We','Анастасия','Загорская','driver3@mail.ze',375295558855,'driver','individual',0,1),(27,'Driver4','$2a$10$zhs2g/EtRs9a9dSOj1TJHudIJsOGFTwGO4XvKVxCVdE8r2qJ1sd1G','Иван','Грозный','driver4@mail.ze',375331112232,'driver','organization',0,1),(28,'Client4','$2a$10$0WVkNo8pceyiqHRz9rL40OscW.SGO1m6vVD9RHcSCUP6H4/jJzAxy','Мария','Князева','client4@tut.com',375445874563,'client','individual',0,1),(31,'Client5','$2a$10$6agM4I5JOpmiVWbjKOtCuecfuTjDe38KNULZHkf/.BE7TZr5AiMSG','Алеся','Миролюбова','client5@tut.by',375441256459,'client','organization',0,1),(32,'Client6','$2a$10$UA92Zritjtseyw78Z5WcuejJ4iBPmsMxzNNn7c6leoU69k97RKCBC','Виталий','Гурбач','shitikov.egor@gmail.com',375292511748,'client','individual',0,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
