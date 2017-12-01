/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.137_3306
Source Server Version : 50626
Source Host           : 192.168.1.137:3306
Source Database       : znms

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-10-11 10:29:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `GRAPH_TEMPLATE`
-- ----------------------------
DROP TABLE IF EXISTS `GRAPH_TEMPLATE`;
CREATE TABLE `GRAPH_TEMPLATE` (
  `GRAPH_TEMPLATE_ID` varchar(10) COLLATE utf8_bin NOT NULL,
  `GRAPH_TEMPLATE_NAME` varchar(100) COLLATE utf8_bin NOT NULL,
  `OID` varchar(255) COLLATE utf8_bin NOT NULL,
  `GRAPH_TEMPLATE_SIMPLE_NAME` varchar(20) COLLATE utf8_bin NOT NULL,
  `DS_NAME` varchar(32) COLLATE utf8_bin NOT NULL,
  `DS_SHOW_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DS_TYPE` int(4) NOT NULL,
  `CONSOL_FUN` int(4) NOT NULL,
  PRIMARY KEY (`GRAPH_TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of GRAPH_TEMPLATE
-- ----------------------------
INSERT INTO `GRAPH_TEMPLATE` VALUES ('1', 'Ruijie_MEM_Usage', '.1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.1', 'RMU', 'mem_usage', '内存使用率', '4', '1');
INSERT INTO `GRAPH_TEMPLATE` VALUES ('2', 'Ruijie_AC_User', '.1.3.6.1.2.1.145.1.1.3.0', 'RAU', 'ac_user', '用户数', '4', '1');
INSERT INTO `GRAPH_TEMPLATE` VALUES ('3', 'Ruijie_AP_Oline', '.1.3.6.1.2.1.145.1.1.1.0', 'RAO', 'ap_online', '在线用户数', '4', '1');
INSERT INTO `GRAPH_TEMPLATE` VALUES ('4', 'Ruijie_CPU_Usage', '.iso.org.dod.internet.private.enterprises.4881.1.1.10.2.36.1.1.3.0', 'RCU', 'cpu_usage', 'cpu使用率', '4', '1');
