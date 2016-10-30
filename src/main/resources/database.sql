-- MySQL Script generated by MySQL Workbench
-- Sat Oct 29 19:38:37 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `comp344_ecommerce` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `comp344_ecommerce` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema comp344_ecommerce
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `comp344_ecommerce` ;

-- -----------------------------------------------------
-- Schema comp344_ecommerce
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `comp344_ecommerce` DEFAULT CHARACTER SET utf8 ;
USE `comp344_ecommerce` ;

-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`login` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `admin` TINYINT(1) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`customers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`customers` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(60) NULL,
  `created_at` DATETIME NULL,
  `login_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`customer_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`customer_addresses` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`customer_addresses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(60) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(2) NULL,
  `zip_code` VARCHAR(10) NULL,
  `country` VARCHAR(45) NULL,
  `phone` VARCHAR(10) NULL,
  `created_at` DATETIME NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`partners`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`partners` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`partners` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(60) NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `type` VARCHAR(20) NULL,
  `phone` VARCHAR(10) NULL,
  `email` VARCHAR(60) NULL,
  `street` VARCHAR(60) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(2) NULL,
  `zip_code` VARCHAR(10) NULL,
  `country` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `login_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`credit_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`credit_cards` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`credit_cards` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_number` VARCHAR(16) NULL,
  `card_type` VARCHAR(45) NULL,
  `expire_month` INT(11) NULL,
  `expire_year` INT(11) NULL,
  `name_on_card` VARCHAR(45) NULL,
  `security_code` VARCHAR(3) NULL,
  `created_at` VARCHAR(45) NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`product_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`product_categories` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`product_categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `description` TEXT NULL,
  `status` TINYINT(1) NULL,
  `ranking` INT(11) NULL,
  `created_at` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`products` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `picture` VARCHAR(255) NULL,
  `brand_name` VARCHAR(60) NULL,
  `description` TEXT NULL,
  `status` TINYINT(1) NULL,
  `quantity_in_stock` INT(11) NULL,
  `unit_price` DECIMAL(10,2) NULL,
  `weight` DECIMAL(10,2) NULL,
  `created_at` DATETIME NULL,
  `product_categories_id` INT NOT NULL,
  `partners_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`orders` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME NULL,
  `total_price` DECIMAL(10,2) NULL,
  `paid_at` DATETIME NULL,
  `customers_id` INT NOT NULL,
  `shipping_addresses_id` INT NOT NULL,
  `billing_addresses_id` INT NOT NULL,
  `partners_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`order_status` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(20) NULL,
  `created_at` DATETIME NULL,
  `orders_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`order_products` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_products` (
  `orders_id` INT NOT NULL,
  `products_id` INT NOT NULL,
  `quantity` INT(11) NULL,
  `unit_price` DECIMAL(10,2) NULL,
  `status` VARCHAR(20) NULL,
  PRIMARY KEY (`orders_id`, `products_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_payments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`order_payments` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_payments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `orders_id` INT NOT NULL,
  `credit_cards_id` INT NOT NULL,
  `amount` DECIMAL(10,2) NULL,
  `paid_at` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`reviews` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`reviews` (
  `id` INT NOT NULL,
  `title` VARCHAR(45) NULL,
  `comment` TEXT NULL,
  `rating` INT(11) NULL,
  `created_at` DATETIME NULL,
  `products_id` INT NOT NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`customer_cart_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`customer_cart_items` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`customer_cart_items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT(11) NULL,
  `created_at` DATETIME NULL,
  `customers_id` INT NOT NULL,
  `products_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

USE `comp344_ecommerce` ;

-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`login` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL,
  `username` VARCHAR(60) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `admin` TINYINT(1) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`customers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`customers` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(60) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `login_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`customer_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`customer_addresses` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`customer_addresses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(60) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `state` VARCHAR(2) NULL DEFAULT NULL,
  `zip_code` VARCHAR(10) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `phone` VARCHAR(10) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`partners`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`partners` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`partners` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(60) NULL DEFAULT NULL,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `type` VARCHAR(20) NULL DEFAULT NULL,
  `phone` VARCHAR(10) NULL DEFAULT NULL,
  `email` VARCHAR(60) NULL DEFAULT NULL,
  `street` VARCHAR(60) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `state` VARCHAR(2) NULL DEFAULT NULL,
  `zip_code` VARCHAR(10) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `login_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`credit_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`credit_cards` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`credit_cards` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_number` VARCHAR(16) NULL DEFAULT NULL,
  `card_type` VARCHAR(45) NULL DEFAULT NULL,
  `expire_month` INT(11) NULL DEFAULT NULL,
  `expire_year` INT(11) NULL DEFAULT NULL,
  `name_on_card` VARCHAR(45) NULL DEFAULT NULL,
  `security_code` VARCHAR(3) NULL DEFAULT NULL,
  `created_at` VARCHAR(45) NULL DEFAULT NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`products` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `picture` VARCHAR(255) NULL DEFAULT NULL,
  `brand_name` VARCHAR(60) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `status` TINYINT(1) NULL DEFAULT NULL,
  `quantity_in_stock` INT(11) NULL DEFAULT NULL,
  `unit_price` DECIMAL(10,2) NULL DEFAULT NULL,
  `weight` DECIMAL(10,2) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `product_categories_id` INT NOT NULL,
  `partners_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`orders` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME NULL DEFAULT NULL,
  `total_price` DECIMAL(10,2) NULL DEFAULT NULL,
  `paid_at` DATETIME NULL DEFAULT NULL,
  `customers_id` INT NOT NULL,
  `shipping_addresses_id` INT NOT NULL,
  `billing_addresses_id` INT NOT NULL,
  `partners_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`order_status` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(20) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `orders_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_payments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`order_payments` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_payments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `orders_id` INT NOT NULL,
  `credit_cards_id` INT NOT NULL,
  `amount` DECIMAL(10,2) NULL DEFAULT NULL,
  `paid_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comp344_ecommerce`.`reviews` ;

CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`reviews` (
  `id` INT NOT NULL,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `comment` TEXT NULL DEFAULT NULL,
  `rating` INT(11) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `products_id` INT NOT NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
