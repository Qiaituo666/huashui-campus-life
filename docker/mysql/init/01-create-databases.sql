-- =====================================================================
-- 华水校园生活服务平台 · MySQL 初始化脚本
-- 容器首次启动时由 /docker-entrypoint-initdb.d/ 自动执行
-- 创建项目所需全部数据库
-- =====================================================================

-- 1. 主业务数据库（与 DDL 脚本一致）
CREATE DATABASE IF NOT EXISTS `huashui_campus_life`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

-- 2. Nacos 配置中心数据库
CREATE DATABASE IF NOT EXISTS `nacos_config`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

-- 3. XXL-JOB 调度中心数据库
CREATE DATABASE IF NOT EXISTS `xxl_job`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE `xxl_job`;

-- XXL-JOB 2.4.1 核心表结构
-- 来源: https://github.com/xuxueli/xxl-job/blob/master/doc/db/tables_xxl_job.sql

CREATE TABLE IF NOT EXISTS `xxl_job_info` (
    `id`                        int(11)      NOT NULL AUTO_INCREMENT,
    `job_group`                 int(11)      NOT NULL COMMENT '执行器主键ID',
    `job_desc`                  varchar(255) NOT NULL,
    `add_time`                  datetime     DEFAULT NULL,
    `update_time`               datetime     DEFAULT NULL,
    `author`                    varchar(64)  DEFAULT NULL COMMENT '作者',
    `alarm_email`               varchar(255) DEFAULT NULL COMMENT '报警邮件',
    `schedule_type`             varchar(50)  NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
    `schedule_conf`             varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
    `misfire_strategy`          varchar(50)  NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
    `executor_route_strategy`   varchar(50)  DEFAULT NULL COMMENT '执行器路由策略',
    `executor_handler`          varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`            varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
    `executor_block_strategy`   varchar(50)  DEFAULT NULL COMMENT '阻塞处理策略',
    `executor_timeout`          int(11)      NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
    `executor_fail_retry_count` int(11)      NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `glue_type`                 varchar(50)  NOT NULL COMMENT 'GLUE类型',
    `glue_source`               mediumtext   COMMENT 'GLUE源代码',
    `glue_remark`               varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
    `glue_updatetime`           datetime     DEFAULT NULL COMMENT 'GLUE更新时间',
    `child_jobid`               varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
    `trigger_status`            tinyint(4)   NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
    `trigger_last_time`         bigint(13)   NOT NULL DEFAULT '0' COMMENT '上次调度时间',
    `trigger_next_time`         bigint(13)   NOT NULL DEFAULT '0' COMMENT '下次调度时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB 任务信息表';

CREATE TABLE IF NOT EXISTS `xxl_job_log` (
    `id`                        bigint(20)   NOT NULL AUTO_INCREMENT,
    `job_group`                 int(11)      NOT NULL COMMENT '执行器主键ID',
    `job_id`                    int(11)      NOT NULL COMMENT '任务主键ID',
    `executor_address`          varchar(255) DEFAULT NULL COMMENT '执行器地址',
    `executor_handler`          varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`            varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
    `executor_sharding_param`   varchar(20)  DEFAULT NULL COMMENT '分片参数',
    `executor_fail_retry_count` int(11)      NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `trigger_time`              datetime     DEFAULT NULL COMMENT '调度时间',
    `trigger_code`              int(11)      NOT NULL COMMENT '调度结果',
    `trigger_msg`               text         COMMENT '调度日志',
    `handle_time`               datetime     DEFAULT NULL COMMENT '执行时间',
    `handle_code`               int(11)      NOT NULL COMMENT '执行结果',
    `handle_msg`                text         COMMENT '执行日志',
    `alarm_status`              tinyint(4)   NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
    PRIMARY KEY (`id`),
    KEY `idx_trigger_time` (`trigger_time`),
    KEY `idx_handle_code` (`handle_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB 调度日志表';

CREATE TABLE IF NOT EXISTS `xxl_job_log_report` (
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `trigger_day`   datetime DEFAULT NULL COMMENT '调度时间',
    `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
    `suc_count`     int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
    `fail_count`    int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
    `update_time`   datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB 调度日志报表';

CREATE TABLE IF NOT EXISTS `xxl_job_logglue` (
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `job_id`      int(11)      NOT NULL COMMENT '任务主键ID',
    `glue_type`   varchar(50)  DEFAULT NULL COMMENT 'GLUE类型',
    `glue_source` mediumtext   COMMENT 'GLUE源代码',
    `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
    `add_time`    datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB GLUE日志表';

CREATE TABLE IF NOT EXISTS `xxl_job_registry` (
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `registry_group` varchar(50)  NOT NULL,
    `registry_key`   varchar(255) NOT NULL,
    `registry_value` varchar(255) NOT NULL,
    `update_time`    datetime     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `i_g_k_v` (`registry_group`, `registry_key`, `registry_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB 注册表';

CREATE TABLE IF NOT EXISTS `xxl_job_group` (
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `app_name`     varchar(64)  NOT NULL COMMENT '执行器AppName',
    `title`        varchar(12)  NOT NULL COMMENT '执行器名称',
    `address_type` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '注册方式：0-自动注册、1-手动录入',
    `address_list` text         COMMENT '执行器地址列表，多地址逗号分隔',
    `update_time`  datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB 执行器组表';

CREATE TABLE IF NOT EXISTS `xxl_job_user` (
    `id`         int(11)     NOT NULL AUTO_INCREMENT,
    `username`   varchar(50) NOT NULL COMMENT '账号',
    `password`   varchar(50) NOT NULL COMMENT '密码',
    `role`       tinyint(4)  NOT NULL COMMENT '角色：0-普通用户、1-管理员',
    `permission` varchar(255) DEFAULT NULL COMMENT '权限',
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='XXL-JOB 用户表';

-- XXL-JOB 默认管理员账号: admin / 123456
INSERT INTO `xxl_job_user` (`username`, `password`, `role`, `permission`)
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
