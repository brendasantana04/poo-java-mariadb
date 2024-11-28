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


USE musicadb;

CREATE TABLE instrumentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(255) NOT NULL,
    preco DECIMAL(65) NOT NULL,
    nome VARCHAR(255) NOT NULL
);
