# 数据库建表语句

CREATE TABLE `test_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `location` varchar(255) DEFAULT NULL COMMENT '位置',
  `time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据导出测试表'