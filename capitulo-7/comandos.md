curl -X POST \
-H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Accept: application/json"  \
-d 'grant_type=password&username=jujuba@mailinator.com&password=123&scope=read write' "http://localhost:8080/oauth/token"



curl -X GET -H "Authorization: Bearer 51ec40fb-368c-4a91-8191-cfe8f5ce25b2" \
"http://localhost:8080/api/v2/livros"
