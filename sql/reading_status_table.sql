CREATE TABLE
  `reading_status_table` (
    `reading_status_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `reading_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `reading_times` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[]',
    `reading_date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[]',
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `plan_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[]',
    PRIMARY KEY (`reading_status_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci