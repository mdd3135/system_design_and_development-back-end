CREATE TABLE
  `movielist_table` (
    `movielist_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movielist_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movielist_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `collector_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`movielist_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci