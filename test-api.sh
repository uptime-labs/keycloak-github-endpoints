#!/bin/bash

export TKN=$(curl -X POST 'https://id.devops-consultants.net/auth/realms/master/protocol/openid-connect/token' \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=admin" \
 -d 'password=St0urbr!dg3' \
 -d 'grant_type=password' \
 -d 'client_id=teleport' \
 -d 'client_secret=JmLHkJ2UmVpWTcV2mFSojkgPIThPohA4' | jq -r '.access_token')

curl -X GET 'https://id.devops-consultants.net/auth/realms/master/github-api/user' \
-H "Accept: application/json" \
-H "Authorization: Bearer $TKN" | jq .

curl -X GET 'https://id.devops-consultants.net/auth/realms/master/github-api/user/teams' \
-H "Accept: application/json" \
-H "Authorization: Bearer $TKN" | jq .
