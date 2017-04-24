-- acessa o banco de dados bookserver
use bookserver;

-- remove estrutra e dados existentes
drop table estante;
drop table livro;
drop table usuario;

-- cria estrutura do banco de dados
create table usuario(
	id int auto_increment primary key,
	nome varchar(100),
	email varchar(100)
);

create table autenticacao_openid (
	usuario_id int references usuario,
	authn_id varchar(255),
	authn_provider varchar(255),
	authn_validade datetime
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
