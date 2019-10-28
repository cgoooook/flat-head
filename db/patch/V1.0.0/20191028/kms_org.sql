/*
Navicat MySQL Data Transfer

Source Server         : kms
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : kms

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-10-28 21:18:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for kms_org
-- ----------------------------
DROP TABLE IF EXISTS `kms_org`;
CREATE TABLE `kms_org` (
  `org_id` varchar(36) NOT NULL,
  `org_name` varchar(200) DEFAULT NULL,
  `org_code` varchar(200) DEFAULT NULL,
  `parent_id` varchar(16) DEFAULT NULL,
  `leaf` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
