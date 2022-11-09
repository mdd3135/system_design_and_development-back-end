CREATE TABLE
  `recommend_table` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `recommend_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci