CREATE TABLE
  `all_book_table` (
    `book_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `book_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `version` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `writer` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `publisher` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `publication_date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `book_picture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `language` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `price` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `booklist_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `book_reaction_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `score/comment_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `total_score_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `total_comment_count` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    `total_score` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
    PRIMARY KEY (`book_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci