-- cria o banco de dados e o usuario de banco da aplicação
CREATE DATABASE clientapp;
CREATE USER 'clientapp'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON clientapp.* TO 'clientapp'@'localhost';

-- acessa o banco de dados recem criado
use clientapp;

-- cria estrutura do banco de dados
create table usuario(
	id int auto_increment primary key,
	nome varchar(100),
	login varchar(100),
	senha varchar(50),
	login_bookserver varchar(100) NULL,
	senha_bookserver varchar(50) NULL
);
