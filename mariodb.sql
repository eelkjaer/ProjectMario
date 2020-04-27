/*
 Navicat Premium Data Transfer

 Source Server         : DigitalOcean nossh
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 104.248.135.65:3306
 Source Schema         : mariodb

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 27/04/2020 14:17:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `phone` int(20) NOT NULL,
  `email` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  PRIMARY KEY (`id`,`phone`) USING BTREE,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of customers
-- ----------------------------
BEGIN;
INSERT INTO `customers` VALUES (1, 'Emil Elkjær', 60146057, 'cph-en93@cphbusiness.dk');
INSERT INTO `customers` VALUES (2, 'Andreas Bergmann', 12121212, 'cph-ab435@cphbusiness.dk');
INSERT INTO `customers` VALUES (3, 'Mohammed Hadra', 30585832, 'cph-mh879@cphbusiness.dk');
INSERT INTO `customers` VALUES (4, 'Jacob Lange Nielsen', 51941066, 'cph-jn352@cphbusiness.dk');
INSERT INTO `customers` VALUES (5, 'john', 121212, 'john@reimer.dk');
COMMIT;

-- ----------------------------
-- Table structure for menuFilling
-- ----------------------------
DROP TABLE IF EXISTS `menuFilling`;
CREATE TABLE `menuFilling` (
  `pizzaId` int(11) NOT NULL,
  `topping` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  KEY `fillingPizzaId` (`pizzaId`),
  CONSTRAINT `fillingPizzaId` FOREIGN KEY (`pizzaId`) REFERENCES `menucard` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of menuFilling
-- ----------------------------
BEGIN;
INSERT INTO `menuFilling` VALUES (1, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (1, 'ost');
INSERT INTO `menuFilling` VALUES (1, 'skinke');
INSERT INTO `menuFilling` VALUES (1, 'oregano');
INSERT INTO `menuFilling` VALUES (2, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (2, 'ost');
INSERT INTO `menuFilling` VALUES (2, 'oksefars');
INSERT INTO `menuFilling` VALUES (2, 'oregano');
INSERT INTO `menuFilling` VALUES (3, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (3, 'ost');
INSERT INTO `menuFilling` VALUES (3, 'pepperoni');
INSERT INTO `menuFilling` VALUES (3, 'oregano');
INSERT INTO `menuFilling` VALUES (4, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (4, 'ost');
INSERT INTO `menuFilling` VALUES (4, 'kødsauce');
INSERT INTO `menuFilling` VALUES (4, 'spaghetti');
INSERT INTO `menuFilling` VALUES (4, 'cocktailpølser');
INSERT INTO `menuFilling` VALUES (4, 'oregano');
INSERT INTO `menuFilling` VALUES (5, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (5, 'ost');
INSERT INTO `menuFilling` VALUES (5, 'skinke');
INSERT INTO `menuFilling` VALUES (5, 'pepperoni');
INSERT INTO `menuFilling` VALUES (5, 'cocktailpølser');
INSERT INTO `menuFilling` VALUES (5, 'oregano');
INSERT INTO `menuFilling` VALUES (6, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (6, 'ost');
INSERT INTO `menuFilling` VALUES (6, 'bacon');
INSERT INTO `menuFilling` VALUES (6, 'oregano');
INSERT INTO `menuFilling` VALUES (7, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (7, 'ost');
INSERT INTO `menuFilling` VALUES (7, 'pepperoni');
INSERT INTO `menuFilling` VALUES (7, 'rød peber');
INSERT INTO `menuFilling` VALUES (7, 'løg');
INSERT INTO `menuFilling` VALUES (7, 'oliven');
INSERT INTO `menuFilling` VALUES (7, 'oregano');
INSERT INTO `menuFilling` VALUES (8, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (8, 'ost');
INSERT INTO `menuFilling` VALUES (8, 'skinke');
INSERT INTO `menuFilling` VALUES (8, 'ananas');
INSERT INTO `menuFilling` VALUES (8, 'champignon');
INSERT INTO `menuFilling` VALUES (8, 'løg');
INSERT INTO `menuFilling` VALUES (8, 'oregano');
INSERT INTO `menuFilling` VALUES (9, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (9, 'ost');
INSERT INTO `menuFilling` VALUES (9, 'skinke');
INSERT INTO `menuFilling` VALUES (9, 'bacon');
INSERT INTO `menuFilling` VALUES (9, 'kebab');
INSERT INTO `menuFilling` VALUES (9, 'chili');
INSERT INTO `menuFilling` VALUES (9, 'oregano');
INSERT INTO `menuFilling` VALUES (10, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (10, 'ost');
INSERT INTO `menuFilling` VALUES (10, 'skinke');
INSERT INTO `menuFilling` VALUES (10, 'champignon');
INSERT INTO `menuFilling` VALUES (10, 'oregano');
INSERT INTO `menuFilling` VALUES (11, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (11, 'ost');
INSERT INTO `menuFilling` VALUES (11, 'skinke');
INSERT INTO `menuFilling` VALUES (11, 'ananas');
INSERT INTO `menuFilling` VALUES (11, 'oregano');
INSERT INTO `menuFilling` VALUES (12, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (12, 'ost');
INSERT INTO `menuFilling` VALUES (12, 'skinke');
INSERT INTO `menuFilling` VALUES (12, 'rejer');
INSERT INTO `menuFilling` VALUES (12, 'oregano');
INSERT INTO `menuFilling` VALUES (13, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (13, 'ost');
INSERT INTO `menuFilling` VALUES (13, 'skinke');
INSERT INTO `menuFilling` VALUES (13, 'bacon');
INSERT INTO `menuFilling` VALUES (13, 'oregano');
INSERT INTO `menuFilling` VALUES (14, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (14, 'ost');
INSERT INTO `menuFilling` VALUES (14, 'pepperoni');
INSERT INTO `menuFilling` VALUES (14, 'bacon');
INSERT INTO `menuFilling` VALUES (14, 'løg');
INSERT INTO `menuFilling` VALUES (14, 'oregano');
INSERT INTO `menuFilling` VALUES (15, 'tomatsauce');
INSERT INTO `menuFilling` VALUES (15, 'ost');
INSERT INTO `menuFilling` VALUES (15, 'skinke');
INSERT INTO `menuFilling` VALUES (15, 'bacon');
COMMIT;

-- ----------------------------
-- Table structure for menucard
-- ----------------------------
DROP TABLE IF EXISTS `menucard`;
CREATE TABLE `menucard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `navn` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `pris` double(255,0) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of menucard
-- ----------------------------
BEGIN;
INSERT INTO `menucard` VALUES (1, 'Vesuvio', 57);
INSERT INTO `menucard` VALUES (2, 'Amerikaner', 53);
INSERT INTO `menucard` VALUES (3, 'Cacciatore', 57);
INSERT INTO `menucard` VALUES (4, 'Cabona', 63);
INSERT INTO `menucard` VALUES (5, 'Dennis', 65);
INSERT INTO `menucard` VALUES (6, 'Bertil', 57);
INSERT INTO `menucard` VALUES (7, 'Silvia', 61);
INSERT INTO `menucard` VALUES (8, 'Victoria', 61);
INSERT INTO `menucard` VALUES (9, 'Toronfo', 61);
INSERT INTO `menucard` VALUES (10, 'Capricciosa', 61);
INSERT INTO `menucard` VALUES (11, 'Hawaii', 61);
INSERT INTO `menucard` VALUES (12, 'Le Blissola', 61);
INSERT INTO `menucard` VALUES (13, 'Venezia', 61);
INSERT INTO `menucard` VALUES (14, 'Mafia', 61);
INSERT INTO `menucard` VALUES (15, 'Dansker', 54);
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pickup` tinyint(1) NOT NULL,
  `customerId` int(11) DEFAULT NULL,
  `readyTime` time NOT NULL,
  `status` enum('working','done') COLLATE utf8_danish_ci NOT NULL,
  `comment` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL,
  `totalPrice` decimal(10,2) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customerId` (`customerId`),
  CONSTRAINT `customerId` FOREIGN KEY (`customerId`) REFERENCES `customers` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=202014 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
INSERT INTO `orders` VALUES (202001, 1, 1, '21:50:01', 'done', 'ingen', 116.00, 'Mario');
INSERT INTO `orders` VALUES (202002, 1, 1, '23:22:22', 'done', 'ingen', 238.00, 'Alfonso');
INSERT INTO `orders` VALUES (202003, 1, 1, '08:21:50', 'done', 'ingen', 57.00, 'Alfonso');
INSERT INTO `orders` VALUES (202006, 1, 1, '09:35:58', 'done', 'ingen', 57.00, 'Alfonso');
INSERT INTO `orders` VALUES (202007, 0, 4, '09:26:36', 'done', 'ingen', 110.00, 'Alfonso');
INSERT INTO `orders` VALUES (202009, 1, 1, '15:11:30', 'done', 'ingen', 63.00, 'Mario');
INSERT INTO `orders` VALUES (202010, 0, 5, '15:56:50', 'done', 'god pizza', 122.00, 'Mario');
INSERT INTO `orders` VALUES (202011, 1, 1, '15:22:16', 'done', 'Ingen bemærkninger', 185.00, 'Alfonso');
INSERT INTO `orders` VALUES (202012, 0, 2, '10:48:29', 'done', 'fuck kagemænd', 189.00, 'Emil');
INSERT INTO `orders` VALUES (202013, 1, 4, '21:30:15', 'working', 'Ingen bemærkninger', 110.00, 'Mario');
COMMIT;

-- ----------------------------
-- Table structure for ordersPizza
-- ----------------------------
DROP TABLE IF EXISTS `ordersPizza`;
CREATE TABLE `ordersPizza` (
  `orderId` int(11) NOT NULL,
  `pizzaId` int(11) NOT NULL,
  KEY `pizza_orderId` (`orderId`),
  KEY `pizza_pizzaId` (`pizzaId`),
  CONSTRAINT `pizza_orderId` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pizza_pizzaId` FOREIGN KEY (`pizzaId`) REFERENCES `menucard` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of ordersPizza
-- ----------------------------
BEGIN;
INSERT INTO `ordersPizza` VALUES (202001, 2);
INSERT INTO `ordersPizza` VALUES (202001, 4);
INSERT INTO `ordersPizza` VALUES (202002, 2);
INSERT INTO `ordersPizza` VALUES (202002, 3);
INSERT INTO `ordersPizza` VALUES (202002, 4);
INSERT INTO `ordersPizza` VALUES (202002, 5);
INSERT INTO `ordersPizza` VALUES (202003, 3);
INSERT INTO `ordersPizza` VALUES (202006, 3);
INSERT INTO `ordersPizza` VALUES (202007, 2);
INSERT INTO `ordersPizza` VALUES (202007, 3);
INSERT INTO `ordersPizza` VALUES (202009, 4);
INSERT INTO `ordersPizza` VALUES (202010, 5);
INSERT INTO `ordersPizza` VALUES (202010, 3);
INSERT INTO `ordersPizza` VALUES (202011, 4);
INSERT INTO `ordersPizza` VALUES (202011, 5);
INSERT INTO `ordersPizza` VALUES (202011, 6);
INSERT INTO `ordersPizza` VALUES (202012, 4);
INSERT INTO `ordersPizza` VALUES (202012, 5);
INSERT INTO `ordersPizza` VALUES (202012, 10);
INSERT INTO `ordersPizza` VALUES (202013, 2);
INSERT INTO `ordersPizza` VALUES (202013, 3);
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `admin` tinyint(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES (1, 'mario', 'Mario', 'mario', 1);
INSERT INTO `users` VALUES (2, 'alfonso', 'Alfonso', 'alfonso', 0);
INSERT INTO `users` VALUES (3, 'emil', 'Emil', 'emil', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
