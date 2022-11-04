CREATE TABLE
  `score_comment_table` (
    `score_comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `score` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `comment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`score_comment_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci