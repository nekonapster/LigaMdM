DROP DATABASE liga_mdm;

CREATE DATABASE if NOT exists liga_mdm;

USE liga_mdm;

CREATE TABLE ligas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE equipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_liga INT NOT NULL,
    FOREIGN KEY (id_liga) REFERENCES ligas(id)
);

CREATE TABLE jornadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    fecha DATE NOT NULL,
    id_liga INT NOT NULL,
    FOREIGN KEY (id_liga) REFERENCES ligas(id)
);

CREATE TABLE partidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_local INT NOT NULL,
    id_visitante INT NOT NULL,
    id_jornada INT NOT NULL,
    fecha DATE NOT NULL,
    puntos_local INT NOT NULL,
    puntos_visitante INT NOT NULL,
    id_liga INT NOT NULL,
    FOREIGN KEY (id_local) REFERENCES equipos(id),
    FOREIGN KEY (id_visitante) REFERENCES equipos(id),
    FOREIGN KEY (id_jornada) REFERENCES jornadas(id),
    FOREIGN KEY (id_liga) REFERENCES ligas(id)
);

CREATE TABLE estadisticas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT NOT NULL,
    partidos_jugados INT NOT NULL,
    partidos_ganados INT NOT NULL,
    partidos_perdidos INT NOT NULL,
    puntos_clasificacion INT NOT NULL,
    puntos_anotados INT NOT NULL,
    puntos_encajados INT NOT NULL,
    id_liga INT NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES equipos(id),
    FOREIGN KEY (id_liga) REFERENCES ligas(id)
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    pass VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

INSERT INTO usuarios(nombre, email, pass, rol) VALUES ("admin", "admin@gmail.com", "rootroot","Administrador");
INSERT INTO usuarios(nombre, email, pass, rol) VALUES ("neko", "neko@gmail.com", "rootroot","Administrador");
INSERT INTO usuarios(nombre, email, pass, rol) VALUES ("neko2", "neko2@gmail.com", "rootroot","Administrador");
