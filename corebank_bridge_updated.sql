-- MySQL dump 10.13  Distrib 8.3.0, for Win64 (x86_64)
--
-- Host: localhost    Database: corebank_bridge
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `account_number` bigint NOT NULL,
  `account_type` enum('SAVINGS','CURRENT') NOT NULL,
  `balance` decimal(38,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `status` enum('ACTIVE','BLOCKED') NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `UK_6kplolsdtr3slnvx97xsy2kc8` (`account_number`),
  KEY `FK19xwsmaoecwrcb3xjtyhlknbx` (`customer_id`),
  CONSTRAINT `FK19xwsmaoecwrcb3xjtyhlknbx` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,840467846938,'SAVINGS',1000.00,'2026-02-11 00:26:38.444000','ACTIVE',7),(2,772568055044,'CURRENT',500.00,'2026-02-11 00:38:51.287000','ACTIVE',2),(3,563001512371,'CURRENT',1000.00,'2026-02-11 11:09:56.976000','ACTIVE',5);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `log_id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `FKjs4iimve3y0xssbtve5ysyef0` (`user_id`),
  CONSTRAINT `FKjs4iimve3y0xssbtve5ysyef0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `loan_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `credit_score` int NOT NULL,
  `emi` decimal(12,2) NOT NULL,
  `loan_amount` decimal(15,2) NOT NULL,
  `loan_type` enum('HOME','CAR','PERSONAL') NOT NULL,
  `salary` decimal(12,2) NOT NULL,
  `status` enum('APPROVED','PENDING','REJECTED') NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`loan_id`),
  KEY `FKklp2npf207iq79o9a94qejmy4` (`customer_id`),
  CONSTRAINT `FKklp2npf207iq79o9a94qejmy4` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `loans_chk_1` CHECK (((`credit_score` >= 300) and (`credit_score` <= 900)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `txn_id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `status` enum('SUCCESS','FAILED','PENDING') NOT NULL,
  `txn_type` enum('DEPOSIT','WITHDRAW','TRANSFER') NOT NULL,
  `from_acc_id` bigint NOT NULL,
  `to_acc_id` bigint DEFAULT NULL,
  PRIMARY KEY (`txn_id`),
  KEY `FK6th0lhd82rofdiafgcbbua5ra` (`from_acc_id`),
  KEY `FKc6nxa794wxdk9ulkuj1jqown3` (`to_acc_id`),
  CONSTRAINT `FK6th0lhd82rofdiafgcbbua5ra` FOREIGN KEY (`from_acc_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKc6nxa794wxdk9ulkuj1jqown3` FOREIGN KEY (`to_acc_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `mfa_enabled` bit(1) NOT NULL,
  `otp_code` varchar(255) DEFAULT NULL,
  `otp_expiry` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `reset_token_expiry` datetime(6) DEFAULT NULL,
  `role` enum('CUSTOMER','ADMIN') NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-02-09 23:39:25.889182','sony@gmail.com','xxx yyy',_binary '\0',NULL,NULL,'$2a$10$5e7v3RWr/mfk8ue9LeEooOxTWUxY8PhzcpQGIP3RRFtsMHtsUzhL.','9999900000',NULL,NULL,'CUSTOMER','ACTIVE'),(2,'2026-02-09 23:47:23.000000','admin@bank.com','Admin User',_binary '\0',NULL,NULL,'$2a$10$pj3W50svJN7hM/9V7lOwCuV/hxTuHjFTwZbwvboGJNrBTeAm456Qe','9999999999',NULL,NULL,'ADMIN','ACTIVE'),(3,'2026-02-10 00:38:09.341189','angel@gmail.com','abc abc',_binary '\0',NULL,NULL,'$2a$10$qv63DWQnsHdcXMG/UAmKauA19WuMJawFI4y3pT32sLLlP4ej9bEKi','9999955555',NULL,NULL,'CUSTOMER','ACTIVE'),(4,'2026-02-10 10:41:55.224096','sara@gmail.com','sara khana',_binary '\0',NULL,NULL,'$2a$10$2NxTLCKb6hUddS/G/PfEfegyuNJwiQypXSkCCROxvigsCZaM.5.KO','8888877777',NULL,NULL,'CUSTOMER','ACTIVE'),(5,'2026-02-10 11:05:12.009616','prasad@gmail.com','Prasad  Rao',_binary '\0',NULL,NULL,'$2a$10$.GQ7uIFloOeiINANyBAG9OSDSTHXSqzs5u8FYv1egoDmtO9Jts9dW','6666677777',NULL,NULL,'CUSTOMER','ACTIVE'),(6,'2026-02-10 13:12:52.021364','dooly@gmail.com','Dolly Fernandez',_binary '\0',NULL,NULL,'$2a$10$HwGKCqBBLJBJn8itcbzbb.nd5QnRbxv/RnYj5KdWaCQJDzUvc7FBe','8888877777',NULL,NULL,'CUSTOMER','ACTIVE'),(7,'2026-02-10 16:09:47.207196','latha@gmail.com','Latha Teetla',_binary '\0',NULL,NULL,'$2a$10$fIkQESbnEbEVGJbVchRHWeM0xakhG5P9q30y8eqiPfXTW96KGR5wW','9999900000',NULL,NULL,'CUSTOMER','ACTIVE'),(8,'2026-02-10 23:41:33.695561','Suraj@gmail.com','Suraj J',_binary '\0',NULL,NULL,'$2a$10$i0oJZalZBXM5vQaAeeqwzeDUvsaXyq/H1/.ythkJ7ClDn5pT1I9aG','8888877777',NULL,NULL,'CUSTOMER','ACTIVE');
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

-- Dump completed on 2026-02-11 12:36:19
