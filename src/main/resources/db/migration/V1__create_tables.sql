CREATE TABLE IF NOT EXISTS `forms` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `created_at`    DATETIME(6)     DEFAULT NULL,
    `description`   VARCHAR(255)    DEFAULT NULL,
    `order_display` INT             DEFAULT NULL,
    `status`        ENUM('ACTIVE', 'DRAFT') DEFAULT NULL,
    `title`         VARCHAR(255)    DEFAULT NULL,
    `updated_at`    DATETIME(6)     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `fields` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `created_at`    DATETIME(6)     DEFAULT NULL,
    `label`         VARCHAR(255)    DEFAULT NULL,
    `options`       VARCHAR(255)    DEFAULT NULL,
    `order_display` INT             DEFAULT NULL,
    `required`      BIT(1)          DEFAULT NULL,
    `type`          ENUM('COLOR', 'DATE', 'NUMBER', 'SELECT', 'TEXT') DEFAULT NULL,
    `updated_at`    DATETIME(6)     DEFAULT NULL,
    `form_id`       BIGINT          DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FKqvybh7bbb87ep1fgw5anjijvj`
        FOREIGN KEY (`form_id`) REFERENCES `forms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `submissions` (
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `submit_at` DATETIME(6) DEFAULT NULL,
    `form_id`   BIGINT      DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FKsa451myl41cavhch5eir0unua`
        FOREIGN KEY (`form_id`) REFERENCES `forms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `submission_value` (
    `id`            BIGINT      NOT NULL AUTO_INCREMENT,
    `value`         VARCHAR(255) DEFAULT NULL,
    `field_id`      BIGINT      DEFAULT NULL,
    `submission_id` BIGINT      DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK2161a9wqrpfukh8qev44ebqw0`
        FOREIGN KEY (`field_id`) REFERENCES `fields` (`id`),
    CONSTRAINT `FKn9bqfdbph81xeikgc7xet5y92`
        FOREIGN KEY (`submission_id`) REFERENCES `submissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;