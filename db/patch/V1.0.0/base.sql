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
                             `key_id` varchar(32) NOT NULL,
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
                                     `key_history_id` varchar(32) NOT NULL,
                                     `key_id` varchar(32) NOT NULL,
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
-- Table structure for table `kms_collection_key`
--

DROP TABLE IF EXISTS `kms_collection_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kms_collection_key` (
                                      `key_id` varchar(32) NOT NULL,
                                      `connection_id` varchar(32) NOT NULL,
                                      PRIMARY KEY (`key_id`,`connection_id`)
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
                              `connection_id` varchar(36) DEFAULT NULL,
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
                           PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kms_org`
--

LOCK TABLES `kms_org` WRITE;
/*!40000 ALTER TABLE `kms_org` DISABLE KEYS */;
INSERT INTO `kms_org` VALUES ('orgRoot','根机构','编码1','-1',0);
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
INSERT INTO `sys_acl_menu` VALUES ('01','系统设置',' ','-1',NULL,0,1,0,NULL,0,'123'),('0101','权限管理','icon-diamond','01',NULL,1,1,0,NULL,0,'123'),('010101','用户管理','fa fa-users','0101','/sys/user',2,1,0,'',1,'123'),('010105','设备管理','fa fa-users','0201','/sys/device',2,1,0,NULL,1,'123'),('02','密钥管理','','-1',NULL,0,1,0,NULL,0,'123'),('0201','密钥管理','','02',NULL,1,1,0,NULL,0,'123'),('020101','模版管理','','0201','/key/template',2,1,0,NULL,1,'123'),('020103','密钥集管理','fa fa-users','0201','/key/collection',2,1,0,NULL,1,'123'),('020104','机构管理','','0201','/sys/org',2,1,0,NULL,1,'123'),('020105','设备管理','fa fa-users','0201','/sys/device',2,1,0,NULL,1,'123');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_permission`
--

LOCK TABLES `sys_acl_permission` WRITE;
/*!40000 ALTER TABLE `sys_acl_permission` DISABLE KEYS */;
INSERT INTO `sys_acl_permission` VALUES (1,'sys:manage:security',NULL),(2,'sys:user:manage',NULL);
/*!40000 ALTER TABLE `sys_acl_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_role`
--

DROP TABLE IF EXISTS `sys_acl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_role` (
                                `role_id` int(11) NOT NULL AUTO_INCREMENT,
                                `role_name` varchar(150) NOT NULL,
                                `role_description` varchar(200) DEFAULT NULL,
                                PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_role`
--

LOCK TABLES `sys_acl_role` WRITE;
/*!40000 ALTER TABLE `sys_acl_role` DISABLE KEYS */;
INSERT INTO `sys_acl_role` VALUES (1,'sys_admin',NULL),(2,'role_admin',NULL);
/*!40000 ALTER TABLE `sys_acl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_acl_role_permission`
--

DROP TABLE IF EXISTS `sys_acl_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl_role_permission` (
                                           `role_id` int(11) NOT NULL,
                                           `perm_id` int(11) NOT NULL,
                                           UNIQUE KEY `sys_acl_role_permission_pk` (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_role_permission`
--

LOCK TABLES `sys_acl_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_acl_role_permission` DISABLE KEYS */;
INSERT INTO `sys_acl_role_permission` VALUES (1,1),(1,2),(2,1),(2,2);
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
INSERT INTO `sys_acl_user` VALUES ('1','pony','admin','admin',NULL,0);
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
                                     `role_id` int(11) DEFAULT NULL,
                                     UNIQUE KEY `sys_acl_user_role_pk` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl_user_role`
--

LOCK TABLES `sys_acl_user_role` WRITE;
/*!40000 ALTER TABLE `sys_acl_user_role` DISABLE KEYS */;
INSERT INTO `sys_acl_user_role` VALUES ('1',2);
/*!40000 ALTER TABLE `sys_acl_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-03 15:08:45
