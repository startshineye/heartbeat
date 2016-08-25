-- ###############
--    create database , if need create, cancel the comment
-- ###############
-- create database if not exists heart_beat default character set utf8;
-- use heart_beat set default character = utf8;

-- ###############
--    grant privileges  to heart_beat/heart_beat
-- ###############
-- GRANT ALL PRIVILEGES ON heart_beat.* TO heart_beat@localhost IDENTIFIED BY "heart_beat";

-- ###############
-- Domain:  User
-- ###############
DROP TABLE IF EXISTS user_;
CREATE TABLE `user_` (
  `id`              INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`            VARCHAR(255) NOT NULL UNIQUE,
  `create_time`     DATETIME,
  `archived`        TINYINT(1)            DEFAULT '0',
  `version`         INT(11)               DEFAULT 0,

  `username`        VARCHAR(255) NOT NULL UNIQUE,
  `password`        VARCHAR(255) NOT NULL,
  `phone`           VARCHAR(255),
  `email`           VARCHAR(255),
  `default_user`    TINYINT(1)            DEFAULT '0',
  `last_login_time` DATETIME,
  `creator_id`      INT(11),
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `creator_id_index` (`creator_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;

-- ###############
-- Domain:  ApplicationInstance
-- ###############
DROP TABLE IF EXISTS application_instance;
CREATE TABLE `application_instance` (
  `id`                     INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`                   VARCHAR(255) NOT NULL UNIQUE,
  `create_time`            DATETIME,
  `archived`               TINYINT(1)            DEFAULT '0',
  `version`                INT(11)               DEFAULT 0,

  `instance_name`          VARCHAR(255),
  `monitor_url`            VARCHAR(255),
  `max_connection_seconds` INT(11)               DEFAULT 0,
  `continue_failed_times`  INT(11)               DEFAULT 2,
  `enabled`                TINYINT(1)            DEFAULT '0',
  `private_instance`       TINYINT(1)            DEFAULT '0',
  `frequency`              VARCHAR(255)          DEFAULT 'THIRTY',
  `request_method`         VARCHAR(255)          DEFAULT 'GET',
  `content_type`           VARCHAR(255),
  `email`                  VARCHAR(255),
  `job_name`               VARCHAR(255),
  `remark`                 TEXT,
  `creator_id`             INT(11),
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `creator_id_index` (`creator_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;

-- ###############
-- Domain:  FrequencyMonitorLog
-- ###############
DROP TABLE IF EXISTS frequency_monitor_log;
CREATE TABLE `frequency_monitor_log` (
  `id`            INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`          VARCHAR(255) NOT NULL UNIQUE,
  `create_time`   DATETIME,
  `archived`      TINYINT(1)            DEFAULT '0',
  `version`       INT(11)               DEFAULT 0,

  `instance_id`   INT(11),
  `normal`        TINYINT(1)            DEFAULT '0',
  `cost_time`     INT(11)               DEFAULT 0,
  `response_size` INT(11)               DEFAULT 0,
  `remark`        TEXT,
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;

-- ###############
-- Domain:  MonitoringReminderLog
-- ###############
DROP TABLE IF EXISTS monitoring_reminder_log;
CREATE TABLE `monitoring_reminder_log` (
  `id`             INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`           VARCHAR(255) NOT NULL UNIQUE,
  `create_time`    DATETIME,
  `archived`       TINYINT(1)            DEFAULT '0',
  `version`        INT(11)               DEFAULT 0,

  `instance_id`    INT(11),
  `monitor_log_id` INT(11),
  `change_normal`  TINYINT(1)            DEFAULT '0',
  `receive_email`  VARCHAR(255),
  `email_content`  TEXT,
  `openid`         VARCHAR(255),
  `wechat_content` TEXT,
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`),
  INDEX `monitor_log_id_index` (`monitor_log_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;

-- ###############
-- Domain:  UserPrivilege
-- ###############
DROP TABLE IF EXISTS user_privilege;
CREATE TABLE `user_privilege` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`        VARCHAR(255) NOT NULL UNIQUE,
  `create_time` DATETIME,
  `archived`    TINYINT(1)            DEFAULT '0',
  `version`     INT(11)               DEFAULT 0,

  `user_id`     INT(11),
  `privilege`   VARCHAR(255),
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;

-- ###############
-- Domain:  SystemSetting
-- ###############
DROP TABLE IF EXISTS system_setting;
CREATE TABLE `system_setting` (
  `id`                  INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`                VARCHAR(255) NOT NULL UNIQUE,
  `create_time`         DATETIME,
  `archived`            TINYINT(1)            DEFAULT '0',
  `version`             INT(11)               DEFAULT 0,

  `allow_user_register` TINYINT(1)            DEFAULT '1',
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;

-- ###############
-- Domain:  InstanceMonitorURLParameter
-- ###############
DROP TABLE IF EXISTS instance_monitor_url_parameter;
CREATE TABLE `instance_monitor_url_parameter` (
  `id`           INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`         VARCHAR(255) NOT NULL UNIQUE,
  `create_time`  DATETIME,
  `archived`     TINYINT(1)            DEFAULT '0',
  `version`      INT(11)               DEFAULT 0,

  `random_value` TINYINT(1)            DEFAULT '0',
  `key_`         VARCHAR(255),
  `value_`       VARCHAR(255),
  `instance_id`  INT(11),
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;


-- ###############
-- Domain:  WeixinUser
-- ###############
DROP TABLE IF EXISTS weixin_user;
CREATE TABLE `weixin_user` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`        VARCHAR(255) NOT NULL UNIQUE,
  `create_time` DATETIME,
  `archived`    TINYINT(1)            DEFAULT '0',
  `version`     INT(11)               DEFAULT 0,

  `openid`      VARCHAR(255) UNIQUE,
  `hb_username` VARCHAR(255),
  `nick_name`   VARCHAR(255),
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `hb_username_index` (`hb_username`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;


-- ###############
-- Domain:  ApplicationInstanceWeixinUser
-- ###############
DROP TABLE IF EXISTS application_instance_weixin_user;
CREATE TABLE `application_instance_weixin_user` (
  `id`             INT(11)      NOT NULL AUTO_INCREMENT,
  `guid`           VARCHAR(255) NOT NULL UNIQUE,
  `create_time`    DATETIME,
  `archived`       TINYINT(1)            DEFAULT '0',
  `version`        INT(11)               DEFAULT 0,

  `instance_id`    INT(11),
  `weixin_user_id` INT(11),
  PRIMARY KEY (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`),
  INDEX `weixin_user_id_index` (`weixin_user_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;


