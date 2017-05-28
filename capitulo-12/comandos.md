## Testes com refresh token

curl -X POST --user cliente-app:123456 \
-d 'grant_type=refresh_token&refresh_token=313abf0f-fbd2-4958-8dfd-992cbbefca8f&scope=read write' \
"http://localhost:8080/oauth/token"


curl -X POST --user cliente-app:123456 \
-d 'grant_type=refresh_token&refresh_token=313abf0f-fbd2-4958-8dfd-992cbbefca8f&scope=read write' \
"http://localhost:8080/oauth/token"


## URL de autorização (Configuração dos endpoints com escopo)

http://localhost:8080/oauth/authorize?client_id=cliente-app&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fcallback&scope=read+write&state=teste


curl -X POST --user cliente-app:123456 \
-H "Content-Type: application/x-www-form-urlencoded" \
-d 'grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read write&code=qpKtTC' "http://localhost:8080/oauth/token"


curl -X GET -H "Authorization: Bearer fa8ceb23-02fd-40e4-b889-64f520417c53" \
"http://localhost:8080/api/v2/livros"
