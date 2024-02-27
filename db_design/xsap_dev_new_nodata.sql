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

 Date: 10/01/2022 10:29:15
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

SET FOREIGN_KEY_CHECKS = 1;
