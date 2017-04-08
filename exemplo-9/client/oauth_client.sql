UPDATE usuario SET token_bookserver = null;
ALTER TABLE usuario ADD COLUMN token_bookserver VARCHAR(60);
ALTER TABLE usuario ADD COLUMN expiracao_token DATETIME;
