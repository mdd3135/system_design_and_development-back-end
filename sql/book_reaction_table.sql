CREATE TABLE
  `book_reaction_table` (
    `book_reaction_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_reaction_picture` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
    `create_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `content_md` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`book_reaction_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci