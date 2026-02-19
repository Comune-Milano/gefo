--
CREATE USER dmifon WITH PASSWORD 'password_sicura';
CREATE DATABASE dmifon;
GRANT CREATE ON DATABASE dmifon TO dmifon;


--
-- DROP SCHEMA dmifonamm;

CREATE SCHEMA dmifonamm AUTHORIZATION dmifon;

-- DROP SEQUENCE dmifonamm.amm_all_id_seq;

CREATE SEQUENCE dmifonamm.amm_all_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_dir_id_seq;

CREATE SEQUENCE dmifonamm.amm_dir_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_ela_id_seq;

CREATE SEQUENCE dmifonamm.amm_ela_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_ent_id_seq;

CREATE SEQUENCE dmifonamm.amm_ent_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_fil_id_seq;

CREATE SEQUENCE dmifonamm.amm_fil_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_fun_id_seq;

CREATE SEQUENCE dmifonamm.amm_fun_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_out_id_seq;

CREATE SEQUENCE dmifonamm.amm_out_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_par_id_seq;

CREATE SEQUENCE dmifonamm.amm_par_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_per_id_seq;

CREATE SEQUENCE dmifonamm.amm_per_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_rig_id_seq;

CREATE SEQUENCE dmifonamm.amm_rig_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_ruo_id_seq;

CREATE SEQUENCE dmifonamm.amm_ruo_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_ute_id_seq;

CREATE SEQUENCE dmifonamm.amm_ute_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_uteruo_id_seq;

CREATE SEQUENCE dmifonamm.amm_uteruo_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.amm_uteruotipfin_id_seq;

CREATE SEQUENCE dmifonamm.amm_uteruotipfin_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonamm.sequence_generator;

CREATE SEQUENCE dmifonamm.sequence_generator
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1050
	CACHE 1
	NO CYCLE;-- dmifonamm.amm_dir definition

-- Drop table

-- DROP TABLE dmifonamm.amm_dir;

CREATE TABLE dmifonamm.amm_dir (
	id serial4 NOT NULL,
	coddir varchar(25) NULL,
	desdir varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_dir PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_dir_2 ON dmifonamm.amm_dir USING btree (coddir);
CREATE UNIQUE INDEX pk_amm_dir_1 ON dmifonamm.amm_dir USING btree (id);


-- dmifonamm.amm_ela definition

-- Drop table

-- DROP TABLE dmifonamm.amm_ela;

CREATE TABLE dmifonamm.amm_ela (
	id serial4 NOT NULL,
	desela varchar(250) NOT NULL,
	staela varchar(1) NOT NULL,
	dtaini timestamp NOT NULL,
	dtafin timestamp NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_ela PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_amm_ela_1 ON dmifonamm.amm_ela USING btree (id);


-- dmifonamm.amm_ent definition

-- Drop table

-- DROP TABLE dmifonamm.amm_ent;

CREATE TABLE dmifonamm.amm_ent (
	id serial4 NOT NULL,
	codent varchar(16) NULL,
	nome varchar(100) NULL,
	descrizione varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_ent PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_ent_2 ON dmifonamm.amm_ent USING btree (codent);
CREATE UNIQUE INDEX pk_amm_ent_1 ON dmifonamm.amm_ent USING btree (id);


-- dmifonamm.amm_fil definition

-- Drop table

-- DROP TABLE dmifonamm.amm_fil;

CREATE TABLE dmifonamm.amm_fil (
	id serial4 NOT NULL,
	file bytea NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	data_type varchar(100) NULL,
	CONSTRAINT pk_amm_fil PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_amm_fil_1 ON dmifonamm.amm_fil USING btree (id);


-- dmifonamm.amm_fun definition

-- Drop table

-- DROP TABLE dmifonamm.amm_fun;

CREATE TABLE dmifonamm.amm_fun (
	id serial4 NOT NULL,
	nome varchar(100) NOT NULL,
	descrizione varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_fun PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_fun_2 ON dmifonamm.amm_fun USING btree (nome);
CREATE UNIQUE INDEX pk_amm_fun_1 ON dmifonamm.amm_fun USING btree (id);


-- dmifonamm.amm_par definition

-- Drop table

-- DROP TABLE dmifonamm.amm_par;

CREATE TABLE dmifonamm.amm_par (
	id serial4 NOT NULL,
	codice varchar(100) NOT NULL,
	valore varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_par PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_par_2 ON dmifonamm.amm_par USING btree (codice);
CREATE UNIQUE INDEX pk_amm_par_1 ON dmifonamm.amm_par USING btree (id);


-- dmifonamm.amm_ruo definition

-- Drop table

-- DROP TABLE dmifonamm.amm_ruo;

CREATE TABLE dmifonamm.amm_ruo (
	id serial4 NOT NULL,
	codruo varchar(15) NOT NULL,
	desruo varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_ruo PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_ruo_2 ON dmifonamm.amm_ruo USING btree (codruo);
CREATE UNIQUE INDEX pk_amm_ruo_1 ON dmifonamm.amm_ruo USING btree (id);


-- dmifonamm.amm_ute definition

-- Drop table

-- DROP TABLE dmifonamm.amm_ute;

CREATE TABLE dmifonamm.amm_ute (
	id serial4 NOT NULL,
	username varchar(30) NOT NULL,
	nome varchar(100) NULL,
	cognome varchar(100) NULL,
	email varchar(100) NULL,
	emailalt varchar(100) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	abilitato bool NOT NULL DEFAULT true,
	CONSTRAINT pk_amm_ute PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_ute_2 ON dmifonamm.amm_ute USING btree (username);
CREATE UNIQUE INDEX pk_amm_ute_1 ON dmifonamm.amm_ute USING btree (id);


-- dmifonamm.databasechangelog definition

-- Drop table

-- DROP TABLE dmifonamm.databasechangelog;

CREATE TABLE dmifonamm.databasechangelog (
	id varchar(255) NOT NULL,
	author varchar(255) NOT NULL,
	filename varchar(255) NOT NULL,
	dateexecuted timestamp NOT NULL,
	orderexecuted int4 NOT NULL,
	exectype varchar(10) NOT NULL,
	md5sum varchar(35) NULL,
	description varchar(255) NULL,
	"comments" varchar(255) NULL,
	tag varchar(255) NULL,
	liquibase varchar(20) NULL,
	contexts varchar(255) NULL,
	labels varchar(255) NULL,
	deployment_id varchar(10) NULL
);


-- dmifonamm.databasechangeloglock definition

-- Drop table

-- DROP TABLE dmifonamm.databasechangeloglock;

CREATE TABLE dmifonamm.databasechangeloglock (
	id int4 NOT NULL,
	"locked" bool NOT NULL,
	lockgranted timestamp NULL,
	lockedby varchar(255) NULL,
	CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id)
);


-- dmifonamm.jhi_authority definition

-- Drop table

-- DROP TABLE dmifonamm.jhi_authority;

CREATE TABLE dmifonamm.jhi_authority (
	"name" varchar(50) NOT NULL,
	CONSTRAINT jhi_authority_pkey PRIMARY KEY (name)
);


-- dmifonamm.jhi_user definition

-- Drop table

-- DROP TABLE dmifonamm.jhi_user;

CREATE TABLE dmifonamm.jhi_user (
	id varchar(100) NOT NULL,
	login varchar(50) NOT NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	email varchar(191) NULL,
	image_url varchar(256) NULL,
	activated bool NOT NULL,
	lang_key varchar(10) NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT jhi_user_pkey PRIMARY KEY (id),
	CONSTRAINT ux_user_email UNIQUE (email),
	CONSTRAINT ux_user_login UNIQUE (login)
);


-- dmifonamm.jhi_user_authority definition

-- Drop table

-- DROP TABLE dmifonamm.jhi_user_authority;

CREATE TABLE dmifonamm.jhi_user_authority (
	user_id varchar(100) NOT NULL,
	authority_name varchar(50) NOT NULL,
	CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name)
);


-- dmifonamm.amm_all definition

-- Drop table

-- DROP TABLE dmifonamm.amm_all;

CREATE TABLE dmifonamm.amm_all (
	id serial4 NOT NULL,
	tipent varchar(4) NOT NULL,
	ident int8 NOT NULL,
	nomfil varchar(100) NOT NULL,
	notfil varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_fil int8 NULL,
	CONSTRAINT pk_amm_all PRIMARY KEY (id),
	CONSTRAINT fk_amm_all_1 FOREIGN KEY (id_fil) REFERENCES dmifonamm.amm_fil(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_amm_all_2 ON dmifonamm.amm_all USING btree (tipent, ident, nomfil);
CREATE UNIQUE INDEX pk_amm_all_1 ON dmifonamm.amm_all USING btree (id);


-- dmifonamm.amm_out definition

-- Drop table

-- DROP TABLE dmifonamm.amm_out;

CREATE TABLE dmifonamm.amm_out (
	id serial4 NOT NULL,
	id_ela int8 NOT NULL,
	desout varchar(250) NOT NULL,
	tipout varchar(3) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_out PRIMARY KEY (id),
	CONSTRAINT fk_amm_out_1 FOREIGN KEY (id_ela) REFERENCES dmifonamm.amm_ela(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_amm_out_2 ON dmifonamm.amm_out USING btree (id_ela);
CREATE UNIQUE INDEX pk_amm_out_1 ON dmifonamm.amm_out USING btree (id);


-- dmifonamm.amm_per definition

-- Drop table

-- DROP TABLE dmifonamm.amm_per;

CREATE TABLE dmifonamm.amm_per (
	id serial4 NOT NULL,
	id_ruo int8 NOT NULL,
	id_fun int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_per PRIMARY KEY (id),
	CONSTRAINT fk_amm_per_1 FOREIGN KEY (id_ruo) REFERENCES dmifonamm.amm_ruo(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_amm_per_2 FOREIGN KEY (id_fun) REFERENCES dmifonamm.amm_fun(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_amm_per_2 ON dmifonamm.amm_per USING btree (id_ruo, id_fun);
CREATE INDEX id_amm_per_3 ON dmifonamm.amm_per USING btree (id_fun);
CREATE UNIQUE INDEX pk_amm_per_1 ON dmifonamm.amm_per USING btree (id);


-- dmifonamm.amm_rig definition

-- Drop table

-- DROP TABLE dmifonamm.amm_rig;

CREATE TABLE dmifonamm.amm_rig (
	id serial4 NOT NULL,
	id_out int8 NOT NULL,
	nrorig int8 NOT NULL,
	prgrig int4 NOT NULL,
	tesrig varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_rig PRIMARY KEY (id),
	CONSTRAINT fk_amm_rig_1 FOREIGN KEY (id_out) REFERENCES dmifonamm.amm_out(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_amm_rig_2 ON dmifonamm.amm_rig USING btree (id_out);
CREATE UNIQUE INDEX pk_amm_rig_1 ON dmifonamm.amm_rig USING btree (id);


-- dmifonamm.amm_uteruo definition

-- Drop table

-- DROP TABLE dmifonamm.amm_uteruo;

CREATE TABLE dmifonamm.amm_uteruo (
	id serial4 NOT NULL,
	id_ute int8 NOT NULL,
	id_ruo int8 NOT NULL,
	flgdef varchar(1) NOT NULL,
	tipcondat varchar(1) NOT NULL,
	id_dir int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_amm_uteruo PRIMARY KEY (id),
	CONSTRAINT fk_amm_uteruo_1 FOREIGN KEY (id_ute) REFERENCES dmifonamm.amm_ute(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_amm_uteruo_2 FOREIGN KEY (id_ruo) REFERENCES dmifonamm.amm_ruo(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_amm_uteruo_3 FOREIGN KEY (id_dir) REFERENCES dmifonamm.amm_dir(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_amm_uteruo_2 ON dmifonamm.amm_uteruo USING btree (id_ute, id_ruo, id_dir);
CREATE INDEX id_amm_uteruo_3 ON dmifonamm.amm_uteruo USING btree (id_ruo);
CREATE INDEX id_amm_uteruo_4 ON dmifonamm.amm_uteruo USING btree (id_dir);
CREATE UNIQUE INDEX pk_amm_uteruo_1 ON dmifonamm.amm_uteruo USING btree (id);


-- dmifonamm.amm_uteruotipfin definition

-- Drop table

-- DROP TABLE dmifonamm.amm_uteruotipfin;

CREATE TABLE dmifonamm.amm_uteruotipfin (
	id serial4 NOT NULL,
	id_uteruo int8 NOT NULL,
	id_tipfin int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	tipcondat varchar(1) NULL,
	CONSTRAINT pk_amm_uteruotipfin PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_amm_uteruotipfin_2 ON dmifonamm.amm_uteruotipfin USING btree (id_uteruo, id_tipfin);
CREATE INDEX id_amm_uteruotipfin_3 ON dmifonamm.amm_uteruotipfin USING btree (id_uteruo);
CREATE INDEX id_amm_uteruotipfin_4 ON dmifonamm.amm_uteruotipfin USING btree (id_tipfin);
CREATE UNIQUE INDEX pk_amm_uteruotipfin_1 ON dmifonamm.amm_uteruotipfin USING btree (id);


-- dmifonamm.amm_uteruotipfin foreign keys

ALTER TABLE dmifonamm.amm_uteruotipfin ADD CONSTRAINT fk_amm_uteruotipfin_1 FOREIGN KEY (id_uteruo) REFERENCES dmifonamm.amm_uteruo(id) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE dmifonamm.amm_uteruotipfin ADD CONSTRAINT fk_amm_uteruotipfin_2 FOREIGN KEY (id_tipfin) REFERENCES dmifonpro.pro_tipfin(id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- DROP SCHEMA dmifonpro;

CREATE SCHEMA dmifonpro AUTHORIZATION dmifon;

-- DROP SEQUENCE dmifonpro.pro_ava_id_seq;

CREATE SEQUENCE dmifonpro.pro_ava_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_ban_id_seq;

CREATE SEQUENCE dmifonpro.pro_ban_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_cum_id_seq;

CREATE SEQUENCE dmifonpro.pro_cum_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_ddr_id_seq;

CREATE SEQUENCE dmifonpro.pro_ddr_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_ddra_id_seq;

CREATE SEQUENCE dmifonpro.pro_ddra_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_detcon_id_seq;

CREATE SEQUENCE dmifonpro.pro_detcon_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_detconddr_id_seq;

CREATE SEQUENCE dmifonpro.pro_detconddr_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_detproddr_id_seq;

CREATE SEQUENCE dmifonpro.pro_detproddr_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_dettipolfas_id_seq;

CREATE SEQUENCE dmifonpro.pro_dettipolfas_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_dirban_id_seq;

CREATE SEQUENCE dmifonpro.pro_dirban_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_entcon_id_seq;

CREATE SEQUENCE dmifonpro.pro_entcon_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_entconman_id_seq;

CREATE SEQUENCE dmifonpro.pro_entconman_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_fas_id_seq;

CREATE SEQUENCE dmifonpro.pro_fas_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_imp_id_seq;

CREATE SEQUENCE dmifonpro.pro_imp_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_impprocum_id_seq;

CREATE SEQUENCE dmifonpro.pro_impprocum_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_ind_id_seq;

CREATE SEQUENCE dmifonpro.pro_ind_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_infagg_id_seq;

CREATE SEQUENCE dmifonpro.pro_infagg_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_lisval_id_seq;

CREATE SEQUENCE dmifonpro.pro_lisval_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_macpro_id_seq;

CREATE SEQUENCE dmifonpro.pro_macpro_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_mil_id_seq;

CREATE SEQUENCE dmifonpro.pro_mil_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_mis_id_seq;

CREATE SEQUENCE dmifonpro.pro_mis_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_mun_id_seq;

CREATE SEQUENCE dmifonpro.pro_mun_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_munpro_id_seq;

CREATE SEQUENCE dmifonpro.pro_munpro_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_nil_id_seq;

CREATE SEQUENCE dmifonpro.pro_nil_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_nilpro_id_seq;

CREATE SEQUENCE dmifonpro.pro_nilpro_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_pre_id_seq;

CREATE SEQUENCE dmifonpro.pro_pre_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_pro_id_seq;

CREATE SEQUENCE dmifonpro.pro_pro_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_procum_id_seq;

CREATE SEQUENCE dmifonpro.pro_procum_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_res_id_seq;

CREATE SEQUENCE dmifonpro.pro_res_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_ric_id_seq;

CREATE SEQUENCE dmifonpro.pro_ric_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_ricute_id_seq;

CREATE SEQUENCE dmifonpro.pro_ricute_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_staban_id_seq;

CREATE SEQUENCE dmifonpro.pro_staban_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_stafin_id_seq;

CREATE SEQUENCE dmifonpro.pro_stafin_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_stapro_id_seq;

CREATE SEQUENCE dmifonpro.pro_stapro_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_strpro_id_seq;

CREATE SEQUENCE dmifonpro.pro_strpro_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tar_id_seq;

CREATE SEQUENCE dmifonpro.pro_tar_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tem_id_seq;

CREATE SEQUENCE dmifonpro.pro_tem_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tipfas_id_seq;

CREATE SEQUENCE dmifonpro.pro_tipfas_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tipfin_id_seq;

CREATE SEQUENCE dmifonpro.pro_tipfin_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tipimp_id_seq;

CREATE SEQUENCE dmifonpro.pro_tipimp_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tipind_id_seq;

CREATE SEQUENCE dmifonpro.pro_tipind_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tipinfagg_id_seq;

CREATE SEQUENCE dmifonpro.pro_tipinfagg_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.pro_tipres_id_seq;

CREATE SEQUENCE dmifonpro.pro_tipres_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.sal_key_peg_id_seq;

CREATE SEQUENCE dmifonpro.sal_key_peg_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE dmifonpro.sequence_generator;

CREATE SEQUENCE dmifonpro.sequence_generator
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1050
	CACHE 1
	NO CYCLE;-- dmifonpro.pro_cum definition

-- Drop table

-- DROP TABLE dmifonpro.pro_cum;

CREATE TABLE dmifonpro.pro_cum (
	id serial4 NOT NULL,
	tipent varchar(4) NOT NULL,
	codentcon varchar(25) NOT NULL,
	notcum varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_cum PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_pro_cum_2 ON dmifonpro.pro_cum USING btree (tipent, codentcon);
CREATE UNIQUE INDEX pk_pro_cum_1 ON dmifonpro.pro_cum USING btree (id);


-- dmifonpro.pro_ddra definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ddra;

CREATE TABLE dmifonpro.pro_ddra (
	id serial4 NOT NULL,
	codddra varchar(25) NOT NULL,
	desddra varchar(250) NOT NULL,
	dtaddra date NOT NULL,
	notddra varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_ddra PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_pro_ddra_2 ON dmifonpro.pro_ddra USING btree (codddra);
CREATE UNIQUE INDEX pk_pro_ddra_1 ON dmifonpro.pro_ddra USING btree (id);


-- dmifonpro.pro_entcon definition

-- Drop table

-- DROP TABLE dmifonpro.pro_entcon;

CREATE TABLE dmifonpro.pro_entcon (
	id serial4 NOT NULL,
	tipent varchar(4) NOT NULL,
	codentcon varchar(25) NOT NULL,
	desimp varchar(250) NULL,
	idcap varchar(25) NULL,
	anncmp int4 NOT NULL,
	impass numeric(17, 2) NOT NULL,
	impliq numeric(17, 2) NOT NULL,
	impman numeric(17, 2) NOT NULL,
	despdd varchar(250) NULL,
	idcro varchar(25) NULL,
	codcup varchar(15) NULL,
	codcig varchar(10) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	nroconapp int8 NOT NULL DEFAULT 0,
	impmaneme numeric(17, 2) NOT NULL DEFAULT 0,
	impeco numeric(17, 2) NOT NULL DEFAULT 0,
	codgami varchar(20) NULL,
	impini numeric(17, 2) NOT NULL DEFAULT 0,
	idtipfin varchar(25) NULL,
	idalt varchar(25) NULL,
	dtareg date NULL,
	codsta varchar(25) NULL,
	impecononvin numeric(17, 3) NOT NULL DEFAULT 0,
	impfpvini numeric(17, 3) NOT NULL DEFAULT 0,
	impfpvfin numeric(17, 3) NOT NULL DEFAULT 0,
	ecovinese numeric(17, 3) NOT NULL DEFAULT 0,
	impmanemeseniva numeric(17, 3) NOT NULL DEFAULT 0,
	CONSTRAINT pk_pro_entcon PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_pro_entcon_2 ON dmifonpro.pro_entcon USING btree (tipent, codentcon);
CREATE INDEX id_pro_entcon_3 ON dmifonpro.pro_entcon USING btree (idcro);
CREATE UNIQUE INDEX pk_pro_entcon_1 ON dmifonpro.pro_entcon USING btree (id);


-- dmifonpro.pro_entconman definition

-- Drop table

-- DROP TABLE dmifonpro.pro_entconman;

CREATE TABLE dmifonpro.pro_entconman (
	id serial4 NOT NULL,
	tipent varchar(4) NOT NULL,
	codentcon varchar(25) NOT NULL,
	tipele varchar(1) NULL,
	anncmp int4 NOT NULL,
	nroipg int8 NOT NULL,
	idimp varchar(25) NULL,
	impman numeric(17, 3) NOT NULL,
	codcig varchar(10) NULL,
	desfat varchar(250) NULL,
	ragsoc varchar(250) NULL,
	codfis varchar(16) NULL,
	codiva varchar(11) NULL,
	idman varchar(25) NULL,
	nroman int8 NOT NULL,
	dtareg date NULL,
	dtapag date NULL,
	annman int4 NOT NULL,
	tipatt varchar(25) NULL,
	idatt varchar(25) NULL,
	codcup varchar(15) NULL,
	benragsoc varchar(250) NULL,
	bencodfis varchar(16) NULL,
	bencodiva varchar(11) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	impivaist numeric(17, 2) NOT NULL DEFAULT 0,
	impivacom numeric(17, 2) NOT NULL DEFAULT 0,
	imprit numeric(17, 2) NOT NULL DEFAULT 0,
	nrosotcon varchar(9) NULL,
	codsog varchar(25) NULL,
	CONSTRAINT pk_pro_entconman PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_pro_entconman_2 ON dmifonpro.pro_entconman USING btree (tipent, codentcon);
CREATE INDEX id_pro_entconman_3 ON dmifonpro.pro_entconman USING btree (idimp);
CREATE UNIQUE INDEX pk_pro_entconman_1 ON dmifonpro.pro_entconman USING btree (id);


-- dmifonpro.pro_lisval definition

-- Drop table

-- DROP TABLE dmifonpro.pro_lisval;

CREATE TABLE dmifonpro.pro_lisval (
	id serial4 NOT NULL,
	tiplis varchar(25) NOT NULL,
	vallis varchar(100) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	codlis varchar(25) NULL,
	ordlis int4 NOT NULL DEFAULT 0,
	tipliscol varchar(25) NULL,
	codliscol varchar(25) NULL,
	CONSTRAINT pk_pro_lisval PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_pro_lisval_2 ON dmifonpro.pro_lisval USING btree (tiplis, vallis);
CREATE INDEX id_pro_lisval_3 ON dmifonpro.pro_lisval USING btree (tiplis, ordlis);
CREATE INDEX id_pro_lisval_4 ON dmifonpro.pro_lisval USING btree (tipliscol, codliscol);
CREATE UNIQUE INDEX pk_pro_lisval_1 ON dmifonpro.pro_lisval USING btree (id);


-- dmifonpro.pro_mun definition

-- Drop table

-- DROP TABLE dmifonpro.pro_mun;

CREATE TABLE dmifonpro.pro_mun (
	id serial4 NOT NULL,
	desmun varchar(50) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_mun PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_pro_mun_1 ON dmifonpro.pro_mun USING btree (id);


-- dmifonpro.pro_staban definition

-- Drop table

-- DROP TABLE dmifonpro.pro_staban;

CREATE TABLE dmifonpro.pro_staban (
	id serial4 NOT NULL,
	dessta varchar(50) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_staban PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_pro_staban_1 ON dmifonpro.pro_staban USING btree (id);


-- dmifonpro.pro_stafin definition

-- Drop table

-- DROP TABLE dmifonpro.pro_stafin;

CREATE TABLE dmifonpro.pro_stafin (
	id serial4 NOT NULL,
	dessta varchar(50) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	tipsta varchar(1) NOT NULL DEFAULT ' '::character varying,
	ordsta int4 NOT NULL DEFAULT 0,
	CONSTRAINT pk_pro_stafin PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_pro_stafin_1 ON dmifonpro.pro_stafin USING btree (id);


-- dmifonpro.pro_stapro definition

-- Drop table

-- DROP TABLE dmifonpro.pro_stapro;

CREATE TABLE dmifonpro.pro_stapro (
	id serial4 NOT NULL,
	dessta varchar(50) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	tipsta varchar(1) NOT NULL DEFAULT 'N'::character varying,
	ordsta int4 NOT NULL DEFAULT 0,
	CONSTRAINT pk_pro_stapro PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_pro_stapro_1 ON dmifonpro.pro_stapro USING btree (id);


-- dmifonpro.pro_tem definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tem;

CREATE TABLE dmifonpro.pro_tem (
	id serial4 NOT NULL,
	destem varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	livuno int8 NOT NULL DEFAULT 0,
	livdue int8 NOT NULL DEFAULT 0,
	codtem varchar(25) NOT NULL DEFAULT ' '::character varying,
	CONSTRAINT pk_pro_tem PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ak_pro_tem_2 ON dmifonpro.pro_tem USING btree (livuno, livdue);
CREATE UNIQUE INDEX ak_pro_tem_3 ON dmifonpro.pro_tem USING btree (codtem);
CREATE UNIQUE INDEX pk_pro_tem_1 ON dmifonpro.pro_tem USING btree (id);


-- dmifonpro.pro_tipfas definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tipfas;

CREATE TABLE dmifonpro.pro_tipfas (
	id serial4 NOT NULL,
	desfas varchar(50) NOT NULL,
	tipcon varchar(1) NOT NULL,
	ordfas int4 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	tipfas varchar(1) NULL,
	CONSTRAINT pk_pro_tipfas PRIMARY KEY (id)
);
CREATE INDEX id_pro_tipfas_2 ON dmifonpro.pro_tipfas USING btree (ordfas);
CREATE UNIQUE INDEX pk_pro_tipfas_1 ON dmifonpro.pro_tipfas USING btree (id);


-- dmifonpro.pro_tipimp definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tipimp;

CREATE TABLE dmifonpro.pro_tipimp (
	id serial4 NOT NULL,
	destipimp varchar(50) NOT NULL,
	flgtipimp varchar(1) NOT NULL,
	flgdicui varchar(1) NOT NULL,
	ordtipimp int4 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_tipimp PRIMARY KEY (id)
);
CREATE INDEX id_pro_tipimp_2 ON dmifonpro.pro_tipimp USING btree (ordtipimp);
CREATE UNIQUE INDEX pk_pro_tipimp_1 ON dmifonpro.pro_tipimp USING btree (id);


-- dmifonpro.pro_tipinfagg definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tipinfagg;

CREATE TABLE dmifonpro.pro_tipinfagg (
	id serial4 NOT NULL,
	destipinfagg varchar(50) NOT NULL,
	ordtipinfagg int4 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_tipinfagg PRIMARY KEY (id)
);
CREATE INDEX id_pro_tipinfagg_2 ON dmifonpro.pro_tipinfagg USING btree (ordtipinfagg);
CREATE UNIQUE INDEX pk_pro_tipinfagg_1 ON dmifonpro.pro_tipinfagg USING btree (id);


-- dmifonpro.pro_tipres definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tipres;

CREATE TABLE dmifonpro.pro_tipres (
	id serial4 NOT NULL,
	destipres varchar(50) NOT NULL,
	ordtipres int4 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_tipres PRIMARY KEY (id)
);
CREATE INDEX id_pro_tipres_2 ON dmifonpro.pro_tipres USING btree (ordtipres);
CREATE UNIQUE INDEX pk_pro_tipres_1 ON dmifonpro.pro_tipres USING btree (id);


-- dmifonpro.sal_2024_01_pro_fas definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2024_01_pro_fas;

CREATE TABLE dmifonpro.sal_2024_01_pro_fas (
	id int4 NULL,
	id_ava int8 NULL,
	id_tipfas int8 NULL,
	dtainiori date NULL,
	dtafinori date NULL,
	dtainipre date NULL,
	dtafinpre date NULL,
	dtainieff date NULL,
	dtafineff date NULL,
	notfas varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL
);


-- dmifonpro.sal_2024_01_pro_mil definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2024_01_pro_mil;

CREATE TABLE dmifonpro.sal_2024_01_pro_mil (
	id int4 NULL,
	id_fas int8 NULL,
	desmil varchar(250) NULL,
	dtaded date NULL,
	dtaeff date NULL,
	notmil varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL
);


-- dmifonpro.sal_2024_02_pro_ban definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2024_02_pro_ban;

CREATE TABLE dmifonpro.sal_2024_02_pro_ban (
	id int4 NULL,
	codban varchar(25) NULL,
	desban varchar(250) NULL,
	id_staban int8 NULL,
	id_tipfin int8 NULL,
	id_tem int8 NULL,
	desent varchar(250) NULL,
	desprvent varchar(250) NULL,
	refmin varchar(250) NULL,
	desben varchar(250) NULL,
	impinv numeric(17, 2) NULL,
	impban numeric(17, 2) NULL,
	impmaspro numeric(17, 2) NULL,
	impmaspar numeric(17, 2) NULL,
	dtapubban date NULL,
	dtachiatt date NULL,
	dtascaban date NULL,
	desdecfin varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL
);


-- dmifonpro.sal_2024_02_pro_pro definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2024_02_pro_pro;

CREATE TABLE dmifonpro.sal_2024_02_pro_pro (
	id int4 NULL,
	codpro varchar(25) NULL,
	despro varchar(500) NULL,
	id_tipfin int8 NULL,
	id_macpro int8 NULL,
	id_ban int8 NULL,
	id_propad int8 NULL,
	iddecpro varchar(90) NULL,
	livpro int4 NULL,
	cntlivinf int4 NULL,
	codappren varchar(25) NULL,
	codcup varchar(15) NULL,
	codcig varchar(10) NULL,
	tippro varchar(3) NULL,
	codtippro varchar(50) NULL,
	desaffhou varchar(250) NULL,
	flgopeavv varchar(1) NULL,
	notpro varchar(500) NULL,
	impigv numeric(17, 2) NULL,
	rifigv varchar(250) NULL,
	impigvdel numeric(17, 2) NULL,
	notigv varchar(250) NULL,
	notaff varchar(250) NULL,
	impimppre numeric(17, 2) NULL,
	impaccpre numeric(17, 2) NULL,
	impmanpre numeric(17, 2) NULL,
	imprevpre numeric(17, 2) NULL,
	impimpric numeric(17, 2) NULL,
	codgami varchar(20) NULL,
	codset varchar(25) NULL,
	codass varchar(25) NULL,
	veraff varchar(250) NULL,
	estaff varchar(250) NULL,
	codcui varchar(25) NULL,
	id_stafin int8 NULL,
	id_stapro int8 NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_tem int8 NULL,
	delcan varchar(250) NULL,
	notimp varchar(500) NULL,
	notpre varchar(250) NULL,
	id_mun int8 NULL,
	id_nil int8 NULL,
	id_dir int8 NULL,
	"nil" int8 NULL,
	codstr varchar(25) NULL,
	codciv varchar(25) NULL
);


-- dmifonpro.sal_2024_02_pro_tem definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2024_02_pro_tem;

CREATE TABLE dmifonpro.sal_2024_02_pro_tem (
	id int4 NULL,
	destem varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	livuno int8 NULL,
	livdue int8 NULL,
	codtem varchar(25) NULL
);


-- dmifonpro.sal_2025_02_pro_detcon definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2025_02_pro_detcon;

CREATE TABLE dmifonpro.sal_2025_02_pro_detcon (
	id int4 NULL,
	id_pro int8 NULL,
	tipent varchar(4) NULL,
	codentcon varchar(25) NULL,
	tipentcol varchar(4) NULL,
	codentcol varchar(25) NULL,
	notent varchar(500) NULL,
	id_tipimp int8 NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_lisvaltipdetcon int8 NULL,
	flgpar varchar(1) NULL,
	impasspar numeric(17, 2) NULL,
	impmanpar numeric(17, 2) NULL
);


-- dmifonpro.sal_2025_02_pro_entcon definition

-- Drop table

-- DROP TABLE dmifonpro.sal_2025_02_pro_entcon;

CREATE TABLE dmifonpro.sal_2025_02_pro_entcon (
	id int4 NULL,
	tipent varchar(4) NULL,
	codentcon varchar(25) NULL,
	desimp varchar(250) NULL,
	idcap varchar(25) NULL,
	anncmp int4 NULL,
	impass numeric(17, 2) NULL,
	impliq numeric(17, 2) NULL,
	impman numeric(17, 2) NULL,
	despdd varchar(250) NULL,
	idcro varchar(25) NULL,
	codcup varchar(15) NULL,
	codcig varchar(10) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	nroconapp int8 NULL,
	impmaneme numeric(17, 2) NULL,
	impeco numeric(17, 2) NULL,
	codgami varchar(20) NULL,
	impini numeric(17, 2) NULL,
	idtipfin varchar(25) NULL,
	idalt varchar(25) NULL,
	dtareg date NULL,
	codsta varchar(25) NULL,
	impecononvin numeric(17, 3) NULL,
	impfpvini numeric(17, 3) NULL,
	impfpvfin numeric(17, 3) NULL
);


-- dmifonpro.sal_key_peg definition

-- Drop table

-- DROP TABLE dmifonpro.sal_key_peg;

CREATE TABLE dmifonpro.sal_key_peg (
	id serial4 NOT NULL,
	tipent varchar(4) NOT NULL,
	codentcon varchar(25) NOT NULL,
	"key" varchar(25) NOT NULL,
	CONSTRAINT pk_sal_key_peg PRIMARY KEY (id)
);
CREATE UNIQUE INDEX pk_sal_key_peg_1 ON dmifonpro.sal_key_peg USING btree (id);


-- dmifonpro.pro_dettipolfas definition

-- Drop table

-- DROP TABLE dmifonpro.pro_dettipolfas;

CREATE TABLE dmifonpro.pro_dettipolfas (
	id serial4 NOT NULL,
	id_lisvaltipolfas int8 NOT NULL,
	id_tipfas int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_dettipolfas PRIMARY KEY (id),
	CONSTRAINT fk_pro_dettipolfas_1 FOREIGN KEY (id_lisvaltipolfas) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_dettipolfas_2 FOREIGN KEY (id_tipfas) REFERENCES dmifonpro.pro_tipfas(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_dettipolfas_2 ON dmifonpro.pro_dettipolfas USING btree (id_lisvaltipolfas);
CREATE INDEX id_pro_dettipolfas_3 ON dmifonpro.pro_dettipolfas USING btree (id_tipfas);
CREATE UNIQUE INDEX pk_pro_dettipolfas_1 ON dmifonpro.pro_dettipolfas USING btree (id);


-- dmifonpro.pro_nil definition

-- Drop table

-- DROP TABLE dmifonpro.pro_nil;

CREATE TABLE dmifonpro.pro_nil (
	id serial4 NOT NULL,
	codnil varchar(25) NOT NULL,
	desnil varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_mun int8 NULL,
	CONSTRAINT pk_pro_nil PRIMARY KEY (id),
	CONSTRAINT fk_pro_nil_1 FOREIGN KEY (id_mun) REFERENCES dmifonpro.pro_mun(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_nil_2 ON dmifonpro.pro_nil USING btree (id_mun);
CREATE UNIQUE INDEX pk_pro_nil_1 ON dmifonpro.pro_nil USING btree (id);


-- dmifonpro.pro_tipfin definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tipfin;

CREATE TABLE dmifonpro.pro_tipfin (
	id serial4 NOT NULL,
	livuno int8 NOT NULL,
	livdue int8 NOT NULL,
	livtre int8 NOT NULL,
	livqua int8 NOT NULL,
	livcin int8 NOT NULL,
	codtipfin varchar(25) NOT NULL,
	destipfin varchar(250) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	livsei int8 NOT NULL DEFAULT 0,
	impdot numeric(17, 3) NOT NULL DEFAULT 0,
	id_lisvalstatipfin int8 NULL,
	CONSTRAINT pk_pro_tipfin PRIMARY KEY (id),
	CONSTRAINT fk_pro_tipfin_1 FOREIGN KEY (id_lisvalstatipfin) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_tipfin_2 ON dmifonpro.pro_tipfin USING btree (livuno, livdue, livtre, livqua, livcin, livsei);
CREATE UNIQUE INDEX ak_pro_tipfin_3 ON dmifonpro.pro_tipfin USING btree (codtipfin);
CREATE UNIQUE INDEX pk_pro_tipfin_1 ON dmifonpro.pro_tipfin USING btree (id);


-- dmifonpro.pro_tipind definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tipind;

CREATE TABLE dmifonpro.pro_tipind (
	id serial4 NOT NULL,
	codtipind varchar(25) NOT NULL,
	destipind varchar(250) NOT NULL,
	id_lisvaltipolind int8 NOT NULL,
	id_lisvalunimis int8 NOT NULL,
	id_tipfin int8 NULL,
	nottipind varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_tipind PRIMARY KEY (id),
	CONSTRAINT fk_pro_tipind_1 FOREIGN KEY (id_lisvaltipolind) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_tipind_2 FOREIGN KEY (id_lisvalunimis) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_tipind_3 FOREIGN KEY (id_tipfin) REFERENCES dmifonpro.pro_tipfin(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_tipind_2 ON dmifonpro.pro_tipind USING btree (codtipind, id_tipfin);
CREATE INDEX id_pro_tipind_3 ON dmifonpro.pro_tipind USING btree (id_tipfin);
CREATE UNIQUE INDEX pk_pro_tipind_1 ON dmifonpro.pro_tipind USING btree (id);


-- dmifonpro.pro_ban definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ban;

CREATE TABLE dmifonpro.pro_ban (
	id serial4 NOT NULL,
	codban varchar(25) NOT NULL,
	desban varchar(250) NOT NULL,
	id_staban int8 NULL,
	id_tipfin int8 NOT NULL,
	id_tem int8 NULL,
	desent varchar(250) NULL,
	desprvent varchar(250) NULL,
	refmin varchar(250) NULL,
	desben varchar(250) NULL,
	impinv numeric(17, 2) NOT NULL,
	impban numeric(17, 2) NOT NULL,
	impmaspro numeric(17, 2) NOT NULL,
	impmaspar numeric(17, 2) NOT NULL,
	dtapubban date NULL,
	dtachiatt date NULL,
	dtascaban date NULL,
	desdecfin varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	capfil varchar(250) NULL,
	modpredom varchar(250) NULL,
	link varchar(250) NULL,
	desbanest varchar(500) NULL,
	notban varchar(250) NULL,
	cofben varchar(250) NULL,
	vinban varchar(250) NULL,
	CONSTRAINT pk_pro_ban PRIMARY KEY (id),
	CONSTRAINT fk_pro_ban_1 FOREIGN KEY (id_tipfin) REFERENCES dmifonpro.pro_tipfin(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ban_2 FOREIGN KEY (id_tem) REFERENCES dmifonpro.pro_tem(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ban_3 FOREIGN KEY (id_staban) REFERENCES dmifonpro.pro_staban(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_ban_2 ON dmifonpro.pro_ban USING btree (codban);
CREATE INDEX id_pro_ban_3 ON dmifonpro.pro_ban USING btree (id_tipfin);
CREATE INDEX id_pro_ban_4 ON dmifonpro.pro_ban USING btree (id_tem);
CREATE INDEX id_pro_ban_5 ON dmifonpro.pro_ban USING btree (id_staban);
CREATE UNIQUE INDEX pk_pro_ban_1 ON dmifonpro.pro_ban USING btree (id);


-- dmifonpro.pro_dirban definition

-- Drop table

-- DROP TABLE dmifonpro.pro_dirban;

CREATE TABLE dmifonpro.pro_dirban (
	id serial4 NOT NULL,
	id_ban int8 NOT NULL,
	id_dir int8 NOT NULL,
	nomdir varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_dirban PRIMARY KEY (id),
	CONSTRAINT fk_pro_dirban_1 FOREIGN KEY (id_ban) REFERENCES dmifonpro.pro_ban(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_dirban_2 ON dmifonpro.pro_dirban USING btree (id_ban, id_dir);
CREATE UNIQUE INDEX pk_pro_dirban_1 ON dmifonpro.pro_dirban USING btree (id);


-- dmifonpro.pro_macpro definition

-- Drop table

-- DROP TABLE dmifonpro.pro_macpro;

CREATE TABLE dmifonpro.pro_macpro (
	id serial4 NOT NULL,
	codmacpro varchar(25) NOT NULL,
	desmacpro varchar(250) NOT NULL,
	id_tipfinda int8 NULL,
	id_tipfina int8 NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_macpro PRIMARY KEY (id),
	CONSTRAINT fk_pro_macpro_1 FOREIGN KEY (id_tipfinda) REFERENCES dmifonpro.pro_tipfin(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_macpro_2 FOREIGN KEY (id_tipfina) REFERENCES dmifonpro.pro_tipfin(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_macpro_2 ON dmifonpro.pro_macpro USING btree (codmacpro, id_tipfinda);
CREATE INDEX id_pro_macpro_2 ON dmifonpro.pro_macpro USING btree (id_tipfinda);
CREATE INDEX id_pro_macpro_3 ON dmifonpro.pro_macpro USING btree (id_tipfina);
CREATE UNIQUE INDEX pk_pro_macpro_1 ON dmifonpro.pro_macpro USING btree (id);


-- dmifonpro.pro_pro definition

-- Drop table

-- DROP TABLE dmifonpro.pro_pro;

CREATE TABLE dmifonpro.pro_pro (
	id serial4 NOT NULL,
	codpro varchar(25) NOT NULL,
	despro varchar(500) NOT NULL,
	id_tipfin int8 NOT NULL,
	id_macpro int8 NOT NULL,
	id_ban int8 NULL,
	id_propad int8 NULL,
	iddecpro varchar(90) NOT NULL,
	livpro int4 NOT NULL,
	cntlivinf int4 NOT NULL,
	codappren varchar(25) NULL,
	codcup varchar(15) NULL,
	codcig varchar(10) NULL,
	tippro varchar(3) NULL,
	codtippro varchar(50) NULL,
	desaffhou varchar(250) NULL,
	flgopeavv varchar(1) NULL,
	notpro varchar(500) NULL,
	impigv numeric(17, 2) NOT NULL,
	rifigv varchar(250) NULL,
	impigvdel numeric(17, 2) NOT NULL,
	notigv varchar(250) NULL,
	notaff varchar(250) NULL,
	impimppre numeric(17, 2) NOT NULL,
	impaccpre numeric(17, 2) NOT NULL,
	impmanpre numeric(17, 2) NOT NULL,
	imprevpre numeric(17, 2) NOT NULL,
	impimpric numeric(17, 2) NOT NULL,
	codgami varchar(20) NULL,
	codset varchar(25) NULL,
	codass varchar(25) NULL,
	veraff varchar(250) NULL,
	estaff varchar(250) NULL,
	codcui varchar(25) NULL,
	id_stafin int8 NOT NULL,
	id_stapro int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_tem int8 NULL,
	delcan varchar(250) NULL,
	notimp varchar(500) NULL,
	notpre varchar(250) NULL,
	id_mun int8 NULL,
	id_nil int8 NULL,
	id_dir int8 NULL,
	"nil" int8 NULL,
	codstr varchar(25) NULL,
	codciv varchar(25) NULL,
	notproest varchar(500) NULL,
	numgraagr varchar(100) NULL,
	id_lisvalruoent int8 NULL,
	annfin int4 NOT NULL DEFAULT 0,
	impavapre numeric(17, 2) NOT NULL DEFAULT 0,
	CONSTRAINT pk_pro_pro PRIMARY KEY (id),
	CONSTRAINT fk_pro_pro_1 FOREIGN KEY (id_tipfin) REFERENCES dmifonpro.pro_tipfin(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_2 FOREIGN KEY (id_macpro) REFERENCES dmifonpro.pro_macpro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_3 FOREIGN KEY (id_ban) REFERENCES dmifonpro.pro_ban(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_4 FOREIGN KEY (id_stafin) REFERENCES dmifonpro.pro_stafin(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_5 FOREIGN KEY (id_stapro) REFERENCES dmifonpro.pro_stapro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_6 FOREIGN KEY (id_tem) REFERENCES dmifonpro.pro_tem(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_7 FOREIGN KEY (id_mun) REFERENCES dmifonpro.pro_mun(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_8 FOREIGN KEY (id_nil) REFERENCES dmifonpro.pro_nil(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_pro_9 FOREIGN KEY (id_lisvalruoent) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_pro_2 ON dmifonpro.pro_pro USING btree (codpro);
CREATE INDEX id_pro_pro_11 ON dmifonpro.pro_pro USING btree (id_tem);
CREATE INDEX id_pro_pro_12 ON dmifonpro.pro_pro USING btree (id_dir);
CREATE INDEX id_pro_pro_13 ON dmifonpro.pro_pro USING btree (id_mun);
CREATE INDEX id_pro_pro_14 ON dmifonpro.pro_pro USING btree (id_nil);
CREATE INDEX id_pro_pro_15 ON dmifonpro.pro_pro USING btree (id_lisvalruoent);
CREATE INDEX id_pro_pro_2 ON dmifonpro.pro_pro USING btree (id_propad);
CREATE INDEX id_pro_pro_3 ON dmifonpro.pro_pro USING btree (iddecpro);
CREATE INDEX id_pro_pro_4 ON dmifonpro.pro_pro USING btree (codgami);
CREATE INDEX id_pro_pro_5 ON dmifonpro.pro_pro USING btree (id_tipfin);
CREATE INDEX id_pro_pro_6 ON dmifonpro.pro_pro USING btree (id_macpro);
CREATE INDEX id_pro_pro_7 ON dmifonpro.pro_pro USING btree (id_ban);
CREATE INDEX id_pro_pro_8 ON dmifonpro.pro_pro USING btree (id_stafin);
CREATE INDEX id_pro_pro_9 ON dmifonpro.pro_pro USING btree (id_stapro);
CREATE UNIQUE INDEX pk_pro_pro_1 ON dmifonpro.pro_pro USING btree (id);


-- dmifonpro.pro_procum definition

-- Drop table

-- DROP TABLE dmifonpro.pro_procum;

CREATE TABLE dmifonpro.pro_procum (
	id serial4 NOT NULL,
	id_cum int8 NOT NULL,
	id_pro int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_procum PRIMARY KEY (id),
	CONSTRAINT fk_pro_procum_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_procum_2 FOREIGN KEY (id_cum) REFERENCES dmifonpro.pro_cum(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_procum_2 ON dmifonpro.pro_procum USING btree (id_cum, id_pro);
CREATE INDEX id_pro_procum_3 ON dmifonpro.pro_procum USING btree (id_pro);
CREATE INDEX id_pro_procum_4 ON dmifonpro.pro_procum USING btree (id_cum);
CREATE UNIQUE INDEX pk_pro_procum_1 ON dmifonpro.pro_procum USING btree (id);


-- dmifonpro.pro_res definition

-- Drop table

-- DROP TABLE dmifonpro.pro_res;

CREATE TABLE dmifonpro.pro_res (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	id_tipres int8 NOT NULL,
	notres varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_ute int8 NULL,
	CONSTRAINT pk_pro_res PRIMARY KEY (id),
	CONSTRAINT fk_pro_res_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_res_2 FOREIGN KEY (id_tipres) REFERENCES dmifonpro.pro_tipres(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_res_2 ON dmifonpro.pro_res USING btree (id_pro, id_tipres, id_ute);
CREATE INDEX id_pro_res_3 ON dmifonpro.pro_res USING btree (id_ute);
CREATE UNIQUE INDEX pk_pro_res_1 ON dmifonpro.pro_res USING btree (id);


-- dmifonpro.pro_ric definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ric;

CREATE TABLE dmifonpro.pro_ric (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	tipric varchar(1) NOT NULL,
	desric varchar(250) NULL,
	staric varchar(3) NULL,
	dtasca date NULL,
	dtainv date NULL,
	risric varchar(250) NULL,
	id_ricpad int8 NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_ric PRIMARY KEY (id),
	CONSTRAINT fk_pro_ric_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_ric_2 ON dmifonpro.pro_ric USING btree (id_ricpad);
CREATE INDEX id_pro_ric_3 ON dmifonpro.pro_ric USING btree (id_pro);
CREATE UNIQUE INDEX pk_pro_ric_1 ON dmifonpro.pro_ric USING btree (id);


-- dmifonpro.pro_ricute definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ricute;

CREATE TABLE dmifonpro.pro_ricute (
	id serial4 NOT NULL,
	id_ric int8 NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_ute int8 NOT NULL,
	CONSTRAINT pk_pro_ricute PRIMARY KEY (id),
	CONSTRAINT fk_pro_ricute_1 FOREIGN KEY (id_ric) REFERENCES dmifonpro.pro_ric(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_ricute_2 ON dmifonpro.pro_ricute USING btree (id_ric, id_ute);
CREATE INDEX id_pro_ricute_3 ON dmifonpro.pro_ricute USING btree (id_ute);
CREATE INDEX id_pro_ricute_4 ON dmifonpro.pro_ricute USING btree (id_ric);
CREATE UNIQUE INDEX pk_pro_ricute_1 ON dmifonpro.pro_ricute USING btree (id);


-- dmifonpro.pro_strpro definition

-- Drop table

-- DROP TABLE dmifonpro.pro_strpro;

CREATE TABLE dmifonpro.pro_strpro (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	codstr varchar(25) NOT NULL,
	codciv varchar(25) NULL,
	imploc numeric(17, 2) NOT NULL,
	notloc varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_strpro PRIMARY KEY (id),
	CONSTRAINT fk_pro_strpro_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_strpro_2 ON dmifonpro.pro_strpro USING btree (id_pro);
CREATE UNIQUE INDEX pk_pro_strpro_1 ON dmifonpro.pro_strpro USING btree (id);


-- dmifonpro.pro_ava definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ava;

CREATE TABLE dmifonpro.pro_ava (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	nrover int8 NOT NULL,
	dtarilava date NOT NULL,
	desstaava varchar(500) NULL,
	prcava numeric(6, 3) NOT NULL,
	id_lisvalfasint int8 NULL,
	id_lisvallivcri int8 NOT NULL,
	notcri varchar(250) NULL,
	id_lisvaltipapp int8 NOT NULL,
	desapp varchar(250) NULL,
	id_lisvalstaant int8 NOT NULL,
	impant numeric(17, 2) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_lisvaltipolfas int8 NULL,
	dtainicanpre date NULL,
	dtainicaneff date NULL,
	CONSTRAINT pk_pro_ava PRIMARY KEY (id),
	CONSTRAINT fk_pro_ava_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ava_2 FOREIGN KEY (id_lisvalfasint) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ava_3 FOREIGN KEY (id_lisvallivcri) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ava_4 FOREIGN KEY (id_lisvaltipapp) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ava_5 FOREIGN KEY (id_lisvalstaant) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ava_6 FOREIGN KEY (id_lisvaltipolfas) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_ava_2 ON dmifonpro.pro_ava USING btree (id_pro, nrover);
CREATE INDEX id_pro_ava_3 ON dmifonpro.pro_ava USING btree (id_lisvalfasint);
CREATE INDEX id_pro_ava_4 ON dmifonpro.pro_ava USING btree (id_lisvallivcri);
CREATE INDEX id_pro_ava_5 ON dmifonpro.pro_ava USING btree (id_lisvaltipapp);
CREATE INDEX id_pro_ava_6 ON dmifonpro.pro_ava USING btree (id_lisvalstaant);
CREATE UNIQUE INDEX pk_pro_ava_1 ON dmifonpro.pro_ava USING btree (id);


-- dmifonpro.pro_ddr definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ddr;

CREATE TABLE dmifonpro.pro_ddr (
	id serial4 NOT NULL,
	codddr varchar(25) NOT NULL,
	desddr varchar(250) NOT NULL,
	dtaddr date NOT NULL,
	id_pro int8 NOT NULL,
	id_ddra int8 NULL,
	impddr numeric(17, 2) NOT NULL,
	impamm numeric(17, 2) NOT NULL,
	impinc numeric(17, 2) NOT NULL,
	prgrev varchar(250) NULL,
	notddr varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	imptra numeric(17, 2) NOT NULL DEFAULT 0,
	impnonammpriliv numeric(17, 2) NOT NULL DEFAULT 0,
	impnonammsecliv numeric(17, 2) NOT NULL DEFAULT 0,
	id_lisvaltipddr int8 NULL,
	CONSTRAINT pk_pro_ddr PRIMARY KEY (id),
	CONSTRAINT fk_pro_ddr_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ddr_2 FOREIGN KEY (id_ddra) REFERENCES dmifonpro.pro_ddra(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ddr_3 FOREIGN KEY (id_lisvaltipddr) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_ddr_2 ON dmifonpro.pro_ddr USING btree (codddr);
CREATE INDEX id_pro_ddr_2 ON dmifonpro.pro_ddr USING btree (id_pro);
CREATE INDEX id_pro_ddr_3 ON dmifonpro.pro_ddr USING btree (id_ddra);
CREATE UNIQUE INDEX pk_pro_ddr_1 ON dmifonpro.pro_ddr USING btree (id);


-- dmifonpro.pro_detcon definition

-- Drop table

-- DROP TABLE dmifonpro.pro_detcon;

CREATE TABLE dmifonpro.pro_detcon (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	tipent varchar(4) NOT NULL,
	codentcon varchar(25) NOT NULL,
	tipentcol varchar(4) NULL,
	codentcol varchar(25) NULL,
	notent varchar(500) NULL,
	id_tipimp int8 NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_lisvaltipdetcon int8 NULL,
	flgpar varchar(1) NULL,
	impasspar numeric(17, 2) NOT NULL DEFAULT 0,
	impmanpar numeric(17, 2) NOT NULL DEFAULT 0,
	CONSTRAINT pk_pro_detcon PRIMARY KEY (id),
	CONSTRAINT fk_pro_detcon_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_detcon_2 FOREIGN KEY (id_tipimp) REFERENCES dmifonpro.pro_tipimp(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_detcon_3 FOREIGN KEY (id_lisvaltipdetcon) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_detcon_2 ON dmifonpro.pro_detcon USING btree (id_pro, tipent, codentcon);
CREATE INDEX id_pro_detcon_3 ON dmifonpro.pro_detcon USING btree (tipent, codentcon);
CREATE INDEX id_pro_detcon_4 ON dmifonpro.pro_detcon USING btree (id_tipimp);
CREATE UNIQUE INDEX pk_pro_detcon_1 ON dmifonpro.pro_detcon USING btree (id);


-- dmifonpro.pro_detconddr definition

-- Drop table

-- DROP TABLE dmifonpro.pro_detconddr;

CREATE TABLE dmifonpro.pro_detconddr (
	id serial4 NOT NULL,
	id_ddr int8 NOT NULL,
	tipent varchar(4) NOT NULL,
	codentcon varchar(25) NOT NULL,
	notent varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	impman numeric(17, 3) NOT NULL DEFAULT 0,
	CONSTRAINT pk_pro_detconddr PRIMARY KEY (id),
	CONSTRAINT fk_pro_detconddr_1 FOREIGN KEY (id_ddr) REFERENCES dmifonpro.pro_ddr(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_detconddr_2 ON dmifonpro.pro_detconddr USING btree (id_ddr, tipent, codentcon);
CREATE INDEX id_pro_detconddr_3 ON dmifonpro.pro_detconddr USING btree (tipent, codentcon);
CREATE UNIQUE INDEX pk_pro_detconddr_1 ON dmifonpro.pro_detconddr USING btree (id);


-- dmifonpro.pro_detproddr definition

-- Drop table

-- DROP TABLE dmifonpro.pro_detproddr;

CREATE TABLE dmifonpro.pro_detproddr (
	id serial4 NOT NULL,
	id_ddr int8 NOT NULL,
	id_pro int8 NOT NULL,
	impddr numeric(17, 2) NOT NULL,
	notdet varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_detproddr PRIMARY KEY (id),
	CONSTRAINT fk_pro_detproddr_1 FOREIGN KEY (id_ddr) REFERENCES dmifonpro.pro_ddr(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_detproddr_2 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_detproddr_2 ON dmifonpro.pro_detproddr USING btree (id_ddr, id_pro);
CREATE INDEX id_pro_detproddr_2 ON dmifonpro.pro_detproddr USING btree (id_ddr);
CREATE INDEX id_pro_detproddr_3 ON dmifonpro.pro_detproddr USING btree (id_pro);
CREATE UNIQUE INDEX pk_pro_detproddr_1 ON dmifonpro.pro_detproddr USING btree (id);


-- dmifonpro.pro_fas definition

-- Drop table

-- DROP TABLE dmifonpro.pro_fas;

CREATE TABLE dmifonpro.pro_fas (
	id serial4 NOT NULL,
	id_ava int8 NOT NULL,
	id_tipfas int8 NOT NULL,
	dtainiori date NULL,
	dtafinori date NULL,
	dtainipre date NULL,
	dtafinpre date NULL,
	dtainieff date NULL,
	dtafineff date NULL,
	notfas varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_fas PRIMARY KEY (id),
	CONSTRAINT fk_pro_fas_1 FOREIGN KEY (id_ava) REFERENCES dmifonpro.pro_ava(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_fas_2 FOREIGN KEY (id_tipfas) REFERENCES dmifonpro.pro_tipfas(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_fas_2 ON dmifonpro.pro_fas USING btree (id_ava, id_tipfas);
CREATE INDEX id_pro_fas_2 ON dmifonpro.pro_fas USING btree (id_ava);
CREATE INDEX id_pro_fas_3 ON dmifonpro.pro_fas USING btree (id_tipfas);
CREATE UNIQUE INDEX pk_pro_fas_1 ON dmifonpro.pro_fas USING btree (id);


-- dmifonpro.pro_imp definition

-- Drop table

-- DROP TABLE dmifonpro.pro_imp;

CREATE TABLE dmifonpro.pro_imp (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	id_tipimp int8 NOT NULL,
	imppro numeric(17, 2) NOT NULL,
	notimp varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_imp PRIMARY KEY (id),
	CONSTRAINT fk_pro_imp_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_imp_2 FOREIGN KEY (id_tipimp) REFERENCES dmifonpro.pro_tipimp(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_imp_2 ON dmifonpro.pro_imp USING btree (id_pro, id_tipimp);
CREATE UNIQUE INDEX pk_pro_imp_1 ON dmifonpro.pro_imp USING btree (id);


-- dmifonpro.pro_impprocum definition

-- Drop table

-- DROP TABLE dmifonpro.pro_impprocum;

CREATE TABLE dmifonpro.pro_impprocum (
	id serial4 NOT NULL,
	id_procum int8 NOT NULL,
	anncmp int4 NOT NULL,
	imppro numeric(17, 2) NOT NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_impprocum PRIMARY KEY (id),
	CONSTRAINT fk_pro_impprocum_1 FOREIGN KEY (id_procum) REFERENCES dmifonpro.pro_procum(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_impprocum_2 ON dmifonpro.pro_impprocum USING btree (id_procum, anncmp);
CREATE INDEX id_pro_impprocum_3 ON dmifonpro.pro_impprocum USING btree (id_procum);
CREATE UNIQUE INDEX pk_pro_impprocum_1 ON dmifonpro.pro_impprocum USING btree (id);


-- dmifonpro.pro_ind definition

-- Drop table

-- DROP TABLE dmifonpro.pro_ind;

CREATE TABLE dmifonpro.pro_ind (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	id_tipind int8 NOT NULL,
	id_lisvalfremis int8 NULL,
	metcal varchar(500) NULL,
	fondat varchar(250) NULL,
	dtaavvmon date NULL,
	notind varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	id_lisvalstaind int8 NULL,
	CONSTRAINT pk_pro_ind PRIMARY KEY (id),
	CONSTRAINT fk_pro_ind_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ind_2 FOREIGN KEY (id_tipind) REFERENCES dmifonpro.pro_tipind(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_ind_3 FOREIGN KEY (id_lisvalfremis) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_ind_2 ON dmifonpro.pro_ind USING btree (id_pro, id_tipind);
CREATE INDEX id_pro_ind_3 ON dmifonpro.pro_ind USING btree (id_pro);
CREATE INDEX id_pro_ind_4 ON dmifonpro.pro_ind USING btree (id_tipind);
CREATE INDEX id_pro_ind_5 ON dmifonpro.pro_ind USING btree (id_lisvalfremis);
CREATE UNIQUE INDEX pk_pro_ind_1 ON dmifonpro.pro_ind USING btree (id);


-- dmifonpro.pro_infagg definition

-- Drop table

-- DROP TABLE dmifonpro.pro_infagg;

CREATE TABLE dmifonpro.pro_infagg (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	id_tipinfagg int8 NOT NULL,
	infagg varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_infagg PRIMARY KEY (id),
	CONSTRAINT fk_pro_infagg_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_infagg_2 FOREIGN KEY (id_tipinfagg) REFERENCES dmifonpro.pro_tipinfagg(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_infagg_2 ON dmifonpro.pro_infagg USING btree (id_pro, id_tipinfagg);
CREATE INDEX id_pro_infagg_2 ON dmifonpro.pro_infagg USING btree (id_pro);
CREATE INDEX id_pro_infagg_3 ON dmifonpro.pro_infagg USING btree (id_tipinfagg);
CREATE UNIQUE INDEX pk_pro_infagg_1 ON dmifonpro.pro_infagg USING btree (id);


-- dmifonpro.pro_mil definition

-- Drop table

-- DROP TABLE dmifonpro.pro_mil;

CREATE TABLE dmifonpro.pro_mil (
	id serial4 NOT NULL,
	id_fas int8 NOT NULL,
	desmil varchar(250) NOT NULL,
	dtaded date NOT NULL,
	dtaeff date NULL,
	notmil varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_mil PRIMARY KEY (id),
	CONSTRAINT fk_pro_mil_1 FOREIGN KEY (id_fas) REFERENCES dmifonpro.pro_fas(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_mil_2 ON dmifonpro.pro_mil USING btree (id_fas);
CREATE UNIQUE INDEX pk_pro_mil_1 ON dmifonpro.pro_mil USING btree (id);


-- dmifonpro.pro_mis definition

-- Drop table

-- DROP TABLE dmifonpro.pro_mis;

CREATE TABLE dmifonpro.pro_mis (
	id serial4 NOT NULL,
	id_ind int8 NOT NULL,
	dtamis date NOT NULL,
	valmis numeric(17, 3) NOT NULL,
	notmis varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_mis PRIMARY KEY (id),
	CONSTRAINT fk_pro_mis_1 FOREIGN KEY (id_ind) REFERENCES dmifonpro.pro_ind(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_mis_2 ON dmifonpro.pro_mis USING btree (id_ind, dtamis);
CREATE INDEX id_pro_mis_3 ON dmifonpro.pro_mis USING btree (id_ind);
CREATE UNIQUE INDEX pk_pro_mis_1 ON dmifonpro.pro_mis USING btree (id);


-- dmifonpro.pro_munpro definition

-- Drop table

-- DROP TABLE dmifonpro.pro_munpro;

CREATE TABLE dmifonpro.pro_munpro (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	id_mun int8 NOT NULL,
	imploc numeric(17, 2) NOT NULL,
	notloc varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_munpro PRIMARY KEY (id),
	CONSTRAINT fk_pro_munpro_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_munpro_2 FOREIGN KEY (id_mun) REFERENCES dmifonpro.pro_mun(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_munpro_2 ON dmifonpro.pro_munpro USING btree (id_pro);
CREATE INDEX id_pro_munpro_3 ON dmifonpro.pro_munpro USING btree (id_mun);
CREATE UNIQUE INDEX pk_pro_munpro_1 ON dmifonpro.pro_munpro USING btree (id);


-- dmifonpro.pro_nilpro definition

-- Drop table

-- DROP TABLE dmifonpro.pro_nilpro;

CREATE TABLE dmifonpro.pro_nilpro (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	id_nil int8 NOT NULL,
	imploc numeric(17, 2) NOT NULL,
	notloc varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_nilpro PRIMARY KEY (id),
	CONSTRAINT fk_pro_nilpro_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_nilpro_2 FOREIGN KEY (id_nil) REFERENCES dmifonpro.pro_nil(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE INDEX id_pro_nilpro_2 ON dmifonpro.pro_nilpro USING btree (id_pro);
CREATE INDEX id_pro_nilpro_3 ON dmifonpro.pro_nilpro USING btree (id_nil);
CREATE UNIQUE INDEX pk_pro_nilpro_1 ON dmifonpro.pro_nilpro USING btree (id);


-- dmifonpro.pro_pre definition

-- Drop table

-- DROP TABLE dmifonpro.pro_pre;

CREATE TABLE dmifonpro.pro_pre (
	id serial4 NOT NULL,
	id_pro int8 NOT NULL,
	dtapre date NOT NULL,
	imppre numeric(17, 2) NOT NULL,
	notpre varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_pre PRIMARY KEY (id),
	CONSTRAINT fk_pro_pre_1 FOREIGN KEY (id_pro) REFERENCES dmifonpro.pro_pro(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_pre_2 ON dmifonpro.pro_pre USING btree (id_pro, dtapre);
CREATE UNIQUE INDEX pk_pro_pre_1 ON dmifonpro.pro_pre USING btree (id);


-- dmifonpro.pro_tar definition

-- Drop table

-- DROP TABLE dmifonpro.pro_tar;

CREATE TABLE dmifonpro.pro_tar (
	id serial4 NOT NULL,
	id_ind int8 NOT NULL,
	dtatar date NOT NULL,
	valtar numeric(17, 3) NOT NULL,
	id_lisvaltipcontar int8 NULL,
	nottar varchar(250) NULL,
	usr_create varchar(30) NULL,
	dt_create timestamp NULL,
	usr_lstupd varchar(30) NULL,
	dt_lstupd timestamp NULL,
	CONSTRAINT pk_pro_tar PRIMARY KEY (id),
	CONSTRAINT fk_pro_tar_1 FOREIGN KEY (id_lisvaltipcontar) REFERENCES dmifonpro.pro_lisval(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_pro_tar_2 FOREIGN KEY (id_ind) REFERENCES dmifonpro.pro_ind(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE UNIQUE INDEX ak_pro_tar_2 ON dmifonpro.pro_tar USING btree (id_ind, dtatar);
CREATE INDEX id_pro_tar_3 ON dmifonpro.pro_tar USING btree (id_lisvaltipcontar);
CREATE INDEX id_pro_tar_4 ON dmifonpro.pro_tar USING btree (id_ind);
CREATE UNIQUE INDEX pk_pro_tar_1 ON dmifonpro.pro_tar USING btree (id);


-- dmifonpro.pro_v_fasi_avanzamento source

CREATE OR REPLACE VIEW dmifonpro.pro_v_fasi_avanzamento
AS SELECT f.id,
    f.id_ava,
    f.id_tipfas,
    f.dtainiori,
    f.dtafinori,
    f.dtainipre,
    f.dtafinpre,
    f.dtainieff,
    f.dtafineff,
    f.notfas,
    f.usr_create,
    f.dt_create,
    f.usr_lstupd,
    f.dt_lstupd,
    tf.desfas
   FROM dmifonpro.pro_fas f
     JOIN dmifonpro.pro_tipfas tf ON f.id_tipfas = tf.id;


-- dmifonpro.pro_v_impegni source

CREATE OR REPLACE VIEW dmifonpro.pro_v_impegni
AS SELECT pori.id AS id_pro,
    ec.tipent,
    ec.codentcon,
    ec.desimp,
    ec.idcap,
    ec.anncmp,
    ec.impass,
    ec.impliq,
    ec.impman,
    ec.despdd,
    ec.idcro,
    ec.codcup,
    ec.codcig
   FROM dmifonpro.pro_pro pori,
    dmifonpro.pro_pro p,
    dmifonpro.pro_detcon dc,
    dmifonpro.pro_entcon ec
  WHERE p.iddecpro::text ~~ (btrim(pori.iddecpro::text) || '%'::text) AND dc.id_pro = p.id AND (dc.tipent::text = 'IMPE'::text AND ec.tipent::text = dc.tipent::text AND ec.codentcon::text = dc.codentcon::text OR dc.tipent::text = 'CRON'::text AND ec.idcro::text = dc.codentcon::text AND ec.tipent::text = 'IMPE'::text OR dc.tipent::text = 'ACCE'::text AND ec.tipent::text = dc.tipent::text AND ec.codentcon::text = dc.codentcon::text OR dc.tipent::text = 'CRON'::text AND ec.idcro::text = dc.codentcon::text AND ec.tipent::text = 'ACCE'::text) AND (dc.flgpar IS NULL OR NOT dc.flgpar::text = 'S'::text)
UNION ALL
 SELECT pori.id AS id_pro,
    ec.tipent,
    ec.codentcon,
    ec.desimp,
    ec.idcap,
    ec.anncmp,
    dc.impasspar AS impass,
    dc.impmanpar AS impliq,
    dc.impmanpar AS impman,
    ec.despdd,
    ec.idcro,
    ec.codcup,
    ec.codcig
   FROM dmifonpro.pro_pro pori,
    dmifonpro.pro_pro p,
    dmifonpro.pro_detcon dc,
    dmifonpro.pro_entcon ec
  WHERE p.iddecpro::text ~~ (btrim(pori.iddecpro::text) || '%'::text) AND dc.id_pro = p.id AND (dc.tipent::text = 'IMPE'::text AND ec.tipent::text = dc.tipent::text AND ec.codentcon::text = dc.codentcon::text OR dc.tipent::text = 'ACCE'::text AND ec.tipent::text = dc.tipent::text AND ec.codentcon::text = dc.codentcon::text) AND dc.flgpar::text = 'S'::text;


-- dmifonpro.pro_v_importi_progetto source

CREATE OR REPLACE VIEW dmifonpro.pro_v_importi_progetto
AS SELECT i.id,
    i.id_pro,
    i.id_tipimp,
    i.imppro,
    i.notimp,
    i.usr_create,
    i.dt_create,
    i.usr_lstupd,
    i.dt_lstupd,
    ti.destipimp,
    ti.flgtipimp,
    ti.flgdicui
   FROM dmifonpro.pro_imp i
     JOIN dmifonpro.pro_tipimp ti ON i.id_tipimp = ti.id;


-- dmifonpro.pro_v_progetti source

CREATE OR REPLACE VIEW dmifonpro.pro_v_progetti
AS SELECT p.id,
    p.codpro,
    p.despro,
    p.id_tipfin,
    p.id_macpro,
    p.id_ban,
    p.id_propad,
    p.iddecpro,
    p.livpro,
    p.cntlivinf,
    p.codappren,
    p.codcup,
    p.codcig,
    p.tippro,
    p.codtippro,
    p.desaffhou,
    p.flgopeavv,
    p.notpro,
    p.impigv,
    p.rifigv,
    p.impigvdel,
    p.notigv,
    p.notaff,
    p.impimppre,
    p.impaccpre,
    p.impmanpre,
    p.imprevpre,
    p.impimpric,
    p.codgami,
    p.codset,
    p.codass,
    p.veraff,
    p.estaff,
    p.codcui,
    p.id_stafin,
    p.id_stapro,
    p.usr_create,
    p.dt_create,
    p.usr_lstupd,
    p.dt_lstupd,
    p.id_tem,
    p.delcan,
    p.notimp,
    p.notpre,
    p.id_mun,
    p.id_nil,
    p.id_dir,
    sp.dessta AS desstapro,
    sf.dessta AS desstafin,
    t.destem,
    p.codstr,
    p.codciv,
    p.notproest,
    p.numgraagr,
    p.id_lisvalruoent,
    p.annfin
   FROM dmifonpro.pro_pro p
     LEFT JOIN dmifonpro.pro_tem t ON p.id_tem = t.id
     JOIN dmifonpro.pro_stapro sp ON p.id_stapro = sp.id
     JOIN dmifonpro.pro_stafin sf ON p.id_stafin = sf.id;
