/*
 Navicat Premium Data Transfer

 Source Server         : RDS_aliyun
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : rm-bp1hul438hq66s09ubo.mysql.rds.aliyuncs.com:3306
 Source Schema         : xsap_dev

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 10/01/2022 10:28:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_class_record
-- ----------------------------
DROP TABLE IF EXISTS `t_class_record`;
CREATE TABLE `t_class_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员id',
  `card_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员卡名',
  `schedule_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '排课记录id',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师评语',
  `check_status` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '用户确认上课与否。1，已上课；0，未上课',
  `reserve_check` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否已预约，默认0',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_modify_time` datetime(0) NULL DEFAULT NULL,
  `version` int(10) UNSIGNED NULL DEFAULT 1,
  `bind_card_id` int(11) NULL DEFAULT NULL COMMENT '绑定会员卡id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_class_member_id`(`member_id`) USING BTREE,
  INDEX `fk_class_schedule_id`(`schedule_id`) USING BTREE,
  CONSTRAINT `fk_class_member_id` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_class_schedule_id` FOREIGN KEY (`schedule_id`) REFERENCES `t_schedule_record` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_class_record
-- ----------------------------
INSERT INTO `t_class_record` VALUES (34, 89, '银卡', 90, '正常预约客户', NULL, 1, 0, '2021-12-30 23:16:59', NULL, 1, 34);
INSERT INTO `t_class_record` VALUES (35, 86, '测试卡', 90, '正常预约客户', NULL, 0, 0, '2021-12-30 23:17:29', NULL, 1, 28);
INSERT INTO `t_class_record` VALUES (36, 90, '测试卡', 90, '正常预约客户', NULL, 0, 0, '2021-12-30 23:17:43', NULL, 1, 27);
INSERT INTO `t_class_record` VALUES (37, 86, '铜卡', 99, '正常预约客户', NULL, 1, 0, '2022-01-01 15:18:15', '2022-01-02 09:29:18', 1, 35);
INSERT INTO `t_class_record` VALUES (38, 85, '测试卡', 100, '正常预约客户', NULL, 1, 0, '2022-01-03 00:56:32', '2022-01-03 21:48:21', 1, 24);
INSERT INTO `t_class_record` VALUES (39, 86, '铜卡', 100, '正常预约客户', NULL, 1, 0, '2022-01-03 00:56:52', '2022-01-03 21:48:21', 1, 35);
INSERT INTO `t_class_record` VALUES (45, 89, '测试卡', 100, '正常预约客户', NULL, 1, 0, '2022-01-03 01:08:35', '2022-01-03 21:48:22', 1, 26);
INSERT INTO `t_class_record` VALUES (46, 85, '银卡', 101, '正常预约客户', NULL, 1, 0, '2022-01-03 01:08:58', NULL, 1, 31);
INSERT INTO `t_class_record` VALUES (47, 86, '金卡', 102, '正常预约客户', NULL, 1, 0, '2022-01-03 01:09:18', '2022-01-04 20:51:39', 1, 29);
INSERT INTO `t_class_record` VALUES (48, 90, '测试卡', 105, '正常预约客户', NULL, 1, 0, '2022-01-03 01:09:39', '2022-01-06 17:41:16', 2, 27);
INSERT INTO `t_class_record` VALUES (49, 87, '测试卡', 106, '正常预约客户', NULL, 1, 0, '2022-01-03 01:18:45', NULL, 1, 25);
INSERT INTO `t_class_record` VALUES (50, 88, '银卡', 111, '正常预约客户', NULL, 1, 0, '2022-01-04 20:42:30', NULL, 1, 36);
INSERT INTO `t_class_record` VALUES (51, 94, '金卡', 111, '正常预约客户', NULL, 0, 0, '2022-01-04 20:42:41', NULL, 1, 39);
INSERT INTO `t_class_record` VALUES (52, 96, '体验卡', 111, '正常预约客户', NULL, 0, 0, '2022-01-04 20:42:56', NULL, 1, 43);
INSERT INTO `t_class_record` VALUES (53, 93, '体验卡', 111, '正常预约客户', NULL, 0, 0, '2022-01-04 20:43:12', NULL, 1, 38);
INSERT INTO `t_class_record` VALUES (58, 89, '测试卡', 118, '正常预约客户', NULL, 0, 0, '2022-01-05 11:29:12', NULL, 1, 26);
INSERT INTO `t_class_record` VALUES (61, 86, '测试卡', 117, '正常预约客户', NULL, 1, 0, '2022-01-05 15:21:28', '2022-01-06 11:41:49', 2, 28);
INSERT INTO `t_class_record` VALUES (63, 85, '测试卡', 118, '正常预约客户', NULL, 0, 0, '2022-01-06 14:21:06', NULL, 1, 24);
INSERT INTO `t_class_record` VALUES (64, 99, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-06 17:27:04', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (65, 99, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-06 17:32:15', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (66, 99, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-06 21:49:55', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (67, 99, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-06 22:00:52', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (68, 99, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-06 22:19:33', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (69, 98, '金卡', 117, '手动添加扣费用户', NULL, 1, 1, '2022-01-07 00:47:12', NULL, 1, 44);
INSERT INTO `t_class_record` VALUES (70, 85, '银卡', 122, '正常预约客户', NULL, 1, 0, '2022-01-07 11:41:16', NULL, 1, 24);
INSERT INTO `t_class_record` VALUES (71, 88, '银卡', 122, '正常预约客户', NULL, 1, 0, '2022-01-07 11:41:31', NULL, 1, 36);
INSERT INTO `t_class_record` VALUES (72, 86, '金卡', 122, '正常预约客户', NULL, 1, 0, '2022-01-07 11:41:40', NULL, 1, 35);
INSERT INTO `t_class_record` VALUES (73, 99, '金卡', 117, '手动添加扣费用户', NULL, 1, 1, '2022-01-07 14:53:54', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (74, 99, '金卡', 117, '手动添加扣费用户', NULL, 1, 1, '2022-01-07 14:58:44', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (75, 101, '体验卡', 118, '手动添加扣费用户', NULL, 1, 1, '2022-01-07 18:56:27', NULL, 1, 46);
INSERT INTO `t_class_record` VALUES (76, 101, '体验卡', 118, '手动添加扣费用户', NULL, 1, 1, '2022-01-07 19:02:14', NULL, 1, 46);
INSERT INTO `t_class_record` VALUES (77, 99, '金卡', 118, '手动添加扣费用户', NULL, 1, 1, '2022-01-07 19:06:28', NULL, 1, 45);
INSERT INTO `t_class_record` VALUES (78, 98, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-08 08:42:00', NULL, 1, 44);
INSERT INTO `t_class_record` VALUES (79, 98, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-08 08:46:06', NULL, 1, 44);
INSERT INTO `t_class_record` VALUES (80, 101, '体验卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-08 09:19:10', NULL, 1, 46);
INSERT INTO `t_class_record` VALUES (81, 101, '体验卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-08 09:23:13', NULL, 1, 46);
INSERT INTO `t_class_record` VALUES (82, 98, '金卡', 116, '手动添加扣费用户', NULL, 1, 1, '2022-01-08 14:11:00', NULL, 1, 44);
INSERT INTO `t_class_record` VALUES (83, 98, '金卡', 113, '手动添加扣费用户', NULL, 1, 1, '2022-01-08 14:28:31', NULL, 1, 44);
INSERT INTO `t_class_record` VALUES (84, 87, '至尊卡', 129, '正常预约客户', NULL, 1, 0, '2022-01-08 18:11:55', '2022-01-08 18:16:42', 2, 33);
INSERT INTO `t_class_record` VALUES (85, 85, '测试卡', 129, '正常预约客户', NULL, 1, 0, '2022-01-08 18:12:10', '2022-01-08 18:16:42', 2, 24);
INSERT INTO `t_class_record` VALUES (86, 90, '测试卡', 132, '正常预约客户', NULL, 1, 0, '2022-01-08 18:25:54', NULL, 1, 27);
INSERT INTO `t_class_record` VALUES (87, 93, '体验卡', 132, '正常预约客户', NULL, 0, 0, '2022-01-08 18:26:11', NULL, 1, 38);

-- ----------------------------
-- Table structure for t_consume_record
-- ----------------------------
DROP TABLE IF EXISTS `t_consume_record`;
CREATE TABLE `t_consume_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `operate_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `card_count_change` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '卡次变化',
  `card_day_change` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '有效天数变化',
  `money_cost` decimal(10, 2) UNSIGNED NULL COMMENT '花费的金额',
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_bind_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员绑定id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  `log_Id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '操作记录id（自建属性）',
  `schedule_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '消费的对象课程【排课】的id（自建属性）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_consume_member_bind_id`(`member_bind_id`) USING BTREE,
  INDEX `t_consume_record_t_member_log_id_fk`(`log_Id`) USING BTREE,
  CONSTRAINT `fk_consume_member_bind_id` FOREIGN KEY (`member_bind_id`) REFERENCES `t_member_bind_record` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_consume_record_t_member_log_id_fk` FOREIGN KEY (`log_Id`) REFERENCES `t_member_log` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消费记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_consume_record
-- ----------------------------
INSERT INTO `t_consume_record` VALUES (39, '绑卡操作', 0, 0, 1.00, '张老师', '办卡的费用', 24, '2021-12-30 13:46:44', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (40, '绑卡操作', 0, 0, 1.00, '张老师', '办卡的费用', 25, '2021-12-30 13:54:54', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (41, '绑卡操作', 0, 0, 1.00, '张老师', '办卡的费用', 26, '2021-12-30 13:56:39', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (42, '绑卡操作', 0, 0, 1.00, '张老师', '办卡的费用', 27, '2021-12-30 14:03:19', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (43, '绑卡操作', 0, 0, 1.00, '张老师', '办卡的费用', 28, '2021-12-30 14:37:08', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (44, '绑卡操作', 0, 0, 999.00, '张老师', '办卡的费用', 29, '2021-12-30 14:39:24', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (45, '绑卡操作', 0, 0, 1.00, '张老师', '办卡的费用', 30, '2021-12-30 15:28:34', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (46, '绑卡操作', 0, 0, 888.00, '张老师', '办卡的费用', 31, '2021-12-30 23:09:41', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (47, '绑卡操作', 0, 0, 666.00, '张老师', '办卡的费用', 32, '2021-12-30 23:10:56', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (48, '绑卡操作', 0, 0, 99999.00, '张老师', '办卡的费用', 33, '2021-12-30 23:11:38', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (49, '绑卡操作', 0, 0, 888.00, '张老师', '办卡的费用', 34, '2021-12-30 23:14:01', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (50, '绑卡操作', 0, 0, 666.00, '张老师', '办卡的费用', 35, '2021-12-30 23:52:31', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (51, '绑卡操作', 0, 0, 888.00, '张老师', '办卡的费用', 36, '2021-12-30 23:53:52', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (52, '绑卡操作', 0, 0, 888.00, '张老师', '办卡的费用', 37, '2022-01-01 12:04:09', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (53, '会员上课扣费', 6, 0, 0.00, '张老师', '赵灵儿上完课了', 34, '2022-01-01 15:09:32', NULL, 1, 118, NULL);
INSERT INTO `t_consume_record` VALUES (54, '会员上课扣费', 4, 0, 0.00, '张老师', NULL, 35, '2022-01-02 09:29:18', NULL, 1, 119, NULL);
INSERT INTO `t_consume_record` VALUES (55, '绑卡操作', 0, 0, 50.00, '张老师', '办卡的费用', 38, '2022-01-02 09:32:41', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (56, '绑卡操作', 0, 0, 999.00, '张老师', '办卡的费用', 39, '2022-01-03 01:23:30', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (57, '会员上课扣费', 1, 0, 0.00, '张老师', 'pdf', 39, '2022-01-03 20:54:36', NULL, 1, 125, NULL);
INSERT INTO `t_consume_record` VALUES (58, '绑卡操作', 0, 0, 999.00, '张老师', '办卡的费用', 40, '2022-01-03 20:57:20', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (59, '会员上课扣费', 1, 0, 0.00, '张老师', '二', 40, '2022-01-03 21:11:35', NULL, 1, 128, NULL);
INSERT INTO `t_consume_record` VALUES (60, '会员上课扣费', 1, 0, 0.00, '张老师', '橱木', 40, '2022-01-03 21:12:41', NULL, 1, 129, NULL);
INSERT INTO `t_consume_record` VALUES (61, '会员上课扣费', 1, 0, 0.00, '张老师', NULL, 24, '2022-01-03 21:48:20', NULL, 1, 130, NULL);
INSERT INTO `t_consume_record` VALUES (62, '会员上课扣费', 2, 0, 0.00, '张老师', NULL, 35, '2022-01-03 21:48:21', NULL, 1, 131, NULL);
INSERT INTO `t_consume_record` VALUES (63, '会员上课扣费', 1, 0, 0.00, '张老师', NULL, 26, '2022-01-03 21:48:21', NULL, 1, 132, NULL);
INSERT INTO `t_consume_record` VALUES (64, '会员上课扣费', 1, 0, 0.00, '张老师', NULL, 24, '2022-01-03 21:48:21', NULL, 1, 133, NULL);
INSERT INTO `t_consume_record` VALUES (65, '会员上课扣费', 2, 0, 0.00, '张老师', NULL, 35, '2022-01-03 21:48:21', NULL, 1, 134, NULL);
INSERT INTO `t_consume_record` VALUES (66, '会员上课扣费', 1, 0, 0.00, '张老师', NULL, 26, '2022-01-03 21:48:22', NULL, 1, 135, NULL);
INSERT INTO `t_consume_record` VALUES (67, '绑卡操作', 0, 0, 999.00, '张老师', '办卡的费用', 41, '2022-01-04 14:15:01', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (68, '会员上课扣费', 2, 0, 1.00, '张老师', '扣费逍遥', 41, '2022-01-04 14:59:55', NULL, 1, 138, NULL);
INSERT INTO `t_consume_record` VALUES (69, '绑卡操作', 0, 0, 50.00, '张老师', '办卡的费用', 42, '2022-01-04 17:12:59', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (70, '绑卡操作', 0, 0, 50.00, '张老师', '办卡的费用', 43, '2022-01-04 17:25:03', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (71, '会员上课扣费', 2, 0, 1.00, '张老师', 'jdls', 42, '2022-01-04 17:35:15', NULL, 1, 144, NULL);
INSERT INTO `t_consume_record` VALUES (72, '会员上课扣费', 2, 0, 0.00, '张老师', '咯i给', 31, '2022-01-04 17:40:37', NULL, 1, 145, NULL);
INSERT INTO `t_consume_record` VALUES (73, '会员上课扣费', 3, 0, 0.00, '张老师', NULL, 29, '2022-01-04 17:41:19', NULL, 1, 146, NULL);
INSERT INTO `t_consume_record` VALUES (74, '会员上课扣费', 2, 0, 1.00, '张老师', 'test', 42, '2022-01-04 17:49:38', NULL, 1, 147, NULL);
INSERT INTO `t_consume_record` VALUES (75, '会员上课扣费', 1, 0, 1.00, '张老师', '测试删除缓存', 42, '2022-01-04 18:14:38', NULL, 1, 148, NULL);
INSERT INTO `t_consume_record` VALUES (76, '会员上课扣费', 1, 0, 2.00, '张老师', '2测试删除缓存', 41, '2022-01-04 18:15:23', NULL, 1, 149, NULL);
INSERT INTO `t_consume_record` VALUES (77, '会员上课扣费', 3, 0, 6.00, '张老师', 'vb', 32, '2022-01-04 20:44:18', NULL, 1, 151, NULL);
INSERT INTO `t_consume_record` VALUES (78, '会员上课扣费', 3, 0, 0.00, '张老师', NULL, 29, '2022-01-04 20:51:22', NULL, 1, 152, NULL);
INSERT INTO `t_consume_record` VALUES (79, '会员上课扣费', 3, 0, 0.00, '张老师', NULL, 29, '2022-01-04 20:51:39', NULL, 1, 153, NULL);
INSERT INTO `t_consume_record` VALUES (80, '会员上课扣费', 3, 0, 0.00, '张老师', NULL, 29, '2022-01-04 21:16:25', NULL, 1, 154, NULL);
INSERT INTO `t_consume_record` VALUES (82, '绑卡操作', 0, 0, 999.00, '张老师', '办卡的费用', 44, '2022-01-04 22:05:20', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (83, '会员上课扣费', 2, 0, 2.00, '张老师', '。。', 44, '2022-01-05 01:33:46', NULL, 1, 158, NULL);
INSERT INTO `t_consume_record` VALUES (84, '绑卡操作', 0, 0, 999.00, '张老师', '办卡的费用', 45, '2022-01-06 11:16:24', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (85, '会员上课扣费操作', 1, 0, 160.00, '张老师', '手动扣费', 45, '2022-01-06 11:21:40', NULL, 1, 193, NULL);
INSERT INTO `t_consume_record` VALUES (86, '会员上课扣费操作', 2, 0, 0.00, '张老师', NULL, 28, '2022-01-06 11:41:49', NULL, 1, 194, NULL);
INSERT INTO `t_consume_record` VALUES (87, '会员上课扣费操作', 5, 0, 0.00, '张老师', '叶凡测试卡', 25, '2022-01-06 14:00:13', NULL, 1, 195, NULL);
INSERT INTO `t_consume_record` VALUES (88, '会员上课扣费操作', 2, 0, 2.00, '张老师', '手动添加的扣费', 45, '2022-01-06 17:27:03', NULL, 1, 196, NULL);
INSERT INTO `t_consume_record` VALUES (89, '会员上课扣费操作', 2, 0, 2.00, '张老师', '扣费第二次手动', 45, '2022-01-06 17:32:15', NULL, 1, 197, NULL);
INSERT INTO `t_consume_record` VALUES (90, '会员上课扣费操作', 5, 0, 0.00, '张老师', NULL, 27, '2022-01-06 17:41:16', NULL, 1, 198, NULL);
INSERT INTO `t_consume_record` VALUES (91, '会员上课扣费操作', 2, 0, 2.00, '张老师', '手动扣费三', 45, '2022-01-06 21:49:55', NULL, 1, 199, NULL);
INSERT INTO `t_consume_record` VALUES (92, '会员上课扣费操作', 2, 0, 3.00, '张老师', '手动扣费四', 45, '2022-01-06 22:00:51', NULL, 1, 200, NULL);
INSERT INTO `t_consume_record` VALUES (93, '会员上课扣费操作', 2, 0, 5.00, '张老师', '手动扣费6', 45, '2022-01-06 22:19:33', NULL, 1, 201, 116);
INSERT INTO `t_consume_record` VALUES (94, '会员上课扣费操作', 2, 0, 3.00, '张老师', '手动扣费。。。。', 44, '2022-01-07 00:47:12', NULL, 1, 202, 117);
INSERT INTO `t_consume_record` VALUES (95, '绑卡操作', 0, 0, 50.00, '张老师', '办卡的费用', 46, '2022-01-07 11:24:16', NULL, 1, NULL, NULL);
INSERT INTO `t_consume_record` VALUES (96, '会员上课扣费操作', 1, 0, 100.00, '张老师', '古', 46, '2022-01-07 11:30:46', NULL, 1, 206, NULL);
INSERT INTO `t_consume_record` VALUES (97, '会员上课扣费操作', 2, 0, 2.00, '张老师', '李老师的课的手动扣费', 45, '2022-01-07 14:53:54', NULL, 1, 209, 117);
INSERT INTO `t_consume_record` VALUES (98, '会员上课扣费操作', 2, 0, 3.00, '张老师', '扣费。', 45, '2022-01-07 14:58:44', NULL, 1, 210, 117);
INSERT INTO `t_consume_record` VALUES (99, '会员上课扣费操作', 2, 0, 6.90, '张老师', '扣费手动课程详情页', 24, '2022-01-07 16:09:26', NULL, 1, 211, 122);
INSERT INTO `t_consume_record` VALUES (100, '会员上课扣费操作', 2, 0, 20.00, '张老师', '手动111', 46, '2022-01-07 18:56:26', NULL, 1, 212, 118);
INSERT INTO `t_consume_record` VALUES (101, '会员上课扣费操作', 3, 0, 8.00, '张老师', '手动。。。。。。1', 46, '2022-01-07 19:02:13', NULL, 1, 213, 118);
INSERT INTO `t_consume_record` VALUES (102, '会员上课扣费操作', 3, 0, 2.00, '张老师', '，', 45, '2022-01-07 19:06:28', NULL, 1, 214, 118);
INSERT INTO `t_consume_record` VALUES (103, '会员上课扣费操作', 2, 0, 3.00, '张老师', '扣费手动', 44, '2022-01-08 08:42:00', NULL, 1, 215, 116);
INSERT INTO `t_consume_record` VALUES (104, '会员上课扣费操作', 3, 0, 10.00, '张老师', '，', 44, '2022-01-08 08:46:05', NULL, 1, 216, 116);
INSERT INTO `t_consume_record` VALUES (105, '会员上课扣费操作', 2, 0, 3.00, '张老师', '扣', 46, '2022-01-08 09:19:10', NULL, 1, 218, 116);
INSERT INTO `t_consume_record` VALUES (106, '会员上课扣费操作', 5, 0, 4.00, '张老师', '5.4', 46, '2022-01-08 09:23:13', NULL, 1, 219, 116);
INSERT INTO `t_consume_record` VALUES (107, '会员上课扣费操作', 2, 0, 0.22, '张老师', '', 36, '2022-01-08 09:34:22', NULL, 1, 220, 122);
INSERT INTO `t_consume_record` VALUES (108, '会员上课扣费操作', 2, 0, 1.78, '张老师', '', 35, '2022-01-08 14:10:02', NULL, 1, 221, 122);
INSERT INTO `t_consume_record` VALUES (109, '会员上课扣费操作', 2, 0, 6.00, '张老师', '手动测试', 44, '2022-01-08 14:11:00', NULL, 1, 222, 116);
INSERT INTO `t_consume_record` VALUES (110, '会员上课扣费操作', 2, 0, 5.00, '张老师', '黑衣人手动扣', 44, '2022-01-08 14:28:31', NULL, 1, 223, 113);
INSERT INTO `t_consume_record` VALUES (111, '会员上课扣费操作', 2, 0, 0.22, '张老师', '张老师确认扣费', 36, '2022-01-08 14:30:52', NULL, 1, 224, 111);
INSERT INTO `t_consume_record` VALUES (112, '会员上课扣费操作', 2, 0, 0.00, '张老师', NULL, 33, '2022-01-08 18:16:42', NULL, 1, 225, NULL);
INSERT INTO `t_consume_record` VALUES (113, '会员上课扣费操作', 2, 0, 0.25, '张老师', NULL, 24, '2022-01-08 18:16:42', NULL, 1, 226, NULL);
INSERT INTO `t_consume_record` VALUES (114, '会员上课扣费操作', 1, 0, 1.00, '张老师', '', 27, '2022-01-09 16:28:35', NULL, 1, 227, 132);

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `duration` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '课程时长',
  `contains` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '课堂容纳人数',
  `color` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡片颜色',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程介绍',
  `times_cost` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '每节课程需花费的次数',
  `limit_sex` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制性别',
  `limit_age` int(10) NULL DEFAULT NULL COMMENT '限制年龄',
  `limit_counts` int(10) NULL DEFAULT NULL COMMENT '限制预约次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES (42, 'MySQL', 45, 20, '#c91616', '<p>MySQL基础</p>', 1, '无限制', 5, 2, '2021-12-30 11:37:00', NULL, 2);
INSERT INTO `t_course` VALUES (43, 'Spring框架', 50, 30, '#1974b5', '<p>spring三剑客</p>', 2, '无限制', 5, 2, '2021-12-30 11:37:46', NULL, 2);
INSERT INTO `t_course` VALUES (44, 'Linux', 50, 20, '#18cccc', '<p>Linux基础操作</p>', 3, '无限制', 7, 2, '2021-12-30 11:38:53', NULL, 2);
INSERT INTO `t_course` VALUES (45, 'ElasticSearch', 40, 30, '#201ce6', '<p>ES基础语法</p>', 3, '男', 9, 2, '2021-12-30 11:40:37', NULL, 2);
INSERT INTO `t_course` VALUES (46, 'SpringBoot', 35, 10, '#cf2562', '<p>SpringBoot进阶</p>', 5, '无限制', 5, 2, '2021-12-30 11:43:04', NULL, 2);
INSERT INTO `t_course` VALUES (47, 'JVM', 60, 30, '#1c22b0', '<p>JVM调优</p>', 5, '无限制', 5, 2, '2021-12-30 11:44:52', NULL, 2);
INSERT INTO `t_course` VALUES (48, 'Shiro', 45, 20, '#b7c41b', '<p>Shiro基本使用</p>', 2, '女', 5, 2, '2021-12-30 11:46:30', NULL, 2);
INSERT INTO `t_course` VALUES (49, 'Nacos', 50, 25, '#54d916', '<p>nacos服务注册&amp;发现与配置管理</p>', 4, '男', 11, 2, '2021-12-30 11:48:20', NULL, 2);
INSERT INTO `t_course` VALUES (50, 'Sentinel', 45, 35, '#db2c14', '<p>限流</p>', 3, '无限制', 5, 2, '2021-12-30 11:49:26', NULL, 2);

-- ----------------------------
-- Table structure for t_course_card
-- ----------------------------
DROP TABLE IF EXISTS `t_course_card`;
CREATE TABLE `t_course_card`  (
  `card_id` bigint(20) UNSIGNED NOT NULL COMMENT '会员卡id',
  `course_id` bigint(20) UNSIGNED NOT NULL COMMENT '课程id',
  PRIMARY KEY (`card_id`, `course_id`) USING BTREE,
  INDEX `fk_course_course_id`(`course_id`) USING BTREE,
  CONSTRAINT `fk_course_card_id` FOREIGN KEY (`card_id`) REFERENCES `t_member_card` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_course_course_id` FOREIGN KEY (`course_id`) REFERENCES `t_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '中间表：课程-会员卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_course_card
-- ----------------------------
INSERT INTO `t_course_card` VALUES (45, 42);
INSERT INTO `t_course_card` VALUES (46, 42);
INSERT INTO `t_course_card` VALUES (48, 42);
INSERT INTO `t_course_card` VALUES (50, 42);
INSERT INTO `t_course_card` VALUES (51, 42);
INSERT INTO `t_course_card` VALUES (45, 43);
INSERT INTO `t_course_card` VALUES (46, 43);
INSERT INTO `t_course_card` VALUES (48, 43);
INSERT INTO `t_course_card` VALUES (49, 43);
INSERT INTO `t_course_card` VALUES (50, 43);
INSERT INTO `t_course_card` VALUES (51, 43);
INSERT INTO `t_course_card` VALUES (45, 44);
INSERT INTO `t_course_card` VALUES (46, 44);
INSERT INTO `t_course_card` VALUES (47, 44);
INSERT INTO `t_course_card` VALUES (48, 44);
INSERT INTO `t_course_card` VALUES (50, 44);
INSERT INTO `t_course_card` VALUES (51, 44);
INSERT INTO `t_course_card` VALUES (45, 46);
INSERT INTO `t_course_card` VALUES (48, 46);
INSERT INTO `t_course_card` VALUES (50, 46);
INSERT INTO `t_course_card` VALUES (51, 46);
INSERT INTO `t_course_card` VALUES (48, 47);
INSERT INTO `t_course_card` VALUES (50, 47);
INSERT INTO `t_course_card` VALUES (51, 47);
INSERT INTO `t_course_card` VALUES (48, 48);
INSERT INTO `t_course_card` VALUES (50, 48);
INSERT INTO `t_course_card` VALUES (51, 48);
INSERT INTO `t_course_card` VALUES (48, 49);
INSERT INTO `t_course_card` VALUES (50, 49);
INSERT INTO `t_course_card` VALUES (51, 49);
INSERT INTO `t_course_card` VALUES (48, 50);
INSERT INTO `t_course_card` VALUES (50, 50);
INSERT INTO `t_course_card` VALUES (51, 50);

-- ----------------------------
-- Table structure for t_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用来登录',
  `sex` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像文件路径',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作角色名，暂不使用',
  `role_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作角色密码',
  `role_type` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '操作角色类型，1，超级管理员；0，普通管理员',
  `role_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作角色邮箱',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除，0有效，1无效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES (1, '张老师', '123456', '女', '2020-09-07', '骨干教师', 'ad8b43ae-c3df-433d-8ddf-b933e90b25e0.jpg', '教spring框架', 'admin', '567', 1, '3496351038@qq.com', 0, '2020-09-03 10:22:27', '2022-01-03 20:03:20', 5);
INSERT INTO `t_employee` VALUES (2, '李老', '123123', '男', '2020-09-09', '骨干教师', '5d0761c1-b3f4-4079-b71b-1b0cdd77c283.jpg', '教前端三剑客', '普通管理员', '11', 0, '3496351038@qq.com', 0, '2020-09-02 14:57:11', '2020-10-18 02:44:40', 1);
INSERT INTO `t_employee` VALUES (3, '黑衣人', '112358', '男', '2020-09-25', '骨干教师', '9065fc87-9721-447d-86e4-932185ebc98e.jpg', '全能', 'test', '111', 0, '3496351038@qq.com', 0, '2020-12-23 13:32:15', NULL, 1);
INSERT INTO `t_employee` VALUES (4, '魏老', '4', '男', '2020-09-26', '骨干教师', '059683ab-4f95-4285-bcdb-6f06f7df9a87.jpg', '微服务开发', 'user1', '123', 0, '3496351038@qq.com', 0, '2020-09-27 11:32:57', NULL, 1);
INSERT INTO `t_employee` VALUES (8, '周老', '8', '男', '2021-12-13', '骨干教师', '472f0f0b-cad5-4dce-9ea6-e353e2d14a76.jpg', '教linux', 'user5', '123', 0, '3496351038@qq.com', 1, '2020-09-27 11:32:57', NULL, 1);
INSERT INTO `t_employee` VALUES (9, '吴老', '92', '男', '1995-02-02', '骨干教师', 'c8b677c6-2cfc-4341-b0a8-4b6be60d2a0b.jpg', '教数据库', '普通管理员', '123', 0, '3496351038@qq.com', 0, '2020-09-27 11:32:57', '2020-10-17 10:23:35', 1);
INSERT INTO `t_employee` VALUES (15, 'admin', '1231234', '女', '2000-02-02', '测试老师介绍', '8c648bef-12ca-4f44-b0dc-c5a24f014c1a.jpg', '测试老师备注', NULL, 'admin', 0, 'test@126.com', 1, '2021-12-30 12:42:08', NULL, 1);
INSERT INTO `t_employee` VALUES (17, 'jack', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '123', 0, NULL, 1, '2022-01-07 13:32:51', NULL, 1);
INSERT INTO `t_employee` VALUES (19, '测试删除老师', '15588884444', '女', '1998-12-28', '测试删除老师介绍', NULL, '测试删除老师备注', NULL, '123', 0, 'test@126.com', 1, '2022-01-09 17:05:19', NULL, 1);

-- ----------------------------
-- Table structure for t_global_reservation_set
-- ----------------------------
DROP TABLE IF EXISTS `t_global_reservation_set`;
CREATE TABLE `t_global_reservation_set`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_day` int(10) NULL DEFAULT NULL COMMENT '可提前预约的天数',
  `end_day` int(10) NULL DEFAULT NULL COMMENT '模式1：提前预约截止天数，上课前',
  `end_time` time(0) NULL DEFAULT NULL COMMENT '模式1：提前预约截止时间(24小时内)，上课前',
  `end_hour` int(10) NULL DEFAULT NULL COMMENT '模式2：提前预约截止小时数，离上课前',
  `cancel_day` int(10) NULL DEFAULT NULL COMMENT '模式1：提前预约取消的距离天数',
  `cancel_time` time(0) NULL DEFAULT NULL COMMENT '模式1：提前预约取消的时间限制（24小时内）',
  `cancel_hour` int(10) NULL DEFAULT NULL COMMENT '模式2：提前预约取消的距离小时数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  `appointment_start_mode` int(1) NOT NULL DEFAULT 1 COMMENT '预约开始时间的模式，1：不限制会员可提前预约天数；2：限制天数',
  `appointment_deadline_mode` int(1) NOT NULL DEFAULT 1 COMMENT '预约截止时间模式；1：不限制截止时间；2：限制为上课前xx小时；3：限制为上课前xx天xx：xx（时间点）',
  `appointment_cancel_mode` int(1) NOT NULL DEFAULT 1 COMMENT '预约取消时间模式；1：不限制取消时间（上课前都可取消）；2：上课前xx小时可取消；3：上课前xx天xx：xx可取消',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全局预约设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_global_reservation_set
-- ----------------------------
INSERT INTO `t_global_reservation_set` VALUES (1, 1, 1, '17:30:00', 2, 1, '12:00:00', 2, '2020-10-21 17:02:12', '2022-01-06 14:20:59', 51, 1, 1, 1);

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像路径',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '用户的逻辑删除，0有效，1无效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES (85, '萧炎', '男', '18899996666', '2000-02-02', '我是萧炎', '46a41b8c-353a-4207-835f-573303f52866.jpg', 0, '2021-12-30 13:45:56', '2021-12-30 15:39:19', 5);
INSERT INTO `t_member` VALUES (86, '林动', '男', '17788889999', '1991-03-01', '我是林动', '8859fe31-385a-407a-a510-a38ad762ce2b.jpg', 0, '2021-12-30 13:53:52', '2021-12-30 15:38:01', 3);
INSERT INTO `t_member` VALUES (87, '叶凡', '男', '19999999999', '2000-02-02', '我是叶凡', 'b86fd292-2767-4d9d-baac-6a94a533e4ad.webp', 0, '2021-12-30 13:54:31', '2021-12-30 15:37:43', 2);
INSERT INTO `t_member` VALUES (88, '楚南', '男', '14455666655', '2000-02-04', '我是楚风', 'f1f5cd8a-648c-4de4-a3af-d2c8f1e82009.jpg', 0, '2021-12-30 13:55:55', '2021-12-30 15:38:15', 3);
INSERT INTO `t_member` VALUES (89, '赵灵儿', '女', '17777777777', '2003-01-29', '我是赵灵儿', '851d1468-2fcc-4e6b-a5d9-35d9e2af31c8.jpg', 0, '2021-12-30 13:56:20', '2021-12-30 15:39:00', 3);
INSERT INTO `t_member` VALUES (90, '任盈盈', '女', '13355666655', '2001-03-02', '我是任盈盈', '1fd6a84a-b5ed-4d35-86cc-9393abe86f8f.jpg', 0, '2021-12-30 14:02:55', '2021-12-30 15:37:28', 3);
INSERT INTO `t_member` VALUES (91, '王腾', '男', '19955555555', '2000-03-03', '大帝之姿王腾', NULL, 1, '2021-12-30 15:26:46', '2021-12-30 15:26:52', 2);
INSERT INTO `t_member` VALUES (92, '测试会员', '男', '11111122222', '2000-03-02', '测试会员', NULL, 1, '2021-12-30 15:28:23', '2021-12-30 15:30:33', 2);
INSERT INTO `t_member` VALUES (93, '令狐冲', '男', '12233225566', '2001-03-02', '我是令狐冲', 'a6dea868-f400-4dfc-a805-6e8815a4a49d.jpg', 0, '2022-01-01 12:03:42', '2022-01-02 14:27:37', 2);
INSERT INTO `t_member` VALUES (94, '东方不败', '女', '16536363655', '2000-02-09', '我是东方sd ', '647dfb99-3500-4c64-8a6d-06f7032711d6.jpg', 0, '2022-01-03 01:23:06', '2022-01-10 00:41:01', 4);
INSERT INTO `t_member` VALUES (95, '林平之', '男', '13322111122', '2003-06-13', '我是林平之', NULL, 1, '2022-01-03 01:25:00', '2022-01-03 01:25:16', 2);
INSERT INTO `t_member` VALUES (96, '张佳严', '女', '18766553322', '2012-03-15', '新会员木月土木dsd', NULL, 0, '2022-01-03 20:47:58', NULL, 2);
INSERT INTO `t_member` VALUES (97, '李逍遥', '男', '17888777788', '2000-03-02', '我是李逍遥', '225bb2f5-ef3b-4fe7-879f-544bfb841630.jpg', 0, '2022-01-04 14:14:26', '2022-01-05 22:44:58', 2);
INSERT INTO `t_member` VALUES (98, '石昊', '男', '16699668899', '2003-01-29', '我叫石昊', '4975caa5-9677-4f7b-974b-ee0f5cfdf811.jpg', 0, '2022-01-04 22:04:36', '2022-01-05 22:43:20', 2);
INSERT INTO `t_member` VALUES (99, '罗小爱', '女', '18809776523', '2017-06-14', '椅套', NULL, 0, '2022-01-06 11:15:15', NULL, 1);
INSERT INTO `t_member` VALUES (100, '上官婉儿', '女', '17756658998', '2005-03-09', '我叫上官婉儿', NULL, 0, '2022-01-06 22:08:19', NULL, 1);
INSERT INTO `t_member` VALUES (101, '王二小', '男', '18695421763', '2016-02-10', '测试用户', NULL, 0, '2022-01-07 11:23:27', NULL, 1);

-- ----------------------------
-- Table structure for t_member_bind_record
-- ----------------------------
DROP TABLE IF EXISTS `t_member_bind_record`;
CREATE TABLE `t_member_bind_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `card_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `valid_count` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '可使用次数',
  `valid_day` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '有效期，按天算',
  `received_money` decimal(10, 2) NULL COMMENT '实收金额',
  `pay_mode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `active_status` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '激活状态，1激活，0非激活',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_bind_member_id`(`member_id`) USING BTREE,
  INDEX `fk_bind_card_id`(`card_id`) USING BTREE,
  CONSTRAINT `fk_bind_card_id` FOREIGN KEY (`card_id`) REFERENCES `t_member_card` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_bind_member_id` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '中间表：会员绑定记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_bind_record
-- ----------------------------
INSERT INTO `t_member_bind_record` VALUES (24, 85, 51, 54, 37, 6.65, '银行卡', '萧炎办了一张金卡', 1, '2021-12-30 13:46:44', '2022-01-08 18:16:42', 5);
INSERT INTO `t_member_bind_record` VALUES (25, 87, 51, 55, 37, 99.00, '银行卡', '叶凡办了至尊卡', 1, '2021-12-30 13:54:54', '2022-01-06 14:00:14', 2);
INSERT INTO `t_member_bind_record` VALUES (26, 89, 51, 58, 37, 55.00, '银行卡', '我是赵灵儿', 1, '2021-12-30 13:56:39', '2022-01-03 21:48:22', 3);
INSERT INTO `t_member_bind_record` VALUES (27, 90, 51, 54, 37, 54.00, '银行卡', '任盈盈办了一张银卡', 1, '2021-12-30 14:03:19', '2022-01-09 16:28:35', 3);
INSERT INTO `t_member_bind_record` VALUES (28, 86, 51, 58, 37, 100.00, '银行卡', '.。', 1, '2021-12-30 14:37:08', '2022-01-06 11:41:49', 2);
INSERT INTO `t_member_bind_record` VALUES (29, 86, 45, 1037, 1029, 100.00, '银行卡', '林动又绑定了一张金卡', 1, '2021-12-30 14:39:24', '2022-01-04 21:16:26', 5);
INSERT INTO `t_member_bind_record` VALUES (30, 92, 51, 60, 37, 100.00, '银行卡', '..', 0, '2021-12-30 15:28:34', '2021-12-30 17:04:16', 2);
INSERT INTO `t_member_bind_record` VALUES (31, 85, 46, 603, 130, 100.00, '银行卡', 'kk', 1, '2021-12-30 23:09:40', '2022-01-04 17:40:37', 2);
INSERT INTO `t_member_bind_record` VALUES (32, 88, 47, 117, 120, 24.00, '支付宝', '10.....', 1, '2021-12-30 23:10:56', '2022-01-04 20:44:18', 3);
INSERT INTO `t_member_bind_record` VALUES (33, 87, 48, 10000047, 10000029, 100.00, '现金', '100.....', 1, '2021-12-30 23:11:38', '2022-01-08 18:16:42', 2);
INSERT INTO `t_member_bind_record` VALUES (34, 89, 46, 599, 130, 100.00, '银行卡', '..', 1, '2021-12-30 23:14:01', '2022-01-01 15:09:33', 2);
INSERT INTO `t_member_bind_record` VALUES (35, 86, 47, 110, 110, 98.22, '现金', '100....', 1, '2021-12-30 23:52:31', '2022-01-08 14:10:02', 5);
INSERT INTO `t_member_bind_record` VALUES (36, 88, 46, 576, 115, 65.56, '现金', '25........', 1, '2021-12-30 23:53:51', '2022-01-08 14:30:52', 3);
INSERT INTO `t_member_bind_record` VALUES (37, 93, 46, 605, 130, 100.00, '现金', '令狐冲绑了一张银卡', 1, '2022-01-01 12:04:08', '2022-01-01 12:04:33', 3);
INSERT INTO `t_member_bind_record` VALUES (38, 93, 50, 15, 14, 50.00, '现金', '..', 1, '2022-01-02 09:32:41', NULL, 1);
INSERT INTO `t_member_bind_record` VALUES (39, 94, 45, 1058, 1119, 1200.00, '现金', '。/。/', 1, '2022-01-03 01:23:30', '2022-01-03 20:54:36', 3);
INSERT INTO `t_member_bind_record` VALUES (40, 96, 45, 1017, 1089, 2000.00, '现金', 'sees', 1, '2022-01-03 20:57:20', '2022-01-04 14:06:49', 9);
INSERT INTO `t_member_bind_record` VALUES (41, 97, 45, 1046, 1029, 97.00, '支付宝', '李逍遥办了一张金卡', 1, '2022-01-04 14:15:01', '2022-01-04 18:15:23', 3);
INSERT INTO `t_member_bind_record` VALUES (42, 97, 50, 60, 47, 107.00, '支付宝', '....', 0, '2022-01-04 17:12:59', '2022-01-05 11:15:43', 8);
INSERT INTO `t_member_bind_record` VALUES (43, 96, 50, 15, 37, 50.00, '支付宝', '0.0.0', 1, '2022-01-04 17:25:03', NULL, 1);
INSERT INTO `t_member_bind_record` VALUES (44, 98, 45, 1036, 1029, 71.00, '现金', '石昊绑了张金卡', 1, '2022-01-04 22:05:19', '2022-01-08 14:28:31', 21);
INSERT INTO `t_member_bind_record` VALUES (45, 99, 45, 991, 999, 819.00, '银行卡', '顶替', 1, '2022-01-06 11:16:24', '2022-01-07 19:06:28', 10);
INSERT INTO `t_member_bind_record` VALUES (46, 101, 50, 4, 17, 165.00, '现金', '0', 1, '2022-01-07 11:24:16', '2022-01-08 09:23:13', 8);

-- ----------------------------
-- Table structure for t_member_card
-- ----------------------------
DROP TABLE IF EXISTS `t_member_card`;
CREATE TABLE `t_member_card`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员卡类型',
  `total_count` int(10) UNSIGNED NULL DEFAULT 24 COMMENT '默认可用次数',
  `total_day` int(10) UNSIGNED NULL DEFAULT 7 COMMENT '默认可用天数',
  `status` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '激活状态，0激活，1非激活',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员卡表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_card
-- ----------------------------
INSERT INTO `t_member_card` VALUES (45, '金卡', 999.00, '金卡可听所有普通课程', '这是金卡', '次卡(无期限)', 999, 999, 0, '2021-12-30 13:02:56', NULL, 1);
INSERT INTO `t_member_card` VALUES (46, '银卡', 888.00, '银卡可听部分课程', '这是银卡', '次卡(无期限)', 555, 100, 0, '2021-12-30 13:27:32', NULL, 1);
INSERT INTO `t_member_card` VALUES (47, '铜卡', 666.00, '铜卡只能听一种课程', '这是铜卡', '次卡(无期限)', 100, 100, 0, '2021-12-30 13:28:53', NULL, 1);
INSERT INTO `t_member_card` VALUES (48, '至尊卡', 99999.00, '至尊卡可以无限期畅听所有课程', '这是至尊卡', '次卡(无期限)', 9999999, 9999999, 0, '2021-12-30 13:30:00', NULL, 1);
INSERT INTO `t_member_card` VALUES (49, '临时卡', 100.00, '临时生效卡', '这是临时卡', '次卡(有期限)', 5, 1, 0, '2021-12-30 13:30:55', NULL, 1);
INSERT INTO `t_member_card` VALUES (50, '体验卡', 50.00, '体验卡可以体验一次所有课程', '这是体验卡', '次卡(有期限)', 5, 7, 0, '2021-12-30 13:31:44', NULL, 1);
INSERT INTO `t_member_card` VALUES (51, '测试卡', 1.00, '测试卡', '这是测试卡', '次卡(有期限)', 10, 7, 0, '2021-12-30 13:32:23', NULL, 1);

-- ----------------------------
-- Table structure for t_member_log
-- ----------------------------
DROP TABLE IF EXISTS `t_member_log`;
CREATE TABLE `t_member_log`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `involve_money` decimal(10, 2) NULL COMMENT '影响的金额',
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员名称',
  `member_bind_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员绑定id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_modify_time` datetime(0) NULL DEFAULT NULL,
  `version` int(10) UNSIGNED NULL DEFAULT 1,
  `card_count_change` int(11) NULL DEFAULT 0 COMMENT '单个操作的卡次变化',
  `card_day_change` int(11) NULL DEFAULT 0 COMMENT '单个操作的天次变化',
  `note` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `card_active_status` tinyint(1) NULL DEFAULT 1 COMMENT '单次操作时会员持有的卡的状态【默认是1，表示激活】',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_log_member_bind_id`(`member_bind_id`) USING BTREE,
  CONSTRAINT `fk_log_member_bind_id` FOREIGN KEY (`member_bind_id`) REFERENCES `t_member_bind_record` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 230 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_log
-- ----------------------------
INSERT INTO `t_member_log` VALUES (90, '绑卡操作', 1.00, '张老师', 24, '2021-12-30 13:46:44', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (91, '绑卡充值操作', 200.00, '张老师', 24, '2021-12-30 13:46:44', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (92, '绑卡操作', 1.00, '张老师', 25, '2021-12-30 13:54:54', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (93, '绑卡充值操作', 99.00, '张老师', 25, '2021-12-30 13:54:54', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (94, '绑卡操作', 1.00, '张老师', 26, '2021-12-30 13:56:39', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (95, '绑卡充值操作', 55.00, '张老师', 26, '2021-12-30 13:56:39', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (96, '绑卡操作', 1.00, '张老师', 27, '2021-12-30 14:03:19', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (97, '绑卡充值操作', 55.00, '张老师', 27, '2021-12-30 14:03:19', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (98, '绑卡操作', 1.00, '张老师', 28, '2021-12-30 14:37:08', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (99, '绑卡充值操作', 100.00, '张老师', 28, '2021-12-30 14:37:08', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (100, '绑卡操作', 999.00, '张老师', 29, '2021-12-30 14:39:24', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (101, '绑卡充值操作', 100.00, '张老师', 29, '2021-12-30 14:39:24', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (102, '绑卡操作', 1.00, '张老师', 30, '2021-12-30 15:28:34', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (103, '绑卡充值操作', 100.00, '张老师', 30, '2021-12-30 15:28:34', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (104, '绑卡操作', 888.00, '张老师', 31, '2021-12-30 23:09:41', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (105, '绑卡充值操作', 100.00, '张老师', 31, '2021-12-30 23:09:41', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (106, '绑卡操作', 666.00, '张老师', 32, '2021-12-30 23:10:56', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (107, '绑卡充值操作', 10.00, '张老师', 32, '2021-12-30 23:10:56', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (108, '绑卡操作', 99999.00, '张老师', 33, '2021-12-30 23:11:38', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (109, '绑卡充值操作', 100.00, '张老师', 33, '2021-12-30 23:11:38', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (110, '绑卡操作', 888.00, '张老师', 34, '2021-12-30 23:14:01', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (111, '绑卡充值操作', 100.00, '张老师', 34, '2021-12-30 23:14:01', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (112, '绑卡操作', 666.00, '张老师', 35, '2021-12-30 23:52:31', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (113, '绑卡充值操作', 100.00, '张老师', 35, '2021-12-30 23:52:31', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (114, '绑卡操作', 888.00, '张老师', 36, '2021-12-30 23:53:51', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (115, '绑卡充值操作', 66.00, '张老师', 36, '2021-12-30 23:53:52', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (116, '绑卡操作', 888.00, '张老师', 37, '2022-01-01 12:04:08', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (117, '绑卡充值操作', 100.00, '张老师', 37, '2022-01-01 12:04:09', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (118, '会员上课扣费', 0.00, '张老师', 34, '2022-01-01 15:09:32', NULL, 1, 6, 0, '赵灵儿上完课了', 1);
INSERT INTO `t_member_log` VALUES (119, '会员上课扣费', 0.00, '张老师', 35, '2022-01-02 09:29:17', NULL, 1, 4, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (120, '绑卡操作', 50.00, '张老师', 38, '2022-01-02 09:32:41', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (121, '绑卡充值操作', 50.00, '张老师', 38, '2022-01-02 09:32:41', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (122, '绑卡操作', 999.00, '张老师', 39, '2022-01-03 01:23:30', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (123, '绑卡充值操作', 200.00, '张老师', 39, '2022-01-03 01:23:30', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (124, '充值操作', 1000.00, '张老师', 39, '2022-01-03 20:54:17', NULL, 1, 10, 90, 'sdfdssds ', 1);
INSERT INTO `t_member_log` VALUES (125, '会员上课扣费', 0.00, '张老师', 39, '2022-01-03 20:54:36', NULL, 1, 1, 0, 'pdf', 1);
INSERT INTO `t_member_log` VALUES (126, '绑卡操作', 999.00, '张老师', 40, '2022-01-03 20:57:20', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (127, '绑卡充值操作', 2000.00, '张老师', 40, '2022-01-03 20:57:20', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (128, '会员上课扣费', 0.00, '张老师', 40, '2022-01-03 21:11:35', NULL, 1, 1, 0, '二', 1);
INSERT INTO `t_member_log` VALUES (129, '会员上课扣费', 0.00, '张老师', 40, '2022-01-03 21:12:41', NULL, 1, 1, 0, '橱木', 1);
INSERT INTO `t_member_log` VALUES (130, '会员上课扣费', 0.00, '张老师', 24, '2022-01-03 21:48:20', NULL, 1, 1, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (131, '会员上课扣费', 0.00, '张老师', 35, '2022-01-03 21:48:20', NULL, 1, 2, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (132, '会员上课扣费', 0.00, '张老师', 26, '2022-01-03 21:48:21', NULL, 1, 1, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (133, '会员上课扣费', 0.00, '张老师', 24, '2022-01-03 21:48:21', NULL, 1, 1, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (134, '会员上课扣费', 0.00, '张老师', 35, '2022-01-03 21:48:21', NULL, 1, 2, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (135, '会员上课扣费', 0.00, '张老师', 26, '2022-01-03 21:48:22', NULL, 1, 1, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (136, '绑卡操作', 999.00, '张老师', 41, '2022-01-04 14:15:01', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (137, '绑卡充值操作', 100.00, '张老师', 41, '2022-01-04 14:15:01', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (138, '会员上课扣费', 1.00, '张老师', 41, '2022-01-04 14:59:55', NULL, 1, 2, 0, '扣费逍遥', 1);
INSERT INTO `t_member_log` VALUES (139, '绑卡操作', 50.00, '张老师', 42, '2022-01-04 17:12:59', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (140, '绑卡充值操作', 100.00, '张老师', 42, '2022-01-04 17:12:59', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (141, '绑卡操作', 50.00, '张老师', 43, '2022-01-04 17:25:03', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (142, '绑卡充值操作', 50.00, '张老师', 43, '2022-01-04 17:25:03', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (143, '充值操作', 10.00, '张老师', 42, '2022-01-04 17:32:08', NULL, 1, 10, 10, '10......', 1);
INSERT INTO `t_member_log` VALUES (144, '会员上课扣费', 1.00, '张老师', 42, '2022-01-04 17:35:15', NULL, 1, 2, 0, 'jdls', 1);
INSERT INTO `t_member_log` VALUES (145, '会员上课扣费', 0.00, '张老师', 31, '2022-01-04 17:40:37', NULL, 1, 2, 0, '咯i给', 1);
INSERT INTO `t_member_log` VALUES (146, '会员上课扣费', 0.00, '张老师', 29, '2022-01-04 17:41:19', NULL, 1, 3, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (147, '会员上课扣费', 1.00, '张老师', 42, '2022-01-04 17:49:38', NULL, 1, 2, 0, 'test', 1);
INSERT INTO `t_member_log` VALUES (148, '会员上课扣费', 1.00, '张老师', 42, '2022-01-04 18:14:38', NULL, 1, 1, 0, '测试删除缓存', 1);
INSERT INTO `t_member_log` VALUES (149, '会员上课扣费', 2.00, '张老师', 41, '2022-01-04 18:15:23', NULL, 1, 1, 0, '2测试删除缓存', 1);
INSERT INTO `t_member_log` VALUES (150, '充值操作', 20.00, '张老师', 32, '2022-01-04 20:43:57', NULL, 1, 10, 10, 'cccc', 1);
INSERT INTO `t_member_log` VALUES (151, '会员上课扣费', 6.00, '张老师', 32, '2022-01-04 20:44:18', NULL, 1, 3, 0, 'vb', 1);
INSERT INTO `t_member_log` VALUES (152, '会员上课扣费', 0.00, '张老师', 29, '2022-01-04 20:51:22', NULL, 1, 3, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (153, '会员上课扣费', 0.00, '张老师', 29, '2022-01-04 20:51:39', NULL, 1, 3, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (154, '会员上课扣费', 0.00, '张老师', 29, '2022-01-04 21:16:25', NULL, 1, 3, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (156, '绑卡操作', 999.00, '张老师', 44, '2022-01-04 22:05:19', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (157, '绑卡充值操作', 100.00, '张老师', 44, '2022-01-04 22:05:20', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (158, '会员上课扣费', 2.00, '张老师', 44, '2022-01-05 01:33:46', NULL, 1, 2, 0, '。。', 1);
INSERT INTO `t_member_log` VALUES (159, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:29:49', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (160, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:32:50', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (161, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:34:07', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (162, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:34:10', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (163, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:44:46', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (164, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:44:48', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (165, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:46:46', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (166, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 14:46:47', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (167, '停用会员卡操作', 0.00, '张老师', 40, '2022-01-05 14:53:35', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (168, '激活会员卡操作', 0.00, '张老师', 40, '2022-01-05 15:05:44', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (169, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:16:29', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (170, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:16:30', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (171, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:16:32', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (172, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:16:33', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (173, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:19:34', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (174, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:26:06', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (175, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:26:07', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (176, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:26:10', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (177, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:27:43', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (178, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:27:45', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (179, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:28:43', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (180, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:28:45', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (181, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:28:47', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (182, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:28:48', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (183, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:29:49', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (184, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:29:50', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (185, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:38:28', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (186, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 22:38:30', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (187, '停用会员卡操作', 0.00, '张老师', 44, '2022-01-05 23:13:24', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (188, '激活会员卡操作', 0.00, '张老师', 44, '2022-01-05 23:13:32', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (189, '绑卡操作', 999.00, '张老师', 45, '2022-01-06 11:16:24', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (190, '绑卡充值操作', 1000.00, '张老师', 45, '2022-01-06 11:16:24', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (191, '停用会员卡操作', 0.00, '张老师', 45, '2022-01-06 11:20:58', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (192, '激活会员卡操作', 0.00, '张老师', 45, '2022-01-06 11:21:17', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (193, '会员上课扣费操作', 160.00, '张老师', 45, '2022-01-06 11:21:39', NULL, 1, 1, 0, '手动扣费', 1);
INSERT INTO `t_member_log` VALUES (194, '会员上课扣费操作', 0.00, '张老师', 28, '2022-01-06 11:41:49', NULL, 1, 2, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (195, '会员上课扣费操作', 0.00, '张老师', 25, '2022-01-06 14:00:13', NULL, 1, 5, 0, '叶凡测试卡', 1);
INSERT INTO `t_member_log` VALUES (196, '会员上课扣费操作', 2.00, '张老师', 45, '2022-01-06 17:27:03', NULL, 1, 2, 0, '手动添加的扣费', 1);
INSERT INTO `t_member_log` VALUES (197, '会员上课扣费操作', 2.00, '张老师', 45, '2022-01-06 17:32:15', NULL, 1, 2, 0, '扣费第二次手动', 1);
INSERT INTO `t_member_log` VALUES (198, '会员上课扣费操作', 0.00, '张老师', 27, '2022-01-06 17:41:16', NULL, 1, 5, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (199, '会员上课扣费操作', 2.00, '张老师', 45, '2022-01-06 21:49:54', NULL, 1, 2, 0, '手动扣费三', 1);
INSERT INTO `t_member_log` VALUES (200, '会员上课扣费操作', 3.00, '张老师', 45, '2022-01-06 22:00:51', NULL, 1, 2, 0, '手动扣费四', 1);
INSERT INTO `t_member_log` VALUES (201, '会员上课扣费操作', 5.00, '张老师', 45, '2022-01-06 22:19:33', NULL, 1, 2, 0, '手动扣费6', 1);
INSERT INTO `t_member_log` VALUES (202, '会员上课扣费操作', 3.00, '张老师', 44, '2022-01-07 00:47:12', NULL, 1, 2, 0, '手动扣费。。。。', 1);
INSERT INTO `t_member_log` VALUES (203, '绑卡操作', 50.00, '张老师', 46, '2022-01-07 11:24:16', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (204, '绑卡充值操作', 0.00, '张老师', 46, '2022-01-07 11:24:17', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (205, '充值操作', 200.00, '张老师', 46, '2022-01-07 11:26:58', NULL, 1, 2, 0, '地下城一', 1);
INSERT INTO `t_member_log` VALUES (206, '会员上课扣费操作', 100.00, '张老师', 46, '2022-01-07 11:30:46', NULL, 1, 1, 0, '古', 1);
INSERT INTO `t_member_log` VALUES (207, '停用会员卡操作', 0.00, '张老师', 46, '2022-01-07 11:31:40', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (208, '激活会员卡操作', 0.00, '张老师', 46, '2022-01-07 11:32:29', NULL, 1, 0, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (209, '会员上课扣费操作', 2.00, '张老师', 45, '2022-01-07 14:53:54', NULL, 1, 2, 0, '李老师的课的手动扣费', 1);
INSERT INTO `t_member_log` VALUES (210, '会员上课扣费操作', 3.00, '张老师', 45, '2022-01-07 14:58:44', NULL, 1, 2, 0, '扣费。', 1);
INSERT INTO `t_member_log` VALUES (211, '会员上课扣费操作', 6.90, '张老师', 24, '2022-01-07 16:09:26', NULL, 1, 2, 0, '扣费手动课程详情页', 1);
INSERT INTO `t_member_log` VALUES (212, '会员上课扣费操作', 20.00, '张老师', 46, '2022-01-07 18:56:26', NULL, 1, 2, 0, '手动111', 1);
INSERT INTO `t_member_log` VALUES (213, '会员上课扣费操作', 8.00, '张老师', 46, '2022-01-07 19:02:13', NULL, 1, 3, 0, '手动。。。。。。1', 1);
INSERT INTO `t_member_log` VALUES (214, '会员上课扣费操作', 2.00, '张老师', 45, '2022-01-07 19:06:27', NULL, 1, 3, 0, '，', 1);
INSERT INTO `t_member_log` VALUES (215, '会员上课扣费操作', 3.00, '张老师', 44, '2022-01-08 08:42:00', NULL, 1, 2, 0, '扣费手动', 1);
INSERT INTO `t_member_log` VALUES (216, '会员上课扣费操作', 10.00, '张老师', 44, '2022-01-08 08:46:05', NULL, 1, 3, 0, '，', 1);
INSERT INTO `t_member_log` VALUES (217, '充值操作', 100.00, '张老师', 46, '2022-01-08 09:18:42', NULL, 1, 10, 10, '充值10次', 1);
INSERT INTO `t_member_log` VALUES (218, '会员上课扣费操作', 3.00, '张老师', 46, '2022-01-08 09:19:10', NULL, 1, 2, 0, '扣', 1);
INSERT INTO `t_member_log` VALUES (219, '会员上课扣费操作', 4.00, '张老师', 46, '2022-01-08 09:23:13', NULL, 1, 5, 0, '5.4', 1);
INSERT INTO `t_member_log` VALUES (220, '会员上课扣费操作', 0.22, '张老师', 36, '2022-01-08 09:34:22', NULL, 1, 2, 0, '', 1);
INSERT INTO `t_member_log` VALUES (221, '会员上课扣费操作', 1.78, '张老师', 35, '2022-01-08 14:10:02', NULL, 1, 2, 0, '', 1);
INSERT INTO `t_member_log` VALUES (222, '会员上课扣费操作', 6.00, '张老师', 44, '2022-01-08 14:11:00', NULL, 1, 2, 0, '手动测试', 1);
INSERT INTO `t_member_log` VALUES (223, '会员上课扣费操作', 5.00, '张老师', 44, '2022-01-08 14:28:31', NULL, 1, 2, 0, '黑衣人手动扣', 1);
INSERT INTO `t_member_log` VALUES (224, '会员上课扣费操作', 0.22, '张老师', 36, '2022-01-08 14:30:52', NULL, 1, 2, 0, '张老师确认扣费', 1);
INSERT INTO `t_member_log` VALUES (225, '会员上课扣费操作', 0.00, '张老师', 33, '2022-01-08 18:16:42', NULL, 1, 2, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (226, '会员上课扣费操作', 0.25, '张老师', 24, '2022-01-08 18:16:42', NULL, 1, 2, 0, NULL, 1);
INSERT INTO `t_member_log` VALUES (227, '会员上课扣费操作', 1.00, '张老师', 27, '2022-01-09 16:28:35', NULL, 1, 1, 0, '', 1);
INSERT INTO `t_member_log` VALUES (228, '停用会员卡操作', 0.00, '张老师', 39, '2022-01-10 00:41:08', NULL, 1, 0, 0, NULL, 0);
INSERT INTO `t_member_log` VALUES (229, '激活会员卡操作', 0.00, '张老师', 39, '2022-01-10 00:41:09', NULL, 1, 0, 0, NULL, 1);

-- ----------------------------
-- Table structure for t_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `t_recharge_record`;
CREATE TABLE `t_recharge_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `add_count` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '充值可用次数',
  `add_day` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '延长有效天数',
  `received_money` decimal(10, 2) UNSIGNED NULL COMMENT '实收金额',
  `pay_mode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_bind_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员绑定id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_modify_time` datetime(0) NULL DEFAULT NULL,
  `version` int(10) UNSIGNED NULL DEFAULT 1,
  `log_Id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '操作记录id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_charge_member_bind_id`(`member_bind_id`) USING BTREE,
  INDEX `t_recharge_record_t_member_log_id_fk`(`log_Id`) USING BTREE,
  CONSTRAINT `fk_charge_member_bind_id` FOREIGN KEY (`member_bind_id`) REFERENCES `t_member_bind_record` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_recharge_record_t_member_log_id_fk` FOREIGN KEY (`log_Id`) REFERENCES `t_member_log` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充值记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_recharge_record
-- ----------------------------
INSERT INTO `t_recharge_record` VALUES (54, 10, 7, 1.00, '银行卡', '张老师', '这是测试卡', 24, '2021-12-30 13:46:44', NULL, 1, 90);
INSERT INTO `t_recharge_record` VALUES (55, 50, 30, 200.00, '银行卡', '张老师', '萧炎办了一张金卡', 24, '2021-12-30 13:46:44', NULL, 1, 91);
INSERT INTO `t_recharge_record` VALUES (56, 10, 7, 1.00, '银行卡', '张老师', '这是测试卡', 25, '2021-12-30 13:54:54', NULL, 1, 92);
INSERT INTO `t_recharge_record` VALUES (57, 50, 30, 99.00, '银行卡', '张老师', '叶凡办了至尊卡', 25, '2021-12-30 13:54:54', NULL, 1, 93);
INSERT INTO `t_recharge_record` VALUES (58, 10, 7, 1.00, '银行卡', '张老师', '这是测试卡', 26, '2021-12-30 13:56:39', NULL, 1, 94);
INSERT INTO `t_recharge_record` VALUES (59, 50, 30, 55.00, '银行卡', '张老师', '我是赵灵儿', 26, '2021-12-30 13:56:39', NULL, 1, 95);
INSERT INTO `t_recharge_record` VALUES (60, 10, 7, 1.00, '银行卡', '张老师', '这是测试卡', 27, '2021-12-30 14:03:19', NULL, 1, 96);
INSERT INTO `t_recharge_record` VALUES (61, 50, 30, 55.00, '银行卡', '张老师', '任盈盈办了一张银卡', 27, '2021-12-30 14:03:19', NULL, 1, 97);
INSERT INTO `t_recharge_record` VALUES (62, 10, 7, 1.00, '银行卡', '张老师', '这是测试卡', 28, '2021-12-30 14:37:08', NULL, 1, 98);
INSERT INTO `t_recharge_record` VALUES (63, 50, 30, 100.00, '银行卡', '张老师', '.。', 28, '2021-12-30 14:37:08', NULL, 1, 99);
INSERT INTO `t_recharge_record` VALUES (64, 999, 999, 999.00, '银行卡', '张老师', '这是金卡', 29, '2021-12-30 14:39:24', NULL, 1, 100);
INSERT INTO `t_recharge_record` VALUES (65, 50, 30, 100.00, '银行卡', '张老师', '林动又绑定了一张金卡', 29, '2021-12-30 14:39:24', NULL, 1, 101);
INSERT INTO `t_recharge_record` VALUES (66, 10, 7, 1.00, '银行卡', '张老师', '这是测试卡', 30, '2021-12-30 15:28:34', NULL, 1, 102);
INSERT INTO `t_recharge_record` VALUES (67, 50, 30, 100.00, '银行卡', '张老师', '..', 30, '2021-12-30 15:28:34', NULL, 1, 103);
INSERT INTO `t_recharge_record` VALUES (68, 555, 100, 888.00, '银行卡', '张老师', '这是银卡', 31, '2021-12-30 23:09:41', NULL, 1, 104);
INSERT INTO `t_recharge_record` VALUES (69, 50, 30, 100.00, '银行卡', '张老师', 'kk', 31, '2021-12-30 23:09:41', NULL, 1, 105);
INSERT INTO `t_recharge_record` VALUES (70, 100, 100, 666.00, '微信', '张老师', '这是铜卡', 32, '2021-12-30 23:10:56', NULL, 1, 106);
INSERT INTO `t_recharge_record` VALUES (71, 10, 10, 10.00, '微信', '张老师', '10.....', 32, '2021-12-30 23:10:56', NULL, 1, 107);
INSERT INTO `t_recharge_record` VALUES (72, 9999999, 9999999, 99999.00, '现金', '张老师', '这是至尊卡', 33, '2021-12-30 23:11:38', NULL, 1, 108);
INSERT INTO `t_recharge_record` VALUES (73, 50, 30, 100.00, '现金', '张老师', '100.....', 33, '2021-12-30 23:11:38', NULL, 1, 109);
INSERT INTO `t_recharge_record` VALUES (74, 555, 100, 888.00, '银行卡', '张老师', '这是银卡', 34, '2021-12-30 23:14:01', NULL, 1, 110);
INSERT INTO `t_recharge_record` VALUES (75, 50, 30, 100.00, '银行卡', '张老师', '..', 34, '2021-12-30 23:14:01', NULL, 1, 111);
INSERT INTO `t_recharge_record` VALUES (76, 100, 100, 666.00, '现金', '张老师', '这是铜卡', 35, '2021-12-30 23:52:31', NULL, 1, 112);
INSERT INTO `t_recharge_record` VALUES (77, 20, 10, 100.00, '现金', '张老师', '100....', 35, '2021-12-30 23:52:31', NULL, 1, 113);
INSERT INTO `t_recharge_record` VALUES (78, 555, 100, 888.00, '现金', '张老师', '这是银卡', 36, '2021-12-30 23:53:51', NULL, 1, 114);
INSERT INTO `t_recharge_record` VALUES (79, 25, 15, 66.00, '现金', '张老师', '25........', 36, '2021-12-30 23:53:52', NULL, 1, 115);
INSERT INTO `t_recharge_record` VALUES (80, 555, 100, 888.00, '现金', '张老师', '这是银卡', 37, '2022-01-01 12:04:08', NULL, 1, 116);
INSERT INTO `t_recharge_record` VALUES (81, 50, 30, 100.00, '现金', '张老师', '令狐冲绑了一张银卡', 37, '2022-01-01 12:04:09', NULL, 1, 117);
INSERT INTO `t_recharge_record` VALUES (82, 5, 7, 50.00, '现金', '张老师', '这是体验卡', 38, '2022-01-02 09:32:41', NULL, 1, 120);
INSERT INTO `t_recharge_record` VALUES (83, 10, 7, 50.00, '现金', '张老师', '..', 38, '2022-01-02 09:32:41', NULL, 1, 121);
INSERT INTO `t_recharge_record` VALUES (84, 999, 999, 999.00, '现金', '张老师', '这是金卡', 39, '2022-01-03 01:23:30', NULL, 1, 122);
INSERT INTO `t_recharge_record` VALUES (85, 50, 30, 200.00, '现金', '张老师', '。/。/', 39, '2022-01-03 01:23:30', NULL, 1, 123);
INSERT INTO `t_recharge_record` VALUES (86, 10, 90, 1000.00, '现金', '张老师', 'sdfdssds ', 39, '2022-01-03 20:54:17', NULL, 1, 124);
INSERT INTO `t_recharge_record` VALUES (87, 999, 999, 999.00, '现金', '张老师', '这是金卡', 40, '2022-01-03 20:57:20', NULL, 1, 126);
INSERT INTO `t_recharge_record` VALUES (88, 20, 90, 2000.00, '现金', '张老师', 'sees', 40, '2022-01-03 20:57:20', NULL, 1, 127);
INSERT INTO `t_recharge_record` VALUES (89, 999, 999, 999.00, '支付宝', '张老师', '这是金卡', 41, '2022-01-04 14:15:01', NULL, 1, 136);
INSERT INTO `t_recharge_record` VALUES (90, 50, 30, 100.00, '支付宝', '张老师', '李逍遥办了一张金卡', 41, '2022-01-04 14:15:01', NULL, 1, 137);
INSERT INTO `t_recharge_record` VALUES (91, 5, 7, 50.00, '支付宝', '张老师', '这是体验卡', 42, '2022-01-04 17:12:59', NULL, 1, 139);
INSERT INTO `t_recharge_record` VALUES (92, 50, 30, 100.00, '支付宝', '张老师', '....', 42, '2022-01-04 17:12:59', NULL, 1, 140);
INSERT INTO `t_recharge_record` VALUES (93, 5, 7, 50.00, '支付宝', '张老师', '这是体验卡', 43, '2022-01-04 17:25:03', NULL, 1, 141);
INSERT INTO `t_recharge_record` VALUES (94, 10, 30, 50.00, '支付宝', '张老师', '0.0.0', 43, '2022-01-04 17:25:03', NULL, 1, 142);
INSERT INTO `t_recharge_record` VALUES (95, 10, 10, 10.00, '支付宝', '张老师', '10......', 42, '2022-01-04 17:32:08', NULL, 1, 143);
INSERT INTO `t_recharge_record` VALUES (96, 10, 10, 20.00, '支付宝', '张老师', 'cccc', 32, '2022-01-04 20:43:57', NULL, 1, 150);
INSERT INTO `t_recharge_record` VALUES (97, 999, 999, 999.00, '现金', '张老师', '这是金卡', 44, '2022-01-04 22:05:19', NULL, 1, 156);
INSERT INTO `t_recharge_record` VALUES (98, 50, 30, 100.00, '现金', '张老师', '石昊绑了张金卡', 44, '2022-01-04 22:05:20', NULL, 1, 157);
INSERT INTO `t_recharge_record` VALUES (99, 999, 999, 999.00, '银行卡', '张老师', '这是金卡', 45, '2022-01-06 11:16:24', NULL, 1, 189);
INSERT INTO `t_recharge_record` VALUES (100, 10, 0, 1000.00, '银行卡', '张老师', '顶替', 45, '2022-01-06 11:16:24', NULL, 1, 190);
INSERT INTO `t_recharge_record` VALUES (101, 5, 7, 50.00, '支付宝', '张老师', '这是体验卡', 46, '2022-01-07 11:24:16', NULL, 1, 203);
INSERT INTO `t_recharge_record` VALUES (102, 0, 0, 0.00, '支付宝', '张老师', '0', 46, '2022-01-07 11:24:17', NULL, 1, 204);
INSERT INTO `t_recharge_record` VALUES (103, 2, 0, 200.00, '银行卡', '张老师', '地下城一', 46, '2022-01-07 11:26:58', NULL, 1, 205);
INSERT INTO `t_recharge_record` VALUES (104, 10, 10, 100.00, '现金', '张老师', '充值10次', 46, '2022-01-08 09:18:42', NULL, 1, 217);

-- ----------------------------
-- Table structure for t_reservation_record
-- ----------------------------
DROP TABLE IF EXISTS `t_reservation_record`;
CREATE TABLE `t_reservation_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '预约状态，1有效，0无效',
  `reserve_nums` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '单次操作预约人数',
  `cancel_times` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '取消次数统计',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师评语',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上课备注',
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `member_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员id',
  `card_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员指定的会员卡来预约',
  `schedule_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '排课记录id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_modify_time` datetime(0) NULL DEFAULT NULL,
  `version` int(10) UNSIGNED NULL DEFAULT 1,
  `card_id` int(11) NULL DEFAULT NULL COMMENT '会员卡id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_reserve_member_id`(`member_id`) USING BTREE,
  INDEX `fk_reserve_schedule_id`(`schedule_id`) USING BTREE,
  CONSTRAINT `fk_reserve_member_id` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_reserve_schedule_id` FOREIGN KEY (`schedule_id`) REFERENCES `t_schedule_record` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '预约记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_reservation_record
-- ----------------------------
INSERT INTO `t_reservation_record` VALUES (72, 1, 2, 0, NULL, '。。', NULL, '张老师', 89, '银卡', 90, '2021-12-30 23:16:59', NULL, 1, 34);
INSERT INTO `t_reservation_record` VALUES (73, 1, 2, 0, NULL, '。', NULL, '张老师', 86, '测试卡', 90, '2021-12-30 23:17:28', NULL, 1, 28);
INSERT INTO `t_reservation_record` VALUES (74, 1, 1, 0, NULL, '，，', NULL, '张老师', 90, '测试卡', 90, '2021-12-30 23:17:43', NULL, 1, 27);
INSERT INTO `t_reservation_record` VALUES (75, 1, 2, 0, NULL, 'test', NULL, '张老师', 86, '铜卡', 99, '2022-01-01 15:18:14', NULL, 1, 35);
INSERT INTO `t_reservation_record` VALUES (76, 1, 1, 0, NULL, '.....', NULL, '张老师', 85, '测试卡', 100, '2022-01-03 00:56:31', NULL, 1, 24);
INSERT INTO `t_reservation_record` VALUES (77, 1, 2, 0, NULL, '1', NULL, '张老师', 86, '铜卡', 100, '2022-01-03 00:56:51', NULL, 1, 35);
INSERT INTO `t_reservation_record` VALUES (79, 0, 1, 4, NULL, '33', NULL, '张老师', 88, '银卡', 100, '2022-01-03 01:07:45', '2022-01-03 01:07:38', 5, 36);
INSERT INTO `t_reservation_record` VALUES (80, 1, 1, 0, NULL, '233', NULL, '张老师', 89, '测试卡', 100, '2022-01-03 01:08:35', NULL, 1, 26);
INSERT INTO `t_reservation_record` VALUES (81, 1, 1, 0, NULL, '', NULL, '张老师', 85, '银卡', 101, '2022-01-03 01:08:58', NULL, 1, 31);
INSERT INTO `t_reservation_record` VALUES (82, 1, 1, 0, NULL, '', NULL, '张老师', 86, '金卡', 102, '2022-01-03 01:09:18', NULL, 1, 29);
INSERT INTO `t_reservation_record` VALUES (83, 1, 1, 0, NULL, '', NULL, '张老师', 90, '测试卡', 105, '2022-01-03 01:09:39', NULL, 1, 27);
INSERT INTO `t_reservation_record` VALUES (84, 1, 1, 0, NULL, '', NULL, '张老师', 87, '测试卡', 106, '2022-01-03 01:18:45', NULL, 1, 25);
INSERT INTO `t_reservation_record` VALUES (85, 1, 1, 0, NULL, 'cc', NULL, '张老师', 88, '银卡', 111, '2022-01-04 20:42:30', NULL, 1, 36);
INSERT INTO `t_reservation_record` VALUES (86, 1, 2, 0, NULL, 'dd', NULL, '张老师', 94, '金卡', 111, '2022-01-04 20:42:41', NULL, 1, 39);
INSERT INTO `t_reservation_record` VALUES (87, 1, 1, 0, NULL, 'ss', NULL, '张老师', 96, '体验卡', 111, '2022-01-04 20:42:56', NULL, 1, 43);
INSERT INTO `t_reservation_record` VALUES (88, 1, 1, 0, NULL, 's', NULL, '张老师', 93, '体验卡', 111, '2022-01-04 20:43:11', NULL, 1, 38);
INSERT INTO `t_reservation_record` VALUES (89, 0, 1, 4, NULL, '欠妥 ', NULL, '张老师', 97, '金卡', 118, '2022-01-05 11:25:40', '2022-01-05 11:23:12', 5, 42);
INSERT INTO `t_reservation_record` VALUES (90, 1, 1, 0, NULL, '在', NULL, '张老师', 89, '测试卡', 118, '2022-01-05 11:29:12', NULL, 1, 26);
INSERT INTO `t_reservation_record` VALUES (91, 0, 1, 1, NULL, '体验卡。。。', NULL, '张老师', 96, '体验卡', 116, '2022-01-05 15:04:11', NULL, 2, 43);
INSERT INTO `t_reservation_record` VALUES (92, 0, 2, 1, NULL, '222', NULL, '张老师', 85, '银卡', 117, '2022-01-05 15:21:56', NULL, 2, 31);
INSERT INTO `t_reservation_record` VALUES (93, 1, 1, 0, NULL, '1', NULL, '张老师', 86, '测试卡', 117, '2022-01-05 15:21:28', NULL, 1, 28);
INSERT INTO `t_reservation_record` VALUES (94, 0, 1, 1, NULL, '11', NULL, '张老师', 88, '铜卡', 117, '2022-01-05 15:21:50', NULL, 2, 32);
INSERT INTO `t_reservation_record` VALUES (95, 1, 2, 0, NULL, '', NULL, '张老师', 85, '测试卡', 118, '2022-01-06 14:21:05', NULL, 1, 24);
INSERT INTO `t_reservation_record` VALUES (96, 1, 1, 0, NULL, '夺', NULL, '张老师', 85, '银卡', 122, '2022-01-07 11:41:16', NULL, 1, 24);
INSERT INTO `t_reservation_record` VALUES (97, 1, 1, 0, NULL, '篇首民', NULL, '张老师', 88, '银卡', 122, '2022-01-07 11:41:30', NULL, 1, 36);
INSERT INTO `t_reservation_record` VALUES (98, 1, 1, 0, NULL, '', NULL, '张老师', 86, '金卡', 122, '2022-01-07 11:41:40', NULL, 1, 35);
INSERT INTO `t_reservation_record` VALUES (99, 1, 1, 0, NULL, '', NULL, '张老师', 87, '至尊卡', 129, '2022-01-08 18:11:54', NULL, 1, 33);
INSERT INTO `t_reservation_record` VALUES (100, 1, 1, 0, NULL, '', NULL, '张老师', 85, '测试卡', 129, '2022-01-08 18:12:10', NULL, 1, 24);
INSERT INTO `t_reservation_record` VALUES (101, 1, 1, 0, NULL, '', NULL, '张老师', 90, '测试卡', 132, '2022-01-08 18:25:54', NULL, 1, 27);
INSERT INTO `t_reservation_record` VALUES (102, 1, 1, 0, NULL, '', NULL, '张老师', 93, '体验卡', 132, '2022-01-08 18:26:11', NULL, 1, 38);

-- ----------------------------
-- Table structure for t_schedule_record
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_record`;
CREATE TABLE `t_schedule_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '课程号',
  `teacher_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '教师号',
  `order_nums` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '此项排课的预约人数',
  `start_date` date NULL DEFAULT NULL COMMENT '上课日期',
  `class_time` time(0) NULL DEFAULT NULL COMMENT '上课时间',
  `limit_sex` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制性别',
  `limit_age` int(10) NULL DEFAULT NULL COMMENT '限制年龄',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `only_course_teach`(`course_id`, `teacher_id`, `class_time`, `start_date`) USING BTREE,
  INDEX `fk_sche_teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `fk_sche_course_id` FOREIGN KEY (`course_id`) REFERENCES `t_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_sche_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `t_employee` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '中间表：排课计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_schedule_record
-- ----------------------------
INSERT INTO `t_schedule_record` VALUES (88, 42, 1, 0, '2021-12-30', '09:30:00', NULL, NULL, '2021-12-30 14:51:44', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (89, 43, 2, 0, '2021-12-30', '10:30:00', NULL, NULL, '2021-12-30 17:27:27', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (90, 44, 3, 5, '2021-12-31', '07:30:00', NULL, NULL, '2021-12-30 23:15:48', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (91, 45, 4, 0, '2021-12-31', '08:30:00', NULL, NULL, '2021-12-30 23:16:13', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (92, 46, 9, 0, '2021-12-31', '09:30:00', NULL, NULL, '2021-12-30 23:16:29', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (93, 47, 3, 0, '2021-12-31', '14:30:00', NULL, NULL, '2021-12-31 00:45:58', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (94, 48, 9, 0, '2021-12-31', '16:30:00', NULL, NULL, '2021-12-31 00:46:18', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (95, 44, 3, 0, '2022-01-01', '07:30:00', NULL, NULL, '2021-12-31 00:46:31', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (96, 45, 4, 0, '2022-01-01', '08:30:00', NULL, NULL, '2021-12-31 00:46:31', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (98, 47, 3, 0, '2022-01-01', '14:30:00', NULL, NULL, '2021-12-31 00:46:31', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (99, 48, 9, 2, '2022-01-01', '16:30:00', NULL, NULL, '2021-12-31 00:46:31', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (100, 42, 9, 5, '2022-01-03', '04:30:00', NULL, NULL, '2022-01-03 00:51:41', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (101, 43, 1, 1, '2022-01-03', '05:30:00', NULL, NULL, '2022-01-03 00:52:00', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (102, 44, 8, 1, '2022-01-03', '06:30:00', NULL, NULL, '2022-01-03 00:53:07', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (105, 46, 3, 1, '2022-01-03', '07:30:00', NULL, NULL, '2022-01-03 00:56:06', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (106, 47, 3, 1, '2022-01-04', '05:30:00', NULL, NULL, '2022-01-03 01:18:23', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (107, 42, 4, 0, '2022-01-03', '21:15:00', NULL, NULL, '2022-01-03 21:06:44', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (108, 50, 3, 0, '2022-01-03', '22:15:00', NULL, NULL, '2022-01-03 21:15:16', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (111, 43, 1, 5, '2022-01-04', '20:45:00', NULL, NULL, '2022-01-04 20:42:13', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (112, 48, 3, 0, '2022-01-04', '21:50:00', NULL, NULL, '2022-01-04 20:47:47', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (113, 47, 3, 0, '2022-01-05', '05:30:00', NULL, NULL, '2022-01-04 20:50:13', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (114, 49, 1, 0, '2022-01-05', '08:30:00', NULL, NULL, '2022-01-04 21:40:32', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (115, 50, 1, 0, '2022-01-05', '01:30:00', NULL, NULL, '2022-01-04 22:01:32', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (116, 42, 1, 0, '2022-01-06', '09:30:00', NULL, NULL, '2022-01-04 22:01:54', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (117, 43, 2, 1, '2022-01-06', '10:30:00', NULL, NULL, '2022-01-04 22:01:54', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (118, 45, 9, 3, '2022-01-06', '15:30:00', NULL, NULL, '2022-01-05 01:30:47', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (119, 44, 8, 0, '2022-01-08', '07:00:00', NULL, NULL, '2022-01-05 01:32:17', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (120, 43, 9, 0, '2022-01-06', '16:20:00', NULL, NULL, '2022-01-05 11:41:47', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (121, 45, 9, 0, '2022-01-06', '17:20:00', NULL, NULL, '2022-01-05 21:22:54', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (122, 43, 2, 3, '2022-01-07', '11:46:00', NULL, NULL, '2022-01-07 11:40:39', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (123, 44, 2, 0, '2022-01-07', '12:50:00', NULL, NULL, '2022-01-07 11:43:35', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (125, 43, 2, 0, '2022-01-13', '10:30:00', NULL, NULL, '2022-01-08 17:44:44', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (127, 43, 9, 0, '2022-01-13', '16:20:00', NULL, NULL, '2022-01-08 17:44:44', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (128, 45, 9, 0, '2022-01-13', '17:20:00', NULL, NULL, '2022-01-08 17:44:44', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (129, 48, 3, 2, '2022-01-08', '18:15:00', NULL, NULL, '2022-01-08 18:11:37', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (130, 43, 4, 0, '2022-01-09', '09:40:00', NULL, NULL, '2022-01-08 18:23:46', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (131, 46, 4, 0, '2022-01-08', '19:30:00', NULL, NULL, '2022-01-08 18:24:27', NULL, 1);
INSERT INTO `t_schedule_record` VALUES (132, 42, 8, 2, '2022-01-08', '19:30:00', NULL, NULL, '2022-01-08 18:25:35', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
