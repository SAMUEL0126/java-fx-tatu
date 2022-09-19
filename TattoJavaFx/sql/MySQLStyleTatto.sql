USE mysql;
SHOW FULL tables;
SELECT * FROM user;
DROP USER username@localhost;


CREATE DATABASE styletatto;
USE styletatto;


CREATE USER 'root'@'localhost' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON  styletatto. * TO 'root'@'localhost';
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
    cantidadTatto INT
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

-- -------------------		INSERT  USUARIOS    -------------------------
INSERT INTO usuarios (login,clave) VALUES ('dairon','123456');
INSERT INTO usuarios (login,clave) VALUES ('dairon','123');

-- -------------------		INSERT  CLIENTES    -------------------------
INSERT INTO styletatto.clientes (cedula, nombre, apellidos,genero) VALUES ('3116515', 'dairon', 'Camargo', 'M');
INSERT INTO styletatto.clientes (cedula, nombre, apellidos,genero) VALUES ('6516585', 'laura', 'sofia', 'F');
INSERT INTO styletatto.clientes (cedula, nombre, apellidos,genero) VALUES ('1793355', 'elena', 'correa', 'M');

-- -------------------		INSERT  ptattos    -------------------------
INSERT INTO styletatto.ptattos (nombre, pventa) VALUES ('Black & Grey', '80000');
INSERT INTO styletatto.ptattos (nombre, pventa) VALUES ('Realismo', '50000');
INSERT INTO styletatto.ptattos (nombre, pventa) VALUES ('Blackwork', '30000');









