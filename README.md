# OAuth 2.0: Proteja suas aplicações com o Spring Security OAuth2

Esse repositório contém os exemplos utilizados no livro sobre OAuth 2.0. 
O livro contém uma abordagem didática sobre o assunto e os exemplos apresentados utilizam o Spring Security OAuth2.

## Docker e Docker Compose

Para facilitar a configuração do ambiente, utilizamos o Docker e o Docker Compose para orquestrar os serviços necessários, como o banco de dados MySQL.

### Iniciando os serviços

Para iniciar os serviços, execute o seguinte comando na raiz do projeto:

```bash
docker-compose up
```

Certifique-se de que a conexão com o MySQL utilize o IP `127.0.0.1` e a porta `3306` ao invés de `localhost`, para evitar problemas de resolução de DNS do Docker.
