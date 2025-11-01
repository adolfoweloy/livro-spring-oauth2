-- o banco de dados deve ter sido criado ao iniciar o docker-compose existente na raiz do repositorio
-- caso queira criar o banco de dados manualmente, descomente a linha abaixo
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
	senha varchar(100)
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
