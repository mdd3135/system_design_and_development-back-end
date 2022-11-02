CREATE TABLE
  `user_table` (
    `user_id` varchar(255)  NOT NULL,
    `user_name` varchar(255)  NOT NULL,
    `user_password` varchar(255)  NOT NULL,
    `sex` varchar(255)  NOT NULL,
    `usr_picture` varchar(255)  DEFAULT '未知',
    `user_description` varchar(255)  DEFAULT NULL,
    `create_time` varchar(255)   NOT NULL,
    `session_id` varchar(255)   DEFAULT NULL,
    `expiration_time` varchar(255)   DEFAULT NULL,
    PRIMARY KEY (`user_id`)
  ) 