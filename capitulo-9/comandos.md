http://localhost:8080/oauth/authorize?client_id=cliente-app&response_type=code&redirect_uri=http://localhost:9000/callback


http://localhost:8080/oauth/authorize?client_id=cliente-app&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fcallback&state=teste



curl -X POST -H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-H "Content-Type: application/x-www-form-urlencoded" \
-d 'grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read write&code=BoEHea' "http://localhost:8080/oauth/token"


curl -X GET -H "Authorization: Bearer 9b0a07ae-78cc-4aaa-9883-e7f8e9b4ab52" \
"http://localhost:8080/api/v2/livros"
