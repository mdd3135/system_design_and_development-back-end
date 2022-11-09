CREATE TABLE
  `movie_reaction_table` (
    `movie_reaction_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_reaction_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`movie_reaction_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci