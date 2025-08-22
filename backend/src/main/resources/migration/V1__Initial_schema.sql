-- Initial baseline migration

CREATE TABLE `batches`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `name`             varchar(255) NOT NULL,
    `date_of_purchase` date         NOT NULL,
    `description`      varchar(255) DEFAULT NULL,
    `maker`            varchar(255) NOT NULL,
    `quantity`         int          NOT NULL,
    `total_price`      int          NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `customers`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT,
    `balance` int          NOT NULL,
    `name`    varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `orders`
(
    `id`               bigint      NOT NULL AUTO_INCREMENT,
    `amount_to_pay`    int         NOT NULL,
    `date_of_purchase` datetime(6) NOT NULL,
    `total_price`      int         NOT NULL,
    `customer_id`      bigint      NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
);

CREATE TABLE `log`
(
    `date_time`      datetime(6)  NOT NULL,
    `actual_balance` int          NOT NULL,
    `event`          varchar(255) NOT NULL,
    `money_exchange` int    DEFAULT NULL,
    `order_no`       bigint DEFAULT NULL,
    PRIMARY KEY (`date_time`),
    FOREIGN KEY (`order_no`) REFERENCES `orders` (`id`)
);

CREATE TABLE `reeds`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `sell_price` int    NOT NULL,
    `status`     enum ('ADJUSTED','DESTROYED','NEW','SOLD','USED') DEFAULT NULL,
    `batch_id`   bigint NOT NULL,
    `order_no`   bigint                                            DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`order_no`) REFERENCES `orders` (`id`),
    FOREIGN KEY (`batch_id`) REFERENCES `batches` (`id`)
);