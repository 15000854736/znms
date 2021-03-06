

SET FOREIGN_KEY_CHECKS=0;
use znms;
-- ----------------------------
-- Table structure for `SM_ADMIN` 用户表
-- ----------------------------
DROP TABLE IF EXISTS `SM_ADMIN`;
CREATE TABLE `SM_ADMIN` (
  `ADMIN_UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `ADMIN_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户账号',
  `ADMIN_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名',
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
-- Records of SM_ADMIN
-- ----------------------------
INSERT INTO `SM_ADMIN` VALUES ('f22702d989f911e5a1d800ff15b4b5bd', 'admin', '超级管理员测试', '33', '1', '2015-11-30 15:17:39', 'admin', '2015-12-02 11:58:28', 'admin', '1', '96e79218965eb72c92a549dd5a330112');

-- ----------------------------
-- Table structure for `SM_PERMISSION` 权限表
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
-- Records of SM_PERMISSION
-- ----------------------------
INSERT INTO `SM_PERMISSION` VALUES ('0', 'root', '全选', '1', null, '1');
INSERT INTO `SM_PERMISSION` VALUES ('100', 'p_system', '系统设置', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('110', 'p_admin_manage', '用户管理', '1', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('111', 'p_admin_manage_add', '添加用户', '1', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('112', 'p_admin_manage_delete', '删除用户', '2', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('113', 'p_admin_manage_edit', '编辑用户', '3', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('114', 'p_admin_manage_detail', '用户详情', '4', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('115', 'p_admin_manage_view', '查看用户', '5', '110', '1');
INSERT INTO `SM_PERMISSION` VALUES ('120', 'p_role_permission', '角色管理', '3', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('121', 'p_role_permission_add', '添加角色', '1', '120', '1');
INSERT INTO `SM_PERMISSION` VALUES ('122', 'p_role_permission_edit', '编辑角色', '2', '120', '1');
INSERT INTO `SM_PERMISSION` VALUES ('123', 'p_role_permission_delete', '删除角色', '3', '120', '1');
INSERT INTO `SM_PERMISSION` VALUES ('124', 'p_role_permission_detail', '角色详情', '4', '120', '1');
INSERT INTO `SM_PERMISSION` VALUES ('125', 'p_role_permission_view', '查看角色', '5', '120', '1');
INSERT INTO `SM_PERMISSION` VALUES ('130', 'p_host', '主机管理', '4', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('131', 'p_host_add', '添加主机', '1', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('132', 'p_host_edit', '编辑主机', '2', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('133', 'p_host_delete', '删除主机', '3', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('134', 'p_host_detail', '主机详情', '4', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('135', 'p_host_view', '查看主机', '5', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('136', 'p_host_enable', '启用主机', '6', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('137', 'p_host_disable', '禁用主机', '7', '130', '1');
INSERT INTO `SM_PERMISSION` VALUES ('140', 'p_graph_tree', '图形树', '5', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('141', 'p_graph_tree_add', '添加图形树', '1', '140', '1');
INSERT INTO `SM_PERMISSION` VALUES ('142', 'p_graph_tree_edit', '编辑图形树', '2', '140', '1');
INSERT INTO `SM_PERMISSION` VALUES ('143', 'p_graph_tree_delete', '删除图形树', '3', '140', '1');
INSERT INTO `SM_PERMISSION` VALUES ('144', 'p_graph_tree_detail', '图形树详情', '4', '140', '1');
INSERT INTO `SM_PERMISSION` VALUES ('145', 'p_graph_tree_view', '查看图形树', '5', '140', '1');
INSERT INTO `SM_PERMISSION` VALUES ('150', 'p_system_option', '系统选项', '6', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('151', 'p_system_option_edit', '编辑系统选项', '1', '150', '1');
INSERT INTO `SM_PERMISSION` VALUES ('152', 'p_system_option_view', '查看系统选项', '2', '150', '1');
INSERT INTO `SM_PERMISSION` VALUES ('160', 'p_system_log_delete_rule', '日志规则', '7', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('161', 'p_system_log_delete_rule_view', '查看日志规则', '1', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('162', 'p_system_log_delete_rule_add', '添加日志规则', '2', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('163', 'p_system_log_delete_rule_edit', '编辑日志规则', '3', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('164', 'p_system_log_delete_rule_delete', '删除日志规则', '4', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('165', 'p_system_log_delete_rule_detail', '日志规则详情', '5', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('166', 'p_system_log_delete_rule_enable', '启用日志规则', '6', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('167', 'p_system_log_delete_rule_disable', '禁用日志规则', '7', '160', '1');
INSERT INTO `SM_PERMISSION` VALUES ('170', 'p_backup_configuration', '配置备份', '8', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1710', 'p_backup_configuration_device_view', '查看配置备份设备', '1', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1711', 'p_backup_configuration_device_add', '添加配置备份设备', '2', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1712', 'p_backup_configuration_device_edit', '修改配置备份设备', '3', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1713', 'p_backup_configuration_device_delete', '删除配置备份设备', '4', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1714', 'p_backup_configuration_device_detail', '查看配置备份设备', '5', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1720', 'p_backup_configuration_ap_view', '查看配置备份账户密码', '6', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1721', 'p_backup_configuration_ap_add', '添加配置备份账户密码', '7', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1722', 'p_backup_configuration_ap_edit', '修改配置备份账户密码', '8', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1723', 'p_backup_configuration_ap_delete', '删除配置备份账户密码', '9', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('1724', 'p_backup_configuration_ap_detail', '查看配置备份账户密码', '10', '170', '1');
INSERT INTO `SM_PERMISSION` VALUES ('180', 'p_graph', '图形管理', '9', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('181', 'p_graph_add', '添加图形', '1', '180', '1');
INSERT INTO `SM_PERMISSION` VALUES ('182', 'p_graph_edit', '编辑图形', '2', '180', '1');
INSERT INTO `SM_PERMISSION` VALUES ('183', 'p_graph_delete', '删除图形', '3', '180', '1');
INSERT INTO `SM_PERMISSION` VALUES ('184', 'p_graph_detail', '图形详情', '4', '180', '1');
INSERT INTO `SM_PERMISSION` VALUES ('185', 'p_graph_view', '查看图形', '5', '180', '1');
INSERT INTO `SM_PERMISSION` VALUES ('190', 'p_threshold_value', '阈值管理', '10', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('191', 'p_threshold_value_view', '查看阈值', '1', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('192', 'p_threshold_value_add', '添加阈值', '2', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('193', 'p_threshold_value_edit', '编辑阈值', '3', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('194', 'p_threshold_value_delete', '删除阈值', '4', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('195', 'p_threshold_value_detail', '阈值详情', '5', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('196', 'p_threshold_value_enable', '启用阈值', '6', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('197', 'p_threshold_value_disable', '禁用阈值', '7', '190', '1');
INSERT INTO `SM_PERMISSION` VALUES ('200', 'p_ap_infomation', '无线管理', '1', '100', '1');
INSERT INTO `SM_PERMISSION` VALUES ('210', 'p_ap_infomation_view', '查看无线信息', '1', '200', '1');
INSERT INTO `SM_PERMISSION` VALUES ('300', 'p_topology_view', '网络拓扑', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('400', 'p_graph_monitor_view', '设备监控', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('500', 'p_host_listbox_view', '主机列表', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('600', 'p_threshold_value_trigger_log', '阈值日志', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('610', 'p_threshold_value_trigger_log_view', '查看阈值触发日志', '1', '600', '1');
INSERT INTO `SM_PERMISSION` VALUES ('611', 'p_threshold_value_trigger_log_delete', '删除阈值触发日志', '2', '600', '1');
INSERT INTO `SM_PERMISSION` VALUES ('612', 'p_threshold_value_trigger_survey_view', '查看阈值触发概况', '3', '600', '1');
INSERT INTO `SM_PERMISSION` VALUES ('613', 'p_threshold_value_trigger_survey_delete', '删除阈值触发概况', '4', '600', '1');
INSERT INTO `SM_PERMISSION` VALUES ('700', 'p_system_log', '系统日志', '1', '0', '1');
INSERT INTO `SM_PERMISSION` VALUES ('710', 'p_system_log_view', '查看系统日志', '1', '700', '1');
INSERT INTO `SM_PERMISSION` VALUES ('711', 'p_system_log_delete', '删除系统日志', '2', '700', '1');
INSERT INTO `SM_PERMISSION` VALUES ('712', 'p_system_log_export', '导出系统日志', '3', '700', '1');
INSERT INTO `SM_PERMISSION` VALUES ('720', 'p_system_log_statistics_view', '查看系统日志统计', '4', '700', '1');
INSERT INTO `SM_PERMISSION` VALUES ('800', 'p_screen_view', '大屏展示', '1', '0', '1');



-- ----------------------------
-- Table structure for `SM_R_ROLE_PERMISSION`
-- ----------------------------
DROP TABLE IF EXISTS `SM_R_ROLE_PERMISSION`;  
CREATE TABLE `SM_R_ROLE_PERMISSION` (
  `R_ROLE_PERMISSION_UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '角色/权限映射ID',
  `ROLE_UUID` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色ID',
  `PERMISSION_UUID` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`R_ROLE_PERMISSION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of SM_R_ROLE_PERMISSION
-- ----------------------------
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('1', '1', '100');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('2', '1', '110');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('3', '1', '111');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('4', '1', '112');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('5', '1', '113');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('6', '1', '114');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('7', '1', '115');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('8', '1', '120');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('9', '1', '121');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('10', '1', '122');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('11', '1', '123');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('12', '1', '124');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('13', '1', '125');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('14', '1', '130');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('15', '1', '131');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('16', '1', '132');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('17', '1', '133');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('18', '1', '134');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('19', '1', '135');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('20', '1', '136');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('21', '1', '137');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('22', '1', '140');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('23', '1', '141');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('24', '1', '142');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('25', '1', '143');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('26', '1', '144');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('27', '1', '145');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('28', '1', '150');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('29', '1', '151');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('30', '1', '152');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('31', '1', '160');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('32', '1', '161');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('33', '1', '162');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('34', '1', '163');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('35', '1', '164');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('36', '1', '165');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('37', '1', '166');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('38', '1', '167');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('39', '1', '170');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('40', '1', '1710');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('401', '1', '1711');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('41', '1', '1712');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('42', '1', '1713');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('43', '1', '1714');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('44', '1', '1720');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('45', '1', '1721');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('46', '1', '1722');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('47', '1', '1723');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('48', '1', '1724');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('54', '1', '180');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('55', '1', '181');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('56', '1', '182');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('57', '1', '183');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('58', '1', '184');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('59', '1', '185');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('60', '1', '190');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('61', '1', '191');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('62', '1', '192');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('63', '1', '193');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('64', '1', '194');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('65', '1', '195');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('66', '1', '196');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('67', '1', '197');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('49', '1', '200');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('50', '1', '210');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('68', '1', '300');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('73', '1', '400');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('75', '1', '500');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('76', '1', '600');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('77', '1', '610');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('74', '1', '611');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('69', '1', '612');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('70', '1', '613');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('71', '1', '700');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('72', '1', '710');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('51', '1', '711');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('52', '1', '712');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('53', '1', '720');
INSERT INTO `SM_R_ROLE_PERMISSION` VALUES ('100', '1', '800');
-- ----------------------------
-- Table structure for `SM_ROLE` 角色表
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
-- Records of SM_ROLE
-- ----------------------------
INSERT INTO `SM_ROLE` VALUES ('1', '超级管理员', '2015-12-01 11:19:28', 'admin', '2015-12-01 11:19:29', 'admin', '1');


-- ----------------------------
-- Table structure for `HOST` 主机表
-- ----------------------------
DROP TABLE IF EXISTS `HOST`;
CREATE TABLE `HOST` (
  `ID` varchar(32) COLLATE utf8_bin NOT NULL,
  `HOST_IP` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '',
  `HOST_NAME` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `NOTES` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SNMP_COMMUNITY` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SNMP_VERSION` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `SNMP_USER_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SNMP_PASSWORD` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SNMP_AUTH_PROTOCOL` varchar(50) COLLATE utf8_bin DEFAULT '',
  `SNMP_PRIV_PASSPHRASE` varchar(200) COLLATE utf8_bin DEFAULT '',
  `SNMP_PRIV_PROTOCOL` varchar(50) COLLATE utf8_bin DEFAULT '',
  `SNMP_CONTEXT` varchar(64) COLLATE utf8_bin DEFAULT '',
  `SNMP_PORT` mediumint(5) DEFAULT NULL,
  `SNMP_TIMEOUT` mediumint(8) DEFAULT NULL,
  `AVAILABILITY_METHOD` smallint(5) unsigned NOT NULL DEFAULT '1',
  `PING_METHOD` smallint(5) unsigned DEFAULT '0',
  `PING_PORT` int(12) unsigned DEFAULT '0',
  `PING_TIMEOUT` int(12) unsigned DEFAULT '500',
  `PING_RETRIES` int(12) unsigned DEFAULT '2',
  `STATUS` tinyint(2) NOT NULL DEFAULT '0',
  `STATUS_FAIL_DATE` datetime DEFAULT '0000-00-00 00:00:00',
  `STATUS_REC_DATE` datetime DEFAULT '0000-00-00 00:00:00',
  `STATUS_LAST_ERROR` varchar(255) COLLATE utf8_bin DEFAULT '',
  `MIN_TIME` decimal(10,2) DEFAULT '9.99',
  `MAX_TIME` decimal(10,2) DEFAULT '0.00',
  `CUR_TIME` decimal(10,2) DEFAULT '0.00',
  `AVG_TIME` decimal(10,2) DEFAULT '0.00',
  `AVAILABILITY` decimal(8,2) DEFAULT '100.00',
  `CREATE_TIME` datetime NOT NULL,
  `HOST_WORK_STATUS` int(3) NOT NULL DEFAULT '1',
  `LAST_SHUT_DOWN_TIME` datetime DEFAULT NULL,
  `TYPE` int(2) DEFAULT NULL,
  `AC_BRAND` varchar(64) COLLATE utf8_bin DEFAULT 'RUIJIE',
  `AP_REGION_UUID` varchar(32),
  `HOST_AXIS` varchar(1000),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


INSERT INTO `HOST` VALUES ('abcdefghijklmnopqrstuvwxyz', '127.0.0.1', '本机', '', 'public', '2', null, null, '', null, '', null, null, null, '2', null, null, null, null, '1', null, null, null, null, null, null, null, null, '2016-10-28 19:01:56', '1', null, '6','','','');
-- ----------------------------
-- Table structure for `GRAPH_TREE` 图形树表
-- ----------------------------
DROP TABLE IF EXISTS `GRAPH_TREE`;
CREATE TABLE `GRAPH_TREE` (
  `GRAPH_TREE_UUID` varchar(32) NOT NULL,
  `GRAPH_TREE_NAME` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`GRAPH_TREE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of GRAPH_TREE
-- ----------------------------
INSERT INTO `GRAPH_TREE` VALUES ('1', '默认图形树', now(3));


-- ----------------------------
-- Table structure for `GRAPH_TREE_ITEM` 图形树条目表
-- ----------------------------
DROP TABLE IF EXISTS `GRAPH_TREE_ITEM`;
CREATE TABLE `GRAPH_TREE_ITEM` (
  `GRAPH_TREE_ITEM_UUID` varchar(32) NOT NULL,
  `TITLE` varchar(255),
  `PARENT_UUID` varchar(32) NOT NULL,
  `SORT` int(12),
  `GRAPH_TREE_ITEM_TYPE` int(1),
  `HOST_ID` varchar(32),
  PRIMARY KEY (`GRAPH_TREE_ITEM_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `GRAPH_TREE_ITEM` VALUES ('abcdefghijklmnopqrstuvwxyz', '', '1', '1', '2', 'abcdefghijklmnopqrstuvwxyz');

-- ----------------------------
-- Table structure for `SYSTEM_OPTION` 系统选项表
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_OPTION`;
CREATE TABLE `SYSTEM_OPTION` (
  `SYSTEM_OPTION_UUID` varchar(32) NOT NULL,
  `SYSTEM_OPTION_KEY` varchar(255)NOT NULL,
  `SYSTEM_OPTION_VALUE` varchar(1000),
  `SYSTEM_OPTION_NAME` varchar(255),
  `CREATE_TIME` datetime NOT NULL,
  `UPDATE_TIME` datetime,
  PRIMARY KEY (`SYSTEM_OPTION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- 系统选项表中插入默认数据
-- ----------------------------
INSERT INTO `SYSTEM_OPTION` VALUES 
('1', 'SMTP_SERVER_HOST_NAME', 'smtp.mxhichina.com', 'SMTP服务器主机名', now(3), null),
('2', 'SMTP_PORT', '25', 'SMTP端口', now(3), null),
('3', 'SMTP_USER_NAME', 'noreply@joywise.net', 'SMTP用户名', now(3), null),
('4', 'SMTP_PASSWORD', '1234Hello', 'SMTP密码', now(3), null),
('5', 'SMTP_PASSWORD_CONFIRM', '1234Hello', 'SMTP密码确认', now(3), null),
('6', 'DISABLE_ALL_THRESHOLD_VALUE', '0', '禁用所有阈值', now(3), null),
('7', 'REMAIN_DAYS', '31', '保留天数', now(3), null),
('15', 'USE_STATUS', '1', '启用状态', now(3), null),
('18', 'LOG_REMAIN_TIME', '2周', '日志保留时间', now(3), null),
('19', 'TFTP_SERVER', '', 'TFTP服务器', now(3), null),
('20', 'BACKUP_PATH', '/opt/znms/file/config_backup', '备份路径', now(3), null),
('21', 'EMAIL_ADDRESS', '', '邮箱地址', now(3), null),
('22', 'BACKUP_DAYS', '30', '备份天数', now(3), null),
('23', 'LOG_REMAIN_NUMBER', '10000000', '日志保留条数', now(3), null),
('24', 'ZOS_IP', '', 'zos IP', now(3), null),
('25', 'ZOS_PORT', '', 'zos端口', now(3), null),
('26', 'ZLOG_IP', '', 'zlog IP', now(3), null),
('27', 'ZLOG_PORT', '', 'zlog端口', now(3), null),
('28', 'SYSTEM_VERSION', 'znms_system_version', '版本号', now(3), null),
('29', 'HOME-BG', 'HOME-BG.jpg', '投屏首页背景图', now(3), null),
('30', 'TOPO-BG', 'TOPO-BG.jpg', '拓扑背景图', now(3), null),
('31', 'POINT', '1', '用户系数', now(3), null),
('32', 'RADIUS', '5', '散点散发只', now(3), null),
('33', 'POINT_SIZE', '2', '圆点大小', now(3), null),
('34', 'SCHOOL_CODE', '', '学校代码', now(3), null),
('35', 'KAFKA_IP', '', 'kafka服务器IP', now(3), null);
-- ----------------------------
-- Table structure for `CPU_TEMPLATE` CPU统计模版表
-- ----------------------------
DROP TABLE IF EXISTS `CPU_TEMPLATE`;
CREATE TABLE `CPU_TEMPLATE` (
  `CPU_TEMPLATE_UUID` varchar(32) NOT NULL,
  `GRAPH_TEMPLATE_UUID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`CPU_TEMPLATE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `MEMORY_TEMPLATE` 内存统计模版表
-- ----------------------------
DROP TABLE IF EXISTS `MEMORY_TEMPLATE`;
CREATE TABLE `MEMORY_TEMPLATE` (
  `MEMORY_TEMPLATE_UUID` varchar(32) NOT NULL,
  `GRAPH_TEMPLATE_UUID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`MEMORY_TEMPLATE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `WIRELESS_STATISTICAL_CONFIGURATION` 无线统计配置表
-- ----------------------------
DROP TABLE IF EXISTS `WIRELESS_STATISTICAL_CONFIGURATION`;
CREATE TABLE `WIRELESS_STATISTICAL_CONFIGURATION` (
  `WIRELESS_STATISTICAL_CONFIGURATION_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `AP_TEMPLATE_UUID` varchar(32) NOT NULL,
  `USER_TEMPLATE_UUID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`WIRELESS_STATISTICAL_CONFIGURATION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `EXPORT_LINK` 出口链路配置表
-- ----------------------------
DROP TABLE IF EXISTS `EXPORT_LINK`;
CREATE TABLE `EXPORT_LINK` (
  `EXPORT_LINK_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `GRAPH_UUID` varchar(32) NOT NULL,
  `EXPORT_LINK_DESCRIPTION` varchar(255),
  `MAX_BAND_WIDTH` bigint,
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`EXPORT_LINK_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `SYSTEM_LOG_DELETE_RULE` 系统日志删除规则表
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_LOG_DELETE_RULE`;
CREATE TABLE `SYSTEM_LOG_DELETE_RULE` (
  `SYSTEM_LOG_DELETE_RULE_UUID` varchar(32) NOT NULL,
  `RULE_NAME` varchar(32) NOT NULL,
  `RULE_STATUS` int,
  `MATCH_TYPE` int,
  `MATCH_STRING` varchar(1000) NOT NULL,
  `NOTE` varchar(1000),
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`SYSTEM_LOG_DELETE_RULE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `BACKUP_CONFIGURATION_ACCOUNT_PASSWORD` 备份配置账户密码表
-- ----------------------------
DROP TABLE IF EXISTS `BACKUP_CONFIGURATION_ACCOUNT_PASSWORD`;
CREATE TABLE `BACKUP_CONFIGURATION_ACCOUNT_PASSWORD` (
  `ACCOUNT_PASSWORD_UUID` varchar(32) NOT NULL,
  `AP_NAME` varchar(32) NOT NULL,
  `CERTIFICATE_NAME` varchar(32) NOT NULL,
  `PASSWORD` varchar(32) NOT NULL,
  `CONFIRM_PASSWORD` varchar(32) NOT NULL,
  `ENABLE_PASSWORD` varchar(32) NOT NULL,
  `CONFIRM_ENABLE_PASSWORD` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`ACCOUNT_PASSWORD_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `BACKUP_CONFIGURATION_DEVICE` 备份配置设备表
-- ----------------------------
DROP TABLE IF EXISTS `BACKUP_CONFIGURATION_DEVICE`;
CREATE TABLE `BACKUP_CONFIGURATION_DEVICE` (
  `DEVICE_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `USE_STATUS` tinyint(1),
  `ACCOUNT_PASSWORD_UUID` varchar(32),
  `BACKUP_CYCLE` int,
  `CONTENT` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(1000),
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`DEVICE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `SYSTEM_LOG` 系统日志表
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_LOG`;
CREATE TABLE `SYSTEM_LOG` (
  `FACILITY_ID` int(10) DEFAULT NULL,
  `PRIORITY_ID` int(10) DEFAULT NULL,
  `HOST_ID` varchar(32) NOT NULL,
  `LOG_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `MESSAGE` varchar(1024) NOT NULL DEFAULT '',
  `SEQ` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`SEQ`),
  KEY `LOG_TIME` (`LOG_TIME`),
  KEY `HOST_ID` (`HOST_ID`),
  KEY `PRIORITY_ID` (`PRIORITY_ID`),
  KEY `FACILITY_ID` (`FACILITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `SYSTEM_LOG_STATISTICS` 系统日志统计表
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_LOG_STATISTICS`;
CREATE TABLE `SYSTEM_LOG_STATISTICS` (
  `HOST_ID` varchar(32) NOT NULL,
  `FACILITY_ID` int(10) NOT NULL,
  `PRIORITY_ID` int(10) NOT NULL,
  `INSERT_TIME` datetime NOT NULL,
  `RECORDS` int(10),
  PRIMARY KEY (`HOST_ID`,`FACILITY_ID`,`PRIORITY_ID`,`INSERT_TIME`),
  KEY `HOST_ID` (`HOST_ID`),
  KEY `FACILITY_ID` (`FACILITY_ID`),
  KEY `PRIORITY_ID` (`PRIORITY_ID`),
  KEY `INSERT_TIME` (`INSERT_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `GRAPH_TEMPLATE` 图形模板表
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
INSERT INTO `GRAPH_TEMPLATE` VALUES ('4', 'Ruijie_CPU_Usage', '.1.3.6.1.4.1.4881.1.1.10.2.36.1.1.3.0', 'RCU', 'cpu_usage', 'cpu使用率', '4', '1');
INSERT INTO `GRAPH_TEMPLATE` VALUES ('5', 'CPU_Usage', '.1.3.6.1.4.1.2021.11.9.0', 'CU', 'cpu_usage', 'cpu使用率', '4', '1');


-- ----------------------------
-- Table structure for `GRAPH` 图形表
-- ----------------------------
DROP TABLE IF EXISTS `GRAPH`;
CREATE TABLE `GRAPH` (
  `GRAPH_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `HOST_IP` varchar(100) NOT NULL,
  `GRAPH_TYPE` int(3),
  `GRAPH_TEMPLATE_ID` varchar(10),
  `GRAPH_TEMPLATE_NAME` varchar(100),
  `GRAPH_NAME` varchar(100),
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`GRAPH_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `GRAPH` VALUES ('5ff38c1da5ba69faab9f414bead37bf6', 'abcdefghijklmnopqrstuvwxyz', '127.0.0.1', '1', '5', 'CPU_Usage', '本机-CPU_Usage', '2016-11-03 09:11:04');

-- ----------------------------
-- Table structure for `GRAPH_OID` 图形OID对应关系表
-- ----------------------------
DROP TABLE IF EXISTS `GRAPH_OID`;
CREATE TABLE `GRAPH_OID` (
  `GRAPH_OID_UUID` varchar(32) NOT NULL,
  `GRAPH_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `HOST_IP` varchar(100) NOT NULL,
  `GRAPH_TEMPLATE_ID`  varchar(10),
  `OID` varchar(100) NOT NULL,
  `FLOW_DIRECTION` varchar(10),
  `RRD_FILE_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`GRAPH_OID_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `GRAPH_OID` VALUES ('fe3ee594749bcbacc4206f97aed84e5f', '5ff38c1da5ba69faab9f414bead37bf6', 'abcdefghijklmnopqrstuvwxyz', '127.0.0.1', '5', '.1.3.6.1.4.1.2021.11.9.0', null, 'CU_5ff38c1da5ba69faab9f414bead37bf6.rrd');


-- ----------------------------
-- Table structure for `CONFIG_BACKUP_RECORD`
-- ----------------------------
DROP TABLE IF EXISTS `CONFIG_BACKUP_RECORD`;
CREATE TABLE `CONFIG_BACKUP_RECORD` (
  `ID` varchar(32) COLLATE utf8_bin NOT NULL,
  `HOST_UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `BACKUP_TIME` datetime DEFAULT NULL,
  `DEBUG_INFO` mediumtext COLLATE utf8_bin,
  `ACTIVATOR` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `FILE_NAME` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `SUCCESS` bit(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of CONFIG_BACKUP_RECORD
-- ----------------------------

-- ----------------------------
-- Table structure for `RRD_DATA_INFO`
-- ----------------------------
DROP TABLE IF EXISTS `RRD_DATA_INFO`;
CREATE TABLE `RRD_DATA_INFO` (
  `RRD_TEMPLATE_NAME` varchar(32) COLLATE utf8_bin NOT NULL,
  `RRD_DATA_ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `RRD_DATA_NAME` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`RRD_DATA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `RRD_DATA_INFO` VALUES ('CU', '5ff38c1da5ba69faab9f414bead37bf6', null);

-- ----------------------------
-- Table structure for `THRESHOLD_VALUE` 阀值表
-- ----------------------------
DROP TABLE IF EXISTS `THRESHOLD_VALUE`;
CREATE TABLE `THRESHOLD_VALUE` (
  `THRESHOLD_VALUE_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `GRAPH_UUID` varchar(32) NOT NULL,
  `FLOW_DIRECTION` int(3),
  `THRESHOLD_VALUE_NAME` varchar(100) NOT NULL,
  `STATUS` int(3),
  `WARNING_HIGH_THRESHOLD_VALUE` decimal(30,2),
  `WARNING_LOW_THRESHOLD_VALUE` decimal(30,2),
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`THRESHOLD_VALUE_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `THRESHOLD_VALUE_TRIGGER_LOG` 阀值触发日志表
-- ----------------------------
DROP TABLE IF EXISTS `THRESHOLD_VALUE_TRIGGER_LOG`;
CREATE TABLE `THRESHOLD_VALUE_TRIGGER_LOG` (
  `LOG_UUID` varchar(32) NOT NULL,
  `HOST_UUID` varchar(32) NOT NULL,
  `THRESHOLD_VALUE_UUID` varchar(32) NOT NULL,
  `ALARM_VALUE` decimal(30,2),
  `CURRENT_VALUE` decimal(30,2),
  `DESCRIPTION` varchar(1000),
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`LOG_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `THRESHOLD_VALUE_TRIGGER_SURVEY` 阀值触发概况表
-- ----------------------------
DROP TABLE IF EXISTS `THRESHOLD_VALUE_TRIGGER_SURVEY`;
CREATE TABLE `THRESHOLD_VALUE_TRIGGER_SURVEY` (
  `SURVEY_UUID` varchar(32) NOT NULL,
  `THRESHOLD_VALUE_UUID` varchar(32) NOT NULL,
  `LAST_HOUR_TRIGGER_COUNT` int,
  `LAST_DAY_TRIGGER_COUNT` int,
  `LAST_WEEK_TRIGGER_COUNT` int,
  PRIMARY KEY (`SURVEY_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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

drop procedure if exists Table_Partition_By_Month;

/*
	对表追加分区，table_name是要分区的表
*/
DELIMITER // 
create procedure Table_Partition_By_Month(IN create_table_name VARCHAR(50))
 begin
	DECLARE database_name VARCHAR(50);
	/*当前分区的日期*/
	DECLARE cur_partition_date VARCHAR(20);
	/*下一个分区日期*/
	DECLARE next_partition_date DATE;
	/*最大分区日期*/
	DECLARE max_partition_date DATE;
	
	/*启动事务*/
	declare exit handler for sqlexception rollback;
    start TRANSACTION;
	
	/* 获取当前数据库名*/
	select database() into database_name;
	
	/*获取已经存在分区的日期*/
    select REPLACE(partition_name,'P','') into cur_partition_date from INFORMATION_SCHEMA.PARTITIONS where TABLE_SCHEMA=database_name and table_name=create_table_name order by partition_ordinal_position DESC limit 1;
		
		set cur_partition_date = CONCAT(cur_partition_date,'01');

		/* 计算下一个分区日期*/
		set next_partition_date = DATE(DATE_ADD(cur_partition_date, INTERVAL 1 MONTH));
		/* 计算最大分区日期*/
		set max_partition_date = DATE(DATE_ADD(DATE(CONCAT(left(CURDATE()+0, 6), '01'))+0, INTERVAL 1 MONTH));
		/*创建分区，一直到当前月份的下一个分区*/
	while (next_partition_date <= max_partition_date) do
		set @cur_partition_name = left(date(next_partition_date)+0, 6);
		set @than_date = DATE(DATE_ADD(DATE(next_partition_date)+0, INTERVAL 1 MONTH));
		SET @execute_sql=CONCAT('ALTER TABLE ',create_table_name,' ADD PARTITION (PARTITION P',@cur_partition_name,' VALUES LESS THAN (TO_DAYS (\'',date(@than_date),'\')))');
		PREPARE stmt2 FROM @execute_sql;
		EXECUTE stmt2;
		DEALLOCATE PREPARE stmt2;
		set next_partition_date = DATE(DATE_ADD(DATE(next_partition_date)+0, INTERVAL 1 MONTH));
	end while;
	
	/*提交事务*/
	COMMIT ;
end //
DELIMITER ; 
drop procedure if exists Table_Partition_By_Year;


-- ----------------------------
-- Table structure for `AP_INFORMATION` AP信息表
-- ----------------------------
DROP TABLE IF EXISTS `AP_INFORMATION`;
CREATE TABLE `AP_INFORMATION` (
  `AP_INFORMATION_UUID` varchar(32) NOT NULL,
  `AP_REGION_UUID` varchar(32),
  `AP_NAME` varchar(100),
  `AP_IP` varchar(100) NOT NULL,
  `AP_MAC` varchar(100),
  `AC_IP` varchar(100) NOT NULL,
  `AP_AXIS` varchar(100),
  `AP_USER_COUNT` int,
  `LAST_UPDATE_TIME` datetime,
  `AP_USER_MAX` int,
  PRIMARY KEY (`AP_INFORMATION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `ONLINE_USER_ANALYSIS` 在线用户统计分析表
-- ----------------------------
DROP TABLE IF EXISTS `ONLINE_USER_ANALYSIS`;
CREATE TABLE `ONLINE_USER_ANALYSIS` (
  `ONLINE_USER_ANALYSIS_UUID` varchar(32) NOT NULL,
  `USER_TYPE` int,
  `USER_COUNT` int,
  `CREATE_TIME` datetime,
  PRIMARY KEY (`ONLINE_USER_ANALYSIS_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `AC_OID_CONFIG`;
CREATE TABLE `AC_OID_CONFIG` (
  `AC_BRAND` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `AC_BRANDNAME` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `AC_IP` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `AC_MAC` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `AC_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `AC_ONLINE_NUM` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `AC_MAX_ONLINE_NUM` varchar(64) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of AC_OID_CONFIG
-- ----------------------------
INSERT INTO `AC_OID_CONFIG` VALUES ('RUIJIE','锐捷', '1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.33', '1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.1', '1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.2', '1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.34', '1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.41');


-- ----------------------------
-- Table structure for `AP_REGION` AP区域表
-- ----------------------------
DROP TABLE IF EXISTS `AP_REGION`;
CREATE TABLE `AP_REGION` (
  `AP_REGION_UUID` varchar(32) NOT NULL,
  `AP_REGION_NAME` varchar(32) NOT NULL,
  `AP_REGION_COORDINATE` varchar(5000),
  PRIMARY KEY (`AP_REGION_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `SCREEN`;
CREATE TABLE `SCREEN` (
  `ID` varchar(32) COLLATE utf8_bin NOT NULL,
  `CONFIG_DATA` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `ROW` int(11) DEFAULT NULL,
  `COL` int(11) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `NAME` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `THUMBNAIL` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `CODE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `SCREEN` (`ID`, `CONFIG_DATA`, `ROW`, `COL`, `CREATETIME`, `UPDATETIME`, `NAME`, `THUMBNAIL`, `CODE`) VALUES ('111111111', '[{x: 0, y: 0, width: 12, height: 2,moduleName:\'index\'}]', 2, 3, '2016-11-29 16:19:07', '2016-11-29 16:19:09', '首页大屏', 'index.png','index');
INSERT INTO `SCREEN` (`ID`, `CONFIG_DATA`, `ROW`, `COL`, `CREATETIME`, `UPDATETIME`, `NAME`, `THUMBNAIL`, `CODE`) VALUES ('222222222', '[{x: 0, y: 0, width: 12, height: 2,moduleName:\'starMap\'}]', 2, 3, '2016-11-29 16:19:07', '2016-11-29 16:19:09', '星光图', 'starMap.png','starMap');
INSERT INTO `SCREEN` (`ID`, `CONFIG_DATA`, `ROW`, `COL`, `CREATETIME`, `UPDATETIME`, `NAME`, `THUMBNAIL`, `CODE`) VALUES ('333333333', '[{x: 0, y: 0, width: 12, height: 2,moduleName:\'deviceStarMap\'}]', 2, 3, now(), now(), '设备星光图', 'deviceStarMap.png','deviceStarMap');


DROP TABLE IF EXISTS `FLOW_DATA`;
CREATE TABLE `FLOW_DATA` (
  `ID` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `TEACHER_WIRELESS_FLOW` double(10,0) DEFAULT NULL,
  `TEACHER_PC_FLOW` double(10,0) DEFAULT NULL,
  `STUDENT_WIRELESS_FLOW` double(10,0) DEFAULT NULL,
  `STUDENT_PC_FLOW` double(10,0) DEFAULT NULL,
  `RECORD_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `AUTH_RESULT`;
CREATE TABLE `AUTH_RESULT` (
	`AUTH_RESULT_UUID`  varchar(32) NOT NULL,
	`SERIAL_NUMBER`  varchar(255) DEFAULT NULL,
	`RETURN_MSG`  varchar(3000) DEFAULT NULL,
	`RESULT_TYPE` tinyint DEFAULT NULL,
	`EXPIRY_DATE` datetime NULL,
	`CREATE_TIME` datetime NULL,
	`UPDATE_TIME` datetime NULL,
	`CREATE_HOST` varchar(32) DEFAULT NULL,
	`UPDATE_HOST` varchar(32) DEFAULT NULL,
	PRIMARY KEY (`AUTH_RESULT_UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
