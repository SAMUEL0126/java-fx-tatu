-- USE mysql;
-- SHOW FULL tables;
-- SELECT * FROM user;
-- DROP USER dairon@localhost;


CREATE DATABASE styletatto;

USE styletatto;


CREATE USER 'dairon'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON  styletatto. * TO 'dairon'@'localhost';
FLUSH PRIVILEGES;




CREATE TABLE usuarios(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50),
    clave VARCHAR(50)
);


CREATE TABLE pTattos(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    pVenta DOUBLE
);


CREATE TABLE ventas (
	codigo INT AUTO_INCREMENT PRIMARY KEY,
    cliente INT,
    producTatto INT,
    tipoTatuaje VARCHAR(50)
);

CREATE TABLE clientes(
	idClientes INT AUTO_INCREMENT PRIMARY KEY,
	cedula CHAR(7),
    nombre VARCHAR(50),
    apellidos VARCHAR(50),
    genero CHAR(2)
);


ALTER TABLE ventas ADD FOREIGN KEY (cliente) REFERENCES clientes(idClientes);
ALTER TABLE ventas ADD FOREIGN KEY (producTatto) REFERENCES pTattos(codigo);


-- -------------------INSERT-------------------------
INSERT INTO usuarios (login,clave) VALUES ('dairon','123456');
