/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : heco_data

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 05/08/2021 11:28:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fee
-- ----------------------------
DROP TABLE IF EXISTS `fee`;
CREATE TABLE `fee`  (
  `transactionHash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blockNumber` bigint(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data10` double NULL DEFAULT NULL,
  `time` bigint(0) NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `decimals` double NULL DEFAULT NULL,
  `value` double NULL DEFAULT NULL,
  PRIMARY KEY (`transactionHash`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fee2
-- ----------------------------
DROP TABLE IF EXISTS `fee2`;
CREATE TABLE `fee2`  (
  `transactionHash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blockNumber` bigint(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data10` double NULL DEFAULT NULL,
  `time` bigint(0) NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `decimals` double NULL DEFAULT NULL,
  `value` double NULL DEFAULT NULL,
  PRIMARY KEY (`transactionHash`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for transaction
-- ----------------------------
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction`  (
  `hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blockNumber` bigint(0) NULL DEFAULT NULL,
  `from_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `to_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `input` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MethodID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tokenAmount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tokenAmount10` double NULL DEFAULT NULL,
  `tokenAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` bigint(0) NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `decimals` double NULL DEFAULT NULL,
  `value` double NULL DEFAULT NULL,
  PRIMARY KEY (`hash`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for transaction2
-- ----------------------------
DROP TABLE IF EXISTS `transaction2`;
CREATE TABLE `transaction2`  (
  `hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blockNumber` bigint(0) NULL DEFAULT NULL,
  `from_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `to_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `input` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MethodID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tokenAmount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tokenAmount10` double NULL DEFAULT NULL,
  `tokenAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` bigint(0) NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `decimals` double NULL DEFAULT NULL,
  `value` double NULL DEFAULT NULL,
  PRIMARY KEY (`hash`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
