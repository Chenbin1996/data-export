/*
Navicat MySQL Data Transfer

Source Server         : 47.96
Source Server Version : 50725
Source Host           : 47.96.226.140:3306
Source Database       : data-export

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-30 14:36:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ed_dbtype
-- ----------------------------
DROP TABLE IF EXISTS `ed_dbtype`;
CREATE TABLE `ed_dbtype` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '数据库名,如MYSQL、ORACLE等',
  `driver_class` varchar(50) NOT NULL COMMENT '数据库驱动',
  `port` int(8) DEFAULT NULL COMMENT 'int',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name-only` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据库类型表';

-- ----------------------------
-- Records of ed_dbtype
-- ----------------------------
INSERT INTO `ed_dbtype` VALUES ('2c1131c881d811e9bf8674d435bc8d5b', 'MySql', 'com.mysql.jdbc.Driver', '3306', 'MySql数据库');

-- ----------------------------
-- Table structure for ed_field_generator
-- ----------------------------
DROP TABLE IF EXISTS `ed_field_generator`;
CREATE TABLE `ed_field_generator` (
  `Id` varchar(32) NOT NULL,
  `tableField_id` varchar(32) DEFAULT NULL,
  `tool_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_field_generator
-- ----------------------------

-- ----------------------------
-- Table structure for ed_field_tools
-- ----------------------------
DROP TABLE IF EXISTS `ed_field_tools`;
CREATE TABLE `ed_field_tools` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `tool_id` varchar(32) NOT NULL COMMENT '工具id',
  `type` int(1) NOT NULL COMMENT '校验或转换失败类型:1.警告,2.错误',
  `field_id` varchar(32) DEFAULT NULL COMMENT '模板字段id',
  `other` varchar(255) DEFAULT NULL COMMENT '存放关于字段信息，如长度校验器可放长度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字段工具表，包含字段对应的规则校验器、字段转换器';

-- ----------------------------
-- Records of ed_field_tools
-- ----------------------------

-- ----------------------------
-- Table structure for ed_field_value
-- ----------------------------
DROP TABLE IF EXISTS `ed_field_value`;
CREATE TABLE `ed_field_value` (
  `Id` varchar(32) NOT NULL,
  `tableField_id` varchar(32) DEFAULT NULL,
  `value` varchar(250) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_field_value
-- ----------------------------

-- ----------------------------
-- Table structure for ed_foreign_key
-- ----------------------------
DROP TABLE IF EXISTS `ed_foreign_key`;
CREATE TABLE `ed_foreign_key` (
  `Id` varchar(32) NOT NULL,
  `tableField_id` varchar(32) NOT NULL,
  `table_name` varchar(250) NOT NULL COMMENT '外键表名称',
  `field` varchar(250) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_foreign_key
-- ----------------------------

-- ----------------------------
-- Table structure for ed_log
-- ----------------------------
DROP TABLE IF EXISTS `ed_log`;
CREATE TABLE `ed_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `template_id` varchar(32) NOT NULL COMMENT '模板id',
  `status` int(1) NOT NULL COMMENT '状态:1.待导入、2.导入完成、3.删除',
  `creator_id` varchar(32) NOT NULL COMMENT '导入人id',
  `foreign_table` varchar(50) NOT NULL COMMENT '关联临时表',
  `expoort_time` datetime DEFAULT NULL COMMENT '导入时间，为实际导入到正式表中的时间',
  `dburl` varchar(50) DEFAULT NULL COMMENT '数据库url',
  `port` int(10) DEFAULT NULL COMMENT '连接端口，缺省使用数据库默认端口',
  `dbname` varchar(50) DEFAULT NULL COMMENT '数据库名',
  `table_name` varchar(250) DEFAULT NULL COMMENT '表名，多张表以逗号隔开',
  `total_count` bigint(20) DEFAULT NULL COMMENT '导入记录数',
  `sort` int(255) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据导入日志表';

-- ----------------------------
-- Records of ed_log
-- ----------------------------

-- ----------------------------
-- Table structure for ed_table_field
-- ----------------------------
DROP TABLE IF EXISTS `ed_table_field`;
CREATE TABLE `ed_table_field` (
  `Id` varchar(32) NOT NULL,
  `table_name` varchar(32) NOT NULL COMMENT '导入表名称',
  `field_name` varchar(250) NOT NULL,
  `type` int(1) NOT NULL,
  `field_type` varchar(255) NOT NULL COMMENT '字段类型',
  `template_id` varchar(32) NOT NULL COMMENT '模版ID',
  `repeat_check` int(255) DEFAULT '0' COMMENT '是否进行重复性校验 1.是 0.否',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_table_field
-- ----------------------------

-- ----------------------------
-- Table structure for ed_template
-- ----------------------------
DROP TABLE IF EXISTS `ed_template`;
CREATE TABLE `ed_template` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `template_name` varchar(512) NOT NULL COMMENT '模板名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(32) NOT NULL COMMENT '创建人',
  `status` int(1) NOT NULL COMMENT '1.正常，2.禁用，3.删除，4.修改未完成',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `app_id` varchar(32) DEFAULT NULL COMMENT 'appId，每个模块的唯一标识',
  `module_Id` varchar(32) DEFAULT NULL COMMENT '模块ID',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `only` (`template_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据导入模板表';

-- ----------------------------
-- Records of ed_template
-- ----------------------------

-- ----------------------------
-- Table structure for ed_template_dbconfig
-- ----------------------------
DROP TABLE IF EXISTS `ed_template_dbconfig`;
CREATE TABLE `ed_template_dbconfig` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `connection_name` varchar(100) NOT NULL COMMENT '连接名称',
  `template_id` varchar(32) NOT NULL COMMENT '模板id',
  `dburl` varchar(50) NOT NULL COMMENT '数据库url',
  `dbtype_id` varchar(32) NOT NULL COMMENT '数据库类型:MYSQL、SQL SERVER等',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '连接密码',
  `port` int(10) NOT NULL COMMENT '连接端口，缺省使用数据库默认端口',
  `dbname` varchar(50) NOT NULL COMMENT '数据库名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='模板数据库配置表';

-- ----------------------------
-- Records of ed_template_dbconfig
-- ----------------------------

-- ----------------------------
-- Table structure for ed_template_field
-- ----------------------------
DROP TABLE IF EXISTS `ed_template_field`;
CREATE TABLE `ed_template_field` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(512) NOT NULL COMMENT '模板字段名称',
  `template_id` varchar(32) NOT NULL COMMENT '所属模板id',
  `default_value` varchar(512) DEFAULT NULL COMMENT '字段缺省值',
  `sort` int(255) NOT NULL COMMENT '字段序号，用于字段1：1对应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='模板字段表';

-- ----------------------------
-- Records of ed_template_field
-- ----------------------------

-- ----------------------------
-- Table structure for ed_template_table
-- ----------------------------
DROP TABLE IF EXISTS `ed_template_table`;
CREATE TABLE `ed_template_table` (
  `Id` varchar(32) NOT NULL,
  `template_id` varchar(32) NOT NULL,
  `table_name` varchar(250) NOT NULL,
  `import_sort` int(50) NOT NULL,
  `key_name` varchar(250) NOT NULL,
  `ken_generate` int(10) NOT NULL COMMENT '1.自增；2.UUID 3.自定义',
  `key_type` varchar(255) NOT NULL COMMENT '主键类型',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_template_table
-- ----------------------------

-- ----------------------------
-- Table structure for ed_template_tablefield
-- ----------------------------
DROP TABLE IF EXISTS `ed_template_tablefield`;
CREATE TABLE `ed_template_tablefield` (
  `Id` varchar(32) NOT NULL,
  `tableField_id` varchar(32) NOT NULL,
  `templateField_id` varchar(32) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_template_tablefield
-- ----------------------------

-- ----------------------------
-- Table structure for ed_template_tool
-- ----------------------------
DROP TABLE IF EXISTS `ed_template_tool`;
CREATE TABLE `ed_template_tool` (
  `Id` varchar(32) NOT NULL,
  `template_id` varchar(32) DEFAULT NULL,
  `tool_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ed_template_tool
-- ----------------------------

-- ----------------------------
-- Table structure for ed_tools
-- ----------------------------
DROP TABLE IF EXISTS `ed_tools`;
CREATE TABLE `ed_tools` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(512) NOT NULL COMMENT '名称',
  `type` int(1) NOT NULL COMMENT '1.校验器，2.转换器',
  `class_name` varchar(200) NOT NULL COMMENT '校验器或转换器类名,后台根据这个反射',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(32) NOT NULL COMMENT '创建人',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `sort_order` int(255) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name-only` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='工具表，包含规则校验器、字段转换器';

-- ----------------------------
-- Records of ed_tools
-- ----------------------------
INSERT INTO `ed_tools` VALUES ('0247691381e011e9bf8674d435bc8d5b', '多关联导入器', '4', 'com.ruxuanwo.data.export.importer.impl.AssociatedImporter', '2019-05-29 15:27:26', '1', '可以导入一对一、一对多、多对多关系型数据', '4');
INSERT INTO `ed_tools` VALUES ('06f0b30981e111e9bf8674d435bc8d5b', '密码加密转换器', '2', 'com.ruxuanwo.data.export.conversion.impl.EncryptionConversion', '2019-05-29 15:34:43', '1', '用户密码加密转换器', '9');
INSERT INTO `ed_tools` VALUES ('1e097f0481df11e9bf8674d435bc8d5b', '排序号生成器', '3', 'com.ruxuanwo.data.export.generator.impl.SortGenerator', '2019-05-29 15:21:03', '1', '生成数据库sort字段', '2');
INSERT INTO `ed_tools` VALUES ('4e30ae7a81e111e9bf8674d435bc8d5b', '转拼音转换器', '2', 'com.ruxuanwo.data.export.conversion.impl.NameConversion', '2019-05-29 15:36:43', '1', '将中文转换成拼音', '10');
INSERT INTO `ed_tools` VALUES ('53e8fa0381e011e9bf8674d435bc8d5b', '非空校验器', '1', 'com.ruxuanwo.data.export.check.impl.NullCheck', '2019-05-29 15:29:43', '1', '效验字符串是否为空', '5');
INSERT INTO `ed_tools` VALUES ('7ea5f08f81df11e9bf8674d435bc8d5b', '默认导入器', '4', 'com.ruxuanwo.data.export.importer.impl.DefaultImporter', '2019-05-29 15:23:45', '1', '只能导入一对一关系型数据', '3');
INSERT INTO `ed_tools` VALUES ('89088e4c81e011e9bf8674d435bc8d5b', '手机号校验器', '1', 'com.ruxuanwo.data.export.check.impl.CellphoneCheck', '2019-05-29 15:31:12', '1', '效验是否为手机号码格式', '6');
INSERT INTO `ed_tools` VALUES ('9ec7fe8c81e111e9bf8674d435bc8d5b', '性别转换器', '2', 'com.ruxuanwo.data.export.conversion.impl.GenderConversion', '2019-05-29 15:38:58', '1', '将中文性别转成数字，男是1，女是2', '11');
INSERT INTO `ed_tools` VALUES ('a67b19cb81e011e9bf8674d435bc8d5b', '长度校验', '1', 'com.ruxuanwo.data.export.check.impl.LengthCheck', '2019-05-29 15:32:02', '1', '输入规定长度效验是否超出', '7');
INSERT INTO `ed_tools` VALUES ('e20f27bf81e011e9bf8674d435bc8d5b', '时间格式转换器', '2', 'com.ruxuanwo.data.export.conversion.impl.TimeFormatConversion', '2019-05-29 15:33:42', '1', '转换成规定时间格式', '8');
INSERT INTO `ed_tools` VALUES ('ec332ab281de11e9bf8674d435bc8d5b', '当前时间生成器', '3', 'com.ruxuanwo.data.export.generator.impl.NowTimeGenerator', '2019-05-29 15:19:39', '1', '生成当前时间', '1');
