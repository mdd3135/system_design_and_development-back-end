CREATE TABLE
  `all_book_table` (
    `book_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `book_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `version` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `writer` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `publisher` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `publication_date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_picture` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
    `language` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `price` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(1023) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`book_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci