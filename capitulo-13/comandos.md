curl -X POST -H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Accept: application/json" \
-d 'grant_type=password&username=jujuba@mailinator.com&password=123&scope=read write' \
"http://localhost:8080/oauth/token"
