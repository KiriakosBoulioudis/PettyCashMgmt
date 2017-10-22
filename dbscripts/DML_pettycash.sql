


-----------------------------------------------------------------------------------
-- insert data into user table
-----------------------------------------------------------------------------------

INSERT INTO "public"."user" 
(name, password) values ('custodio', 'custodio');

INSERT INTO "public"."user" 
(name, password) values ('custodio2', 'custodio2');


-----------------------------------------------------------------------------------
-- insert data into role table
-----------------------------------------------------------------------------------

INSERT INTO "public"."role" 
(rolename) values ('CUSTODIAN');


-----------------------------------------------------------------------------------
-- insert data into user_role table
-----------------------------------------------------------------------------------

INSERT INTO "public"."user_role" ( user_id, role_id) 
SELECT f1.id, f2.id
FROM (Select us.id from "public"."user" as us where name ='custodio') AS f1
CROSS JOIN (Select usr.id from "public"."role" as usr where rolename ='CUSTODIAN') AS f2;


INSERT INTO "public"."user_role" ( user_id, role_id) 
SELECT f1.id, f2.id
FROM (Select us.id from "public"."user" as us where name ='custodio2') AS f1
CROSS JOIN (Select usr.id from "public"."role" as usr where rolename ='CUSTODIAN') AS f2;

