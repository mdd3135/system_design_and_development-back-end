CREATE TABLE
  `movielist_table` (
    `movielist_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movielist_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movielist_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `total_movie_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `score_comment_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `total_score_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `total_coment_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `total_score` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    PRIMARY KEY (`movielist_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci