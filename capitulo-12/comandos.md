curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
-H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-d 'grant_type=refresh_token&refresh_token=313abf0f-fbd2-4958-8dfd-992cbbefca8f&scope=read write' \
"http://localhost:8080/oauth/token"


curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
-H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-d 'grant_type=refresh_token&refresh_token=313abf0f-fbd2-4958-8dfd-992cbbefca8f&scope=read write' \
"http://localhost:8080/oauth/token"


curl -X POST -H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-H "Content-Type: application/x-www-form-urlencoded" \
-d 'grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read write&code=qpKtTC' "http://localhost:8080/oauth/token"



curl -X GET -H "Authorization: Bearer fa8ceb23-02fd-40e4-b889-64f520417c53" \
"http://localhost:8080/api/v2/livros"
