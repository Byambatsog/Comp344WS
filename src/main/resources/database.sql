CREATE SCHEMA IF NOT EXISTS `comp344_ecommerce` DEFAULT CHARACTER SET utf8 ;
USE `comp344_ecommerce` ;

-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`login`
-- -----------------------------------------------------
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
CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(60) NULL,
  `created_at` DATETIME NULL,
  `login_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_customers_login1_idx` (`login_id` ASC),
  CONSTRAINT `fk_customers_login1`
  FOREIGN KEY (`login_id`)
  REFERENCES `comp344_ecommerce`.`login` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`customer_addresses`
-- -----------------------------------------------------
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
  PRIMARY KEY (`id`),
  INDEX `fk_customer_addresses_customers_idx` (`customers_id` ASC),
  CONSTRAINT `fk_customer_addresses_customers`
  FOREIGN KEY (`customers_id`)
  REFERENCES `comp344_ecommerce`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`partners`
-- -----------------------------------------------------
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
  PRIMARY KEY (`id`),
  INDEX `fk_partners_login1_idx` (`login_id` ASC),
  CONSTRAINT `fk_partners_login1`
  FOREIGN KEY (`login_id`)
  REFERENCES `comp344_ecommerce`.`login` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`credit_cards`
-- -----------------------------------------------------
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
  PRIMARY KEY (`id`),
  INDEX `fk_credit_cards_customers1_idx` (`customers_id` ASC),
  CONSTRAINT `fk_credit_cards_customers1`
  FOREIGN KEY (`customers_id`)
  REFERENCES `comp344_ecommerce`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`product_categories`
-- -----------------------------------------------------
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
  PRIMARY KEY (`id`),
  INDEX `fk_products_product_categories1_idx` (`product_categories_id` ASC),
  INDEX `fk_products_partners1_idx` (`partners_id` ASC),
  CONSTRAINT `fk_products_product_categories1`
  FOREIGN KEY (`product_categories_id`)
  REFERENCES `comp344_ecommerce`.`product_categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_partners1`
  FOREIGN KEY (`partners_id`)
  REFERENCES `comp344_ecommerce`.`partners` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME NULL,
  `total_price` DECIMAL(10,2) NULL,
  `paid_at` DATETIME NULL,
  `customers_id` INT NOT NULL,
  `shipping_addresses_id` INT NOT NULL,
  `billing_addresses_id` INT NOT NULL,
  `partners_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_customers1_idx` (`customers_id` ASC),
  INDEX `fk_orders_customer_addresses1_idx` (`shipping_addresses_id` ASC),
  INDEX `fk_orders_customer_addresses2_idx` (`billing_addresses_id` ASC),
  INDEX `fk_orders_partners1_idx` (`partners_id` ASC),
  CONSTRAINT `fk_orders_customers1`
  FOREIGN KEY (`customers_id`)
  REFERENCES `comp344_ecommerce`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_customer_addresses1`
  FOREIGN KEY (`shipping_addresses_id`)
  REFERENCES `comp344_ecommerce`.`customer_addresses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_customer_addresses2`
  FOREIGN KEY (`billing_addresses_id`)
  REFERENCES `comp344_ecommerce`.`customer_addresses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_partners1`
  FOREIGN KEY (`partners_id`)
  REFERENCES `comp344_ecommerce`.`partners` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(20) NULL,
  `updated_at` DATETIME NULL,
  `orders_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_status_orders1_idx` (`orders_id` ASC),
  CONSTRAINT `fk_order_status_orders1`
  FOREIGN KEY (`orders_id`)
  REFERENCES `comp344_ecommerce`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_products` (
  `orders_id` INT NOT NULL,
  `products_id` INT NOT NULL,
  `quantity` INT(11) NULL,
  `unit_price` DECIMAL(10,2) NULL,
  `status` VARCHAR(20) NULL,
  INDEX `fk_order_products_orders1_idx` (`orders_id` ASC),
  INDEX `fk_order_products_products1_idx` (`products_id` ASC),
  PRIMARY KEY (`orders_id`, `products_id`),
  CONSTRAINT `fk_order_products_orders1`
  FOREIGN KEY (`orders_id`)
  REFERENCES `comp344_ecommerce`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_products_products1`
  FOREIGN KEY (`products_id`)
  REFERENCES `comp344_ecommerce`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`order_payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`order_payments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `orders_id` INT NOT NULL,
  `credit_cards_id` INT NOT NULL,
  `amount` DECIMAL(10,2) NULL,
  `paid_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_payments_orders1_idx` (`orders_id` ASC),
  INDEX `fk_order_payments_credit_cards1_idx` (`credit_cards_id` ASC),
  CONSTRAINT `fk_order_payments_orders1`
  FOREIGN KEY (`orders_id`)
  REFERENCES `comp344_ecommerce`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_payments_credit_cards1`
  FOREIGN KEY (`credit_cards_id`)
  REFERENCES `comp344_ecommerce`.`credit_cards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comp344_ecommerce`.`reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comp344_ecommerce`.`reviews` (
  `id` INT NOT NULL,
  `title` VARCHAR(45) NULL,
  `comment` TEXT NULL,
  `rating` INT(11) NULL,
  `created_at` DATETIME NULL,
  `products_id` INT NOT NULL,
  `customers_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_reviews_products1_idx` (`products_id` ASC),
  INDEX `fk_reviews_customers1_idx` (`customers_id` ASC),
  CONSTRAINT `fk_reviews_products1`
  FOREIGN KEY (`products_id`)
  REFERENCES `comp344_ecommerce`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reviews_customers1`
  FOREIGN KEY (`customers_id`)
  REFERENCES `comp344_ecommerce`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;