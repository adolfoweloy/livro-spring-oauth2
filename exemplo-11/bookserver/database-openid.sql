-- acessa o banco de dados bookserver
use bookserver;


alter table usuario drop column senha;

create table autenticacao_openid (
	usuario_id int references usuario,
	authn_id varchar(255),
	authn_provider varchar(255),
	authn_validade datetime
);
