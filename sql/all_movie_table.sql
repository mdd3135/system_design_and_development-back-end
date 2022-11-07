CREATE TABLE
  `all_movie_table` (
    `movie_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `movie_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `director` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `scriptwriter` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `main_performer` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `movie_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `producer_country` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `language` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `release_date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `duration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(1023) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`movie_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci