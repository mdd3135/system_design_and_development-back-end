CREATE TABLE
  `booklist_table` (
    `booklist_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `booklist_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `booklist_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'null',
    `collector_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`booklist_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci