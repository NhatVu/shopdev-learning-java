CREATE SCHEMA IF NOT EXISTS learning_jpa_hibernate;

CREATE SEQUENCE learning_jpa_hibernate.message_id_seq
	INCREMENT BY 1
	MINVALUE 1;

CREATE TABLE learning_jpa_hibernate.message (
	id int8 NOT NULL,
	text varchar(255) NULL,
	CONSTRAINT message_pkey PRIMARY KEY (id)
);