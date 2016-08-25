SET NAMES utf8;


-- 2015-05-01
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


ALTER TABLE monitoring_reminder_log ADD `openid` VARCHAR(255);
ALTER TABLE monitoring_reminder_log ADD `wechat_content` TEXT;


-- index
CREATE INDEX instance_id_create_time_index ON frequency_monitor_log (instance_id, create_time);
CREATE INDEX create_time_index ON frequency_monitor_log (create_time);
CREATE INDEX archived_index ON frequency_monitor_log (archived);

CREATE INDEX create_time_index ON monitoring_reminder_log (create_time);
CREATE INDEX archived_index ON monitoring_reminder_log (archived);


-- 2016-08-02
ALTER TABLE application_instance ADD `private_instance` TINYINT(1) DEFAULT '0';

CREATE INDEX archived_enabled_private_instance_index ON application_instance (archived, enabled, private_instance);
CREATE INDEX archived_instance_id_create_time_index ON monitoring_reminder_log (archived, instance_id, create_time);
CREATE INDEX archived_create_time_index ON monitoring_reminder_log (archived, create_time);

CREATE INDEX archived_private_instance_index ON application_instance (archived, private_instance);
CREATE INDEX instance_id_archived_index ON frequency_monitor_log (instance_id, archived);


-- 2016-08-05
ALTER TABLE application_instance ADD `continue_failed_times` INT(11) DEFAULT 2;

