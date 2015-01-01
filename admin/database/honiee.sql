/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50045
Source Host           : localhost:3306
Source Database       : honiee

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2014-12-31 17:01:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `applications`
-- ----------------------------
DROP TABLE IF EXISTS `applications`;
CREATE TABLE `applications` (
  `application_id` int(11) NOT NULL COMMENT '应用Id',
  `application_name` varchar(50) NOT NULL COMMENT '应用名称',
  `application_description` varchar(1024) default NULL COMMENT '应用描述',
  PRIMARY KEY  (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of applications
-- ----------------------------
INSERT INTO applications VALUES ('1', 'doubletrack', '双轨管理系统');

-- ----------------------------
-- Table structure for `card_info`
-- ----------------------------
DROP TABLE IF EXISTS `card_info`;
CREATE TABLE `card_info` (
  `card_no` varchar(16) NOT NULL COMMENT '卡号',
  `card_id` int(11) default NULL COMMENT '卡标识号',
  `member_id` int(11) default NULL COMMENT '会员ID',
  `card_type` int(2) default NULL COMMENT '卡片类型',
  `exp_data` datetime default NULL COMMENT '过期时间',
  `enc_cvv` varchar(64) default NULL COMMENT '加密的CVV码',
  `enc_cvv2` varchar(64) default NULL COMMENT '加密的CVV2码',
  `enc_password` varchar(64) default NULL COMMENT '加密的密码',
  `status` int(1) default NULL COMMENT '状态',
  `creation_time` datetime default NULL COMMENT '开户时间',
  `activation_time` datetime default NULL COMMENT '激活时间',
  `last_act_time` datetime default NULL COMMENT '最近活动时间',
  PRIMARY KEY  (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card_info
-- ----------------------------

-- ----------------------------
-- Table structure for `card_transaction_details`
-- ----------------------------
DROP TABLE IF EXISTS `card_transaction_details`;
CREATE TABLE `card_transaction_details` (
  `txn_id` int(11) NOT NULL COMMENT '交易ID',
  `txn_type` int(2) default NULL COMMENT '交易类型',
  `card_no` varchar(16) default NULL COMMENT '卡信息编号',
  `member_id` int(11) default NULL COMMENT '个人客户信息ID',
  `cur` int(2) default NULL COMMENT '交易币种',
  `amount` decimal(15,2) default NULL COMMENT '交易金额',
  `consume_pts` int(11) default NULL COMMENT '本次消费实用的积分',
  `get_pts` int(11) default NULL COMMENT '本次消费获得积分',
  `organization_id` int(11) default NULL COMMENT '门店ID',
  `organization_name` varchar(30) default NULL COMMENT '门店名称',
  `terminal_id` int(11) default NULL COMMENT '终端号',
  `terminal_name` varchar(128) default NULL COMMENT '终端名称',
  `operator_id` int(11) default NULL COMMENT '操作员编号',
  `reconciliation_date` datetime default NULL COMMENT '对账时间',
  `txn_time` datetime default NULL COMMENT '交易时间',
  `voided` int(1) default NULL COMMENT '撤销、冲正标志',
  `voided_time` datetime default NULL COMMENT '撤销、冲正时间',
  PRIMARY KEY  (`txn_id`),
  KEY `card_no_fk` (`card_no`),
  CONSTRAINT `card_no_fk` FOREIGN KEY (`card_no`) REFERENCES `card_info` (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card_transaction_details
-- ----------------------------

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL COMMENT '类别ID',
  `category_name` varchar(30) NOT NULL COMMENT '类别名称',
  `parent_category_id` int(11) default NULL COMMENT '父类别名称',
  `category_level` int(2) default NULL COMMENT '类别等级',
  `category_id_sequence` varchar(1024) default NULL COMMENT '类别序列',
  `sort_id` int(4) default NULL COMMENT '排序ID',
  PRIMARY KEY  (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `customer_code` varchar(20) NOT NULL COMMENT '客户编码，年份（4位）月份（2位）编号（3），例如201209000',
  `customer_name` varchar(30) NOT NULL COMMENT '客户名称',
  `customer_type` int(1) default NULL COMMENT '客户类型',
  `level` int(8) default NULL COMMENT '客户等级',
  `phone` varchar(50) default NULL COMMENT '联系电话',
  `contact` varchar(50) default NULL COMMENT '联系人',
  `id_card_number` varchar(50) default NULL COMMENT '身份证',
  `qq` varchar(50) default NULL COMMENT 'QQ号码',
  `bank_type` varchar(20) default NULL COMMENT '开户行',
  `bank_account` varchar(50) default NULL COMMENT '银行账号',
  `address` varchar(256) default NULL COMMENT '联系地址',
  `status` int(1) default NULL COMMENT '状态：1-正常；2-冻结；3-删除',
  `activat_status` int(2) default NULL COMMENT '0-都未激活；1-手机号激活；2-邮箱激活；3-都激活',
  `parent_customer_id` int(11) default NULL COMMENT '上级客户编号',
  `recommend_customer_id` int(11) default NULL COMMENT '推荐客户编号',
  `customer_sequence` varchar(1024) default NULL COMMENT '上级客户序列',
  `remark` varchar(1024) default NULL COMMENT '备注',
  `email` varchar(128) default NULL COMMENT '客户邮箱',
  PRIMARY KEY  (`customer_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO customer VALUES ('1', '20120915000', '张三', '1', '1', '36521452', '张三', '365425845625141236', '524887423', '农行', '36520125463015', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', '1', '2', null, null, '1', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', null);
INSERT INTO customer VALUES ('5', '20120919004', '周七', '1', '3', '35412547', '周七', '36524295323651242357', '254417', '农行', '3652165224522553', '测试测试测试测试测试测试测试', '1', '2', '2', '2', '1,2,5', '测试测试测试测试测试测试测试', null);
INSERT INTO customer VALUES ('6', '20120919005', '白八', '3', '4', '32154755', '白八', '36524295323651242359', '52488742', '农行', '3652165224522553', '测试', '1', '2', '4', '4', '1,2,4,6', '测试', null);
INSERT INTO customer VALUES ('9', '201210191001', '何九', '2', '3', '32154755', '何九', '36524295323651242358', '254416', '农行', '36520125463015', '', '1', '2', '3', '3', '1,3,9', '', null);
INSERT INTO customer VALUES ('4', '20120919003', '马六', '1', '3', '35412547', '马六', '36524295323651242357', '254417', '农行', '3652165224522553', '测试测试测试测试测试测试测试', '1', '2', '2', '2', '1,2,4', '测试测试测试测试测试测试测试', null);
INSERT INTO customer VALUES ('10', '201210191002', '梁十', '3', '3', '32014568', '李四', '36524295323651242358', '254417', '农行', '3652165224522553', '', '1', '3', '3', '3', '1,3,10', '', null);
INSERT INTO customer VALUES ('2', '20120919001', '李四', '1', '2', '35412548', '李四', '36524295323651242358', '254416', '农行', '3652165224522552', '测试测试测试测试测试测试测试', '1', '0', '1', '1', '1,2', '测试测试测试测试测试测试测试', null);
INSERT INTO customer VALUES ('3', '20120919002', '王五', '1', '2', '35412547', '王五', '36524295323651242357', '254417', '农行', '3652165224522553', '测试测试测试测试测试测试测试', '1', '1', '1', '1', '1,3', '测试测试测试测试测试测试测试', null);
INSERT INTO customer VALUES ('11', '201411281001', '777777', '3', '2', '', '', '', '', '', '', '8888888888888', '1', '0', '1', '2', '1,11', '8999999', null);
INSERT INTO customer VALUES ('12', '201412011001', '6666666666', '1', '2', '', '', '', '', '', '', '', '1', '0', '1', '3', '1,12', '', null);
INSERT INTO customer VALUES ('13', '201412011002', '哦哦哦哦哦', '1', '4', '', '', '', '', '', '', '', '-1', '2', '10', '3', '1,3,10,13', '', null);
INSERT INTO customer VALUES ('14', '201412011003', '哦哦哦哦哦', '2', '3', '', '', '', '', '', '', '', '1', '3', '3', '1', '1,3,14', '', null);
INSERT INTO customer VALUES ('15', '201412011004', 'eeeeeee', '1', '2', '', '', '', '', '', '', '', '1', '2', '1', '4', '1,15', '', null);
INSERT INTO customer VALUES ('16', '201412011005', '777777', '2', '3', '', '', '', '', '', '', '', '1', '-1', '3', '1', '1,3,16', '', null);

-- ----------------------------
-- Table structure for `deal`
-- ----------------------------
DROP TABLE IF EXISTS `deal`;
CREATE TABLE `deal` (
  `deal_id` int(11) NOT NULL COMMENT '交易ID',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `deal_begin_time` datetime NOT NULL COMMENT '交易开始时间',
  `deal_end_time` datetime NOT NULL COMMENT '交易完成时间',
  `deal_amount` int(11) NOT NULL COMMENT '交易金额',
  `deal_status` int(2) default NULL COMMENT '交易状态 0-交易进行中;1-付款成功;2-付款失败',
  `operator_id` int(11) default NULL COMMENT '交易操作员',
  `sync_token` int(2) default NULL COMMENT '用于在并发情况下处理时得到一个令牌,避免重复处理交易或订单.',
  `asyn_accounting` int(1) default '0' COMMENT '异步记账的标志，0-同步 1-异步',
  `transaction_time` datetime default NULL COMMENT '交易发生逻辑时间',
  `fee_type` int(1) default NULL COMMENT '1-实时收费 ;2-预收;3-后收',
  PRIMARY KEY  (`deal_id`),
  KEY `FK_order_id` (`order_id`),
  CONSTRAINT `FK_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deal
-- ----------------------------

-- ----------------------------
-- Table structure for `employee_position`
-- ----------------------------
DROP TABLE IF EXISTS `employee_position`;
CREATE TABLE `employee_position` (
  `employee_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  PRIMARY KEY  (`employee_id`,`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee_position
-- ----------------------------
INSERT INTO employee_position VALUES ('1', '3');
INSERT INTO employee_position VALUES ('2', '2');
INSERT INTO employee_position VALUES ('3', '4');
INSERT INTO employee_position VALUES ('4', '4');
INSERT INTO employee_position VALUES ('5', '1');
INSERT INTO employee_position VALUES ('5', '2');
INSERT INTO employee_position VALUES ('5', '3');
INSERT INTO employee_position VALUES ('5', '4');
INSERT INTO employee_position VALUES ('5', '5');
INSERT INTO employee_position VALUES ('5', '6');
INSERT INTO employee_position VALUES ('6', '3');
INSERT INTO employee_position VALUES ('6', '6');
INSERT INTO employee_position VALUES ('7', '1');
INSERT INTO employee_position VALUES ('7', '2');
INSERT INTO employee_position VALUES ('7', '3');
INSERT INTO employee_position VALUES ('7', '4');
INSERT INTO employee_position VALUES ('7', '5');
INSERT INTO employee_position VALUES ('7', '9');
INSERT INTO employee_position VALUES ('7', '11');
INSERT INTO employee_position VALUES ('9', '1');
INSERT INTO employee_position VALUES ('9', '2');
INSERT INTO employee_position VALUES ('9', '3');
INSERT INTO employee_position VALUES ('14', '22');
INSERT INTO employee_position VALUES ('14', '23');
INSERT INTO employee_position VALUES ('101', '4');
INSERT INTO employee_position VALUES ('102', '3');
INSERT INTO employee_position VALUES ('104', '4');
INSERT INTO employee_position VALUES ('104', '10');
INSERT INTO employee_position VALUES ('105', '4');
INSERT INTO employee_position VALUES ('112', '15');

-- ----------------------------
-- Table structure for `entry`
-- ----------------------------
DROP TABLE IF EXISTS `entry`;
CREATE TABLE `entry` (
  `vouchercode` varchar(20) NOT NULL COMMENT '凭证号',
  `entry_code` int(11) NOT NULL COMMENT '分录id',
  `deal_id` int(11) default NULL COMMENT '订单号',
  `acct_code` varchar(32) default NULL,
  `crdr` int(1) default NULL COMMENT '借贷标志  1-借方(dr,负的);2-贷方(cr,正的)',
  `amount` int(11) default NULL,
  `payment_service_code` int(11) default NULL COMMENT '支付服务号',
  `digest` varchar(512) default NULL COMMENT '摘要',
  `creation_time` datetime default NULL COMMENT '创建时间',
  `status` int(11) default NULL COMMENT '状态:1-已记账，0-未记账',
  `post_time` datetime default NULL COMMENT '分录登账时间',
  `entry_type` int(2) default NULL COMMENT '1-支付类 ；2-存货类； 3-手工',
  `currency_code` int(2) default NULL COMMENT '记账币种',
  `exchange_rate` int(11) default NULL COMMENT '记账汇率',
  `asyn_accounting` int(1) default NULL COMMENT '异步记账的标志，0-同步 1-异步',
  PRIMARY KEY  (`vouchercode`,`entry_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of entry
-- ----------------------------

-- ----------------------------
-- Table structure for `function`
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function` (
  `function_id` int(11) NOT NULL COMMENT '功能ID',
  `function_code` varchar(64) NOT NULL COMMENT '功能编号',
  `function_name` varchar(128) NOT NULL COMMENT '功能名称',
  `function_type` int(11) default NULL COMMENT '功能类型',
  `function_description` varchar(256) default NULL COMMENT '功能描述',
  `application_id` int(11) NOT NULL COMMENT '所属应用ID',
  `default_url` varchar(256) default NULL COMMENT '默认URL',
  `status` int(2) NOT NULL default '1' COMMENT '状态：0-无效，1-有效，2-删除 ; 默认值:1',
  `create_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后更新时间',
  PRIMARY KEY  (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of function
-- ----------------------------

-- ----------------------------
-- Table structure for `item_category`
-- ----------------------------
DROP TABLE IF EXISTS `item_category`;
CREATE TABLE `item_category` (
  `id` int(11) NOT NULL auto_increment,
  `catId` int(11) default NULL,
  `parentId` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_category
-- ----------------------------
INSERT INTO item_category VALUES ('1', '11', '0');
INSERT INTO item_category VALUES ('2', '22', '1');
INSERT INTO item_category VALUES ('3', '33', '1');
INSERT INTO item_category VALUES ('4', '44', '2');
INSERT INTO item_category VALUES ('5', '55', '3');
INSERT INTO item_category VALUES ('6', '66', '0');
INSERT INTO item_category VALUES ('7', '77', '6');
INSERT INTO item_category VALUES ('8', '88', '6');
INSERT INTO item_category VALUES ('9', '99', '2');

-- ----------------------------
-- Table structure for `member`
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `name` varchar(64) default NULL COMMENT '会员名称',
  `parent_member_id` int(11) default NULL COMMENT '父会员ID',
  `member_id_sequence` varchar(512) default NULL COMMENT '会员ID序列',
  `member_name_sequence` varchar(1024) default NULL COMMENT '会员名称序列',
  `member_level` int(2) default NULL COMMENT '会员等级',
  `id_type` int(2) default NULL COMMENT '身份证件类型',
  `id_number` varchar(20) default NULL COMMENT '身份证件号码',
  `sex` int(1) default NULL COMMENT '性别',
  `birthday` datetime default NULL COMMENT '生日',
  `mobile` varchar(32) default NULL COMMENT '手机号码',
  `phone` varchar(32) default NULL COMMENT '联系电话号码',
  `post_code` varchar(6) default NULL COMMENT '邮编',
  `address` varchar(128) default NULL COMMENT '地址',
  `email` varchar(128) default NULL COMMENT '电子邮件',
  `creation_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后更新时间',
  PRIMARY KEY  (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO member VALUES ('1', '超级管理员', '0', '0，1', '超级管理员', '1', '1', '370306111111111111', '1', '1982-01-01 19:08:26', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `menu_item`
-- ----------------------------
DROP TABLE IF EXISTS `menu_item`;
CREATE TABLE `menu_item` (
  `id` int(11) NOT NULL COMMENT '菜单ID',
  `menu_code` varchar(64) NOT NULL COMMENT '菜单编号',
  `menu_name` varchar(30) NOT NULL COMMENT '菜单显示名称',
  `menu_type` int(2) default NULL COMMENT '菜单类型',
  `parent_menu_id` int(11) NOT NULL COMMENT '父菜单ID，0顶级菜单',
  `resources_id` int(11) default NULL COMMENT '资源ID',
  `menu_order` int(2) NOT NULL COMMENT '菜单顺序',
  `status` int(2) NOT NULL default '1' COMMENT '菜单状态:  0-初始化，1-有效，2-无效,3-删除; 默认值:0',
  `target` varchar(20) default NULL,
  `rel` varchar(50) default NULL,
  `menu_description` varchar(128) default NULL COMMENT '菜单描述',
  `menu_tips` varchar(30) default NULL COMMENT '菜单提示',
  `has_sub` int(1) default NULL COMMENT '是否有子节点: 0-无子节点，1-有子节点',
  `create_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后修改时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_item
-- ----------------------------
INSERT INTO menu_item VALUES ('1', '1001', '权限管理', null, '0', null, '1', '1', null, null, null, '权限管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('2', '1002', '成员管理', null, '0', null, '2', '1', null, null, null, '成员管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('3', '1003', '系统管理', null, '0', null, '3', '1', null, null, null, '系统管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('4', '1004', '积分管理', null, '0', null, '4', '1', null, null, null, '积分管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('5', '1005', '系统审核', null, '0', null, '5', '1', null, null, null, '系统审核', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('6', '1006', '会员管理', null, '0', null, '6', '1', null, null, null, '会员管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('7', '1007', '会员事物', null, '0', null, '7', '1', null, null, null, '会员事物', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('8', '1008', '财务管理', null, '0', null, '8', '1', null, null, null, '财务管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('9', '1009', '用户信息', null, '0', null, '9', '1', null, null, null, '用户信息', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('10', '1010', '报表管理', null, '0', null, '10', '1', null, null, null, '报表管理', '1', '2011-12-22 23:39:41', null);
INSERT INTO menu_item VALUES ('11', '1011', '菜单管理', null, '1003', null, '1', '1', null, null, null, '菜单管理', '0', '2011-12-22 23:39:41', null);

-- ----------------------------
-- Table structure for `myboard_group`
-- ----------------------------
DROP TABLE IF EXISTS `myboard_group`;
CREATE TABLE `myboard_group` (
  `mygroup_id` int(11) unsigned NOT NULL auto_increment,
  `mygroup_name` varchar(64) NOT NULL,
  `parent_id` int(11) unsigned NOT NULL default '0',
  `create_time` datetime NOT NULL,
  `comment` varchar(256) default NULL,
  `has_child` int(11) unsigned default '0',
  PRIMARY KEY  (`mygroup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of myboard_group
-- ----------------------------
INSERT INTO myboard_group VALUES ('1', 'test1', '0', '2012-10-14 20:16:20', null, '1');
INSERT INTO myboard_group VALUES ('2', 'test2', '1', '2012-10-14 20:16:57', null, '1');
INSERT INTO myboard_group VALUES ('3', 'test3', '1', '2012-10-14 20:17:14', null, '1');
INSERT INTO myboard_group VALUES ('4', 'test4', '2', '2012-10-14 20:18:10', null, '0');
INSERT INTO myboard_group VALUES ('5', 'test5', '2', '2012-10-14 20:18:30', null, '0');
INSERT INTO myboard_group VALUES ('6', 'test6', '3', '2012-10-14 20:18:46', null, '0');
INSERT INTO myboard_group VALUES ('7', 'test7', '3', '2012-10-14 20:18:51', null, '0');
INSERT INTO myboard_group VALUES ('8', 'test8', '4', '2012-10-14 20:19:21', null, '0');
INSERT INTO myboard_group VALUES ('9', 'test9', '4', '2012-10-14 20:19:28', null, '0');
INSERT INTO myboard_group VALUES ('10', 'test10', '6', '2012-10-14 20:19:54', null, '0');
INSERT INTO myboard_group VALUES ('11', 'test11', '6', '2012-10-14 20:20:00', null, '0');
INSERT INTO myboard_group VALUES ('12', 'test12', '7', '2012-10-14 20:20:33', null, '0');
INSERT INTO myboard_group VALUES ('13', 'test13', '7', '2012-10-14 20:20:38', null, '0');
INSERT INTO myboard_group VALUES ('14', 'test14', '10', '2012-10-14 20:21:13', null, '0');
INSERT INTO myboard_group VALUES ('15', 'test15', '10', '2012-10-14 20:21:20', null, '0');

-- ----------------------------
-- Table structure for `operator`
-- ----------------------------
DROP TABLE IF EXISTS `operator`;
CREATE TABLE `operator` (
  `operator_id` int(11) NOT NULL COMMENT '操作员ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `login_name` varchar(64) NOT NULL COMMENT '操作员登录名',
  `password` varchar(100) NOT NULL COMMENT '操作员密码',
  `status` int(2) NOT NULL default '1' COMMENT '操作员状态: 0-初始化,1-正常,2-锁定，3-删除; 默认值:0',
  PRIMARY KEY  (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator
-- ----------------------------
INSERT INTO operator VALUES ('1', '1', 'super', '111111', '1');
INSERT INTO operator VALUES ('2', '1', 'test', '111111', '1');
INSERT INTO operator VALUES ('3', '1', 'asd', '111111', '1');
INSERT INTO operator VALUES ('4', '1', 'dfsf', '111111', '1');
INSERT INTO operator VALUES ('5', '1', 'werw', '111111', '1');
INSERT INTO operator VALUES ('6', '1', 'fdfg', '111111', '1');
INSERT INTO operator VALUES ('7', '1', 'uyt56', '111111', '1');
INSERT INTO operator VALUES ('8', '1', 'kghg', '111111', '1');
INSERT INTO operator VALUES ('9', '1', '453f', '111111', '1');
INSERT INTO operator VALUES ('10', '1', 'dfgd', '111111', '1');
INSERT INTO operator VALUES ('11', '1', 'grtrd', '111111', '1');
INSERT INTO operator VALUES ('12', '1', 'hrtfgd', '111111', '1');
INSERT INTO operator VALUES ('13', '1', 'rsdgf', '111111', '1');
INSERT INTO operator VALUES ('14', '1', 'gdrrf', '111111', '1');
INSERT INTO operator VALUES ('15', '1', 'jrtrr', '111111', '1');

-- ----------------------------
-- Table structure for `operator_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `operator_login_log`;
CREATE TABLE `operator_login_log` (
  `id` int(11) NOT NULL COMMENT '登录日志ID',
  `login_ip` varchar(20) NOT NULL COMMENT '用户IP',
  `operator_id` int(11) NOT NULL COMMENT '操作员ID',
  `login_name` varchar(64) default NULL COMMENT '操作员名称',
  `employee_id` int(11) default NULL COMMENT '员工ID',
  `employee_name` varchar(50) default NULL COMMENT '员工姓名',
  `login_from` varchar(30) default NULL COMMENT '登录来源',
  `login_result` int(8) default NULL COMMENT '登录结果',
  `failure_type` int(8) default NULL COMMENT '失败原因',
  `login_date` datetime default NULL COMMENT '登录时间',
  `description` varchar(15) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for `operator_resources`
-- ----------------------------
DROP TABLE IF EXISTS `operator_resources`;
CREATE TABLE `operator_resources` (
  `id` int(11) NOT NULL COMMENT '角色操作员关联表ID',
  `operator_Id` int(11) NOT NULL COMMENT '操作员ID',
  `resources_id` int(11) NOT NULL COMMENT '资源ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator_resources
-- ----------------------------
INSERT INTO operator_resources VALUES ('1', '1', '4');
INSERT INTO operator_resources VALUES ('2', '1', '5');

-- ----------------------------
-- Table structure for `operator_role`
-- ----------------------------
DROP TABLE IF EXISTS `operator_role`;
CREATE TABLE `operator_role` (
  `id` int(11) NOT NULL COMMENT '角色操作员关联表ID',
  `operator_Id` int(11) NOT NULL COMMENT '操作员ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator_role
-- ----------------------------
INSERT INTO operator_role VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `order_code` varchar(50) default NULL COMMENT '订单编号',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `customer_code` varchar(20) NOT NULL COMMENT '客户编号',
  `customer_name` varchar(50) NOT NULL COMMENT '客户名称',
  `customer_type` int(1) default NULL COMMENT '客户类型',
  `total_price` double(15,2) default NULL COMMENT '总价',
  `create_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后更新时间',
  `remark` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO orders VALUES ('4', '201210241001', '10', '201210191002', '梁十', '3', '101400.00', '2012-10-24 00:21:48', '2012-10-24 00:28:36', '');
INSERT INTO orders VALUES ('5', '201210241002', '10', '201210191002', '梁十', '3', '54600.00', '2012-10-24 00:22:12', null, '');
INSERT INTO orders VALUES ('6', '201210241003', '9', '201210191001', '何九', '2', '93600.00', '2012-10-24 00:28:49', null, '');
INSERT INTO orders VALUES ('7', '201210241004', '9', '201210191001', '何九', '2', '140400.00', '2012-10-24 00:30:02', null, '');
INSERT INTO orders VALUES ('8', '201210241005', '6', '20120919005', '白八', '3', '85800.00', '2012-10-24 00:33:18', null, '');
INSERT INTO orders VALUES ('9', '201210241006', '6', '20120919005', '白八', '3', '148200.00', '2012-10-24 00:33:44', null, '');
INSERT INTO orders VALUES ('10', '201210261001', '10', '201210191002', '梁十', '3', '93600.00', '2012-10-26 22:27:18', null, '');
INSERT INTO orders VALUES ('11', '201411271001', '1', '20120915000', '张三', '1', '432900.00', '2014-11-27 10:50:26', null, '');
INSERT INTO orders VALUES ('12', '201411271002', '1', '20120915000', '张三', '1', '3466320.00', '2014-11-27 15:20:43', null, '');
INSERT INTO orders VALUES ('13', '201411281001', '1', '20120915000', '张三', '1', '2599740.00', '2014-11-28 15:33:13', null, '77777777777777777777');

-- ----------------------------
-- Table structure for `order_product_list`
-- ----------------------------
DROP TABLE IF EXISTS `order_product_list`;
CREATE TABLE `order_product_list` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `order_id` int(11) default NULL COMMENT '订单ID',
  `product_id` int(11) unsigned NOT NULL COMMENT '产品ID',
  `product_name` varchar(50) default NULL COMMENT '产品名称',
  `product_price` double(15,2) default NULL COMMENT '单价',
  `product_count` int(5) default NULL COMMENT '商品数量',
  `total_price` double(15,2) NOT NULL COMMENT '总价',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='订单产品列表';

-- ----------------------------
-- Records of order_product_list
-- ----------------------------
INSERT INTO order_product_list VALUES ('4', '4', '1', '延城风姿', '780.00', '130', '101400.00');
INSERT INTO order_product_list VALUES ('5', '5', '1', '延城风姿', '780.00', '70', '54600.00');
INSERT INTO order_product_list VALUES ('6', '6', '1', '延城风姿', '780.00', '120', '93600.00');
INSERT INTO order_product_list VALUES ('7', '7', '1', '延城风姿', '780.00', '180', '140400.00');
INSERT INTO order_product_list VALUES ('8', '8', '1', '延城风姿', '780.00', '110', '85800.00');
INSERT INTO order_product_list VALUES ('9', '9', '1', '延城风姿', '780.00', '190', '148200.00');
INSERT INTO order_product_list VALUES ('10', '10', '1', '延城风姿', '780.00', '120', '93600.00');
INSERT INTO order_product_list VALUES ('11', '11', '1', '延城风姿', '780.00', '555', '432900.00');
INSERT INTO order_product_list VALUES ('12', '12', '1', '延城风姿', '780.00', '4444', '3466320.00');
INSERT INTO order_product_list VALUES ('13', '13', '1', '延城风姿', '780.00', '3333', '2599740.00');

-- ----------------------------
-- Table structure for `organization`
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `organization_id` int(11) NOT NULL COMMENT '机构ID',
  `organization_code` varchar(12) NOT NULL COMMENT '机构代码',
  `organization_name` varchar(64) NOT NULL COMMENT '机构名称',
  `organization_level` int(2) NOT NULL COMMENT '机构等级',
  `parent_organization_Id` int(11) NOT NULL COMMENT '父机构ID',
  `organization_id_sequence` varchar(512) default NULL COMMENT '机构编号序列',
  `organization_name_sequence` varchar(1024) default NULL COMMENT '机构名称序列',
  `organization_type` int(2) default NULL COMMENT '机构类型: 1-集团公司, 2-公司, 3-一级部门, 4-二级部门, 5-三级部门; 默认:2',
  `organization_address` varchar(256) default NULL COMMENT '机构地址',
  `organization_zip_code` varchar(15) default NULL COMMENT '机构邮政编码',
  `manager_id` int(11) default NULL COMMENT '机构经理编号',
  `link_man` varchar(30) default NULL COMMENT '联系人',
  `link_man_tel` varchar(20) default NULL COMMENT '联系人电话',
  `link_man_email` varchar(128) default NULL COMMENT '联系人电子邮件',
  `web_url` varchar(512) default NULL COMMENT '网站URL',
  `memo` varchar(512) default NULL COMMENT '备注',
  PRIMARY KEY  (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO organization VALUES ('1', '10001', '快钱', '1', '0', '1', '快钱', '2', '房地大厦', '200120', '1', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('2', '10002', '财务部', '2', '1', '1,2', '快钱,PMO', '3', '', '200120', '3', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('3', '10003', '行政部', '2', '1', '1,3', '快钱,行政部', '3', '', '200120', '2', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('4', '10004', 'PMD', '2', '1', '1,4', '快钱,人事部', '3', '', '200120', '10', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('5', '10005', '人事部', '2', '1', '1,5', '快钱,财务部', '3', '', '200120', '4', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('6', '10006', '测试部', '2', '1', '1,6', '快钱,架构组', '3', '', '200120', '7', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('7', '10007', 'EFS', '2', '1', '1,7', '快钱,EFS', '3', '', '200120', '5', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('8', '10008', 'NBIZ', '2', '1', '1,8', '快钱,NBIZ', '3', '', '200120', '19', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('9', '10009', 'TB', '2', '1', '1,9', '快钱,TB', '3', '', '200120', '31', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('10', '100010', '架构组', '2', '1', '1,10', '快钱,测试部门', '3', '', '200120', '71', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('11', '100011', 'SCM', '2', '1', '1,11', '快钱,SCM', '3', '', '200120', '98', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('12', '100012', 'UIUE', '2', '1', '1,12', '快钱,UIUE', '3', '', '200120', '16', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('13', '100013', 'APP', '3', '2', '1,2,13', '快钱,PMO,APP', '4', '', '200120', '11', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('14', '100014', 'DDP', '3', '2', '1,2,14', '快钱,PMO,APP', '4', '', '200120', '12', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('15', '100015', 'FI', '3', '2', '1,2,15', '快钱,PMO,DDP', '4', '', '200120', '15', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('16', '100016', 'FO', '3', '2', '1,2,16', '快钱,PMO,FI', '4', '', '200120', '18', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('17', '100017', 'FSC', '3', '2', '1,2,17', '快钱,PMO,FSC', '4', '', '200120', '43', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('18', '100018', 'EFS', '3', '2', '1,2,18', '快钱,PMO,INF', '4', '', '200120', '35', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('19', '100019', 'MA', '3', '2', '1,2,19', '快钱,PMO,MA', '4', '', '200120', '27', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');
INSERT INTO organization VALUES ('20', '100020', 'PE', '3', '2', '1,2,20', '快钱,PMO,PE', '4', '', '200120', '53', '张三', '13680025478', 'test@99bill.com', 'www.99bill.com', '快钱信息技术有限公司，行业内领先的第三方支付公司');

-- ----------------------------
-- Table structure for `payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `payment_order`;
CREATE TABLE `payment_order` (
  `order_sequence_id` int(30) NOT NULL COMMENT '流水号',
  `order_id` varchar(50) default NULL COMMENT '订单号(由调用服务端生成的，多应用交互时使用)',
  `submit_acct_code` int(25) default NULL COMMENT '订单提交人',
  `order_code` int(3) default NULL COMMENT '订单类型(1-充值;2-销售;3-冲正)',
  `pay_method` int(2) default NULL COMMENT '订单支付方式(1-现金;2-会员卡;3-信用卡;4-借记卡)',
  `order_amount` int(15) default NULL COMMENT '订单金额',
  `discount_amount` int(15) default NULL COMMENT '优惠金额',
  `coupon_number` varchar(50) default NULL COMMENT '优惠券编号',
  `loyal_card_number` varchar(50) default NULL COMMENT '优惠卡号',
  `payer_acct_code` int(25) default NULL COMMENT '付款方帐户(付款方帐户和机构号必须有一个不为空)',
  `payer_acct_type` int(2) default NULL COMMENT '付款方帐户类型',
  `payer_org_code` int(2) default NULL COMMENT '付款方机构类型',
  `payer_org_type` int(2) default NULL COMMENT '付款方机构类型',
  `payee_acct_code` int(25) default NULL COMMENT '收款方帐户(收款方帐户和机构号必须有一个不为空)',
  `payee_acct_type` int(2) default NULL COMMENT '收款方帐户类型',
  `payee_org_code` int(2) default NULL COMMENT '收款方机构代号',
  `payee_org_type` int(2) default NULL COMMENT '收款方机构类型',
  `order_time` date default NULL COMMENT '订单提交时间',
  `order_status` int(2) default NULL COMMENT '订单状态',
  `last_update_time` date default NULL COMMENT '订单最后更新时间',
  `receiving_type` varchar(50) default NULL COMMENT '订单收货类型;1-当场;2-快递;3-API;4-WEB',
  `interface_version` varchar(20) default NULL COMMENT '接口版本(多应用接口版本号)',
  `memo` varchar(1024) default NULL COMMENT '备注',
  `related_sequence_id` varchar(50) default NULL COMMENT '关联订单流水号',
  `related_type` int(2) default NULL COMMENT '1-退款;2-冲正',
  `payee_identity` varchar(50) default NULL COMMENT '收款人的标识',
  `order_digest` varchar(1024) default NULL COMMENT '定单摘要',
  `order_origin` varchar(1024) default NULL COMMENT '订单来源 1-线下;2-WEB;3-API;4-WAP',
  `is_reversed` int(1) default NULL COMMENT '是否已冲正 0:未冲正,1:已冲正',
  `ip` varchar(20) default NULL COMMENT '提交订单的ip地址',
  `reference_number` varchar(30) default NULL COMMENT '关联业务编号，用于存放跟订单相关的业务编号，如银行账户验证中的账户id',
  `merchant_order_time` date default NULL COMMENT '商户订单时间',
  `version` int(10) default NULL COMMENT '用于控制并发',
  `currency_code` varchar(10) default NULL COMMENT '交易中金额使用的币种',
  `payer_display_name` varchar(20) default NULL COMMENT '付款方显示名称',
  `payee_display_name` varchar(20) default NULL COMMENT '收款方显示名称',
  PRIMARY KEY  (`order_sequence_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of payment_order
-- ----------------------------

-- ----------------------------
-- Table structure for `position`
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `position_id` int(11) NOT NULL COMMENT '职位ID',
  `position_name` varchar(30) default NULL COMMENT '职位名称',
  `meno` varchar(1024) default NULL COMMENT '备注',
  `creationDate` datetime default NULL COMMENT '创建时间',
  `creator` int(11) default NULL COMMENT '创建者',
  PRIMARY KEY  (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO position VALUES ('1', 'CEO', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('2', '财务总监', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('3', '行政总监', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('4', '技术总监', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('5', '人事总监', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('6', '测试总监', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('7', '财务专员', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('8', '行政专员', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('9', '项目经理', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('10', '助理项目经理', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('11', '高级技术经理', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('12', '技术经理', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('13', '架构师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('14', '助理架构师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('15', '资深软件工程师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('16', '高级软件工程师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('17', '中级软件工程师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('18', '初级软件工程师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('19', '实习软件工程师', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('20', '高级测试', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('21', '中级测试', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('22', '初级测试', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('23', 'SQA', null, '2011-11-20 16:24:58', '2');
INSERT INTO position VALUES ('24', '配置管理员', null, '2011-11-20 16:24:58', '2');

-- ----------------------------
-- Table structure for `pricing_strategy`
-- ----------------------------
DROP TABLE IF EXISTS `pricing_strategy`;
CREATE TABLE `pricing_strategy` (
  `pricing_strategy_id` int(11) NOT NULL COMMENT '策略ID',
  `pricing_strategy_name` varchar(50) NOT NULL COMMENT '策略名称',
  `member_id` int(11) default NULL COMMENT '会员ID',
  `member_type` int(2) default NULL COMMENT '会员类型：1-个人，2-企业',
  `member_level` int(2) default NULL COMMENT '会员等级',
  `commodity_id` int(11) default NULL COMMENT '商品ID',
  `status` int(2) default NULL COMMENT '策略状态：1-新建待审核,2-重审待审核，3-审核通过,4-审核拒绝，5-作废',
  `type` int(2) default NULL COMMENT '策略类型： 1-客户,2-客户等级,3-产品,4-全局',
  `model` int(1) default '1' COMMENT '策略模式： 1-普通,2-追加,3-折上折',
  `valid_time` datetime default NULL COMMENT '生效时间',
  `invalid_time` datetime default NULL COMMENT '失效时间',
  `creation_time` datetime NOT NULL COMMENT '创建时间',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `modify_time` datetime default NULL COMMENT '修改时间',
  `modifier` int(11) default NULL COMMENT '修改人',
  `review_time` datetime default NULL COMMENT '审核时间',
  `reviewer` int(11) default NULL COMMENT '审核人',
  `priority` int(3) default NULL COMMENT '优先级',
  `remark` varchar(1024) default NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pricing_strategy
-- ----------------------------

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `product_name` varchar(20) NOT NULL COMMENT '产品名称',
  `product_expand_name` varchar(512) default NULL COMMENT '商品扩展名称',
  `category_id` int(11) default NULL COMMENT '类别ID',
  `product_total_count` int(11) default NULL COMMENT '产品数量',
  `basic_price` float(11,2) default NULL COMMENT '基本价格',
  `description` varchar(1024) default NULL COMMENT '产品描述',
  `merchant_product_code` varchar(256) default NULL COMMENT '商家产品编码',
  PRIMARY KEY  (`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO product VALUES ('1', '延城风姿', '延城风姿', '1', '99999', '780.00', '基础套装', '342353452');
INSERT INTO product VALUES ('2', 'asdfasdf', 'sfgsdfgsdf', '1', '23434', '222.00', 'ttttttttttttttttttttt', '352345234');
INSERT INTO product VALUES ('3', '222222', '3', '1', null, null, '', '');
INSERT INTO product VALUES ('4', '222222', '', '-1', null, null, '', '');

-- ----------------------------
-- Table structure for `product_item`
-- ----------------------------
DROP TABLE IF EXISTS `product_item`;
CREATE TABLE `product_item` (
  `product_item_id` int(11) NOT NULL COMMENT '特征量ID',
  `product_item_name` varchar(256) default NULL COMMENT '特征量名称',
  `product_item_value` varchar(256) default NULL COMMENT '特征量值',
  `category_id` int(11) default NULL COMMENT '类别ID',
  `parent_product_item_id` int(11) default NULL COMMENT '父特征量ID',
  `status` int(2) default NULL COMMENT '状态',
  `sort_id` int(4) default NULL COMMENT '排序',
  PRIMARY KEY  (`product_item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_item
-- ----------------------------

-- ----------------------------
-- Table structure for `product_item_list`
-- ----------------------------
DROP TABLE IF EXISTS `product_item_list`;
CREATE TABLE `product_item_list` (
  `product_item_list_id` int(11) NOT NULL COMMENT '商品特征量ID',
  `product_id` int(11) default NULL COMMENT '商品ID',
  `product_item_id` int(11) default NULL COMMENT '特征量ID',
  PRIMARY KEY  (`product_item_list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_item_list
-- ----------------------------

-- ----------------------------
-- Table structure for `product_sku`
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
  `product_sku_id` int(11) NOT NULL COMMENT '商品SKUID',
  `product_count` int(11) default NULL COMMENT '商品数量',
  `product_price_id` float(11,2) default NULL COMMENT '商品价格ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  PRIMARY KEY  (`product_sku_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_sku
-- ----------------------------

-- ----------------------------
-- Table structure for `rebates`
-- ----------------------------
DROP TABLE IF EXISTS `rebates`;
CREATE TABLE `rebates` (
  `rebates_id` int(11) NOT NULL COMMENT '返利ID',
  `customer_id` int(11) default NULL COMMENT '客户ID',
  `customer_code` varchar(20) default NULL COMMENT '客户编号',
  `customer_name` varchar(30) default NULL COMMENT '客户名称',
  `customer_type` int(1) default NULL COMMENT '客户类型',
  `count` int(11) default NULL COMMENT '数量',
  `rebates_type` int(1) default NULL COMMENT '返利类型：1-直接销售；2-分享销售',
  `create_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后更新时间',
  PRIMARY KEY  (`rebates_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rebates
-- ----------------------------
INSERT INTO rebates VALUES ('5', '10', '201210191002', '梁十', '3', '125', null, '2012-10-24 00:22:02', '2012-10-26 22:41:37');
INSERT INTO rebates VALUES ('6', '3', '20120919002', '王五', '1', '120', null, '2012-10-24 00:22:02', '2012-10-26 22:41:38');
INSERT INTO rebates VALUES ('7', '1', '20120915000', '张三', '1', '7777', null, '2012-10-24 00:22:02', '2014-11-28 15:33:33');
INSERT INTO rebates VALUES ('8', '10', '201210191002', '梁十', '3', '0', null, '2012-10-24 00:22:25', null);
INSERT INTO rebates VALUES ('9', '9', '201210191001', '何九', '2', '0', null, '2012-10-24 00:29:40', null);
INSERT INTO rebates VALUES ('10', '3', '20120919002', '王五', '1', '0', null, '2012-10-24 00:29:40', '2012-10-24 00:30:18');
INSERT INTO rebates VALUES ('11', '9', '201210191001', '何九', '2', '0', null, '2012-10-24 00:30:18', null);
INSERT INTO rebates VALUES ('12', '6', '20120919005', '白八', '3', '250', null, '2012-10-24 00:33:36', '2012-11-01 15:21:04');
INSERT INTO rebates VALUES ('13', '4', '20120919003', '马六', '1', '550', null, '2012-10-24 00:33:36', '2012-11-01 15:21:04');
INSERT INTO rebates VALUES ('14', '2', '20120919001', '李四', '1', '550', null, '2012-10-24 00:33:36', '2012-11-01 15:21:04');
INSERT INTO rebates VALUES ('15', '1', '20120915000', '张三', '1', '555', null, '2012-10-24 00:33:36', '2014-11-27 10:51:44');
INSERT INTO rebates VALUES ('16', '6', '20120919005', '白八', '3', '0', null, '2012-10-24 00:34:00', null);
INSERT INTO rebates VALUES ('17', '11', '201211011001', '性丽娟', '2', '0', null, '2012-11-01 15:19:01', null);
INSERT INTO rebates VALUES ('18', '11', '201211011001', '性丽娟', '2', '10', null, '2012-11-01 15:21:04', null);

-- ----------------------------
-- Table structure for `resources`
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `resource_id` int(11) NOT NULL,
  `resource_code` varchar(64) NOT NULL,
  `resource_name` varchar(30) default NULL,
  `resource_type` int(2) default NULL,
  `parent_resource_id` int(11) default NULL,
  `application_id` int(11) default NULL,
  `application_type` int(2) default NULL,
  `status` int(2) default NULL,
  `url` varchar(512) default NULL,
  `target` varchar(20) default NULL,
  `rel` varchar(50) default NULL,
  `description` varchar(1024) default NULL,
  `create_time` datetime default NULL,
  `last_modify_time` datetime default NULL,
  PRIMARY KEY  (`resource_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO resources VALUES ('2', '101', '成员管理', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('4', '103', '积分管理', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('5', '104', '系统审核', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('6', '110', '权限管理1', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('7', '111', '成员管理1', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('8', '112', '系统管理1', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('9', '113', '积分管理1', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);
INSERT INTO resources VALUES ('10', '114', '系统审核1', null, null, null, null, '1', null, null, null, null, '2013-01-07 22:12:40', null);

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `role_code` varchar(64) NOT NULL COMMENT '角色编号',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `role_group_id` int(11) default NULL COMMENT '角色组ID',
  `role_type` int(2) default NULL COMMENT '角色类型',
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('1', '100', '超级管理员', null, null);
INSERT INTO role VALUES ('2', '110', '部门经理', null, null);

-- ----------------------------
-- Table structure for `role_resources`
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `id` int(11) NOT NULL COMMENT '角色操作员关联表ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_Id` int(11) NOT NULL COMMENT '资源ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resources
-- ----------------------------
INSERT INTO role_resources VALUES ('1', '1', '1');
INSERT INTO role_resources VALUES ('2', '1', '2');
INSERT INTO role_resources VALUES ('3', '1', '3');

-- ----------------------------
-- Table structure for `spread`
-- ----------------------------
DROP TABLE IF EXISTS `spread`;
CREATE TABLE `spread` (
  `id` int(11) NOT NULL auto_increment,
  `request_url` varchar(255) default NULL,
  `channel` varchar(11) default NULL COMMENT '渠道',
  `spread_id` int(11) default NULL COMMENT '推广人',
  `code_name` varchar(50) default NULL COMMENT '二维码名称(命名规则：推广人id-渠道的key)',
  `mark` varchar(200) NOT NULL COMMENT '备注',
  `code_path` varchar(50) default NULL COMMENT '二维码图片存储位置',
  `logo_name` varchar(50) default NULL COMMENT 'logo名称',
  `logo_path` varchar(28) default NULL COMMENT 'logo目录',
  `logo_width` varchar(4) default NULL,
  `logo_heigth` varchar(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of spread
-- ----------------------------
INSERT INTO spread VALUES ('1', 'http://www.honiee.com', '101', '100011', '101-100011.jpg', 'www', 'ui/code', 'favicon.ico', 'ui/', null, null);
INSERT INTO spread VALUES ('2', 'http://www.honiee.com', '101', '100011', '101-100011.jpg', 'www', 'ui/code', 'favicon.ico', 'ui', null, null);
INSERT INTO spread VALUES ('3', 'http://www.honiee.com', '102', '122112', '102-122112.jpg', '', 'ui/code', 'favicon.ico', 'ui', null, null);
INSERT INTO spread VALUES ('4', 'http://www.honiee.com', '102', '122112', '102-122112.jpg', '', 'ui/code', 'favicon.ico', 'ui', null, null);

-- ----------------------------
-- Table structure for `staff`
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL COMMENT '员工ID',
  `staff_code` varchar(12) NOT NULL COMMENT '员工编号',
  `staff_name` varchar(50) NOT NULL COMMENT '员工名称',
  `organization_id` int(11) default NULL COMMENT '机构编号',
  `position_id` int(11) default NULL,
  `office_tel` varchar(15) default NULL COMMENT '办公电话',
  `office_phone` varchar(15) default NULL COMMENT '办公手机号',
  `office_email` varchar(128) default NULL COMMENT '办公邮箱',
  `home_address` varchar(128) default NULL COMMENT '家庭地址',
  `zip_code` varchar(10) default NULL COMMENT '邮政编码',
  `personal_tel` varchar(15) default NULL COMMENT '个人电话',
  `personal_phone` varchar(15) default NULL COMMENT '个人电话2',
  `personal_email` varchar(128) default NULL COMMENT '个人邮箱',
  `id_card` varchar(30) default NULL COMMENT '身份证',
  `birthday` datetime default NULL COMMENT '生日',
  `gender` int(2) default NULL COMMENT '性别:0.未知;1.男;2.女',
  `education` int(2) default NULL COMMENT '学历:0-未知,1-高中以下,2-专科,3-本科,4-研究生,5-博士,6-博士后',
  `hobby` varchar(400) default NULL COMMENT '兴趣爱好',
  `specialty` varchar(400) default NULL COMMENT '专长，特长',
  `training` varchar(800) default NULL COMMENT '培训经历',
  `regedit_date` datetime default NULL COMMENT '注册时间',
  `create_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后修改时间',
  PRIMARY KEY  (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO staff VALUES ('1', '1000001', '关一', '1', '1', '021-12345678', '13680025478', 'guoyi@99bill.com', '', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', null, '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('2', '1000002', '李二', null, null, '021-12345678', '13680025478', 'lisi@99bill.com', '2222222222222222233333333333', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '2', '3', '4444', '4444', '3333333333333333333333333333333333', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('3', '1000003', '王三', '3', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('4', '1000004', '马666', null, null, '021-12345678', '13680025478', 'maliu@99bill.com', '555555555555555', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '3', '3', '4444', '55555', '55777777777777777777777', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('5', '1000005', '路五', '5', null, '021-12345678', '13680025478', 'luqi@99bill.com', '', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('6', '1000006', '胡六', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('7', '1000007', '杜七', '19', null, '021-12345678', '13680025478', 'hello@99bill.com', '', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('8', '1000008', '张八', '19', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('9', '1000009', '周九', '19', null, '021-12345678', '13680025478', 'test@99bill.com', '', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('10', '1000010', '赵十', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('11', '1000011', '关一1', '15', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('12', '1000012', '李二2', '15', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('13', '1000013', '王三3', '15', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('14', '1000014', '马四4', '10', null, '021-12345678', '13680025478', 'maliu@99bill.com', '', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', null, '3', '', '', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('15', '1000015', '路五5', '9', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('16', '1000016', '胡六6', '9', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('17', '1000017', '杜七7', '9', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('18', '1000018', '张八8', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('19', '1000019', '周九9', '13', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('20', '1000020', '赵十10', '13', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('21', '1000021', '关一11', '13', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('22', '1000022', '李二12', '13', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('23', '1000023', '王三13', '13', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('24', '1000024', '马四14', '10', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('25', '1000025', '路五15', '16', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('26', '1000026', '胡六16', '16', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('27', '1000027', '杜七17', '16', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('28', '1000028', '张八18', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('29', '1000029', '周九19', '17', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('30', '1000030', '赵十20', '17', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('31', '1000031', '关一21', '10', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('32', '1000032', '李二22', '17', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('33', '1000033', '王三23', '17', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('34', '1000034', '马四24', '10', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('35', '1000035', '路五25', '18', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('36', '1000036', '胡六26', '18', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('37', '1000037', '杜七27', '10', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('38', '1000038', '张八28', '20', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('39', '1000039', '周九29', '20', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('40', '1000040', '赵十30', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('41', '1000041', '关一31', '15', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('42', '1000042', '李二32', '15', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('43', '1000043', '王三33', '10', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('44', '1000044', '马四34', '6', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('45', '1000045', '路五35', '20', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('46', '1000046', '胡六36', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('47', '1000047', '杜七37', '15', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('48', '1000048', '张八38', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('49', '1000049', '周九39', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('50', '1000050', '赵十40', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('51', '1000051', '关一41', '19', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('52', '1000052', '李二42', '19', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('53', '1000053', '王三43', '19', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('54', '1000054', '马四44', '19', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('55', '1000055', '路五45', '19', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('56', '1000056', '胡六46', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('57', '1000057', '杜七47', '6', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('58', '1000058', '张八48', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('59', '1000059', '周九49', '19', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('60', '1000060', '赵十50', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('61', '1000061', '关一51', '13', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('62', '1000062', '李二52', '13', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('63', '1000063', '王三53', '13', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('64', '1000064', '马四54', '13', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('65', '1000065', '路五55', '13', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('66', '1000066', '胡六56', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('67', '1000067', '杜七57', '6', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('68', '1000068', '张八58', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('69', '1000069', '周九59', '13', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('70', '1000070', '赵十60', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('71', '1000071', '关一61', '14', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('72', '1000072', '李二62', '14', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('73', '1000073', '王三63', '14', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('74', '1000074', '马四64', '14', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('75', '1000075', '路五65', '14', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('76', '1000076', '胡六66', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('77', '1000077', '杜七67', '6', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('78', '1000078', '张八68', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('79', '1000079', '周九69', '14', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('80', '1000080', '赵十70', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('81', '1000081', '关一71', '15', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('82', '1000082', '李二72', '15', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('83', '1000083', '王三73', '15', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('84', '1000084', '马四74', '15', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('85', '1000085', '路五75', '15', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('86', '1000086', '胡六76', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('87', '1000087', '杜七77', '6', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('88', '1000088', '张八78', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('89', '1000089', '周九79', '15', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('90', '1000090', '赵十80', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('91', '1000091', '关一81', '16', null, '021-12345678', '13680025478', 'guoyi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'guogyi@99bill.com', '370206124502102456', '2011-11-20 02:48:18', '1', '3', '', '', '', '2011-11-20 02:48:18', '2011-11-20 02:48:18', '2011-11-20 02:48:18');
INSERT INTO staff VALUES ('92', '1000092', '李二82', '16', null, '021-12345678', '13680025478', 'lisi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('93', '1000093', '王三83', '16', null, '021-12345678', '13680025478', 'wangwu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('94', '1000094', '马四84', '16', null, '021-12345678', '13680025478', 'maliu@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:05', '1', '3', '', '', '', '2011-11-14 21:05:05', '2011-11-14 21:05:05', '2011-11-14 21:05:05');
INSERT INTO staff VALUES ('95', '1000095', '路五85', '16', null, '021-12345678', '13680025478', 'luqi@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('96', '1000096', '胡六86', '6', null, '021-12345678', '13680025478', 'huba@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('97', '1000097', '杜七87', '6', null, '021-12345678', '13680025478', 'hello@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('98', '1000098', '张八88', '6', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('99', '1000099', '周九89', '16', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('100', '10000100', '赵十90', '10', null, '021-12345678', '13680025478', 'test@99bill.com', '家庭地址', '200000', '021-12345678', '13680025478', 'test@99bill.com', '370206124502102456', '2011-11-14 21:05:06', '1', '3', '', '', '', '2011-11-14 21:05:06', '2011-11-14 21:05:06', '2011-11-14 21:05:06');
INSERT INTO staff VALUES ('101', '201411271001', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 14:58:02', null);
INSERT INTO staff VALUES ('102', '201411271002', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 15:03:22', null);
INSERT INTO staff VALUES ('103', '201411271003', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 15:05:11', null);
INSERT INTO staff VALUES ('104', '201411271004', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 15:11:12', null);
INSERT INTO staff VALUES ('105', '201411271005', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 15:13:36', null);
INSERT INTO staff VALUES ('106', '201411271006', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 15:40:44', null);
INSERT INTO staff VALUES ('107', '201411271007', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 15:45:43', null);
INSERT INTO staff VALUES ('108', '201411271008', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 16:19:09', null);
INSERT INTO staff VALUES ('109', '201411271009', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 16:54:02', null);
INSERT INTO staff VALUES ('110', '201411271010', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 17:04:02', null);
INSERT INTO staff VALUES ('117', '201411271017', '小明', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 20:50:59', null);
INSERT INTO staff VALUES ('118', '201411271018', '小林', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 21:28:02', null);
INSERT INTO staff VALUES ('119', '201411271019', '小林', null, null, '', '', '', '', '', '', '', '', '', null, null, null, '', '', '', null, '2014-11-27 21:30:45', null);

-- ----------------------------
-- Table structure for `treenodes`
-- ----------------------------
DROP TABLE IF EXISTS `treenodes`;
CREATE TABLE `treenodes` (
  `id` int(11) NOT NULL,
  `nodename` varchar(20) default NULL,
  `pid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treenodes
-- ----------------------------
INSERT INTO treenodes VALUES ('1', 'A', '0');
INSERT INTO treenodes VALUES ('2', 'B', '1');
INSERT INTO treenodes VALUES ('3', 'C', '1');
INSERT INTO treenodes VALUES ('4', 'D', '2');
INSERT INTO treenodes VALUES ('5', 'E', '2');
INSERT INTO treenodes VALUES ('6', 'F', '3');
INSERT INTO treenodes VALUES ('7', 'G', '6');
INSERT INTO treenodes VALUES ('8', 'H', '0');
INSERT INTO treenodes VALUES ('9', 'I', '8');
INSERT INTO treenodes VALUES ('10', 'J', '8');
INSERT INTO treenodes VALUES ('11', 'K', '8');
INSERT INTO treenodes VALUES ('12', 'L', '9');
INSERT INTO treenodes VALUES ('13', 'M', '9');
INSERT INTO treenodes VALUES ('14', 'N', '12');
INSERT INTO treenodes VALUES ('15', 'O', '12');
INSERT INTO treenodes VALUES ('16', 'P', '15');
INSERT INTO treenodes VALUES ('17', 'Q', '15');

-- ----------------------------
-- Table structure for `verification`
-- ----------------------------
DROP TABLE IF EXISTS `verification`;
CREATE TABLE `verification` (
  `id` int(11) default NULL COMMENT 'id',
  `count` varchar(20) default NULL COMMENT '数量',
  `init_time` varchar(20) default NULL COMMENT '初始化时间',
  `request_code` varchar(50) default NULL COMMENT '请求编码',
  `verification_code` varchar(50) default NULL COMMENT '授权证码'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of verification
-- ----------------------------

-- ----------------------------
-- Table structure for `wage`
-- ----------------------------
DROP TABLE IF EXISTS `wage`;
CREATE TABLE `wage` (
  `wage_id` int(11) NOT NULL COMMENT '返利ID',
  `customer_id` int(11) default NULL COMMENT '客户ID',
  `customer_code` varchar(20) default NULL COMMENT '客户编号',
  `customer_name` varchar(30) default NULL COMMENT '客户名称',
  `phone` varchar(50) default NULL,
  `id_card_number` varchar(50) default NULL COMMENT '身份证',
  `bank_type` varchar(20) default NULL COMMENT '开户行',
  `bank_account` varchar(50) default NULL COMMENT '银行账号',
  `salary` float(11,2) default NULL COMMENT '薪水',
  `create_time` datetime default NULL COMMENT '创建时间',
  `last_modify_time` datetime default NULL COMMENT '最后更新时间',
  PRIMARY KEY  (`wage_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wage
-- ----------------------------
INSERT INTO wage VALUES ('2', '10', '201210191002', '梁十', '32014568', '36524295323651242358', '农行', '3652165224522553', '23400.00', '2012-10-24 00:22:59', null);
INSERT INTO wage VALUES ('3', '9', '201210191001', '何九', '32154755', '36524295323651242358', '农行', '36520125463015', '23400.00', '2012-10-24 00:31:36', null);
INSERT INTO wage VALUES ('4', '3', '20120919002', '王五', '35412547', '36524295323651242357', '农行', '3652165224522553', '23400.00', '2012-10-24 00:31:44', null);
INSERT INTO wage VALUES ('5', '1', '20120915000', '张三', '36521452', '365425845625141236', '农行', '36520125463015', '23400.00', '2012-10-24 00:37:43', null);
INSERT INTO wage VALUES ('6', '6', '20120919005', '白八', '32154755', '36524295323651242359', '农行', '3652165224522553', '23400.00', '2012-10-24 00:37:43', null);
INSERT INTO wage VALUES ('7', '1', '20120915000', '张三', '36521452', '365425845625141236', '农行', '36520125463015', '23400.00', '2012-11-01 15:21:50', null);
INSERT INTO wage VALUES ('8', '11', '201211011001', '性丽娟', '25641545', '4651311561132123', '农行', '36520125463015', '37440.00', '2012-11-01 15:21:50', null);

-- ----------------------------
-- Procedure structure for `findLChild`
-- ----------------------------
DROP PROCEDURE IF EXISTS `findLChild`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `findLChild`(root_id bigint(20))
BEGIN
 create temporary table if not exists tmp_table (id bigint(20),pid bigint(20));
 insert into tmp_table values(root_id,0);
 -- SET@@max_sp_recursion_depth=99; -- 不知何用
 call iterative(root_id);
 select ic.id,ic.catid,ic.parentId from item_category ic,tmp_table tt where ic.id=tt.id;
 drop temporary table if exists tmp_table;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `getGroupChildren`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getGroupChildren`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getGroupChildren`(IN parent_idd INT)
BEGIN
	DECLARE
		lev INT;

DECLARE
	child_count INT;

SET lev = 1;

SET child_count = 0;

DROP TABLE
IF EXISTS group_tmp1;

CREATE TEMPORARY TABLE group_tmp1 (
	mygroup_id INT (11),
	mygroup_name VARCHAR (64),
	parent_id INT (11),
	`create_time` datetime,
	`comment` VARCHAR (256),
	levv INT
) engine=memory;

INSERT INTO group_tmp1 SELECT
	mygroup_id,
	mygroup_name,
	parent_id,
	`create_time`,
	`comment`,
	1
FROM
	`myboard_group`
WHERE
	parent_id = parent_idd;

SELECT
	count(1) INTO child_count
FROM
	`myboard_group`
WHERE
	parent_id = parent_idd;

WHILE child_count > 0 DO

SET lev = lev + 1;

INSERT INTO group_tmp1 SELECT
	t.mygroup_id,
	t.mygroup_name,
	t.parent_id,
	t.`create_time`,
	t.`comment`,
	levv
FROM
	`myboard_group` t
JOIN group_tmp1 a ON t.parent_id = a.mygroup_id
AND a.levv = lev - 1;

SELECT
	count(1) INTO child_count
FROM
	`myboard_group` t
JOIN group_tmp1 a ON t.parent_id = a.mygroup_id
AND a.levv = lev - 1;

END
WHILE;

SELECT
	*
FROM
	group_tmp1;

DROP TEMPORARY TABLE group_tmp1;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `iterative`
-- ----------------------------
DROP PROCEDURE IF EXISTS `iterative`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `iterative`(root_id bigint(20))
BEGIN
 declare tid bigint(20) default -1;
 declare cur1 CURSOR FOR select id from item_category where parentId=root_id;
 declare CONTINUE HANDLER FOR SQLSTATE '02000' SET tid = null;
 -- if layer > 0 then    -- 控制查询的层级数量
  OPEN cur1;
  FETCH cur1 INTO tid;
  WHILE(tid is not null)
   DO
    insert into tmp_table values(tid,root_id);    
    call iterative(tid); -- call iterative(tid,layer-1);
    FETCH cur1 INTO tid;
  END WHILE;
 -- end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `getChildLst`
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN 
       DECLARE sTemp VARCHAR(1000); 
       DECLARE sTempChd VARCHAR(1000); 
     
       SET sTemp = '$'; 
       SET sTempChd =cast(rootId as CHAR); 
    
       WHILE sTempChd is not null DO 
         SET sTemp = concat(sTemp,',',sTempChd); 
         SELECT group_concat(id) INTO sTempChd FROM treeNodes where FIND_IN_SET(pid,sTempChd)>0; 
       END WHILE; 
       RETURN sTemp; 
     END
;;
DELIMITER ;
