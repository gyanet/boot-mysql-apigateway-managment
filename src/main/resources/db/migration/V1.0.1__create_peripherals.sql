DROP TABLE IF EXISTS `peripherals`;
CREATE TABLE `peripherals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `uid` bigint(20) DEFAULT NULL,
  `vendor` varchar(255) DEFAULT NULL,
  `gateway_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3c41gra8i66q1vfpqgj2vu4y8` (`gateway_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `peripherals`
  ADD CONSTRAINT `FK3c41gra8i66q1vfpqgj2vu4y8` FOREIGN KEY (`gateway_id`) REFERENCES `gateways` (`id`);
COMMIT;