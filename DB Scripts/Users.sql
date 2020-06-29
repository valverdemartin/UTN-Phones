use utnphones;

CREATE USER'client'@'localhost' IDENTIFIED BY 'Passw0rdClient';
grant select, update on utnphones.users to 'client'@'localhost' IDENTIFIED BY 'Passw0rdClient';
grant select on utnphones.phone_lines to 'client'@'localhost' IDENTIFIED BY 'Passw0rdClient';
grant select on utnphones.calls to 'client'@'localhost' IDENTIFIED BY 'Passw0rdClient';
grant select on utnphones.bills to 'client'@'localhost' IDENTIFIED BY 'Passw0rdClient';

CREATE USER 'backoffice'@'localhost' IDENTIFIED BY 'Passw0rdBackOffice';
grant insert, select, update, delete on utnphones.users to 'backoffice'@'localhost' IDENTIFIED BY 'Passw0rdBackOffice';
grant insert, select, update, delete on utnphones.phone_lines to 'backoffice'@'localhost' IDENTIFIED BY 'Passw0rdBackOffice';
grant select on utnphones.rates to 'backoffice'@'localhost' IDENTIFIED BY 'Passw0rdBackOffice';
grant select on utnphones.calls to 'backoffice'@'localhost' IDENTIFIED BY 'Passw0rdBackOffice';
grant select on utnphones.bills to 'backoffice'@'localhost' IDENTIFIED BY 'Passw0rdBackOffice';

CREATE USER'infra'@'localhost' IDENTIFIED BY 'Passw0rdInfra';
grant insert on utnphones.calls to 'infra'@'localhost' IDENTIFIED BY 'Passw0rdInfra';

FLUSH privileges;