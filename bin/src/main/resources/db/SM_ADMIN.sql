/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3308
Source Database       : znms

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2015-11-30 13:21:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `SM_ADMIN`
-- ----------------------------
DROP TABLE IF EXISTS `SM_ADMIN`;
CREATE TABLE `SM_ADMIN` (
  `ADMIN_UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '管理员ID',
  `ADMIN_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '管理员账号',
  `ADMIN_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '管理员姓名',
  `CONTACT_NUMBER` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `ROLE_UUID` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_ADMIN` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_ADMIN` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `STATUS` int(11) DEFAULT NULL COMMENT ' 状态 0：未激活、1：激活、2：停用',
  `ADMIN_PWD` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ADMIN_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `SM_ADMIN`
-- ----------------------------
BEGIN;
INSERT INTO `SM_ADMIN` VALUES ('9129da4297ea11e59bce34293386068a', '1', '1', '111111', '1', '2015-12-01 13:15:41', null, '2015-12-01 13:15:41', null, '0', null), ('f22702d989f911e5a1d800ff15b4b5bd', 'admin', '超级管理员测试', '33', '1', '2015-11-30 15:17:39', 'admin', '2015-12-02 11:58:28', 'admin', '1', '96e79218965eb72c92a549dd5a330112');
COMMIT;