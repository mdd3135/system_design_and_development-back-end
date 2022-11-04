CREATE TABLE
  `movie_reaction_table` (
    `movie_reaction_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_reaction_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `content_md` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `score_comment_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `total_score_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `total_comment_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `total_score` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    PRIMARY KEY (`movie_reaction_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci