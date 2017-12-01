/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.137_3306
Source Server Version : 50626
Source Host           : 192.168.1.137:3306
Source Database       : znms

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-10-19 11:07:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `IMPORT_RESULT`
-- ----------------------------
DROP TABLE IF EXISTS `IMPORT_RESULT`;
CREATE TABLE `IMPORT_RESULT` (
  `ID` varchar(32) COLLATE utf8_bin NOT NULL,
  `REGISTER_TIME` datetime DEFAULT NULL,
  `FINISH_TIME` datetime DEFAULT NULL,
  `RESULT` bit(1) DEFAULT NULL,
  `ADMIN_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of IMPORT_RESULT
-- ----------------------------
