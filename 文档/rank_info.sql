/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : mysql

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 23/11/2020 16:47:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rank_info
-- ----------------------------
DROP TABLE IF EXISTS `rank_info`;
CREATE TABLE `rank_info`  (
  `rankid` int(11) NOT NULL COMMENT '排行榜编号 主键',
  `rankname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排行榜名称',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '刷新时间，最新获取时间',
  `appname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称 qq/酷狗/....',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键uuid',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rank_info
-- ----------------------------
INSERT INTO `rank_info` VALUES (23, '情歌对唱榜', '2020-11-23 16:30:07', '百度音乐', '09a73fe285164440b8da070acf936fd3');
INSERT INTO `rank_info` VALUES (6, 'KTV热歌榜', '2020-11-23 16:24:17', '百度音乐', '0da9f5fb719642799ee856576b6e7e3b');
INSERT INTO `rank_info` VALUES (31308, '华语新歌榜', '2020-11-23 16:16:17', '酷狗音乐', '1496892f26814d6dbd8f909711861cb7');
INSERT INTO `rank_info` VALUES (17, '巅峰榜·日本', '2020-11-23 16:17:02', 'QQ音乐', '20255ef942c44a1cb84fac83d4a4856e');
INSERT INTO `rank_info` VALUES (33166, '欧美金曲榜', '2020-11-23 16:16:17', '酷狗音乐', '26648f560afa4d4bbed8db9eac8246b5');
INSERT INTO `rank_info` VALUES (58, '说唱榜', '2020-11-23 16:17:02', 'QQ音乐', '269610fbd29546c29775d967f6538393');
INSERT INTO `rank_info` VALUES (42808, '台湾KKBOX风云榜', '2020-11-23 16:16:17', '酷狗音乐', '283c79e09d7b4018b915e9a4e0026193');
INSERT INTO `rank_info` VALUES (27, '巅峰榜·新歌', '2020-11-23 16:17:02', 'QQ音乐', '2dd8b97c8af047648b8998efe0c68ae6');
INSERT INTO `rank_info` VALUES (23784, '网络红歌榜', '2020-11-23 16:16:17', '酷狗音乐', '30a27610c3d644cf97b2ce022cd6235e');
INSERT INTO `rank_info` VALUES (36, '巅峰榜·K歌金曲', '2020-11-23 16:17:02', 'QQ音乐', '321cfe63dad84df29f9aff7393af47fc');
INSERT INTO `rank_info` VALUES (4681, '美国BillBoard榜', '2020-11-23 16:16:17', '酷狗音乐', '35cace409bed405d873bfd134789f270');
INSERT INTO `rank_info` VALUES (5, '巅峰榜·内地', '2020-11-23 16:17:02', 'QQ音乐', '3a94d505c7e94129aca7a5572bf511f1');
INSERT INTO `rank_info` VALUES (20, '华语金曲榜', '2020-11-23 16:28:18', '百度音乐', '43e33f07b3274de79d28295742e850f8');
INSERT INTO `rank_info` VALUES (9, '雪碧音碰音榜', '2020-11-23 16:25:45', '百度音乐', '45d5b28eef5945c5950343456588fb50');
INSERT INTO `rank_info` VALUES (26, '巅峰榜·热歌', '2020-11-23 16:17:02', 'QQ音乐', '4b14b5126a0845bbb86f09edc835087f');
INSERT INTO `rank_info` VALUES (14, '影视金曲榜', '2020-11-23 16:27:12', '百度音乐', '4e5418c3e4204bc9a432c4e215a45e5f');
INSERT INTO `rank_info` VALUES (65, '国风热歌榜', '2020-11-23 16:17:02', 'QQ音乐', '51ddfe450c4f4266a2f81e00f4dadcd1');
INSERT INTO `rank_info` VALUES (46868, '日本SPACE SHOWER榜', '2020-11-23 16:16:17', '酷狗音乐', '54bc24573afd4bc09bb46bc25e31555c');
INSERT INTO `rank_info` VALUES (62, '飙升榜', '2020-11-23 16:17:02', 'QQ音乐', '56ef1be4377a4568b718ff2602bf7525');
INSERT INTO `rank_info` VALUES (31313, '粤语新歌榜', '2020-11-23 16:16:17', '酷狗音乐', '573eb5aae075471eb1f44c1602eb4e24');
INSERT INTO `rank_info` VALUES (33163, '影视金曲榜', '2020-11-23 16:16:17', '酷狗音乐', '5ca0b386b3ff40608e1820b0f547aee5');
INSERT INTO `rank_info` VALUES (25, '网络歌曲榜', '2020-11-23 16:31:02', '百度音乐', '61839d80b19f4b26bbe1e9453188f392');
INSERT INTO `rank_info` VALUES (4673, '日本公信榜', '2020-11-23 16:16:17', '酷狗音乐', '6b02c659148c43d0bc369c76e0fd4cd6');
INSERT INTO `rank_info` VALUES (4, '巅峰榜·流行指数', '2020-11-23 16:17:02', 'QQ音乐', '6e0052de49be49de8bd41bda53223cd9');
INSERT INTO `rank_info` VALUES (16, '巅峰榜·韩国', '2020-11-23 16:17:02', 'QQ音乐', '6e5ca21704454916a21d87560dff78d8');
INSERT INTO `rank_info` VALUES (22, '经典老歌榜', '2020-11-23 16:29:35', '百度音乐', '7452ad894b084f9a87364e1106cb63ca');
INSERT INTO `rank_info` VALUES (64, '综艺新歌榜', '2020-11-23 16:17:02', 'QQ音乐', '7ad0ca09edaa4a39a5fb1c7de95cb2ad');
INSERT INTO `rank_info` VALUES (1, '新歌榜', '2020-11-23 16:22:01', '百度音乐', '822c2bd3ad7b400ba0851c0c31ff239b');
INSERT INTO `rank_info` VALUES (30972, '腾讯音乐人原创榜', '2020-11-23 16:16:17', '酷狗音乐', '825b9074932342058f3475bfd83a4f7e');
INSERT INTO `rank_info` VALUES (37361, '酷狗雷达榜', '2020-11-23 16:16:17', '酷狗音乐', '8bddfca2d087440c9ccef6e5d1053a42');
INSERT INTO `rank_info` VALUES (67, '听歌识曲榜', '2020-11-23 16:17:02', 'QQ音乐', '949767ae52ad4b74a2e1244767c58ebc');
INSERT INTO `rank_info` VALUES (22603, '5sing音乐榜', '2020-11-23 16:16:17', '酷狗音乐', '963c7828f4f7400a87a76a4aee67f912');
INSERT INTO `rank_info` VALUES (31312, '日本新歌榜', '2020-11-23 16:16:17', '酷狗音乐', '966ff2307f014548ae53304ad07242a4');
INSERT INTO `rank_info` VALUES (38623, '韩国Melon音乐榜', '2020-11-23 16:16:17', '酷狗音乐', '9784a7245e9046d7a80beb608c52a1f5');
INSERT INTO `rank_info` VALUES (24971, 'DJ热歌榜', '2020-11-23 16:16:17', '酷狗音乐', '9c261448c280474abdea052a1835cd40');
INSERT INTO `rank_info` VALUES (21, '欧美金曲榜', '2020-11-23 16:28:58', '百度音乐', 'a695e5b258394705a62a8ff2199709db');
INSERT INTO `rank_info` VALUES (61, '台湾地区榜', '2020-11-23 16:17:02', 'QQ音乐', 'a757110bb6f8464ba2ab2a335dd7706d');
INSERT INTO `rank_info` VALUES (60, '抖音排行榜', '2020-11-23 16:17:02', 'QQ音乐', 'a8841656b6bb439b805d402862ff6976');
INSERT INTO `rank_info` VALUES (52, '巅峰榜·腾讯音乐人原创榜', '2020-11-23 16:17:02', 'QQ音乐', 'aaa576d1ffea4aa3baf48f113010ce55');
INSERT INTO `rank_info` VALUES (42807, 'joox本地热歌榜', '2020-11-23 16:16:17', '酷狗音乐', 'b552017417cc4a4386ad896f63fdb4d7');
INSERT INTO `rank_info` VALUES (74, 'Q音快手榜', '2020-11-23 16:17:02', 'QQ音乐', 'b6f264f62c0840dbafa4e65601c03b50');
INSERT INTO `rank_info` VALUES (46910, '综艺新歌榜', '2020-11-23 16:16:17', '酷狗音乐', 'b7fc94a14b2049d1bc0d579a8bc0e39b');
INSERT INTO `rank_info` VALUES (4680, '英国单曲榜', '2020-11-23 16:16:17', '酷狗音乐', 'b8e3a102dc6443c791c9778aad0b3384');
INSERT INTO `rank_info` VALUES (75, '有声榜', '2020-11-23 16:17:02', 'QQ音乐', 'bdf446ca6b874083bdc7415cb69170e3');
INSERT INTO `rank_info` VALUES (44412, '酷狗说唱榜', '2020-11-23 16:16:17', '酷狗音乐', 'c26940bdfd0c488cb8fc28bc6b7ca576');
INSERT INTO `rank_info` VALUES (59, '香港地区榜', '2020-11-23 16:17:02', 'QQ音乐', 'c5e21a5f48b64ffbb681677bfcd9079b');
INSERT INTO `rank_info` VALUES (11, '摇滚榜', '2020-11-23 16:23:27', '百度音乐', 'cb80118b2f0145b2b21c8355a4c746ae');
INSERT INTO `rank_info` VALUES (33161, '国风新歌榜', '2020-11-23 16:16:17', '酷狗音乐', 'cd85eeb0939341c5b7b07dae72d29ca2');
INSERT INTO `rank_info` VALUES (2, '热歌榜', '2020-11-23 16:22:41', '百度音乐', 'ceedd1ecb201457cb3b70955d6b40aca');
INSERT INTO `rank_info` VALUES (6666, '酷狗飙升榜', '2020-11-23 16:16:17', '酷狗音乐', 'd0614d6a9af84cd496c0fee87f3d1f0e');
INSERT INTO `rank_info` VALUES (63, 'DJ舞曲榜', '2020-11-23 16:17:02', 'QQ音乐', 'd171d00f988644928e736f4080b0a8e2');
INSERT INTO `rank_info` VALUES (28, '巅峰榜·网络歌曲', '2020-11-23 16:17:02', 'QQ音乐', 'd1ab4818da1540e5acba5293961ad447');
INSERT INTO `rank_info` VALUES (3, '巅峰榜·欧美', '2020-11-23 16:17:02', 'QQ音乐', 'd25b2d4929b44b87929da1b8e6d06fd6');
INSERT INTO `rank_info` VALUES (57, '电音榜', '2020-11-23 16:17:02', 'QQ音乐', 'd5b77c759b6148a891be032c17371147');
INSERT INTO `rank_info` VALUES (29, '巅峰榜·影视金曲', '2020-11-23 16:17:02', 'QQ音乐', 'dee536f8ac0b49c1b4ad0b573dadcfe8');
INSERT INTO `rank_info` VALUES (70, '达人音乐榜', '2020-11-23 16:17:02', 'QQ音乐', 'e2961012f2e8429d8b0be8c6715f9d0f');
INSERT INTO `rank_info` VALUES (33162, 'ACG新歌榜', '2020-11-23 16:16:17', '酷狗音乐', 'ef73ba7be8e746c9aff2c33981d16b40');
INSERT INTO `rank_info` VALUES (31310, '欧美新歌榜', '2020-11-23 16:16:17', '酷狗音乐', 'f0c0b54eaa0548919c03990888224a85');
INSERT INTO `rank_info` VALUES (33160, '电音热歌榜', '2020-11-23 16:16:17', '酷狗音乐', 'f8df0ec0e2df46bb88210c2c7f9b0707');
INSERT INTO `rank_info` VALUES (72, '动漫音乐榜', '2020-11-23 16:17:02', 'QQ音乐', 'f99705555c714d4b8e140ffba548c110');
INSERT INTO `rank_info` VALUES (73, '游戏音乐榜', '2020-11-23 16:17:02', 'QQ音乐', 'faeb2650708c48af867cead719063fbb');
INSERT INTO `rank_info` VALUES (201, '巅峰榜·MV', '2020-11-23 16:17:02', 'QQ音乐', 'fbc0f5d447fd4eae83988a22dcd5cf97');
INSERT INTO `rank_info` VALUES (8888, '酷狗TOP500', '2020-11-23 16:16:17', '酷狗音乐', 'fd9e1bcafcd743fdb77f7e527e73db33');

SET FOREIGN_KEY_CHECKS = 1;
