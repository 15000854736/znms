/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3308
Source Database       : znms

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2015-11-30 13:23:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `SM_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SM_ROLE`;
CREATE TABLE `SM_ROLE` (
  `ROLE_UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_ADMIN` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_ADMIN` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态 0：未激活、1：激活、2：停用',
  PRIMARY KEY (`ROLE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `SM_ROLE`
-- ----------------------------
BEGIN;
INSERT INTO `SM_ROLE` VALUES ('1', '超级管理员', '2015-12-01 11:19:28', 'admin', '2015-12-01 11:19:29', 'admin', '1'), ('2', 'ssss', '2015-12-02 12:40:54', 'admin', '2015-12-02 12:41:03', 'admin', '1');
COMMIT;
