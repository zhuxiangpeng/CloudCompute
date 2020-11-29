/*
 Navicat MySQL Data Transfer

 Source Server         : cloud
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : localhost:3306
 Source Schema         : mysql

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 29/11/2020 15:37:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for music_rank_info
-- ----------------------------
DROP TABLE IF EXISTS `music_rank_info`;
CREATE TABLE `music_rank_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `musicid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '音乐id',
  `musicname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音乐名称',
  `appname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属app名称',
  `hotnum` int(11) NULL DEFAULT NULL COMMENT '热度值',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `rankid` int(11) NULL DEFAULT NULL COMMENT '所属榜单id',
  `rankname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属榜单名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
