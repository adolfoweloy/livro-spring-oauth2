curl -X POST -H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Accept: application/json" \
-d 'grant_type=password&username=oauth@mailinator.com&password=123&scope=read write' \
"http://localhost:8080/oauth/token"


curl -X GET -H "Authorization: Basic cmVzb3VyY2Utc2VydmVyOmFiY2Q=" \
"http://localhost:8080/oauth/check_token?token=0f184456-dea3-4b65-b78a-c498979b5bef"


curl -X POST -H "Authorization: Basic Y2xpZW50ZS1hcHA6MTIzNDU2" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Accept: application/json" \
-d 'grant_type=password&username=oauth@mailinator.com&password=123&scope=read' "http://localhost:8080/oauth/token"



curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYm9va3MiXSwidXNlcl9uYW1lIjoib2F1dGhAbWFpbGluYXRvci5jb20iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDkxODM4MjIyLCJhdXRob3JpdGllcyI6WyJST0xFX1VTVUFSSU9fQ09NVU0iLCJyZWFkIl0sImp0aSI6IjRkMzk0ODI3LTA2YmMtNDFhMy1hMjdjLWU1MTg1NmMwN2YzMCIsImNsaWVudF9pZCI6ImNsaWVudGUtYXBwIn0.vqmPrX7GIuvNvtjmjlVKIB7gyC0CfaGOTpSPawwEczI" "http://localhost:8080/api/v2/livros"
