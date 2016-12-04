-- MySQL dump 10.13  Distrib 5.7.13, for osx10.11 (x86_64)
--
-- Host: localhost    Database: comp344_ecommerce
-- ------------------------------------------------------
-- Server version	5.7.13

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

CREATE SCHEMA comp344_ecommerce;
USE comp344_ecommerce;
--
-- Table structure for table `credit_cards`
--

DROP TABLE IF EXISTS `credit_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_cards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(16) DEFAULT NULL,
  `card_type` varchar(45) DEFAULT NULL,
  `expire_month` int(11) DEFAULT NULL,
  `expire_year` int(11) DEFAULT NULL,
  `name_on_card` varchar(45) DEFAULT NULL,
  `security_code` varchar(3) DEFAULT NULL,
  `created_at` varchar(45) DEFAULT NULL,
  `customers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_credit_cards_customers1_idx` (`customers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_cards`
--

LOCK TABLES `credit_cards` WRITE;
/*!40000 ALTER TABLE `credit_cards` DISABLE KEYS */;
INSERT INTO `credit_cards` VALUES (1,'4444555566667777','Master',8,2019,'Byambatsog','221','2016-10-04 20:56:37.672',1),(2,'4443555466657776','Visa',7,2018,'Franberty','231','2016-10-04 20:56:37.672',2),(3,'4442555366647775','Amex',6,2017,'Denis','230','2016-10-04 20:56:37.672',3),(4,'4441555266535763','Master',5,2019,'Melisa','247','2016-10-04 20:56:37.672',4),(5,'4440767867383939','Master',6,2018,'Faith','649','2016-10-04 20:56:37.672',5),(6,'5308792733793027','Master',7,2017,'John','674','2016-10-04 20:56:37.672',6),(7,'8376093730373839','Visa',1,2019,'Michael','832','2016-10-04 20:56:37.672',7),(8,'5687930357859574','Amex',9,2018,'Amy','329','2016-10-04 20:56:37.672',8),(9,'5683264985759588','Master',10,2017,'Gabriel','042','2016-10-04 20:56:37.672',9),(10,'6585769734858600','Master',12,2016,'Damani','284','2016-10-04 20:56:37.672',10),(11,'4648494749494749','Visa',3,2018,'Fisher','479','2016-10-04 20:56:37.672',1),(12,'8598534078583900','Visa',1,2019,'Lawrence','789','2016-10-04 20:56:37.672',2),(13,'5648584943844899','Master',8,2020,'Dom','847','2016-10-04 20:56:37.672',3),(14,'6759808780583998','Master',9,2019,'Armani','973','2016-10-04 20:56:37.672',4),(15,'8789598578383078','Amex',7,2019,'Joe','984','2016-10-04 20:56:37.672',5),(16,'7465985703875685','Master',10,2020,'Betty','684','2016-10-04 20:56:37.672',6),(17,'6848948232984874','Visa',12,2018,'Ellen','746','2016-10-04 20:56:37.672',7),(18,'8477944004804800','Master',9,2019,'Denis','970','2016-10-04 20:56:37.672',8),(19,'7859789037904804','Master',7,2018,'Vivian','980','2016-10-04 20:56:37.672',9),(20,'8759808057980850','Visa',8,2019,'Florence','930','2016-10-04 20:56:37.672',10);
/*!40000 ALTER TABLE `credit_cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_addresses`
--

DROP TABLE IF EXISTS `customer_addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `street` varchar(60) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zip_code` varchar(10) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `customers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_addresses_customers_idx` (`customers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_addresses`
--

LOCK TABLES `customer_addresses` WRITE;
/*!40000 ALTER TABLE `customer_addresses` DISABLE KEYS */;
INSERT INTO `customer_addresses` VALUES (1,'1246 W Pratt blvd','Chicago','IL','60626','United States','7734567212','2016-10-04 20:56:38',1),(2,'6564 N Sheridan rd','Chicago','IL','60626','United States','7737891234','2016-10-04 20:56:37',2),(3,'1584 East 51st str','Brooklyn','NY','10587','United States','3469871254','2016-10-04 20:56:37',3),(4,'1871 S King dr','Phoenix','AZ','87390','United States','6268709809','2016-10-04 20:56:37',4),(5,'2971 W Illinois rd','Chicago','IL','60611','United States','3128975600','2016-10-04 20:56:37',5),(6,'7894 E Rush str','Chicago','IL','60611','United States','7735601209','2016-10-04 20:56:37',6),(7,'11 E Pearson dr','Chicago','IL','60611','United States','7733128910','2016-10-04 20:56:37',7),(8,'151 S Lake str','Chicago','IL','60654','United States','7730980202','2016-10-04 20:56:37',8),(9,'121 N Bluemoon','Chicago','IL','60617','United States','3128901254','2016-10-04 20:56:37',9),(10,'615 Rogers Ave','Chicago','IL','60613','United States','7738091586','2016-10-04 20:56:37',10);
/*!40000 ALTER TABLE `customer_addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_cart_items`
--

DROP TABLE IF EXISTS `customer_cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_cart_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) DEFAULT NULL,
  `customer_carts_id` int(11) NOT NULL,
  `products_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_cart_items`
--

LOCK TABLES `customer_cart_items` WRITE;
/*!40000 ALTER TABLE `customer_cart_items` DISABLE KEYS */;
INSERT INTO `customer_cart_items` VALUES (1,3,1,1),(3,3,1,2),(4,2,2,1),(5,3,2,2),(6,1,2,3),(7,2,3,4),(8,1,3,5),(9,3,4,6),(10,1,4,7),(11,2,5,8),(12,3,5,9),(13,3,6,10),(14,2,6,11),(15,2,7,12),(16,1,7,13),(17,1,8,14),(18,2,8,15),(19,2,9,16),(20,3,9,17),(21,3,10,18),(22,2,10,19),(23,2,11,20),(24,3,11,21);
/*!40000 ALTER TABLE `customer_cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_carts`
--

DROP TABLE IF EXISTS `customer_carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_carts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` varchar(45) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `customers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_carts`
--

LOCK TABLES `customer_carts` WRITE;
/*!40000 ALTER TABLE `customer_carts` DISABLE KEYS */;
INSERT INTO `customer_carts` VALUES (1,'2016-10-31 21:00:02.072',0,1),(2,'2016-11-05 22:52:55.165',0,1),(3,'2016-11-05 22:54:37.932',0,2),(4,'2016-11-05 22:54:51.162',0,3),(5,'2016-11-05 22:56:15.76',0,4),(6,'2016-11-05 22:56:27.226',0,5),(7,'2016-11-05 22:56:52.63',0,6),(8,'2016-11-05 22:57:13.191',0,7),(9,'2016-11-05 22:57:43.013',0,8),(10,'2016-11-05 22:57:54.206',0,9),(11,'2016-11-05 22:58:12.185',0,10);
/*!40000 ALTER TABLE `customer_carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `login_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customers_login1_idx` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Byambatsog','Chimed','byambatsog@gmail.com','2016-10-04 20:56:37',1),(2,'Francis','Albert','fran@gmail.com','2016-10-04 20:56:37',2),(3,'Albert','Francis','albert@gmail.com','2016-10-04 20:56:37',3),(4,'Ogor','Francis','ogor@gmail.com','2016-10-04 20:56:37',4),(5,'Chris','Emma','cemma@gmail.com','2016-10-04 20:56:37',5),(6,'Perry','Elis','pellis@gmail.com','2016-10-04 20:56:37',6),(7,'Sean','John','sjohn@gmail.com','2016-10-04 20:56:37',7),(8,'Tom','Ford','tford@gmail.com','2016-10-04 20:56:37',8),(9,'Erica','Peter','epeter@gmail.com','2016-10-04 20:56:37',9),(10,'Roneka','Daniels','rdaniels@gmail.com','2016-10-04 20:56:37',10);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `password` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `role` varchar(20) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'byambatsog@gmail.com','25f9e794323b453885f5181f1b624d0b',1,0,'2016-10-04 20:56:37'),(2,'fran@gmail.com','2fc27231161d0350e1636984e5445fc6',1,0,'2016-10-30 17:15:47'),(3,'albert@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(4,'ogor@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(5,'cemma@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(6,'pellis@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(7,'sjohn@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(8,'tford@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(9,'epeter@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(10,'rdaniels@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-10-30 21:38:32'),(11,'bigvalueinc@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(12,'alldayzip@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(13,'antonline@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(14,'digitalsystems@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(15,'musicpeople@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(16,'furnitureworld@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(17,'spacejet@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(18,'sportsworld@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40'),(19,'shoesalliance@gmail.com','f23101051a46ffea551fb117564a3fe4',1,0,'2016-11-04 16:37:40');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_products`
--

DROP TABLE IF EXISTS `order_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_products` (
  `orders_id` int(11) NOT NULL,
  `products_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `tracking_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`orders_id`,`products_id`),
  KEY `fk_order_products_orders1_idx` (`orders_id`),
  KEY `fk_order_products_products1_idx` (`products_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_products`
--

LOCK TABLES `order_products` WRITE;
/*!40000 ALTER TABLE `order_products` DISABLE KEYS */;
INSERT INTO `order_products` VALUES (1,1,3,1668.92,'SHIPPED','1122334455'),(1,2,3,990.00,'SHIPPED','3344556677'),(2,1,2,1668.92,'ORDERED',NULL),(2,2,3,990.00,'ORDERED',NULL),(2,3,1,679.99,'ORDERED',NULL),(3,4,2,299.99,'ORDERED',NULL),(3,5,1,240.99,'ORDERED',NULL),(4,6,3,899.99,'ORDERED',NULL),(4,7,1,449.99,'ORDERED',NULL),(5,8,2,1065.99,'FULFILLED','3344556673'),(5,9,3,1219.95,'FULFILLED','3344556673'),(6,10,3,1675.00,'ORDERED',NULL),(6,11,2,319.99,'ORDERED',NULL),(7,12,2,13.99,'ORDERED',NULL),(7,13,1,129.99,'ORDERED',NULL),(8,14,1,34.99,'ORDERED',NULL),(8,15,2,14.98,'ORDERED',NULL),(9,16,2,197.99,'ORDERED',NULL),(9,17,3,202.99,'ORDERED',NULL),(10,18,3,343.13,'SHIPPED','3344556672'),(10,19,2,329.13,'SHIPPED','3344556672'),(11,20,2,775.00,'DELIVERED','3344556671'),(11,21,3,1199.00,'DELIVERED','3344556671');
/*!40000 ALTER TABLE `order_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `orders_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_status_orders1_idx` (`orders_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT INTO `order_status` VALUES (1,'ORDERED','2016-11-01 17:53:38',1),(2,'SHIPPED','2016-11-01 20:41:14',1),(3,'ORDERED','2016-11-05 23:08:43',2),(4,'ORDERED','2016-11-05 23:10:15',3),(5,'ORDERED','2016-11-05 23:10:30',4),(6,'ORDERED','2016-11-05 23:10:42',5),(7,'ORDERED','2016-11-05 23:10:55',6),(8,'ORDERED','2016-11-05 23:11:02',7),(9,'ORDERED','2016-11-05 23:11:10',8),(10,'ORDERED','2016-11-05 23:11:19',9),(11,'ORDERED','2016-11-05 23:11:27',10),(12,'ORDERED','2016-11-05 23:11:39',11),(13,'FULFILLED','2016-11-06 12:17:52',11),(14,'SHIPPED','2016-11-06 12:20:34',11),(15,'DELIVERED','2016-11-06 12:28:31',11),(16,'FULFILLED','2016-11-06 12:30:00',10),(17,'SHIPPED','2016-11-06 12:30:25',10),(18,'FULFILLED','2016-11-06 12:32:13',5);
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `paid_at` datetime DEFAULT NULL,
  `last_status` varchar(29) NOT NULL DEFAULT 'ORDERED',
  `customers_id` int(11) NOT NULL,
  `shipping_addresses_id` int(11) NOT NULL,
  `billing_addresses_id` int(11) NOT NULL,
  `credit_cards_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_customers1_idx` (`customers_id`),
  KEY `fk_orders_customer_addresses1_idx` (`shipping_addresses_id`),
  KEY `fk_orders_customer_addresses2_idx` (`billing_addresses_id`),
  KEY `fk_orders_customer_credit_card_idx` (`credit_cards_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2016-11-01 17:53:38',7976.76,'2016-11-01 17:53:38','SHIPPED',1,1,1,1),(2,'2016-11-05 23:08:42',6987.83,'2016-11-05 23:08:42','ORDERED',1,1,1,1),(3,'2016-11-05 23:10:15',840.97,'2016-11-05 23:10:15','ORDERED',2,2,2,2),(4,'2016-11-05 23:10:30',3149.96,'2016-11-05 23:10:30','ORDERED',3,3,3,3),(5,'2016-11-05 23:10:42',5791.83,'2016-11-05 23:10:42','FULFILLED',4,4,4,4),(6,'2016-11-05 23:10:55',5664.98,'2016-11-05 23:10:55','ORDERED',5,5,5,5),(7,'2016-11-05 23:11:02',157.97,'2016-11-05 23:11:02','ORDERED',6,6,6,6),(8,'2016-11-05 23:11:10',64.95,'2016-11-05 23:11:10','ORDERED',7,7,7,7),(9,'2016-11-05 23:11:19',1004.95,'2016-11-05 23:11:19','ORDERED',8,8,8,8),(10,'2016-11-05 23:11:27',1687.65,'2016-11-05 23:11:27','SHIPPED',9,9,9,9),(11,'2016-11-05 23:11:39',5147.00,'2016-11-05 23:11:39','DELIVERED',10,10,10,10);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partners`
--

DROP TABLE IF EXISTS `partners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(60) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `street` varchar(60) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zip_code` varchar(10) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `login_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_partners_login1_idx` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partners`
--

LOCK TABLES `partners` WRITE;
/*!40000 ALTER TABLE `partners` DISABLE KEYS */;
INSERT INTO `partners` VALUES (1,'Big Value Inc','','','COMPANY','7735952314','bigvalueinc@gmail.com','1240 Loyola ave','Chicago','IL','60626','United States','2016-10-24 21:11:16',11),(2,'All day zip','Antonia','Mark','COMPANY','6585074949','alldayzip@gmail.com','6732 Madison Str','Chicago','IL','60615','United States','2016-10-30 17:15:47',12),(4,'Ant online','Patrick','Ewing','COMPANY',NULL,'antonline@gmail.com','7482 Devon Str','Chicago','IL','60621','United States','2016-10-30 21:38:32',13),(5,'Digital Systems','Frank','Emma','COMPANY','3128490900','digitalsystems@gmail.com','5474 Western Str','Chicago','IL','60667','United Stated','2016-10-04 20:56:37',14),(6,'Music People','Marvin','Epps','COMPANY','7737489012','musicpeople@gmail.com','5647 Chicago Ave','Chicago','IL','60689','United States','2016-10-04 20:56:37',15),(7,'Furniture World','Solomon','Vernon','PERSONAL','7733939393','furnitureworld@gmail.com','4563 State Str','Chicago','IL','60611','United States','2016-10-04 20:56:37',16),(8,'Space Jet','Bryan','Louis','PERSONAL','3129874561','spacejet@gmail.com','5646 Chicago Dr','Chicago','IL','60623','United States','2016-10-04 20:56:37',17),(9,'Sports World','Michael','Jordan','PERSONAL','3126789012','sportsworld@gmail.com','6729 Lincoln Ave','Chicago','IL','60612','United States','2016-10-04 20:56:37',18),(10,'Shoes Alliance','Tom','Ford','PERSONAL','3126758998','shoesalliance@gmail.com','8234 Franklin Dr','Chicago','IL','60634','United States','2016-10-04 20:56:37',19);
/*!40000 ALTER TABLE `partners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_categories`
--

DROP TABLE IF EXISTS `product_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` text,
  `status` tinyint(1) DEFAULT NULL,
  `ranking` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_categories`
--

LOCK TABLES `product_categories` WRITE;
/*!40000 ALTER TABLE `product_categories` DISABLE KEYS */;
INSERT INTO `product_categories` VALUES (1,'Cameras & Photo','',1,1,'2016-10-24 21:05:12'),(2,'Car Electronics','',1,1,'2016-10-24 21:05:30'),(3,'Cell Phones & Accessories','',1,1,'2016-10-24 21:05:48'),(4,'Computers & Tablets','',1,1,'2016-10-24 21:06:03'),(5,'TV, Video, & Audio','',1,1,'2016-10-24 21:06:23'),(6,'Video Games & Consoles','',1,1,'2016-10-24 21:06:46'),(7,'Virtual Reality','',1,1,'2016-10-24 21:06:57'),(8,'Computer Equipments','',1,1,'2016-10-04 20:56:37'),(9,'Electrical Equipmets','',1,1,'2016-10-04 20:56:37'),(10,'Musical Equipments','',1,1,'2016-10-04 20:56:37');
/*!40000 ALTER TABLE `product_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `brand_name` varchar(60) DEFAULT NULL,
  `description` text,
  `status` tinyint(1) DEFAULT NULL,
  `quantity_in_stock` int(11) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `product_categories_id` int(11) NOT NULL,
  `partners_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_products_product_categories1_idx` (`product_categories_id`),
  KEY `fk_products_partners1_idx` (`partners_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Nikon D500 20.9MP Digital SLR Camera','http://i.ebayimg.com/images/g/4~wAAOSwLnBX9oYY/s-l500.jpg','Nikon','description',1,198,1668.92,32.10,'2016-10-24 21:19:39',1,1),(2,'Apple iPhone 7 Plus (Latest Model)','http://i.ebayimg.com/images/g/yZ8AAOSw4shX-HvJ/s-l500.jpg','Apple','GPS, Internet Browser, Music Player, Near Field Communication',1,197,990.00,2.40,'2016-10-31 15:41:51',3,4),(3,'New Microsoft Surface Pro 3','http://i.ebayimg.com/images/g/sScAAOSwPCVX6VLY/s-l1600.jpg','Microsoft ','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag',1,199,679.99,4.50,'2016-11-04 17:00:57',4,5),(4,'Apple iPad Mini 4 7.9\" Retina Display','http://i.ebayimg.com/images/g/QtgAAOSw4GVYG0pU/s-l1600.jpg','Apple','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag',1,198,299.99,2.50,'2016-11-04 17:04:07',4,5),(5,'7\" Quad Core Google Android 4.4.2 KitKat','http://i.ebayimg.com/images/g/~nkAAOSw7hRWOx5n/s-l1600.jpg','Google','Built-In Front Camera,Built-In Rear Camera,Expanda, Bluetooth, Built-In Front Camera, Built-In Rear Camera, Email, Expandable Memory, File Browsing, HD Compatible, Integrated Speakers, Media Player, MP3 Player, Touch Screen, Video Recorder, Web Browser',1,199,240.99,3.50,'2016-11-04 17:28:25',4,5),(6,'HP - Omen 15.6 inch Laptop - Intel Core i7','http://i.ebayimg.com/images/g/JcwAAOSwFdtXyb5C/s-l1600.jpg','HP','Windows 10 NVIDIA GeForce GTX 950M 2GB dedicated graphics Technical details: 6th Gen Intel Corei7 processor; 15.6 inch display; 8GB memory; 1TB hard drive Special features: Bluetooth; backlit keyboard; HDMI output Note: DVD/CD drive not included',1,197,899.99,5.30,'2016-11-04 17:31:44',4,5),(7,'Dell 15.6 inch HD TouchScreen QuadCore','http://i.ebayimg.com/images/g/EbQAAOSwfZ1WX086/s-l1600.jpg','Dell','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag.',1,199,449.99,4.30,'2016-11-04 17:32:45',4,5),(8,'Apple MacBook Pro MF839LL/A 13.3-Inch','http://i.ebayimg.com/images/g/5iQAAOSwLF1YBko3/s-l1600.jpg','Apple','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag.',1,198,1065.99,2.30,'2016-11-04 17:33:41',4,5),(9,'Lenovo ThinkPad P50 20EN0013US 15.6 inch','http://i.ebayimg.com/images/g/oC8AAOSwKtlWh~Zq/s-l1600.jpg','Lenovo','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag.',1,197,1219.95,3.30,'2016-11-04 17:34:38',4,5),(10,'ASUS ROG GL502VS-DB71 15 inch','http://i.ebayimg.com/images/g/JbgAAOSwr0ZXN5BR/s-l1600.jpg','Asus','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag.',1,97,1675.00,4.30,'2016-11-04 17:35:32',4,5),(11,'Apple Watch - 38mm - Stainless Steel Case','http://i.ebayimg.com/images/g/Qd8AAOSwneRXRG1l/s-l1600.jpg','Apple',' A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag. See the seller\'s listing for full details.',1,198,319.99,1.30,'2016-11-05 20:50:18',9,2),(12,'MICRO USB - CAR CHARGER','http://i.ebayimg.com/images/g/k9wAAOSwaB5Xvckz/s-l500.jpg','Eco Charger','A brand-new, unused, unopened, undamaged item in its original packaging (where packaging is applicable). Packaging should be the same as what is found in a retail store, unless the item is handmade or was packaged by the manufacturer in non-retail packaging, such as an unprinted box or plastic bag',1,248,13.99,0.30,'2016-11-05 20:52:28',9,2),(13,'Kicker KPM Bluetooth wireless speaker','http://i.ebayimg.com/images/g/zkUAAOSwuAVWzihG/s-l1600.jpg','Kicker','Kicker KPM50 Bluetooth Speaker delivers elegant bass with Two 2-inch drivers are complemented by dual-balanced bass radiators for exceptional sound from a compact design thanks to the elegant aluminum cabinet. KPM50 brings durable and elegant materials that look great in any décor, with audio performance that is sure to please the most demanding listeners',1,249,129.99,2.30,'2016-11-05 20:55:36',9,2),(14,'LG Electronics Tone Infinim HBS-900 Bluetooth Wireless Stereo Headset','http://i.ebayimg.com/images/g/kw0AAOSwHMJYHUu6/s-l1600.jpg','LG','',1,249,34.99,0.40,'2016-11-05 20:58:19',10,6),(15,'Gaming Headset Surround Stereo Headband Headphone','http://d3d71ba2asa5oz.cloudfront.net/12023541/images/60082-01__1.jpg','Samsung','',1,98,14.98,1.10,'2016-11-05 20:59:59',10,6),(16,'Samsung UN32J4500AFXZA 32-Inch','http://i.ebayimg.com/images/g/Hb8AAOSwZJBX-kEu/s-l500.jpg','Samsung','',1,118,197.99,12.30,'2016-11-05 21:01:45',5,7),(17,'22 Inch Naxa NTD-2252 12 Volt AC/DC Widescreen LED','http://i.ebayimg.com/images/g/ppoAAOSwDN1UUqRY/s-l500.jpg','Naxa','',1,217,202.99,10.30,'2016-11-05 21:02:36',5,7),(18,'New In Box Acer Aspire E 15 15.6 inch','http://i.ebayimg.com/images/g/LE0AAOSw44BYDQ9E/s-l1600.jpg','Acer','',1,137,343.13,4.30,'2016-11-05 21:05:50',4,5),(19,'Dell 12.5 inch Intel Core i5 3.0GHz','http://i.ebayimg.com/images/g/HJkAAOSwmLlYB6Jp/s-l1600.jpg','Dell','',1,238,329.13,5.30,'2016-11-05 21:07:10',4,5),(20,'Dell Inspiron 15 5000 FHD 15.6 Touchscreen','http://i.ebayimg.com/images/g/25sAAOSwXeJYGfqS/s-l500.jpg','Dell','',1,338,775.00,3.30,'2016-11-05 21:07:58',4,5),(21,'Lenovo ThinkPad X1 Carbon 4 i5-6200U','http://i.ebayimg.com/images/g/gKcAAOSwygJXhSKv/s-l1600.jpg','Lenovo','',1,207,1199.00,4.30,'2016-11-05 21:09:37',4,5);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `comment` text,
  `rating` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `products_id` int(11) NOT NULL,
  `customers_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reviews_products1_idx` (`products_id`),
  KEY `fk_reviews_customers1_idx` (`customers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=308 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'Amazing','This photo camera is amazing',5,'2016-10-31 22:13:41',1,1),(2,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:14:13',1,1),(3,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:14:39',1,2),(4,'Love this device,very fast','Liteweight',5,'2016-11-05 22:15:05',1,3),(5,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:15:27',1,4),(6,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:29:31',1,5),(7,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:29:48',1,6),(8,'nice','good smart phone',4,'2016-11-05 22:31:02',1,7),(9,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:40:32',1,8),(10,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:40:54',1,9),(11,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:41:19',1,10),(12,'Amazing','This photo camera is amazing',5,'2016-11-05 22:43:49',2,1),(13,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:43:49',2,1),(14,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:43:49',2,2),(15,'Love this device,very fast','Liteweight',5,'2016-11-05 22:43:49',2,3),(16,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:43:49',2,4),(17,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:43:49',2,5),(18,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:43:49',2,6),(19,'nice','good smart phone',4,'2016-11-05 22:43:49',2,7),(20,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:43:49',2,8),(21,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:43:49',2,9),(22,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:43:49',2,10),(27,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',3,1),(28,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',3,1),(29,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',3,2),(30,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',3,3),(31,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',3,4),(32,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',3,5),(33,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',3,6),(34,'nice','good smart phone',4,'2016-11-05 22:45:35',3,7),(35,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',3,8),(36,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',3,9),(37,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',3,10),(42,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',4,1),(43,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',4,1),(44,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',4,2),(45,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',4,3),(46,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',4,4),(47,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',4,5),(48,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',4,6),(49,'nice','good smart phone',4,'2016-11-05 22:45:35',4,7),(50,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',4,8),(51,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',4,9),(52,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',4,10),(57,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',5,1),(58,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',5,1),(59,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',5,2),(60,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',5,3),(61,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',5,4),(62,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',5,5),(63,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',5,6),(64,'nice','good smart phone',4,'2016-11-05 22:45:35',5,7),(65,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',5,8),(66,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',5,9),(67,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',5,10),(72,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',6,1),(73,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',6,1),(74,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',6,2),(75,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',6,3),(76,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',6,4),(77,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',6,5),(78,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',6,6),(79,'nice','good smart phone',4,'2016-11-05 22:45:35',6,7),(80,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',6,8),(81,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',6,9),(82,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',6,10),(87,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',7,1),(88,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',7,1),(89,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',7,2),(90,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',7,3),(91,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',7,4),(92,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',7,5),(93,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',7,6),(94,'nice','good smart phone',4,'2016-11-05 22:45:35',7,7),(95,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',7,8),(96,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',7,9),(97,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',7,10),(102,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',8,1),(103,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',8,1),(104,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',8,2),(105,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',8,3),(106,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',8,4),(107,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',8,5),(108,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',8,6),(109,'nice','good smart phone',4,'2016-11-05 22:45:35',8,7),(110,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',8,8),(111,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',8,9),(112,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',8,10),(117,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',9,1),(118,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',9,1),(119,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',9,2),(120,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',9,3),(121,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',9,4),(122,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',9,5),(123,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',9,6),(124,'nice','good smart phone',4,'2016-11-05 22:45:35',9,7),(125,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',9,8),(126,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',9,9),(127,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',9,10),(132,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',10,1),(133,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',10,1),(134,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',10,2),(135,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',10,3),(136,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',10,4),(137,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',10,5),(138,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',10,6),(139,'nice','good smart phone',4,'2016-11-05 22:45:35',10,7),(140,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',10,8),(141,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',10,9),(142,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',10,10),(147,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',11,1),(148,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',11,1),(149,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',11,2),(150,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',11,3),(151,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',11,4),(152,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',11,5),(153,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',11,6),(154,'nice','good smart phone',4,'2016-11-05 22:45:35',11,7),(155,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',11,8),(156,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',11,9),(157,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',11,10),(162,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',12,1),(163,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',12,1),(164,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',12,2),(165,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',12,3),(166,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',12,4),(167,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',12,5),(168,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',12,6),(169,'nice','good smart phone',4,'2016-11-05 22:45:35',12,7),(170,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',12,8),(171,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',12,9),(172,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',12,10),(177,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',13,1),(178,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',13,1),(179,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',13,2),(180,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',13,3),(181,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',13,4),(182,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',13,5),(183,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',13,6),(184,'nice','good smart phone',4,'2016-11-05 22:45:35',13,7),(185,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',13,8),(186,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',13,9),(187,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',13,10),(192,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:35',14,1),(193,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:35',14,1),(194,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:35',14,2),(195,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:35',14,3),(196,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:35',14,4),(197,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:35',14,5),(198,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:35',14,6),(199,'nice','good smart phone',4,'2016-11-05 22:45:35',14,7),(200,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:35',14,8),(201,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:35',14,9),(202,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:35',14,10),(207,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:36',15,1),(208,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:36',15,1),(209,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:36',15,2),(210,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:36',15,3),(211,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:36',15,4),(212,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:36',15,5),(213,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:36',15,6),(214,'nice','good smart phone',4,'2016-11-05 22:45:36',15,7),(215,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:36',15,8),(216,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:36',15,9),(217,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:36',15,10),(222,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:36',16,1),(223,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:36',16,1),(224,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:36',16,2),(225,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:36',16,3),(226,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:36',16,4),(227,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:36',16,5),(228,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:36',16,6),(229,'nice','good smart phone',4,'2016-11-05 22:45:36',16,7),(230,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:36',16,8),(231,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:36',16,9),(232,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:36',16,10),(237,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:36',17,1),(238,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:36',17,1),(239,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:36',17,2),(240,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:36',17,3),(241,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:36',17,4),(242,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:36',17,5),(243,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:36',17,6),(244,'nice','good smart phone',4,'2016-11-05 22:45:36',17,7),(245,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:36',17,8),(246,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:36',17,9),(247,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:36',17,10),(252,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:36',18,1),(253,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:36',18,1),(254,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:36',18,2),(255,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:36',18,3),(256,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:36',18,4),(257,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:36',18,5),(258,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:36',18,6),(259,'nice','good smart phone',4,'2016-11-05 22:45:36',18,7),(260,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:36',18,8),(261,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:36',18,9),(262,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:36',18,10),(267,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:36',19,1),(268,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:36',19,1),(269,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:36',19,2),(270,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:36',19,3),(271,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:36',19,4),(272,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:36',19,5),(273,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:36',19,6),(274,'nice','good smart phone',4,'2016-11-05 22:45:36',19,7),(275,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:36',19,8),(276,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:36',19,9),(277,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:36',19,10),(282,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:36',20,1),(283,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:36',20,1),(284,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:36',20,2),(285,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:36',20,3),(286,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:36',20,4),(287,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:36',20,5),(288,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:36',20,6),(289,'nice','good smart phone',4,'2016-11-05 22:45:36',20,7),(290,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:36',20,8),(291,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:36',20,9),(292,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:36',20,10),(297,'Amazing','This photo camera is amazing',5,'2016-11-05 22:45:37',21,1),(298,'very fast','I upgraded from Generation 2 to Pro 9.7.   Amazingly fast, clear display and easy to use.  Glad i did this',5,'2016-11-05 22:45:37',21,1),(299,'Successful tech upgrade','Since it was time to move on from my iPad 2, I looked at prices on eBay. I found a well-written ad offering a great price. The transaction went smoothly and the iPad Pro 9.7 is all it should be. Noticeably faster processing and higher quality camera are my two important features. I am still exploring my new device.',3,'2016-11-05 22:45:37',21,2),(300,'Love this device,very fast','Liteweight',5,'2016-11-05 22:45:37',21,3),(301,'iPad Pro','Very pleased with the product! Works great no issues and good quality',4,'2016-11-05 22:45:37',21,4),(302,'Beautiful!!!!','Really fast, GooD touch in you hands, GooD cuality value!!',5,'2016-11-05 22:45:37',21,5),(303,'Too much information','I was interested in your phone as it has many good features. Until I read the agreement.  NO ONE has the right to look into my private information. Especially my bank account.',1,'2016-11-05 22:45:37',21,6),(304,'nice','good smart phone',4,'2016-11-05 22:45:37',21,7),(305,'Good quality','A brand-new, unused, unopened, undamaged item in',5,'2016-11-05 22:45:37',21,8),(306,'Good deal','3G Data Capable, 4G Data Capable, 4K Video Recording, Bluetooth Enabled, Fingerprint Sensor, Global Ready, GPS, Internet Browser, Music Player, QWERTY Keyboar',4,'2016-11-05 22:45:37',21,9),(307,'fantastic','A brand-new, unused, unopened, undamaged item in its original packaging',3,'2016-11-05 22:45:37',21,10);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'comp344_ecommerce'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-06 12:49:52
