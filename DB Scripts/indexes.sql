INSERT INTO utnphones.bills (id_line,calls_count,total_price,cost_price,bill_date,due_date,active) VALUES 
(2,400,890.9249877929688,300.0,'2020-06-28','2020-07-13',1)
;

update calls set call_date = "2020-06-25 00:00:41"
where id between 300001 and 1000000;

explain select count(*) from calls where id_origin_line = 1;

select count(*) from calls where id_origin_line = 1;

select * from calls where call_date between "2020-06-20 00:00:42" and "2020-06-24 00:00:42"

select count(*) from calls where call_date between "2020-06-20 00:00:42" and "2020-06-25 00:00:42"

explain select count(*) from calls where call_date between "2020-06-20 00:00:42" and "2020-06-24 00:00:42"

create index idx_calls_by_date on calls(call_date) using btree;

create index idx_calls_line on calls(id_origin_line) using hash;

create index idx_bill_by_date on bills(bill_date) using btree;

create index idx_bill_client on bills (id_line) using hash;

drop index idx_calls_by_date on calls;
drop index idx_calls_line on calls;

