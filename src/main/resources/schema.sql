CREATE TABLE IF NOT EXISTS currencies (
    id              BIGINT                      NOT NULL    AUTO_INCREMENT,
    code            VARCHAR(64)                 NOT NULL    UNIQUE,
    name 	        VARCHAR(64)                 NOT NULL,
    created_date    TIMESTAMP WITH TIME ZONE    NOT NULL,
    last_modified   TIMESTAMP WITH TIME ZONE    NOT NULL,
    version         INT                         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `headers` (
  `id`            BIGINT                    NOT NULL              AUTO_INCREMENT  COMMENT '編號' ,
  `description`   VARCHAR(50)               NOT NULL                              COMMENT 'Header項目',
  `url`           VARCHAR(50)               NULL                                  COMMENT '連結',
  `priority`      INT                       NOT NULL  DEFAULT 1                   COMMENT '排序設定',
  `status`        CHAR(1)                   NOT NULL  DEFAULT '0'                 COMMENT '狀態 0:停用 1:啟用 2:刪除',
  `open_method`   CHAR(1)                   NOT NULL  DEFAULT '0'                 COMMENT '連結開啟方式 0:原視窗開啟 1:另開新視窗',
  `creator`       VARCHAR(20)               NULL                                  COMMENT '建立者',
  `created_date`  TIMESTAMP WITH TIME ZONE  NULL                                  COMMENT '建立時間',
  `modifier`      VARCHAR(20)               NULL                                  COMMENT '修改者',
  `last_modified` TIMESTAMP WITH TIME ZONE  NULL                                  COMMENT '修改時間',
  `version`       INT                       NULL,
  PRIMARY KEY (`id`)
);