CREATE TABLE users (
	master_id serial NOT NULL UNIQUE,
	email varchar(255) NOT NULL,
	password text NOT NULL);

INSERT INTO users VALUES (3, 'kulbakolearn@mail.ru', '3a265d620c3143fd81005e939b1db6565dc426af');