INSERT INTO `kms`.`sys_acl_menu` (`menu_id`, `menu_name`, `menu_icon`, `parent_id`, `menu_url`, `level`, `weight`, `disabled`, `local`, `leaf`, `perm_token`) VALUES ('02', '密钥管理', NULL, '-1', NULL, '0', '1', '0', NULL, '0', '123');
INSERT INTO `kms`.`sys_acl_menu` (`menu_id`, `menu_name`, `menu_icon`, `parent_id`, `menu_url`, `level`, `weight`, `disabled`, `local`, `leaf`, `perm_token`) VALUES ('0201', '密钥管理', NULL, '02', NULL, '1', '1', '0', NULL, '0', '123');
INSERT INTO `kms`.`sys_acl_menu` (`menu_id`, `menu_name`, `menu_icon`, `parent_id`, `menu_url`, `level`, `weight`, `disabled`, `local`, `leaf`, `perm_token`) VALUES ('020104', '机构管理', NULL, '0201', '/sys/org', '2', '1', '0', NULL, '1', '123');
