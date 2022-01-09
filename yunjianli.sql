/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : yunjianli

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 09/01/2022 22:29:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `menu_parent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '当前菜单的上级菜单',
  `menu_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单路径',
  `menu_level` int(255) NOT NULL COMMENT '1:一级菜单 2:二级菜单 3:三级菜单',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '用户管理', '0', '/user', 1);
INSERT INTO `menu` VALUES (2, '模板管理', '0', '/templet', 1);
INSERT INTO `menu` VALUES (3, '订单管理', '0', '/order', 1);
INSERT INTO `menu` VALUES (4, '文章管理', '0', '/article', 1);
INSERT INTO `menu` VALUES (5, '表单', '0', '/form', 1);
INSERT INTO `menu` VALUES (6, '例子', '0', '/example', 1);
INSERT INTO `menu` VALUES (7, '表格', '6', 'table', 2);
INSERT INTO `menu` VALUES (8, '树', '6', 'tree', 2);

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  UNIQUE INDEX `authentication_id`(`authentication_id`) USING BTREE,
  INDEX `token_id_index`(`token_id`) USING BTREE,
  INDEX `authentication_id_index`(`authentication_id`) USING BTREE,
  INDEX `user_name_index`(`user_name`) USING BTREE,
  INDEX `client_id_index`(`client_id`) USING BTREE,
  INDEX `refresh_token_index`(`refresh_token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `archived` tinyint(1) NULL DEFAULT 0,
  `trusted` tinyint(1) NULL DEFAULT 0,
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'false',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('login', 'all_login', 'pass', 'all', 'authorization_code,password,implicit,client_credentials', 'https://www.baidu.com', NULL, NULL, NULL, NULL, '2021-12-18 12:37:16', 0, 0, 'false');

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  INDEX `code_index`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL,
  INDEX `token_id_index`(`token_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission_resource
-- ----------------------------
DROP TABLE IF EXISTS `permission_resource`;
CREATE TABLE `permission_resource`  (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id默认从1000开始',
  `resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
  `path` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源路径',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方法',
  `type` int(255) NOT NULL COMMENT '资源类型 0 menu',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_resource
-- ----------------------------
INSERT INTO `permission_resource` VALUES (1000, '用户管理', '/user', 'get', 0);
INSERT INTO `permission_resource` VALUES (1001, '模板管理', '/templet', 'get', 0);
INSERT INTO `permission_resource` VALUES (1002, '订单管理', '/order', 'get', 0);
INSERT INTO `permission_resource` VALUES (1003, '文章管理', '/article', 'get', 0);

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表主键 默认从100开始',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_role
-- ----------------------------
INSERT INTO `permission_role` VALUES (100, 'DEFAULT_USER');
INSERT INTO `permission_role` VALUES (101, 'VIP_USER');
INSERT INTO `permission_role` VALUES (102, 'ADMIN_MANAGER');

-- ----------------------------
-- Table structure for permission_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `permission_role_resource`;
CREATE TABLE `permission_role_resource`  (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_role_resource
-- ----------------------------
INSERT INTO `permission_role_resource` VALUES (100, 1000);
INSERT INTO `permission_role_resource` VALUES (100, 1001);
INSERT INTO `permission_role_resource` VALUES (100, 1002);
INSERT INTO `permission_role_resource` VALUES (100, 1003);

-- ----------------------------
-- Table structure for permission_user_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_user_role`;
CREATE TABLE `permission_user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_user_role
-- ----------------------------
INSERT INTO `permission_user_role` VALUES (10000, 100);
INSERT INTO `permission_user_role` VALUES (10000, 102);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '用户id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (102, 1);
INSERT INTO `role_menu` VALUES (102, 2);
INSERT INTO `role_menu` VALUES (102, 4);
INSERT INTO `role_menu` VALUES (102, 5);
INSERT INTO `role_menu` VALUES (102, 6);
INSERT INTO `role_menu` VALUES (102, 7);
INSERT INTO `role_menu` VALUES (102, 8);
INSERT INTO `role_menu` VALUES (102, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
  `sex` bit(1) NOT NULL COMMENT '用户性别 1是男 0 是女',
  `status` int(255) NOT NULL DEFAULT 1 COMMENT '当前账号状态 1启动 0禁用',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10000, 'fuyun', 'redfuyun', '17354285667', b'1', 1);

SET FOREIGN_KEY_CHECKS = 1;
