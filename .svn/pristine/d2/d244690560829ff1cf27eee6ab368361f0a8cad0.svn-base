/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3308
Source Database       : znms

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2015-11-30 13:22:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `SM_PERMISSION`
-- ----------------------------
DROP TABLE IF EXISTS `SM_PERMISSION`;
CREATE TABLE `SM_PERMISSION` (
  `PERMISSION_UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  `PERMISSION_CODE` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '权限代码',
  `PERMISSION_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `VIEW_ORDER` int(11) NOT NULL COMMENT '顺序',
  `FATHER_NODE_UUID` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点ID',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态 0：未激活、1：激活、2：停用',
  PRIMARY KEY (`PERMISSION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `SM_ROLE`
-- ----------------------------
BEGIN;
INSERT INTO `SM_PERMISSION` VALUES ('0', 'root', '全选', '1', null, '1');
INSERT INTO `SM_PERMISSION` VALUES ('100', 'p_system', '系统管理', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('110', 'p_admin_manage', '管理员管理', '1', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('111', 'p_admin_manage_add', '管理员添加', '1', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('112', 'p_admin_manage_delete', '管理员删除', '2', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('113', 'p_admin_manage_edit', '管理员编辑', '3', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('114', 'p_admin_manage_detail', '管理员详细', '4', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('115', 'p_admin_manage_view', '管理员查看', '5', '110', '1');

INSERT INTO `SM_PERMISSION` VALUES ('130', 'p_role_permission', '权限管理', '3', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('131', 'p_role_permission_add', '权限添加', '1', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('132', 'p_role_permission_edit', '权限编辑', '2', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('133', 'p_role_permission_delete', '权限删除', '3', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('134', 'p_role_permission_detail', '权限详细', '4', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('135', 'p_role_permission_view', '权限查看', '5', '130', '1');
COMMIT;