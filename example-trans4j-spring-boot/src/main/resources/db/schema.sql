DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL COMMENT '名字',
    `is_deleted`  int          NOT NULL DEFAULT 0 COMMENT '删除标识;0正常 1删除',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
);

