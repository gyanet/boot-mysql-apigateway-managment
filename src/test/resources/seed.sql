INSERT INTO gateways (id,ip_address,name,serial_number) VALUES (1,'34.212.22.224','pos-1-0.7tir.sepanta.net','AKSJUALKI901HBGWUHA');
INSERT INTO gateways (id,ip_address,name,serial_number) VALUES (2,'123.125.67.159','vmi130970.contaboserver.net','AJSHUY6TGVCFSG6528IK');

INSERT INTO  peripherals (id,date_created,status,uid,vendor,gateway_id) VALUES (1,CURRENT_DATE ,'ONLINE',9653,'Soylent Corp',1);
INSERT INTO  peripherals (id,date_created,status,uid,vendor,gateway_id) VALUES (2,CURRENT_DATE,'OFFLINE',9597,'Soylent Corp',1);
INSERT INTO  peripherals (id,date_created,status,uid,vendor,gateway_id) VALUES (3,CURRENT_DATE,'ONLINE',5687,'Globex Corporation',2);
INSERT INTO  peripherals (id,date_created,status,uid,vendor,gateway_id) VALUES (4,CURRENT_DATE,'ONLINE',611466,'Globex Corporation',2);