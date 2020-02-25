ALTER TABLE `peripherals`
  drop CONSTRAINT `FK3c41gra8i66q1vfpqgj2vu4y8`;
COMMIT;

truncate table peripherals;
truncate table gateways;

ALTER TABLE `peripherals`
  add CONSTRAINT `FK3c41gra8i66q1vfpqgj2vu4y8` FOREIGN KEY (`gateway_id`) REFERENCES `gateways` (`id`);
COMMIT;
