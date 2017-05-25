curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
-H "Authorization: Basic Y2xpZW50ZS1hZG1pbjoxMjNhYmM=" \
-d 'grant_type=client_credentials' \
"http://localhost:8080/oauth/token"



curl -X GET -H "Authorization: Bearer 1b4ea189-21a4-4c7d-b2e6-5eb016b50746" \
"http://localhost:8080/api/v2/admin/total_livros"
