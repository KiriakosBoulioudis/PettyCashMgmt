
-----------------------------------------------------------------------------------
-- Create user table
-----------------------------------------------------------------------------------
CREATE TABLE "public"."user"
(
	"id" SERIAL PRIMARY KEY,
	"name" varchar(100) NOT NULL,
	"password" varchar(1024) NULL,
	"enabled" bool DEFAULT true NOT NULL
)
;

-----------------------------------------------------------------------------------
-- Create role table
-----------------------------------------------------------------------------------
CREATE TABLE "public"."role"
(
	"id" SERIAL PRIMARY KEY,
	"rolename" varchar(30) NOT NULL
)
;

-----------------------------------------------------------------------------------
-- Create user_role table
-----------------------------------------------------------------------------------
CREATE TABLE "public"."user_role"
(
	"id" SERIAL PRIMARY KEY,
	"user_id" integer NOT NULL,
    "role_id" integer NOT NULL
)
;

-----------------------------------------------------------------------------------
-- Create constraints at table usuario_rol
-----------------------------------------------------------------------------------
ALTER TABLE "public"."user_role" ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES "public"."role"(id);


ALTER TABLE "public"."user_role" ADD CONSTRAINT fk_user_rol_user FOREIGN KEY (user_id) REFERENCES "public"."user"(id);


ALTER TABLE "public"."user_role" ADD CONSTRAINT uq_user_role UNIQUE (role_id, user_id);


-----------------------------------------------------------------------------------
-- Create cuenta table
-----------------------------------------------------------------------------------
CREATE TABLE "public"."public_request"
(
	"id" SERIAL PRIMARY KEY,
	"user_id" integer NOT NULL,
	"amount" numeric(19,2) NOT NULL,
	"request_date" timestamp NOT NULL,
	"delivered" bool DEFAULT false NOT NULL,
	"delivery_date" timestamp NULL,
	"delivery_user_id" integer NULL
)
;


-----------------------------------------------------------------------------------
-- Create relacion_cuentas table
-----------------------------------------------------------------------------------
CREATE TABLE "public"."custodian_request"
(
   "id" SERIAL PRIMARY KEY,
   "user_id" integer NOT NULL,
   "amount" numeric(19,2) NOT NULL,
   "request_date" timestamp NOT NULL
);


-----------------------------------------------------------------------------------
-- Create cash_balance table
-----------------------------------------------------------------------------------
CREATE TABLE "public"."cash_balance"
(
   "id" SERIAL PRIMARY KEY,
   "user_id" integer NOT NULL,
   "amount" numeric(19,2) NOT NULL,
   "change_date" timestamp NOT NULL
);



-----------------------------------------------------------------------------------
-- Create constraints at tables
-----------------------------------------------------------------------------------
ALTER TABLE "public"."public_request" ADD CONSTRAINT fk_public_request_user FOREIGN KEY (user_id) REFERENCES "public"."user"(id);

ALTER TABLE "public"."custodian_request" ADD CONSTRAINT fk_custodian_request_user FOREIGN KEY (user_id) REFERENCES "public"."user"(id);

ALTER TABLE "public"."cash_balance" ADD CONSTRAINT fk_cash_balance_user FOREIGN KEY (user_id) REFERENCES "public"."user"(id);

ALTER TABLE "public"."public_request" ADD CONSTRAINT ck_delivered_user_not_null CHECK ( (NOT delivered) OR (delivery_user_id IS NOT NULL) ); 

