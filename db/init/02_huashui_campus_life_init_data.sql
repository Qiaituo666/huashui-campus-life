-- =====================================================================
-- 华水校园生活服务平台 · 初始数据脚本
-- 版本: V3.0
-- 说明: 包含校区、楼栋、楼栋配置、角色、权限的基础初始化数据
-- 数据来源: 华北水利水电大学三校区宿舍真实配置
-- 作者: 陈会闯
-- =====================================================================

USE `huashui_campus_life`;


-- =====================================================================
-- 一、校区数据（3条）
-- =====================================================================
INSERT INTO `sys_campus` (`id`, `campus_name`, `campus_code`, `location`, `sort_order`) VALUES
(1, '龙子湖校区', 'LZH', '郑州', 1),
(2, '花园校区',   'HY',  '郑州', 2),
(3, '江淮校区',   'JH',  '信阳', 3);


-- =====================================================================
-- 二、楼栋数据
-- =====================================================================

-- ---------------------------------------
-- 2.1 龙子湖校区 — 一区（4人间为主）
-- ---------------------------------------
INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(1,  1, '一区', '1号楼',  'B01', 'FOUR', 6, 1000, '经管、外语等本科女生', 1, 1),
(2,  1, '一区', '2号楼',  'B02', 'FOUR', 6, 1000, '建筑学院本科女生',   1, 2),
(3,  1, '一区', '3号楼',  'B03', 'FOUR', 6, 1000, NULL,                  1, 3),
(4,  1, '一区', '4A号楼', 'B4A', 'SIX',  6, 800,  '一区补充楼栋',         0, 4),
(5,  1, '一区', '5号楼',  'B05', 'FOUR', 6, 1000, NULL,                  1, 5),
(6,  1, '一区', '6号楼',  'B06', 'FOUR', 6, 1000, NULL,                  1, 6),
(7,  1, '一区', '7号楼',  'B07', 'FOUR', 6, 1000, '土木、水利等本科女生',  1, 7);

-- ---------------------------------------
-- 2.2 龙子湖校区 — 二区（六人间）
-- ---------------------------------------
INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(8,  1, '二区', '8号楼',  'B08', 'SIX', 6, 800, '一区生活圈，女生居多',         1, 8),
(9,  1, '二区', '9号楼',  'B09', 'SIX', 6, 800, NULL,                           1, 9),
(10, 1, '二区', '10号楼', 'B10', 'SIX', 6, 800, NULL,                           1, 10),
(11, 1, '二区', '11号楼', 'B11', 'SIX', 6, 800, NULL,                           1, 11),
(12, 1, '二区', '12号楼', 'B12', 'SIX', 6, 800, NULL,                           1, 12),
(13, 1, '二区', '13号楼', 'B13', 'SIX', 6, 800, NULL,                           1, 13),
(14, 1, '二区', '14号楼', 'B14', 'SIX', 6, 800, NULL,                           1, 14),
(15, 1, '二区', '15号楼', 'B15', 'SIX', 6, 800, NULL,                           1, 15);

INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(16, 1, '二区', '16号楼', 'B16', 'SIX', 6, 800, '二区生活圈，女寝',       1, 16),
(17, 1, '二区', '17号楼', 'B17', 'SIX', 6, 800, '二区生活圈，多男寝',     1, 17),
(18, 1, '二区', '18号楼', 'B18', 'SIX', 6, 800, NULL,                     1, 18),
(19, 1, '二区', '19号楼', 'B19', 'SIX', 6, 800, NULL,                     1, 19),
(20, 1, '二区', '20号楼', 'B20', 'SIX', 6, 800, NULL,                     1, 20),
(21, 1, '二区', '21号楼', 'B21', 'SIX', 6, 800, NULL,                     1, 21),
(22, 1, '二区', '22号楼', 'B22', 'SIX', 6, 800, NULL,                     1, 22);

INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(23, 1, '二区', '8A号楼', 'B8A', 'SIX', 6, 800, NULL, 0, 23);

-- ---------------------------------------
-- 2.3 花园校区 — 南苑
-- ---------------------------------------
INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(24, 2, '南苑', '4号楼',  'HY-B04', 'FOUR', 6, 1000, '对标龙子湖一区，离教学楼/图书馆最近',            1, 24),
(25, 2, '南苑', '1号楼',  'HY-B01', 'SIX',  6, 800,  '2025年翻新：全新床桌、防盗门、人脸识别门禁',      1, 25),
(26, 2, '南苑', '2号楼',  'HY-B02', 'SIX',  6, 800,  '2025年翻新',                                    1, 26),
(27, 2, '南苑', '3号楼',  'HY-B03', 'SIX',  6, 800,  '2025年翻新',                                    1, 27),
(28, 2, '南苑', '6号楼',  'HY-B06', 'SIX',  6, 800,  '2025年翻新',                                    1, 28),
(29, 2, '南苑', '7号楼',  'HY-B07', 'SIX',  6, 800,  '2025年翻新',                                    1, 29);

-- ---------------------------------------
-- 2.4 花园校区 — 北苑
-- ---------------------------------------
INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(30, 2, '北苑', '9号楼',  'HY-B09', 'SIX', 6, 800, '楼下配套生活广场、公共大澡堂、北苑食堂', 0, 30),
(31, 2, '北苑', '10号楼', 'HY-B10', 'SIX', 6, 800, NULL, 0, 31),
(32, 2, '北苑', '11号楼', 'HY-B11', 'SIX', 6, 800, NULL, 0, 32),
(33, 2, '北苑', '12号楼', 'HY-B12', 'SIX', 6, 800, NULL, 0, 33),
(34, 2, '北苑', '13号楼', 'HY-B13', 'SIX', 6, 800, NULL, 0, 34),
(35, 2, '北苑', '14号楼', 'HY-B14', 'SIX', 6, 800, NULL, 0, 35);

-- ---------------------------------------
-- 2.5 江淮校区（全部四人间）
-- ---------------------------------------
INSERT INTO `dorm_building` (`id`, `campus_id`, `area`, `building_name`, `building_code`, `room_type`, `total_floors`, `accommodation_fee`, `description`, `is_standardized`, `sort_order`) VALUES
(36, 3, '', '1号楼', 'JH-B01', 'FOUR', 6, 1000, '华水宿舍天花板：干湿分离独卫、超大阳台', 1, 36),
(37, 3, '', '2号楼', 'JH-B02', 'FOUR', 6, 1000, NULL, 1, 37),
(38, 3, '', '3号楼（善水书院）', 'JH-B03', 'FOUR', 6, 1000, '善水书院：书院活动室、自习室、心理驿站', 1, 38),
(39, 3, '', '4号楼（善水书院）', 'JH-B04', 'FOUR', 6, 1000, '善水书院：书院活动室、自习室、心理驿站', 1, 39);


-- =====================================================================
-- 三、楼栋配置数据
-- =====================================================================

-- ---------------------------------------
-- 3.1 龙子湖一区四人间配置（1~3、5~7 号楼统一）
-- ---------------------------------------
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(1, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(2, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(3, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(5, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(6, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(7, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m');

-- 龙子湖一区 4A 号楼（六人间）
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(4, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m');

-- ---------------------------------------
-- 3.2 龙子湖二区六人间配置（8~22, 8A 号楼）
-- ---------------------------------------
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(8,  0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(9,  0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(10, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(11, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(12, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(13, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(14, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(15, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(16, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(17, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(18, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(19, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(20, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(21, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(22, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m'),
(23, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '06:00-08:00, 18:00-23:00', 1, 1, 1, 1, 0, '0.9m×2m');

-- ---------------------------------------
-- 3.3 花园校区南苑 4号楼（四人间，对标龙子湖一区）
-- ---------------------------------------
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(24, 1, 'PRIVATE', 1, 'STANDARD', 'BUNK_DESK', 'TILE', 'LIMITED', '时段与龙子湖相近', 1, 1, 1, 1, 0, '0.9m×2m');

-- 花园校区南苑六人间（上下铺混合，2025年翻新）
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(25, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'TILE', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(26, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'TILE', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(27, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'TILE', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(28, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'TILE', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(29, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'TILE', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m');

-- 花园校区北苑六人间
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(30, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'CEMENT', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(31, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'CEMENT', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(32, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'CEMENT', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(33, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'CEMENT', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(34, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'CEMENT', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m'),
(35, 0, 'PUBLIC_STALL', 1, 'STANDARD', 'BUNK_MIXED', 'CEMENT', 'LIMITED', NULL, 1, 1, 1, 1, 0, '0.9m×2m');

-- ---------------------------------------
-- 3.4 江淮校区四人间（华水天花板）
-- ---------------------------------------
INSERT INTO `dorm_building_config` (`building_id`, `has_private_bath`, `bath_type`, `has_balcony`, `balcony_type`, `bed_type`, `floor_type`, `hot_water_type`, `hot_water_hours`, `has_ac`, `has_heating`, `has_drinking_water`, `has_laundry`, `has_study_room`, `bed_size`)
VALUES
(36, 1, 'DRY_WET_SEPARATED', 1, 'LARGE', 'BUNK_DESK', 'TILE', 'ALL_DAY', NULL,                 1, 1, 1, 1, 1, '0.9m×2m'),
(37, 1, 'DRY_WET_SEPARATED', 1, 'LARGE', 'BUNK_DESK', 'TILE', 'ALL_DAY', NULL,                 1, 1, 1, 1, 1, '0.9m×2m'),
(38, 1, 'DRY_WET_SEPARATED', 1, 'LARGE', 'BUNK_DESK', 'TILE', 'ALL_DAY', '善水书院活动室/自习室', 1, 1, 1, 1, 1, '0.9m×2m'),
(39, 1, 'DRY_WET_SEPARATED', 1, 'LARGE', 'BUNK_DESK', 'TILE', 'ALL_DAY', '善水书院活动室/自习室', 1, 1, 1, 1, 1, '0.9m×2m');


-- =====================================================================
-- 四、角色数据（4条）
-- =====================================================================
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `sort_order`) VALUES
(1, '学生',       'STUDENT',       '在校住宿学生，可查看个人宿舍信息、水电查询/缴费、请假/报修提交、宿舍评价',      1),
(2, '保洁人员',   'CLEANER',       '负责楼栋清洁，可考勤打卡、请假申请、任务接收/完成、工作记录查看',               2),
(3, '宿舍管理员', 'DORM_MANAGER',   '管辖校区楼栋的日常事务：房间/床位管理、学生管理、请假审批、报修派单、水电管控、保洁任务分配、评价发起', 3),
(4, '超级管理员', 'SUPER_ADMIN',    '全局系统管理：用户创建/编辑、权限配置、三校区数据大盘、系统公告、操作日志审计', 4);


-- =====================================================================
-- 五、权限数据
-- =====================================================================
-- 采用 资源:操作 的 RBAC 权限编码模式
-- 权限类型: API-接口级权限
-- =====================================================================

-- 5.1 基础数据权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(1,  '查看校区信息',    'campus:view',    0, 'API', 1),
(2,  '查看楼栋信息',    'building:view',  0, 'API', 2),
(3,  '查看楼栋配置',    'building:config', 0, 'API', 3);

-- 5.2 用户与账号权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(10, '修改个人信息',    'user:update_self', 0, 'API', 10),
(11, '创建用户',        'user:create',      0, 'API', 11),
(12, '编辑用户',        'user:update',      0, 'API', 12),
(13, '冻结/解冻用户',   'user:freeze',      0, 'API', 13),
(14, '重置用户密码',    'user:reset_pwd',   0, 'API', 14),
(15, '批量导入用户',    'user:import',      0, 'API', 15),
(16, '管理角色权限',    'role:manage',      0, 'API', 16);

-- 5.3 宿舍管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(20, '查看房间信息',    'room:view',        0, 'API', 20),
(21, '修改房间配置',    'room:update',      0, 'API', 21),
(22, '分配床位',        'room:assign',      0, 'API', 22),
(23, '办理退宿',        'room:checkout',    0, 'API', 23),
(24, '查看学生住宿信息','student:view',     0, 'API', 24),
(25, '批量导入学生',    'student:import',   0, 'API', 25),
(26, '导出学生名单',    'student:export',   0, 'API', 26);

-- 5.4 水电管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(30, '查询水费余额',    'water:query',       0, 'API', 30),
(31, '查询电费余额',    'electricity:query', 0, 'API', 31),
(32, '查看水电账单',    'utility:bill',      0, 'API', 32),
(33, '在线缴费',        'payment:create',    0, 'API', 33),
(34, '查看缴费记录',    'payment:view',      0, 'API', 34),
(35, '停水管控',        'water:stop',        0, 'API', 35),
(36, '恢复供水',        'water:restore',     0, 'API', 36),
(37, '停电管控',        'electricity:stop',  0, 'API', 37),
(38, '恢复供电',        'electricity:restore',0, 'API', 38),
(39, '发送缴费提醒',    'utility:notify',    0, 'API', 39);

-- 5.5 请假管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(40, '提交请假申请',    'leave:create',   0, 'API', 40),
(41, '查看我的请假',    'leave:view_self',0, 'API', 41),
(42, '查看管辖请假',    'leave:view',     0, 'API', 42),
(43, '审批请假',        'leave:approve',  0, 'API', 43);

-- 5.6 报修管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(50, '提交报修',        'repair:create',  0, 'API', 50),
(51, '查看我的报修',    'repair:view_self',0,'API', 51),
(52, '评价维修',        'repair:evaluate', 0, 'API', 52),
(53, '查看管辖报修',    'repair:view',    0, 'API', 53),
(54, '分配维修工单',    'repair:assign',  0, 'API', 54);

-- 5.7 保洁管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(60, '考勤签到',        'attendance:check_in', 0, 'API', 60),
(61, '查看我的考勤',    'attendance:view_self',0,'API', 61),
(62, '查看管辖考勤',    'attendance:view',     0, 'API', 62),
(63, '查看我的任务',    'task:view_self',      0, 'API', 63),
(64, '完成任务',        'task:complete',       0, 'API', 64),
(65, '分配保洁任务',    'task:assign',         0, 'API', 65),
(66, '查看管辖任务',    'task:view',           0, 'API', 66),
(67, '创建工作记录',    'work_log:create',     0, 'API', 67),
(68, '查看我的工作记录','work_log:view_self',  0, 'API', 68);

-- 5.8 评价管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(70, '提交评价',        'evaluation:create',    0, 'API', 70),
(71, '创建评价活动',    'evaluation:config',    0, 'API', 71),
(72, '查看评价统计',    'evaluation:stats',     0, 'API', 72);

-- 5.9 系统管理权限
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `parent_id`, `perm_type`, `sort_order`) VALUES
(80, '发布系统公告',    'system:announce',   0, 'API', 80),
(81, '查看操作日志',    'system:audit',      0, 'API', 81),
(82, '查看数据大盘',    'data:dashboard',    0, 'API', 82),
(83, '查看所有校区数据','data:cross_campus', 0, 'API', 83);


-- =====================================================================
-- 六、角色-权限关联数据
-- =====================================================================

-- 6.1 学生角色权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(1, 1),  -- campus:view（限本人校区）
(1, 2),  -- building:view（限本人校区）
(1, 3),  -- building:config
(1, 10), -- user:update_self
(1, 30), -- water:query
(1, 31), -- electricity:query
(1, 32), -- utility:bill
(1, 33), -- payment:create
(1, 34), -- payment:view
(1, 40), -- leave:create
(1, 41), -- leave:view_self
(1, 50), -- repair:create
(1, 51), -- repair:view_self
(1, 52), -- repair:evaluate
(1, 70); -- evaluation:create

-- 6.2 保洁人员角色权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(2, 1),  -- campus:view（限分配校区）
(2, 2),  -- building:view（限分配楼栋）
(2, 10), -- user:update_self
(2, 40), -- leave:create
(2, 41), -- leave:view_self
(2, 60), -- attendance:check_in
(2, 61), -- attendance:view_self
(2, 63), -- task:view_self
(2, 64), -- task:complete
(2, 67), -- work_log:create
(2, 68); -- work_log:view_self

-- 6.3 宿舍管理员角色权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(3, 1),  -- campus:view（限管辖校区）
(3, 2),  -- building:view
(3, 3),  -- building:config
(3, 20), -- room:view
(3, 21), -- room:update
(3, 22), -- room:assign
(3, 23), -- room:checkout
(3, 24), -- student:view
(3, 25), -- student:import
(3, 26), -- student:export
(3, 30), -- water:query
(3, 31), -- electricity:query
(3, 35), -- water:stop
(3, 36), -- water:restore
(3, 37), -- electricity:stop
(3, 38), -- electricity:restore
(3, 39), -- utility:notify
(3, 42), -- leave:view
(3, 43), -- leave:approve
(3, 53), -- repair:view
(3, 54), -- repair:assign
(3, 62), -- attendance:view
(3, 65), -- task:assign
(3, 66), -- task:view
(3, 71), -- evaluation:config
(3, 72); -- evaluation:stats

-- 6.4 超级管理员角色权限（全部）
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(4, 1),  (4, 2),  (4, 3),
(4, 11), (4, 12), (4, 13), (4, 14), (4, 15), (4, 16),
(4, 20), (4, 21), (4, 22), (4, 23), (4, 24), (4, 25), (4, 26),
(4, 30), (4, 31), (4, 32), (4, 35), (4, 36), (4, 37), (4, 38), (4, 39),
(4, 42), (4, 43),
(4, 53), (4, 54),
(4, 62), (4, 65), (4, 66),
(4, 71), (4, 72),
(4, 80), (4, 81), (4, 82), (4, 83);


-- =====================================================================
-- 初始数据植入完成
-- 共计: 3校区 + 39楼栋 + 39楼栋配置 + 4角色 + 43权限 + 角色-权限关联
-- =====================================================================
