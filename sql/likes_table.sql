CREATE TABLE
  `likes_table` (
    `likes_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `dest_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`likes_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci
