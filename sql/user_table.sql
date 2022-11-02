CREATE TABLE
  `user_table` (
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `user_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `user_password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `sex` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `usr_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'NULL',
    `user_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `session_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `expiration_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'NULL',
    `user_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '普通',
    `seen_movie_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `read_book_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `movielist_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `booklist_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `book_reaction_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `movie_reaction_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`user_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci