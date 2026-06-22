-- =====================================================================
-- 华水校园生活服务平台 · 数据库建表脚本 (DDL)
-- 版本: V3.0
-- 数据库: MySQL 8.0+
-- 字符集: utf8mb4
-- 引擎: InnoDB
-- 对应项目: huashui-campus-life (Spring Boot 3.5.x + MyBatis Plus 3.5.12)
-- 作者: 陈会闯
-- =====================================================================

CREATE DATABASE IF NOT EXISTS `huashui_campus_life`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE `huashui_campus_life`;

-- =====================================================================
-- 一、基础数据模块 (Base Data)
-- =====================================================================

-- ----------------------------
-- 1. 校区表
-- ----------------------------
DROP TABLE IF EXISTS `sys_campus`;
CREATE TABLE `sys_campus` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `campus_name`   VARCHAR(32)  NOT NULL                COMMENT '校区名称（龙子湖校区/花园校区/江淮校区）',
    `campus_code`   VARCHAR(16)  NOT NULL                COMMENT '校区编码：LZH-龙子湖, HY-花园, JH-江淮',
    `location`      VARCHAR(64)  NOT NULL DEFAULT ''     COMMENT '所在城市（郑州/信阳）',
    `sort_order`    INT          NOT NULL DEFAULT 0      COMMENT '排序序号，越小越靠前',
    `status`        TINYINT(1)   NOT NULL DEFAULT 1      COMMENT '状态：0-禁用, 1-启用',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '逻辑删除：0-未删除, 1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_campus_code` (`campus_code`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='校区表 - 三校区基本信息';

-- ----------------------------
-- 2. 学院/专业表
-- ----------------------------
DROP TABLE IF EXISTS `sys_college`;
CREATE TABLE `sys_college` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `college_name`  VARCHAR(64)  NOT NULL                COMMENT '学院名称（如：信息工程学院）',
    `college_code`  VARCHAR(16)  NOT NULL                COMMENT '学院编码',
    `campus_id`     BIGINT       DEFAULT NULL            COMMENT '所属校区ID（NULL表示跨校区）',
    `sort_order`    INT          NOT NULL DEFAULT 0      COMMENT '排序序号',
    `status`        TINYINT(1)   NOT NULL DEFAULT 1      COMMENT '状态：0-禁用, 1-启用',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_college_code` (`college_code`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院表 - 各学院基本信息';


-- =====================================================================
-- 二、用户与权限模块 (User & RBAC)
-- =====================================================================

-- ----------------------------
-- 3. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`        VARCHAR(32)  NOT NULL                COMMENT '用户名（学生为学号，教职工为工号）',
    `password`        VARCHAR(128) NOT NULL                COMMENT '密码（BCrypt加密存储）',
    `real_name`       VARCHAR(32)  NOT NULL DEFAULT ''     COMMENT '真实姓名',
    `phone`           VARCHAR(16)  DEFAULT NULL            COMMENT '手机号（绑定后可用于登录/找回密码）',
    `email`           VARCHAR(64)  DEFAULT NULL            COMMENT '邮箱',
    `avatar`          VARCHAR(256) DEFAULT NULL            COMMENT '头像URL',
    `gender`          TINYINT(1)   DEFAULT NULL            COMMENT '性别：0-女, 1-男',
    `user_type`       VARCHAR(16)  NOT NULL                COMMENT '用户类型：STUDENT-学生, CLEANER-保洁, DORM_MANAGER-宿管, SUPER_ADMIN-超级管理员',
    `campus_id`       BIGINT       DEFAULT NULL            COMMENT '所属校区ID（结合sys_campus.id，超级管理员可为NULL）',
    `college_id`      BIGINT       DEFAULT NULL            COMMENT '所属学院ID（学生必填）',
    `major`           VARCHAR(64)  DEFAULT NULL            COMMENT '专业名称',
    `grade`           VARCHAR(8)   DEFAULT NULL            COMMENT '入学年级（如：2024）',
    `last_login_time` DATETIME     DEFAULT NULL            COMMENT '最后登录时间',
    `status`          TINYINT(1)   NOT NULL DEFAULT 1      COMMENT '账号状态：0-冻结, 1-正常',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`      TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    UNIQUE KEY `uk_phone` (`phone`) USING BTREE,
    KEY `idx_user_type` (`user_type`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_college_id` (`college_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE,
    KEY `idx_real_name` (`real_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表 - 系统所有用户（学生/保洁/宿管/超级管理员）';

-- ----------------------------
-- 4. 角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_name`     VARCHAR(32)  NOT NULL                COMMENT '角色名称（学生/保洁/宿管/超级管理员）',
    `role_code`     VARCHAR(32)  NOT NULL                COMMENT '角色编码：STUDENT, CLEANER, DORM_MANAGER, SUPER_ADMIN',
    `description`   VARCHAR(256) DEFAULT NULL            COMMENT '角色描述',
    `sort_order`    INT          NOT NULL DEFAULT 0      COMMENT '排序序号',
    `status`        TINYINT(1)   NOT NULL DEFAULT 1      COMMENT '状态：0-禁用, 1-启用',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_code` (`role_code`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表 - RBAC角色定义';

-- ----------------------------
-- 5. 权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `permission_name` VARCHAR(64)  NOT NULL                COMMENT '权限名称',
    `permission_code` VARCHAR(64)  NOT NULL                COMMENT '权限编码（例：campus:view, room:assign）',
    `parent_id`       BIGINT       DEFAULT 0               COMMENT '父权限ID（0表示顶级）',
    `perm_type`       VARCHAR(16)  NOT NULL DEFAULT 'BUTTON' COMMENT '权限类型：MENU-菜单, BUTTON-按钮, API-接口',
    `path`            VARCHAR(128) DEFAULT NULL            COMMENT '前端路由路径（菜单类型）',
    `component`       VARCHAR(128) DEFAULT NULL            COMMENT '前端组件路径（菜单类型）',
    `icon`            VARCHAR(64)  DEFAULT NULL            COMMENT '菜单图标',
    `sort_order`      INT          NOT NULL DEFAULT 0      COMMENT '排序序号',
    `status`          TINYINT(1)   NOT NULL DEFAULT 1      COMMENT '状态：0-禁用, 1-启用',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`      TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_permission_code` (`permission_code`) USING BTREE,
    KEY `idx_parent_id` (`parent_id`) USING BTREE,
    KEY `idx_perm_type` (`perm_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表 - 细粒度权限定义';

-- ----------------------------
-- 6. 用户-角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `id`      BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL                COMMENT '用户ID（关联sys_user.id）',
    `role_id` BIGINT NOT NULL                COMMENT '角色ID（关联sys_role.id）',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`) USING BTREE,
    KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

-- ----------------------------
-- 7. 角色-权限关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
    `id`            BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`       BIGINT NOT NULL                COMMENT '角色ID（关联sys_role.id）',
    `permission_id` BIGINT NOT NULL                COMMENT '权限ID（关联sys_permission.id）',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_perm` (`role_id`, `permission_id`) USING BTREE,
    KEY `idx_permission_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';


-- =====================================================================
-- 三、宿舍模块 (Dormitory)
-- =====================================================================

-- ----------------------------
-- 8. 楼栋表
-- ----------------------------
DROP TABLE IF EXISTS `dorm_building`;
CREATE TABLE `dorm_building` (
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `campus_id`         BIGINT       NOT NULL                COMMENT '所属校区ID（关联sys_campus.id）',
    `area`              VARCHAR(16)  NOT NULL DEFAULT ''     COMMENT '片区分组：一区/二区/南苑/北苑（花园校区专用），龙子湖一区/二区，江淮校区填空',
    `building_name`     VARCHAR(32)  NOT NULL                COMMENT '楼栋名称（如：1号楼、4A号楼、善水书院3号楼）',
    `building_code`     VARCHAR(16)  NOT NULL                COMMENT '楼栋编码（如：B01, B4A）',
    `room_type`         VARCHAR(8)   NOT NULL                COMMENT '房型枚举：FOUR-四人间, SIX-六人间',
    `total_floors`      INT          NOT NULL DEFAULT 6      COMMENT '总层数',
    `accommodation_fee` INT          NOT NULL                COMMENT '住宿费（元/年）：1000（四人间）/ 800（六人间）',
    `description`       VARCHAR(256) DEFAULT NULL            COMMENT '楼栋简介（入住学院群体等）',
    `is_standardized`   TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '是否河南省标准化学生公寓：0-否, 1-是',
    `sort_order`        INT          NOT NULL DEFAULT 0      COMMENT '排序序号',
    `status`            TINYINT(1)   NOT NULL DEFAULT 1      COMMENT '状态：0-停用, 1-正常',
    `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`        TINYINT(1)   NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_building_code` (`building_code`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_room_type` (`room_type`) USING BTREE,
    KEY `idx_area` (`area`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼栋表 - 三校区全部楼栋信息';

-- ----------------------------
-- 9. 楼栋配置表（V3.0 新增）
-- ----------------------------
DROP TABLE IF EXISTS `dorm_building_config`;
CREATE TABLE `dorm_building_config` (
    `id`                  BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `building_id`         BIGINT      NOT NULL                COMMENT '楼栋ID（关联dorm_building.id，一对一）',
    `has_private_bath`    TINYINT(1)  NOT NULL DEFAULT 0      COMMENT '是否有独立卫浴：0-无, 1-有',
    `bath_type`           VARCHAR(32) NOT NULL DEFAULT 'PUBLIC_STALL' COMMENT '卫浴类型：PRIVATE-独立卫浴, PUBLIC_STALL-楼层公共隔间, DRY_WET_SEPARATED-干湿分离独立卫浴（江淮）',
    `has_balcony`         TINYINT(1)  NOT NULL DEFAULT 1      COMMENT '是否有阳台：0-无, 1-有',
    `balcony_type`        VARCHAR(16) DEFAULT 'STANDARD'      COMMENT '阳台类型：STANDARD-标准, LARGE-超大（江淮）',
    `bed_type`            VARCHAR(16) NOT NULL DEFAULT 'BUNK_DESK' COMMENT '床铺类型：BUNK_DESK-上床下桌, BUNK_MIXED-上下铺混合',
    `floor_type`          VARCHAR(16) NOT NULL DEFAULT 'TILE' COMMENT '地面材质：TILE-瓷砖, CEMENT-水泥',
    `hot_water_type`      VARCHAR(16) NOT NULL DEFAULT 'LIMITED' COMMENT '热水供应类型：ALL_DAY-24小时, LIMITED-限时供应',
    `hot_water_hours`     VARCHAR(32) DEFAULT NULL            COMMENT '热水供应时段描述（限时供应时填写，如：06:00-08:00, 18:00-23:00）',
    `has_ac`              TINYINT(1)  NOT NULL DEFAULT 1      COMMENT '是否有空调位：0-无, 1-有（可租赁）',
    `has_heating`         TINYINT(1)  NOT NULL DEFAULT 1      COMMENT '是否有暖气：0-无, 1-有',
    `has_drinking_water`  TINYINT(1)  NOT NULL DEFAULT 1      COMMENT '是否有直饮水机：0-无, 1-有',
    `has_laundry`         TINYINT(1)  NOT NULL DEFAULT 1      COMMENT '是否有扫码洗衣机：0-无, 1-有',
    `has_study_room`      TINYINT(1)  NOT NULL DEFAULT 0      COMMENT '是否有公共自习室/活动室：0-无, 1-有（江淮有书院活动室）',
    `bed_size`            VARCHAR(16) DEFAULT '0.9m×2m'       COMMENT '床铺尺寸（默认0.9m×2m标准单人床）',
    `create_time`         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_building_id` (`building_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼栋配置表 - 每栋楼的硬件设施详情（V3.0新增）';

-- ----------------------------
-- 10. 房间表
-- ----------------------------
DROP TABLE IF EXISTS `dorm_room`;
CREATE TABLE `dorm_room` (
    `id`            BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `building_id`   BIGINT      NOT NULL                COMMENT '所属楼栋ID（关联dorm_building.id）',
    `room_number`   VARCHAR(16) NOT NULL                COMMENT '房间号（如：301）',
    `floor_number`  INT         NOT NULL                COMMENT '所在楼层',
    `room_type`     VARCHAR(8)  NOT NULL                COMMENT '房型：FOUR-四人间, SIX-六人间（与楼栋一致）',
    `total_beds`    INT         NOT NULL                COMMENT '总床位数（4或6）',
    `occupied_beds` INT         NOT NULL DEFAULT 0      COMMENT '已入住人数',
    `status`        TINYINT(1)  NOT NULL DEFAULT 1      COMMENT '房间状态：0-维护中, 1-正常使用',
    `remark`        VARCHAR(256) DEFAULT NULL           COMMENT '备注',
    `create_time`   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    TINYINT(1)  NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_building_room` (`building_id`, `room_number`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表 - 每栋楼的房间';

-- ----------------------------
-- 11. 床位表
-- ----------------------------
DROP TABLE IF EXISTS `dorm_bed`;
CREATE TABLE `dorm_bed` (
    `id`          BIGINT     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `room_id`     BIGINT     NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `bed_number`  VARCHAR(8) NOT NULL                COMMENT '床位号（如：1号床、2号床）',
    `student_id`  BIGINT     DEFAULT NULL            COMMENT '入住学生ID（关联sys_user.id，空表示空床位）',
    `status`      TINYINT(1) NOT NULL DEFAULT 0      COMMENT '床位状态：0-空闲, 1-已入住, 2-预留',
    `create_time` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_room_bed` (`room_id`, `bed_number`) USING BTREE,
    KEY `idx_student_id` (`student_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='床位表 - 房间内的具体床位';

-- ----------------------------
-- 12. 学生住宿记录表
-- ----------------------------
DROP TABLE IF EXISTS `dorm_student_record`;
CREATE TABLE `dorm_student_record` (
    `id`            BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `student_id`    BIGINT   NOT NULL                COMMENT '学生用户ID（关联sys_user.id）',
    `campus_id`     BIGINT   NOT NULL                COMMENT '校区ID（关联sys_campus.id）',
    `building_id`   BIGINT   NOT NULL                COMMENT '楼栋ID（关联dorm_building.id）',
    `room_id`       BIGINT   NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `bed_id`        BIGINT   NOT NULL                COMMENT '床位ID（关联dorm_bed.id）',
    `check_in_time` DATETIME NOT NULL                COMMENT '入住时间',
    `check_out_time` DATETIME DEFAULT NULL           COMMENT '退宿时间（NULL表示仍在住）',
    `status`        TINYINT(1) NOT NULL DEFAULT 1    COMMENT '住宿状态：0-已退宿, 1-在住',
    `create_time`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_student_id` (`student_id`) USING BTREE,
    KEY `idx_room_id` (`room_id`) USING BTREE,
    KEY `idx_bed_id` (`bed_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生住宿记录表 - 记录学生的入住/退宿历史';


-- =====================================================================
-- 四、水电模块 (Utility: Water & Electricity)
-- =====================================================================

-- ----------------------------
-- 13. 水费余额表（按房间）
-- ----------------------------
DROP TABLE IF EXISTS `water_balance`;
CREATE TABLE `water_balance` (
    `id`              BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `room_id`         BIGINT         NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `balance`         DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '当前水费余额（元）',
    `free_quota`      DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '基础免费额度（元/月）',
    `total_usage`     DECIMAL(10,3)  NOT NULL DEFAULT 0.000  COMMENT '累计用水量（吨）',
    `status`          TINYINT(1)     NOT NULL DEFAULT 1      COMMENT '供水状态：0-已停水, 1-正常供水',
    `stopped_time`    DATETIME       DEFAULT NULL            COMMENT '停水时间',
    `restored_time`   DATETIME       DEFAULT NULL            COMMENT '恢复供水时间',
    `create_time`     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_room_id` (`room_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='水费余额表 - 每间宿舍的水费账户';

-- ----------------------------
-- 14. 电费余额表（按房间）
-- ----------------------------
DROP TABLE IF EXISTS `electricity_balance`;
CREATE TABLE `electricity_balance` (
    `id`              BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `room_id`         BIGINT         NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `balance`         DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '当前电费余额（元）',
    `free_quota`      DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '基础免费额度（元/月）',
    `total_usage`     DECIMAL(10,3)  NOT NULL DEFAULT 0.000  COMMENT '累计用电量（度/kWh）',
    `status`          TINYINT(1)     NOT NULL DEFAULT 1      COMMENT '供电状态：0-已停电, 1-正常供电',
    `stopped_time`    DATETIME       DEFAULT NULL            COMMENT '停电时间',
    `restored_time`   DATETIME       DEFAULT NULL            COMMENT '恢复供电时间',
    `create_time`     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_room_id` (`room_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电费余额表 - 每间宿舍的电费账户';

-- ----------------------------
-- 15. 用水记录表
-- ----------------------------
DROP TABLE IF EXISTS `water_record`;
CREATE TABLE `water_record` (
    `id`              BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `room_id`         BIGINT         NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `record_date`     DATE           NOT NULL                COMMENT '记录日期',
    `usage_amount`    DECIMAL(10,3)  NOT NULL DEFAULT 0.000  COMMENT '用水量（吨）',
    `unit_price`      DECIMAL(8,4)   NOT NULL DEFAULT 0.0000 COMMENT '水费单价（元/吨）',
    `amount`          DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '费用金额（元）',
    `balance_before`  DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '扣费前余额（元）',
    `balance_after`   DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '扣费后余额（元）',
    `create_time`     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_room_date` (`room_id`, `record_date`) USING BTREE,
    KEY `idx_record_date` (`record_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用水记录表 - 每日用水明细';

-- ----------------------------
-- 16. 用电记录表
-- ----------------------------
DROP TABLE IF EXISTS `electricity_record`;
CREATE TABLE `electricity_record` (
    `id`              BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `room_id`         BIGINT         NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `record_date`     DATE           NOT NULL                COMMENT '记录日期',
    `usage_amount`    DECIMAL(10,3)  NOT NULL DEFAULT 0.000  COMMENT '用电量（度/kWh）',
    `unit_price`      DECIMAL(8,4)   NOT NULL DEFAULT 0.0000 COMMENT '电费单价（元/度）',
    `amount`          DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '费用金额（元）',
    `balance_before`  DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '扣费前余额（元）',
    `balance_after`   DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '扣费后余额（元）',
    `create_time`     DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_room_date` (`room_id`, `record_date`) USING BTREE,
    KEY `idx_record_date` (`record_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用电记录表 - 每日用电明细';


-- =====================================================================
-- 五、缴费模块 (Payment)
-- =====================================================================

-- ----------------------------
-- 17. 缴费订单表
-- ----------------------------
DROP TABLE IF EXISTS `payment_order`;
CREATE TABLE `payment_order` (
    `id`             BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no`       VARCHAR(32)    NOT NULL                COMMENT '订单号（唯一交易流水号，如：PAY202606220001）',
    `user_id`        BIGINT         NOT NULL                COMMENT '缴费用户ID（关联sys_user.id）',
    `room_id`        BIGINT         NOT NULL                COMMENT '房间ID（关联dorm_room.id）',
    `payment_type`   VARCHAR(16)    NOT NULL                COMMENT '缴费类型：WATER-水费, ELECTRICITY-电费',
    `amount`         DECIMAL(10,2)  NOT NULL                COMMENT '充值金额（元）',
    `pay_method`     VARCHAR(16)    NOT NULL                COMMENT '支付方式：WECHAT-微信, ALIPAY-支付宝, CAMPUS_CARD-校园卡（预留）',
    `status`         VARCHAR(16)    NOT NULL DEFAULT 'PENDING' COMMENT '支付状态：PENDING-待支付, SUCCESS-成功, FAILED-失败, REFUNDED-已退款',
    `transaction_id` VARCHAR(64)    DEFAULT NULL            COMMENT '第三方交易流水号',
    `paid_time`      DATETIME       DEFAULT NULL            COMMENT '支付完成时间',
    `refund_time`    DATETIME       DEFAULT NULL            COMMENT '退款时间',
    `refund_reason`  VARCHAR(256)   DEFAULT NULL            COMMENT '退款原因',
    `create_time`    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_order_no` (`order_no`) USING BTREE,
    KEY `idx_user_id` (`user_id`) USING BTREE,
    KEY `idx_room_id` (`room_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费订单表 - 水电费充值订单记录';


-- =====================================================================
-- 六、请假模块 (Leave)
-- =====================================================================

-- ----------------------------
-- 18. 请假申请表
-- ----------------------------
DROP TABLE IF EXISTS `leave_request`;
CREATE TABLE `leave_request` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `applicant_id`    BIGINT        NOT NULL                COMMENT '申请人ID（关联sys_user.id）',
    `applicant_type`  VARCHAR(16)   NOT NULL                COMMENT '申请人类型：STUDENT-学生, CLEANER-保洁',
    `leave_type`      VARCHAR(16)   NOT NULL                COMMENT '请假类型：PERSONAL-事假, SICK-病假, OTHER-其他',
    `campus_id`       BIGINT        NOT NULL                COMMENT '所在校区ID',
    `start_time`      DATETIME      NOT NULL                COMMENT '请假开始时间',
    `end_time`        DATETIME      NOT NULL                COMMENT '请假结束时间',
    `reason`          VARCHAR(512)  NOT NULL                COMMENT '请假原因（不少于10字）',
    `proof_images`    JSON          DEFAULT NULL            COMMENT '证明材料图片URL数组（JSON格式，病假须上传医院证明）',
    `status`          VARCHAR(16)   NOT NULL DEFAULT 'PENDING' COMMENT '审批状态：PENDING-待审批, APPROVED-已通过, REJECTED-已拒绝',
    `approver_id`     BIGINT        DEFAULT NULL            COMMENT '审批人ID（关联sys_user.id，宿管）',
    `approve_time`    DATETIME      DEFAULT NULL            COMMENT '审批时间',
    `approve_opinion` VARCHAR(256)  DEFAULT NULL            COMMENT '审批意见（通过时填写）',
    `reject_reason`   VARCHAR(256)  DEFAULT NULL            COMMENT '拒绝原因（拒绝时必填）',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_applicant_id` (`applicant_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE,
    KEY `idx_approver_id` (`approver_id`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假申请表 - 学生和保洁的请假申请';


-- =====================================================================
-- 七、报修模块 (Repair)
-- =====================================================================

-- ----------------------------
-- 19. 报修工单表
-- ----------------------------
DROP TABLE IF EXISTS `repair_order`;
CREATE TABLE `repair_order` (
    `id`               BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no`         VARCHAR(32)   NOT NULL                COMMENT '工单编号（如：REP202606220001）',
    `student_id`       BIGINT        NOT NULL                COMMENT '报修学生ID（关联sys_user.id）',
    `campus_id`        BIGINT        NOT NULL                COMMENT '校区ID',
    `building_id`      BIGINT        NOT NULL                COMMENT '楼栋ID',
    `room_id`          BIGINT        NOT NULL                COMMENT '房间ID',
    `repair_type`      VARCHAR(16)   NOT NULL                COMMENT '报修类型：PIPE-水管, LIGHT-灯具, LOCK-门锁, AC-空调, NETWORK-网络, DOOR_WINDOW-门窗, FURNITURE-家具, HEATING-暖气, OTHER-其他',
    `description`      VARCHAR(1024) NOT NULL                COMMENT '问题描述（故障现象详细描述）',
    `images`           JSON          DEFAULT NULL            COMMENT '故障图片URL数组（JSON格式，最多3张）',
    `contact_phone`    VARCHAR(16)   NOT NULL                COMMENT '联系电话',
    `appointment_time` VARCHAR(64)   DEFAULT NULL            COMMENT '预约维修时间段（可选）',
    `status`           VARCHAR(16)   NOT NULL DEFAULT 'PENDING' COMMENT '工单状态：PENDING-待受理, ASSIGNED-已派单, REPAIRING-维修中, COMPLETED-已完成, EVALUATED-已评价',
    `repairer_name`    VARCHAR(32)   DEFAULT NULL            COMMENT '维修人员姓名',
    `repairer_id`      BIGINT        DEFAULT NULL            COMMENT '维修人员ID（关联sys_user.id）',
    `assigner_id`      BIGINT        DEFAULT NULL            COMMENT '派单人ID（宿管）',
    `assigned_time`    DATETIME      DEFAULT NULL            COMMENT '派单时间',
    `repair_time`      DATETIME      DEFAULT NULL            COMMENT '实际维修时间',
    `repair_result`    VARCHAR(512)  DEFAULT NULL            COMMENT '维修处理结果描述',
    `repair_images`    JSON          DEFAULT NULL            COMMENT '维修后现场照片URL数组（JSON格式）',
    `rating`           TINYINT       DEFAULT NULL            COMMENT '学生评分（1-5星，维修完成后可评）',
    `create_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_order_no` (`order_no`) USING BTREE,
    KEY `idx_student_id` (`student_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE,
    KEY `idx_repair_type` (`repair_type`) USING BTREE,
    KEY `idx_repairer_id` (`repairer_id`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报修工单表 - 故障报修全生命周期记录';


-- =====================================================================
-- 八、保洁模块 (Cleaner: Attendance & Task)
-- =====================================================================

-- ----------------------------
-- 20. 考勤记录表
-- ----------------------------
DROP TABLE IF EXISTS `attendance_record`;
CREATE TABLE `attendance_record` (
    `id`                BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `cleaner_id`        BIGINT      NOT NULL                COMMENT '保洁人员ID（关联sys_user.id）',
    `campus_id`         BIGINT      NOT NULL                COMMENT '校区ID',
    `building_id`       BIGINT      NOT NULL                COMMENT '负责楼栋ID',
    `attendance_date`   DATE        NOT NULL                COMMENT '考勤日期',
    `check_in_time`     DATETIME    DEFAULT NULL            COMMENT '签到时间',
    `check_in_type`     VARCHAR(16) DEFAULT NULL            COMMENT '签到方式：GPS-GPS定位签到, PHOTO-拍照签到',
    `check_in_location` VARCHAR(128) DEFAULT NULL           COMMENT '签到GPS位置描述',
    `check_in_photo`    VARCHAR(256) DEFAULT NULL           COMMENT '签到拍照URL',
    `check_in_status`   VARCHAR(16) NOT NULL DEFAULT 'ABSENT' COMMENT '出勤状态：NORMAL-正常出勤, LATE-迟到, ABSENT-缺勤',
    `is_holiday`        TINYINT(1)  NOT NULL DEFAULT 0      COMMENT '是否节假日：0-否, 1-是（节假日不计入缺勤）',
    `remark`            VARCHAR(256) DEFAULT NULL           COMMENT '备注',
    `create_time`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_cleaner_date` (`cleaner_id`, `attendance_date`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_attendance_date` (`attendance_date`) USING BTREE,
    KEY `idx_check_in_status` (`check_in_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤记录表 - 保洁人员每日考勤';

-- ----------------------------
-- 21. 保洁任务表
-- ----------------------------
DROP TABLE IF EXISTS `clean_task`;
CREATE TABLE `clean_task` (
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `task_name`      VARCHAR(128) NOT NULL                COMMENT '任务名称（如：龙子湖一区1号楼3层走廊清洁）',
    `cleaner_id`     BIGINT       NOT NULL                COMMENT '执行保洁人员ID（关联sys_user.id）',
    `campus_id`      BIGINT       NOT NULL                COMMENT '校区ID',
    `building_id`    BIGINT       NOT NULL                COMMENT '楼栋ID',
    `area_desc`      VARCHAR(256) NOT NULL                COMMENT '清洁区域描述（如：3层走廊、公共洗漱间）',
    `deadline`       DATETIME     NOT NULL                COMMENT '截止时间（须在此前完成）',
    `status`         VARCHAR(16)  NOT NULL DEFAULT 'PENDING' COMMENT '任务状态：PENDING-待完成, COMPLETED-已完成, OVERDUE-已超时',
    `completed_time` DATETIME     DEFAULT NULL            COMMENT '实际完成时间',
    `complete_image` VARCHAR(256) DEFAULT NULL            COMMENT '完成后现场照片URL',
    `remark`         VARCHAR(256) DEFAULT NULL            COMMENT '备注（特殊情况说明）',
    `assigner_id`    BIGINT       NOT NULL                COMMENT '任务分配人ID（关联sys_user.id，宿管）',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_cleaner_id` (`cleaner_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE,
    KEY `idx_deadline` (`deadline`) USING BTREE,
    KEY `idx_assigner_id` (`assigner_id`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保洁任务表 - 宿管分配的清洁任务';

-- ----------------------------
-- 22. 保洁工作记录表
-- ----------------------------
DROP TABLE IF EXISTS `clean_work_log`;
CREATE TABLE `clean_work_log` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `cleaner_id`  BIGINT       NOT NULL                COMMENT '保洁人员ID（关联sys_user.id）',
    `work_date`   DATE         NOT NULL                COMMENT '工作日期',
    `campus_id`   BIGINT       NOT NULL                COMMENT '校区ID',
    `building_id` BIGINT       NOT NULL                COMMENT '楼栋ID',
    `area_desc`   VARCHAR(256) NOT NULL                COMMENT '工作区域描述',
    `task_content` VARCHAR(512) NOT NULL               COMMENT '任务内容（具体工作描述）',
    `status`      VARCHAR(16)  NOT NULL DEFAULT 'COMPLETED' COMMENT '完成情况：COMPLETED-已完成, PARTIAL-部分完成, INCOMPLETE-未完成',
    `image`       VARCHAR(256) DEFAULT NULL            COMMENT '完成现场照片URL',
    `remark`      VARCHAR(256) DEFAULT NULL            COMMENT '特殊情况记录',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_cleaner_date` (`cleaner_id`, `work_date`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_work_date` (`work_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保洁工作记录表 - 保洁人员每日工作日志';


-- =====================================================================
-- 九、评价模块 (Evaluation)
-- =====================================================================

-- ----------------------------
-- 23. 评价配置表
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_config`;
CREATE TABLE `evaluation_config` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_name` VARCHAR(128) NOT NULL                COMMENT '评价活动名称（如：2024年春季学期宿舍满意度调查）',
    `campus_id`   BIGINT       NOT NULL                COMMENT '校区ID',
    `building_ids` JSON        DEFAULT NULL            COMMENT '参与评价的楼栋ID数组（JSON格式，NULL表示全部管辖楼栋）',
    `start_time`  DATETIME     NOT NULL                COMMENT '评价开始时间',
    `end_time`    DATETIME     NOT NULL                COMMENT '评价结束时间',
    `status`      VARCHAR(16)  NOT NULL DEFAULT 'DRAFT' COMMENT '状态：DRAFT-草稿, ACTIVE-进行中, CLOSED-已关闭',
    `creator_id`  BIGINT       NOT NULL                COMMENT '创建人ID（关联sys_user.id，宿管）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_campus_id` (`campus_id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE,
    KEY `idx_creator_id` (`creator_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价配置表 - 宿管发起的评价活动';

-- ----------------------------
-- 24. 评价维度表
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_dimension`;
CREATE TABLE `evaluation_dimension` (
    `id`             BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_id`      BIGINT      NOT NULL                COMMENT '评价配置ID（关联evaluation_config.id）',
    `dimension_name` VARCHAR(64) NOT NULL                COMMENT '评价维度名称（如：卫生状况、安全管理、设施维护、服务态度、管理规范）',
    `sort_order`     INT         NOT NULL DEFAULT 0      COMMENT '排序序号',
    `create_time`    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_config_id` (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价维度表 - 每次评价活动的评价维度';

-- ----------------------------
-- 25. 评价结果表（学生提交记录）
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_result`;
CREATE TABLE `evaluation_result` (
    `id`          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_id`   BIGINT        NOT NULL                COMMENT '评价配置ID（关联evaluation_config.id）',
    `student_id`  BIGINT        NOT NULL                COMMENT '评价学生ID（关联sys_user.id）',
    `campus_id`   BIGINT        NOT NULL                COMMENT '校区ID',
    `building_id` BIGINT        NOT NULL                COMMENT '楼栋ID',
    `room_id`     BIGINT        NOT NULL                COMMENT '房间ID',
    `suggestion`  VARCHAR(512)  DEFAULT NULL            COMMENT '文字建议（选填）',
    `submit_time` DATETIME      NOT NULL                COMMENT '提交时间（提交后不可修改）',
    `create_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_config_student` (`config_id`, `student_id`) USING BTREE,
    KEY `idx_student_id` (`student_id`) USING BTREE,
    KEY `idx_config_id` (`config_id`) USING BTREE,
    KEY `idx_building_id` (`building_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价结果表 - 学生提交的评价记录';

-- ----------------------------
-- 26. 评价分数表（每维度分数）
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_score`;
CREATE TABLE `evaluation_score` (
    `id`             BIGINT     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `result_id`      BIGINT     NOT NULL                COMMENT '评价结果ID（关联evaluation_result.id）',
    `dimension_id`   BIGINT     NOT NULL                COMMENT '评价维度ID（关联evaluation_dimension.id）',
    `dimension_name` VARCHAR(64) NOT NULL               COMMENT '评价维度名称（冗余，方便查询）',
    `score`          TINYINT    NOT NULL                COMMENT '评分（1-5星）',
    `create_time`    DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_result_dimension` (`result_id`, `dimension_id`) USING BTREE,
    KEY `idx_result_id` (`result_id`) USING BTREE,
    KEY `idx_dimension_id` (`dimension_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价分数表 - 每项维度打分记录';


-- =====================================================================
-- 十、系统模块 (System)
-- =====================================================================

-- ----------------------------
-- 27. 系统公告表（V3.0 新增）
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice` (
    `id`               BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title`            VARCHAR(128)  NOT NULL                COMMENT '公告标题',
    `content`          TEXT          NOT NULL                COMMENT '公告正文（支持富文本）',
    `notice_type`      VARCHAR(16)   NOT NULL                COMMENT '公告类型：WATER_STOP-停水通知, POWER_STOP-停电通知, HOLIDAY-放假通知, SAFETY_CHECK-安全检查, AC_RENTAL-空调租赁通知, OTHER-其他',
    `attachment`       VARCHAR(256)  DEFAULT NULL            COMMENT '附件URL（可选）',
    `push_scope`       VARCHAR(8)    NOT NULL DEFAULT 'ALL'  COMMENT '推送范围：ALL-全部, CAMPUS-指定校区, ROLE-指定角色',
    `target_campus_ids` JSON         DEFAULT NULL            COMMENT '目标校区ID数组（JSON格式，push_scope=CAMPUS时使用）',
    `target_roles`     JSON          DEFAULT NULL            COMMENT '目标角色数组（JSON格式，如：["STUDENT","CLEANER"]，push_scope=ROLE时使用）',
    `is_top`           TINYINT(1)    NOT NULL DEFAULT 0      COMMENT '是否置顶：0-否, 1-是',
    `publisher_id`     BIGINT        NOT NULL                COMMENT '发布人ID（关联sys_user.id，超级管理员）',
    `publish_time`     DATETIME      DEFAULT NULL            COMMENT '发布时间（NULL为草稿未发布）',
    `view_count`       INT           NOT NULL DEFAULT 0      COMMENT '总浏览量',
    `status`           TINYINT(1)    NOT NULL DEFAULT 0      COMMENT '状态：0-草稿, 1-已发布, 2-已撤回',
    `create_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`       TINYINT(1)    NOT NULL DEFAULT 0      COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_notice_type` (`notice_type`) USING BTREE,
    KEY `idx_publisher_id` (`publisher_id`) USING BTREE,
    KEY `idx_publish_time` (`publish_time`) USING BTREE,
    KEY `idx_is_top` (`is_top`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告表 - 超级管理员发布的系统公告（V3.0新增）';

-- ----------------------------
-- 28. 公告已读记录表
-- ----------------------------
DROP TABLE IF EXISTS `notice_read_record`;
CREATE TABLE `notice_read_record` (
    `id`        BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `notice_id` BIGINT   NOT NULL                COMMENT '公告ID（关联system_notice.id）',
    `user_id`   BIGINT   NOT NULL                COMMENT '已读用户ID（关联sys_user.id）',
    `read_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '阅读时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_notice_user` (`notice_id`, `user_id`) USING BTREE,
    KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告已读记录表 - 追踪公告阅读情况';

-- ----------------------------
-- 29. 操作日志表（V3.0 新增）
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `operator_id`     BIGINT        NOT NULL                COMMENT '操作人ID（关联sys_user.id）',
    `operator_name`   VARCHAR(32)   NOT NULL                COMMENT '操作人姓名（冗余，方便查询）',
    `operation_type`  VARCHAR(16)   NOT NULL                COMMENT '操作类型：CREATE-创建, UPDATE-修改, DELETE-删除, APPROVE-审批',
    `operation_module` VARCHAR(32)   NOT NULL               COMMENT '操作模块（如：用户管理、宿舍管理、报修管理）',
    `operation_desc`  VARCHAR(256)  NOT NULL                COMMENT '操作描述（如：分配床位-学号2024001→龙子湖1号楼301房间3号床）',
    `target_type`     VARCHAR(32)   DEFAULT NULL            COMMENT '目标资源类型（如：sys_user, dorm_bed, repair_order）',
    `target_id`       BIGINT        DEFAULT NULL            COMMENT '目标资源ID',
    `request_ip`      VARCHAR(64)   DEFAULT NULL            COMMENT '请求来源IP',
    `request_method`  VARCHAR(8)    DEFAULT NULL            COMMENT '请求方法（GET/POST/PUT/DELETE）',
    `request_url`     VARCHAR(256)  DEFAULT NULL            COMMENT '请求URL',
    `request_params`  JSON          DEFAULT NULL            COMMENT '请求参数（JSON格式，脱敏处理）',
    `old_data`        JSON          DEFAULT NULL            COMMENT '变更前数据（JSON格式）',
    `new_data`        JSON          DEFAULT NULL            COMMENT '变更后数据（JSON格式）',
    `cost_time`       INT           DEFAULT NULL            COMMENT '操作耗时（毫秒）',
    `status`          TINYINT(1)    NOT NULL DEFAULT 1      COMMENT '操作状态：0-失败, 1-成功',
    `error_msg`       VARCHAR(512)  DEFAULT NULL            COMMENT '错误信息',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_operator_id` (`operator_id`) USING BTREE,
    KEY `idx_operation_type` (`operation_type`) USING BTREE,
    KEY `idx_operation_module` (`operation_module`) USING BTREE,
    KEY `idx_target` (`target_type`, `target_id`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表 - 全局操作审计日志（V3.0新增）';


-- =====================================================================
-- 建表完成
-- =====================================================================
-- 下一文件: 02_huashui_campus_life_init_data.sql — 初始数据
-- 说明文档: 数据库设计文档.md — 完整字段说明
-- =====================================================================
