CREATE TABLE `springcloud`.`payment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `serial` VARCHAR(200) NULL DEFAULT '',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;