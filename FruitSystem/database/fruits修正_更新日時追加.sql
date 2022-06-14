/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Schema         : fruits

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 24/11/2020 10:27:23
*/
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fruit_manager` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fruit_manager`;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `add_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `add_details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情图',
  `u_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `is_default` tinyint(4) NULL DEFAULT 0 COMMENT '是否默认收货地址',
  PRIMARY KEY (`add_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------

-- ----------------------------
-- Table structure for fruits
-- ----------------------------
DROP TABLE IF EXISTS `fruits`;
CREATE TABLE `fruits`  (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `specification` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `supplier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商',
  `place_origin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产地',
  `price` int(11) NULL DEFAULT NULL COMMENT '价格(毛)',
  `price_off` int(11) NULL DEFAULT NULL COMMENT '折扣价',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品展示小图片',
  PRIMARY KEY (`f_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fruits
-- ----------------------------
INSERT INTO `fruits` VALUES (1, '新鲜青柠', '500g/盒', '爱鲜蜂', '长沙', 150, 145, '/del/fruits-img7.png');
INSERT INTO `fruits` VALUES (2, '新鲜橙子', '500g/盒', '爱鲜蜂', '长沙', 150, 145, '/del/fruits-img4.png');
INSERT INTO `fruits` VALUES (3, '新鲜猕猴桃', '500g/盒', '爱鲜蜂', '长沙', 150, 145, '/del/fruits-img2.png');

-- ----------------------------
-- Table structure for fruits_image_details
-- ----------------------------
DROP TABLE IF EXISTS `fruits_image_details`;
CREATE TABLE `fruits_image_details`  (
  `fid_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '水果详情图地址',
  `f_id` int(11) NULL DEFAULT NULL COMMENT '水果id',
  PRIMARY KEY (`fid_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fruits_image_details
-- ----------------------------
INSERT INTO `fruits_image_details` VALUES (1, '/del/fruits-img7.png', 1);
INSERT INTO `fruits_image_details` VALUES (2, '/del/fruits-img4.png', 2);
INSERT INTO `fruits_image_details` VALUES (3, '/del/fruits-img2.png', 3);

-- ----------------------------
-- Table structure for fruits_images
-- ----------------------------
DROP TABLE IF EXISTS `fruits_images`;
CREATE TABLE `fruits_images`  (
  `fi_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '水果图片地址',
  `f_id` int(11) NULL DEFAULT NULL COMMENT '水果id',
  PRIMARY KEY (`fi_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fruits_images
-- ----------------------------
INSERT INTO `fruits_images` VALUES (1, '/del/special-sale-img1.jpg', 1);
INSERT INTO `fruits_images` VALUES (2, '/del/special-sale-img4.jpg', 2);
INSERT INTO `fruits_images` VALUES (3, '/del/special-sale-img5.jpg', 3);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `oi_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `o_id` int(11) NULL DEFAULT NULL COMMENT '订单编号',
  `f_id` int(11) NULL DEFAULT NULL COMMENT '商品编号',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `price` int(11) NULL DEFAULT NULL COMMENT '商品价格',
  `num` int(11) NULL DEFAULT NULL COMMENT '购买数量',
  `is_review` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '是否评价',
  `is_deliver` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '是否发货',
  `is_take` tinyint(4) NULL DEFAULT 0 COMMENT '是否收货',
  PRIMARY KEY (`oi_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (128, 119, 2, '新鲜橙子 500g/盒', 145, 2, 1, 1, 1);
INSERT INTO `order_item` VALUES (129, 120, 2, '新鲜橙子 500g/盒', 145, 2, 1, 1, 1);
INSERT INTO `order_item` VALUES (130, 120, 3, '新鲜猕猴桃 500g/盒', 145, 2, 1, 1, 1);
INSERT INTO `order_item` VALUES (131, 121, 2, '新鲜橙子 500g/盒', 145, 2, 0, 0, 0);
INSERT INTO `order_item` VALUES (132, 122, 2, '新鲜橙子 500g/盒', 145, 2, 0, 0, 0);
INSERT INTO `order_item` VALUES (134, 124, 1, '新鲜青柠 500g/盒', 145, 1, 0, 1, 1);
INSERT INTO `order_item` VALUES (135, 125, 1, '新鲜青柠 500g/盒', 145, 1, 0, 1, 0);
INSERT INTO `order_item` VALUES (141, 130, 1, '新鲜青柠 500g/盒', 145, 1, 0, 0, 0);
INSERT INTO `order_item` VALUES (142, 131, 3, '新鲜猕猴桃 500g/盒', 145, 3, 0, 0, 0);
INSERT INTO `order_item` VALUES (143, 132, 1, '新鲜青柠 500g/盒', 145, 1, 0, 0, 0);
INSERT INTO `order_item` VALUES (145, 134, 1, '新鲜青柠 500g/盒', 145, 2, 0, 0, 0);
INSERT INTO `order_item` VALUES (146, 135, 2, '新鲜橙子 500g/盒', 145, 1, 0, 0, 0);
INSERT INTO `order_item` VALUES (147, 136, 2, '新鲜橙子 500g/盒', 145, 1, 0, 0, 0);
INSERT INTO `order_item` VALUES (148, 137, 2, '新鲜橙子 500g/盒', 145, 1, 0, 0, 0);

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`  (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `u_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `f_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `is_anonymous` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否匿名评论',
  `r_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`r_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of review
-- ----------------------------

-- ----------------------------
-- Table structure for shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart`  (
  `sc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `u_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `f_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `num` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`sc_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop_cart
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `o_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `u_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `add_id` int(11) NULL DEFAULT NULL COMMENT '收货地址id ',
  `is_play` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '是否付款',
  `o_time` datetime NULL DEFAULT NULL COMMENT '订单时间',
  `total_money` int(11) NULL DEFAULT NULL COMMENT '订单金额(单位/毛)',
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  PRIMARY KEY (`o_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 138 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
-- CREATE TABLE `users`  (
--   `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
--   `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
--   `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
--   `head_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
--   `account` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
--   PRIMARY KEY (`u_id`)
-- ) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `users`  (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `head_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `account` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
  PRIMARY KEY (`u_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Records of users
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;


