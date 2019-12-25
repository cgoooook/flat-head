-- MySQL dump 10.13  Distrib 5.7.27, for macos10.14 (x86_64)
--
-- Host: localhost    Database: kms
-- ------------------------------------------------------
-- Server version	5.7.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `kms_a_key`
--

DROP TABLE IF EXISTS `kms_a_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_a_key` (
                             `key_id` varchar(36) NOT NULL,
                             `key_name` varchar(128) DEFAULT NULL,
                             `key_alg` varchar(15) DEFAULT NULL,
                             `length` int(11) DEFAULT NULL,
                             `version` int(11) DEFAULT '1',
                             `check_value` varchar(20) DEFAULT NULL,
                             `key_value` varchar(200) DEFAULT NULL,
                             `create_by` varchar(20) DEFAULT NULL,
                             `template_id` varchar(36) DEFAULT NULL,
                             `org_id` varchar(36) DEFAULT NULL,
                             `status` int(11) DEFAULT NULL COMMENT '0 create 1 generate 2 active 3 inactive 4 archive 5 delete',
                             `mode` varchar(20) DEFAULT NULL,
                             PRIMARY KEY (`key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_a_key`
--

LOCK TABLES `kms_a_key` WRITE;
/*!40000 ALTER TABLE `kms_a_key` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_a_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_a_key_history`
--

DROP TABLE IF EXISTS `kms_a_key_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_a_key_history` (
                                     `key_history_id` varchar(36) NOT NULL,
                                     `key_id` varchar(36) NOT NULL,
                                     `key_name` varchar(128) DEFAULT NULL,
                                     `key_alg` varchar(15) DEFAULT NULL,
                                     `length` int(11) DEFAULT NULL,
                                     `version` int(11) DEFAULT '1',
                                     `check_value` varchar(20) DEFAULT NULL,
                                     `key_value` varchar(200) DEFAULT NULL,
                                     `create_by` varchar(20) DEFAULT NULL,
                                     `template_id` varchar(36) DEFAULT NULL,
                                     `org_id` varchar(36) DEFAULT NULL,
                                     `status` int(11) DEFAULT NULL COMMENT '0 create 1 generate 2 active 3 inactive 4 archive ',
                                     PRIMARY KEY (`key_history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_a_key_history`
--

LOCK TABLES `kms_a_key_history` WRITE;
/*!40000 ALTER TABLE `kms_a_key_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_a_key_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_a_task`
--

DROP TABLE IF EXISTS `kms_a_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_a_task` (
                              `id` int(10) NOT NULL AUTO_INCREMENT,
                              `task_name` varchar(20) DEFAULT NULL,
                              `algorithm` varchar(20) DEFAULT NULL,
                              `planned_quantity` int(10) DEFAULT NULL,
                              `current_quantity` int(10) DEFAULT NULL,
                              `status` int(1) DEFAULT NULL,
                              `length` varchar(50) DEFAULT NULL,
                              `run_status` int(11) DEFAULT '0',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_a_task`
--

LOCK TABLES `kms_a_task` WRITE;
/*!40000 ALTER TABLE `kms_a_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_a_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_collection_key`
--

DROP TABLE IF EXISTS `kms_collection_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_collection_key` (
                                      `key_id` varchar(36) NOT NULL,
                                      `collection_id` varchar(36) NOT NULL,
                                      PRIMARY KEY (`key_id`,`collection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_collection_key`
--

LOCK TABLES `kms_collection_key` WRITE;
/*!40000 ALTER TABLE `kms_collection_key` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_collection_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_device`
--

DROP TABLE IF EXISTS `kms_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_device` (
                              `device_id` varchar(36) NOT NULL,
                              `device_name` varchar(30) DEFAULT NULL,
                              `device_code` varchar(30) DEFAULT NULL,
                              `device_ip` varchar(20) DEFAULT NULL,
                              `org_id` varchar(36) NOT NULL,
                              `collection_id` varchar(36) DEFAULT NULL,
                              PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_device`
--

LOCK TABLES `kms_device` WRITE;
/*!40000 ALTER TABLE `kms_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_device_key_pair`
--

DROP TABLE IF EXISTS `kms_device_key_pair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_device_key_pair` (
                                       `device_id` varchar(36) NOT NULL,
                                       `alg` varchar(32) DEFAULT NULL,
                                       `attr` varchar(60) DEFAULT NULL COMMENT 'RSA 模长\nSM2 曲线参数 P256',
                                       `pub_key` varchar(5000) DEFAULT NULL,
                                       `pri_key` varchar(5000) DEFAULT NULL,
                                       `cert_content` varchar(5000) DEFAULT NULL,
                                       `status` int(11) DEFAULT '1',
                                       `reason` varchar(500) DEFAULT NULL,
                                       `key_pair_id` varchar(36) NOT NULL,
                                       PRIMARY KEY (`key_pair_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_device_key_pair`
--

LOCK TABLES `kms_device_key_pair` WRITE;
/*!40000 ALTER TABLE `kms_device_key_pair` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_device_key_pair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_key_collection`
--

DROP TABLE IF EXISTS `kms_key_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_key_collection` (
                                      `collection_id` varchar(36) NOT NULL,
                                      `collection_name` varchar(120) DEFAULT NULL,
                                      `org_id` varchar(36) DEFAULT NULL,
                                      PRIMARY KEY (`collection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_key_collection`
--

LOCK TABLES `kms_key_collection` WRITE;
/*!40000 ALTER TABLE `kms_key_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_key_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_key_pair`
--

DROP TABLE IF EXISTS `kms_key_pair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_key_pair` (
                                `id` varchar(36) NOT NULL,
                                `alg` varchar(32) DEFAULT NULL,
                                `attr` varchar(60) DEFAULT NULL COMMENT 'RSA 模长\nSM2 曲线参数 P256',
                                `pub_key` varchar(5000) DEFAULT NULL,
                                `pri_key` varchar(5000) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_key_pair`
--

LOCK TABLES `kms_key_pair` WRITE;
/*!40000 ALTER TABLE `kms_key_pair` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_key_pair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_key_pair_apply`
--

DROP TABLE IF EXISTS `kms_key_pair_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_key_pair_apply` (
                                      `id` varchar(36) NOT NULL,
                                      `alg` varchar(32) DEFAULT NULL,
                                      `attr` varchar(60) DEFAULT NULL COMMENT 'RSA 模长\nSM2 曲线参数 P256',
                                      `pub_key` varchar(5000) DEFAULT NULL,
                                      `pri_key` varchar(5000) DEFAULT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_key_pair_apply`
--

LOCK TABLES `kms_key_pair_apply` WRITE;
/*!40000 ALTER TABLE `kms_key_pair_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_key_pair_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_operate_archive_log`
--

DROP TABLE IF EXISTS `kms_operate_archive_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_operate_archive_log` (
                                           `log_id` varchar(36) NOT NULL,
                                           `operate_type` varchar(20) DEFAULT NULL,
                                           `operate_user` varchar(30) DEFAULT NULL,
                                           `operate_time` datetime DEFAULT NULL,
                                           `operator_result` int(11) DEFAULT NULL,
                                           `operate_content` varchar(500) DEFAULT NULL,
                                           `content_hmac` varchar(30) DEFAULT NULL,
                                           `audit_flag` int(11) DEFAULT NULL,
                                           PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_operate_archive_log`
--

LOCK TABLES `kms_operate_archive_log` WRITE;
/*!40000 ALTER TABLE `kms_operate_archive_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_operate_archive_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_operate_log`
--

DROP TABLE IF EXISTS `kms_operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_operate_log` (
                                   `log_id` varchar(36) NOT NULL,
                                   `operate_type` varchar(20) DEFAULT NULL,
                                   `operate_user` varchar(30) DEFAULT NULL,
                                   `operate_time` datetime DEFAULT NULL,
                                   `operator_result` int(11) DEFAULT NULL,
                                   `operate_content` varchar(500) DEFAULT NULL,
                                   `content_hmac` varchar(30) DEFAULT NULL,
                                   `audit_flag` int(11) DEFAULT NULL,
                                   PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_operate_log`
--

LOCK TABLES `kms_operate_log` WRITE;
/*!40000 ALTER TABLE `kms_operate_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `kms_operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_org`
--

DROP TABLE IF EXISTS `kms_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_org` (
                           `org_id` varchar(36) NOT NULL,
                           `org_name` varchar(200) DEFAULT NULL,
                           `org_code` varchar(200) DEFAULT NULL,
                           `parent_id` varchar(16) DEFAULT NULL,
                           `leaf` tinyint(4) DEFAULT '0',
                           `properties` varchar(5000) DEFAULT NULL,
                           PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_org`
--

LOCK TABLES `kms_org` WRITE;
/*!40000 ALTER TABLE `kms_org` DISABLE KEYS */;
INSERT INTO `kms_org` VALUES ('orgRoot','根机构','orgroot','-1',0,'');
/*!40000 ALTER TABLE `kms_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kms_template`
--

DROP TABLE IF EXISTS `kms_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_template` (
                                `template_id` varchar(36) NOT NULL,
                                `template_name` varchar(30) DEFAULT NULL,
                                `is_built_in` int(11) DEFAULT '0',
                                `node` int(11) DEFAULT NULL,
                                `start_date` datetime DEFAULT NULL,
                                `end_date` datetime DEFAULT NULL,
                                `key_usages` varchar(200) DEFAULT NULL,
                                `extend_usages` varchar(500) DEFAULT NULL,
                                `status` int(11) DEFAULT '1',
                                PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_template`
--

LOCK TABLES `kms_template` WRITE;
/*!40000 ALTER TABLE `kms_template` DISABLE KEYS */;
INSERT INTO `kms_template` VALUES ('1','根节点模版',1,1,'2019-10-26 13:02:17','2020-10-27 13:02:26','Derive,Encrypt','',1),('9500a9e8-0c6f-438e-9c33-66b4ec11c73e','中节点模版',1,2,'2019-10-30 04:10:31','2019-10-30 05:10:31','Derive,Encrypt','',1),('ea535252-aca2-4e2b-9eec-2c731dfc504e','终节点模版',1,3,'2019-10-27 05:10:45','2019-10-31 10:10:39','Derive,Encrypt','',1);
/*!40000 ALTER TABLE `kms_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mail_config`
--

DROP TABLE IF EXISTS `mail_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail_config` (
                               `id` int(32) NOT NULL AUTO_INCREMENT,
                               `addr` varchar(16) DEFAULT NULL,
                               `port` int(8) DEFAULT NULL,
                               `time_out` int(8) DEFAULT NULL,
                               `send_mailbox` varchar(32) DEFAULT NULL,
                               `password` varchar(32) DEFAULT NULL,
                               `receiving_mailbox` varchar(32) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mail_config`
--

LOCK TABLES `mail_config` WRITE;
/*!40000 ALTER TABLE `mail_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_menu`
--

DROP TABLE IF EXISTS `sys_acl_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_menu` (
                                `menu_id` varchar(20) NOT NULL,
                                `menu_name` varchar(200) DEFAULT NULL,
                                `menu_icon` varchar(100) DEFAULT NULL,
                                `parent_id` varchar(20) DEFAULT NULL,
                                `menu_url` varchar(200) DEFAULT NULL,
                                `level` int(11) DEFAULT NULL,
                                `weight` int(11) DEFAULT NULL,
                                `disabled` tinyint(4) DEFAULT '0',
                                `local` varchar(10) DEFAULT NULL,
                                `leaf` tinyint(4) DEFAULT NULL,
                                `perm_token` varchar(200) DEFAULT NULL,
                                PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_menu`
--

LOCK TABLES `sys_acl_menu` WRITE;
/*!40000 ALTER TABLE `sys_acl_menu` DISABLE KEYS */;
INSERT INTO `sys_acl_menu` VALUES ('01','系统设置',' ','-1',NULL,0,1,0,NULL,0,'sys:setting:open'),('0101','权限管理','icon-diamond','01',NULL,1,1,0,NULL,0,'sys:security:open'),('010101','用户管理','fa fa-users','0101','/sys/user',2,1,0,'',1,'sys:user:open'),('010102','角色管理','fa fa-users','0101','/sys/role',2,1,0,NULL,1,'sys:role:open'),('02','密钥管理','','-1',NULL,0,1,0,NULL,0,'key:key:open'),('0201','密钥管理','','02',NULL,1,1,0,NULL,0,'key:key:manage'),('020101','模版管理','','0201','/key/template',2,1,0,NULL,1,'key:template:open'),('020102','密钥管理','fa fa-users','0201','/key/key',2,1,0,'',1,'key:key:operator'),('020103','密钥集管理','fa fa-users','0201','/key/collection',2,1,0,NULL,1,'key:collection:open'),('020104','机构管理','','0201','/sys/org',2,1,0,NULL,1,'key:org:open'),('020105','设备管理','fa fa-users','0201','/sys/device',2,1,0,NULL,1,'key:dev:open'),('03','配置管理',' ','-1',NULL,0,1,0,NULL,0,'config:config:manage'),('0301','配置管理','','03',NULL,1,1,0,NULL,0,'config:config:open'),('030101','数据库配置管理',NULL,'0301','/db/config',2,1,0,NULL,1,'config:database:open'),('030102','日志配置',NULL,'0301','/log/config',2,1,0,NULL,1,'config:log:open'),('030103','UI配置',NULL,'0301','/ui/config',2,1,0,NULL,1,'config:ui:open'),('030104','邮箱配置',NULL,'0301','/mail/config',2,1,0,NULL,1,'config:mail:open'),('030105','任务配置',NULL,'0301','/task/config',2,1,0,NULL,1,'config:task:open'),('04','日志管理',' ','-1',NULL,0,1,0,NULL,0,'log:log:manage'),('0401','日志管理','','04',NULL,1,1,0,NULL,0,'log:log:open'),('040101','管理日志',NULL,'0401','/log/manage',2,1,0,NULL,1,'log:operate:open'),('040102','服务日志',NULL,'0401','/log/serverLog',2,1,0,NULL,1,'log:sever:open'),('040103','日志归档',NULL,'0401','/log/archiving',2,1,0,NULL,1,'log:archive:open'),('040104','日志审计',NULL,'0401','/log/audit',2,1,0,NULL,1,'log:audit:open');
/*!40000 ALTER TABLE `sys_acl_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_permission`
--

DROP TABLE IF EXISTS `sys_acl_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_permission` (
                                      `permission_id` int(11) NOT NULL AUTO_INCREMENT,
                                      `perm_token` varchar(200) NOT NULL,
                                      `perm_description` varchar(200) DEFAULT NULL,
                                      PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_permission`
--

LOCK TABLES `sys_acl_permission` WRITE;
/*!40000 ALTER TABLE `sys_acl_permission` DISABLE KEYS */;
INSERT INTO `sys_acl_permission` VALUES (1,'sys:setting:open',NULL),(2,'sys:security:open',NULL),(3,'sys:user:open',NULL),(4,'sys:role:open',NULL),(5,'key:key:open',NULL),(6,'key:key:manage',NULL),(7,'key:template:open',NULL),(8,'key:key:operator',NULL),(9,'key:collection:open',NULL),(10,'key:org:open',NULL),(11,'key:dev:open',NULL),(12,'config:config:manage',NULL),(13,'config:config:open',NULL),(14,'config:database:open',NULL),(15,'config:log:open',NULL),(16,'config:ui:open',NULL),(17,'config:mail:open',NULL),(18,'config:task:open',NULL),(19,'log:log:manage',NULL),(20,'log:log:open',NULL),(21,'log:operate:open',NULL),(22,'log:sever:open',NULL),(23,'log:archive:open',NULL),(24,'log:audit:open',NULL);
/*!40000 ALTER TABLE `sys_acl_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_role`
--

DROP TABLE IF EXISTS `sys_acl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_role` (
                                `role_id` varchar(36) NOT NULL,
                                `role_name` varchar(150) NOT NULL,
                                `role_description` varchar(200) DEFAULT NULL,
                                PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_role`
--

LOCK TABLES `sys_acl_role` WRITE;
/*!40000 ALTER TABLE `sys_acl_role` DISABLE KEYS */;
INSERT INTO `sys_acl_role` VALUES ('50d7191b-b143-41a2-80ce-e5b5c886c494','超级管理',''),('d1af8e66-5d6b-4804-8618-10763fcf928a','key mangage','');
/*!40000 ALTER TABLE `sys_acl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_role_permission`
--

DROP TABLE IF EXISTS `sys_acl_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_role_permission` (
                                           `role_id` varchar(36) NOT NULL,
                                           `perm_id` int(11) NOT NULL,
                                           UNIQUE KEY `sys_acl_role_permission_pk` (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_role_permission`
--

LOCK TABLES `sys_acl_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_acl_role_permission` DISABLE KEYS */;
INSERT INTO `sys_acl_role_permission` VALUES ('50d7191b-b143-41a2-80ce-e5b5c886c494',1),('50d7191b-b143-41a2-80ce-e5b5c886c494',2),('50d7191b-b143-41a2-80ce-e5b5c886c494',3),('50d7191b-b143-41a2-80ce-e5b5c886c494',4),('50d7191b-b143-41a2-80ce-e5b5c886c494',5),('50d7191b-b143-41a2-80ce-e5b5c886c494',6),('50d7191b-b143-41a2-80ce-e5b5c886c494',7),('50d7191b-b143-41a2-80ce-e5b5c886c494',8),('50d7191b-b143-41a2-80ce-e5b5c886c494',9),('50d7191b-b143-41a2-80ce-e5b5c886c494',10),('50d7191b-b143-41a2-80ce-e5b5c886c494',11),('50d7191b-b143-41a2-80ce-e5b5c886c494',12),('50d7191b-b143-41a2-80ce-e5b5c886c494',13),('50d7191b-b143-41a2-80ce-e5b5c886c494',14),('50d7191b-b143-41a2-80ce-e5b5c886c494',15),('50d7191b-b143-41a2-80ce-e5b5c886c494',16),('50d7191b-b143-41a2-80ce-e5b5c886c494',17),('50d7191b-b143-41a2-80ce-e5b5c886c494',18),('50d7191b-b143-41a2-80ce-e5b5c886c494',19),('50d7191b-b143-41a2-80ce-e5b5c886c494',20),('50d7191b-b143-41a2-80ce-e5b5c886c494',21),('50d7191b-b143-41a2-80ce-e5b5c886c494',22),('50d7191b-b143-41a2-80ce-e5b5c886c494',23),('50d7191b-b143-41a2-80ce-e5b5c886c494',24),('d1af8e66-5d6b-4804-8618-10763fcf928a',5),('d1af8e66-5d6b-4804-8618-10763fcf928a',6),('d1af8e66-5d6b-4804-8618-10763fcf928a',7),('d1af8e66-5d6b-4804-8618-10763fcf928a',8),('d1af8e66-5d6b-4804-8618-10763fcf928a',9),('d1af8e66-5d6b-4804-8618-10763fcf928a',10),('d1af8e66-5d6b-4804-8618-10763fcf928a',11);
/*!40000 ALTER TABLE `sys_acl_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_user`
--

DROP TABLE IF EXISTS `sys_acl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_user` (
                                `user_id` varchar(36) NOT NULL,
                                `nick_name` varchar(150) DEFAULT NULL,
                                `username` varchar(30) NOT NULL,
                                `password` varchar(32) NOT NULL,
                                `pub_key` varchar(500) DEFAULT NULL,
                                `account_type` int(11) DEFAULT '0',
                                PRIMARY KEY (`user_id`),
                                UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_user`
--

LOCK TABLES `sys_acl_user` WRITE;
/*!40000 ALTER TABLE `sys_acl_user` DISABLE KEYS */;
INSERT INTO `sys_acl_user` VALUES ('1','pony','admin','c3284d0f94606de1fd2af172aba15bf3',NULL,0),('7ec7672e-1ad0-4523-9056-33e39dc5a4f8','1','1','c4ca4238a0b923820dcc509a6f75849b',NULL,0);
/*!40000 ALTER TABLE `sys_acl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_user_role`
--

DROP TABLE IF EXISTS `sys_acl_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_user_role` (
                                     `user_id` varchar(36) DEFAULT NULL,
                                     `role_id` varchar(36) DEFAULT NULL,
                                     UNIQUE KEY `sys_acl_user_role_pk` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_user_role`
--

LOCK TABLES `sys_acl_user_role` WRITE;
/*!40000 ALTER TABLE `sys_acl_user_role` DISABLE KEYS */;
INSERT INTO `sys_acl_user_role` VALUES ('1','50d7191b-b143-41a2-80ce-e5b5c886c494'),('7ec7672e-1ad0-4523-9056-33e39dc5a4f8','d1af8e66-5d6b-4804-8618-10763fcf928a');
/*!40000 ALTER TABLE `sys_acl_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `config_name` varchar(50) DEFAULT NULL,
                              `config_value` varchar(200) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'log_level','INFO'),(2,'log_days','3'),(3,'copyright',''),(4,'DMK','280e053b16f2e4e618a6bb7a70952f65');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logo`
--

DROP TABLE IF EXISTS `sys_logo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_logo` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `logo` longblob,
                            `status` int(11) DEFAULT NULL COMMENT '0 停用 1 在用',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logo`
--

LOCK TABLES `sys_logo` WRITE;
/*!40000 ALTER TABLE `sys_logo` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_logo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-25 20:19:02
