-- cria o banco de dados e o usuario da aplicação
CREATE DATABASE bookserver;
CREATE USER 'bookserver'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON bookserver.* TO 'bookserver'@'localhost';

-- acessa o banco de dados recem criado
use bookserver;

-- cria estrutura do banco de dados
create table usuario(
	id int auto_increment primary key,
	nome varchar(100),
	email varchar(100),
	senha varchar(50)
);

create table estante(
	id int auto_increment primary key,
	usuario_id int references usuario(id)
);

create table livro(
	id int auto_increment primary key,
	estante_id int references estante(id),
	titulo varchar(255),
	nota int
);
