CREATE TABLE
  `user_table` (
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `user_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `user_password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `sex` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `user_picture` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
    `user_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `session_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `expiration_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
    `user_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '普通',
    PRIMARY KEY (`user_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci