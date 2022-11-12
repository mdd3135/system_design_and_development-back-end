CREATE TABLE
  `user_table` (
    `user_id` varchar(255) NOT NULL,
    `user_name` varchar(255) NOT NULL,
    `user_password` varchar(255) NOT NULL,
    `sex` varchar(255) NOT NULL,
    `user_picture` varchar(255) NOT NULL DEFAULT '',
    `user_description` varchar(255) NOT NULL,
    `create_time` varchar(255) NOT NULL,
    `session_id` varchar(255) NOT NULL,
    `expiration_time` varchar(255) NOT NULL DEFAULT '',
    `user_type` varchar(255) NOT NULL DEFAULT '普通',
    `user_email` varchar(255) NOT NULL DEFAULT '',
    `verified_code` varchar(255) NOT NULL DEFAULT '',
    PRIMARY KEY (`user_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci