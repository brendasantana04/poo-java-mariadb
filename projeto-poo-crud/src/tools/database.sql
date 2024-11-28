CREATE DATABASE musicadb;

USE musicadb;

CREATE TABLE musicas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    artista VARCHAR(255) NOT NULL,
    album VARCHAR(255),
    genero VARCHAR(100),
    duracao INT NOT NULL
);
