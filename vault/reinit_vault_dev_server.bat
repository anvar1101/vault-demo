@rem Enabling secret backends
docker exec vault vault secrets enable database
docker exec vault vault secrets enable transit
docker exec vault vault auth enable approle
docker exec vault vault secrets enable kv
docker exec vault vault secrets enable rabbitmq

docker exec vault vault write -f transit/keys/custom_data type="rsa-2048"

@rem creating database credentials store for database postgresql://{{username}}:{{password}}@db_app_1:5432/app1_db
docker exec vault vault write database/config/api-server-1-db plugin_name=postgresql-database-plugin connection_url="postgresql://{{username}}:{{password}}@db_app_1:5432/app1_db" allowed_roles="db-1-role" username="app1_user" password="q"
docker exec vault vault write database/roles/db-1-role db_name=api-server-1-db creation_statements="CREATE USER \"{{name}}\" WITH PASSWORD '{{password}}';GRANT ALL PRIVILEGES ON DATABASE app1_db TO \"{{name}}\";" default_ttl="3m" max_ttl="3m" revocation_statements="REVOKE ALL PRIVILEGES ON DATABASE  app1_db  FROM \"{{name}}\";DROP ROLE IF EXISTS  \"{{name}}\";" renew_statements="ALTER ROLE \"{{name}}\" WITH PASSWORD '{{password}}';"

docker exec vault vault write database/config/api-server-2-db plugin_name=postgresql-database-plugin connection_url="postgresql://{{username}}:{{password}}@db_app_2:5432/app2_db" allowed_roles="db-2-role" username="app2_user" password="q" 
docker exec vault vault write database/roles/db-2-role db_name=api-server-2-db creation_statements="CREATE USER \"{{name}}\" WITH PASSWORD '{{password}}';GRANT ALL PRIVILEGES ON DATABASE app2_db TO \"{{name}}\";"  default_ttl="3m" max_ttl="3m" revocation_statements="REVOKE ALL PRIVILEGES ON DATABASE  app2_db  FROM \"{{name}}\";DROP ROLE IF EXISTS  \"{{name}}\";" renew_statements="ALTER ROLE \"{{name}}\" WITH PASSWORD '{{password}}';"


@rem setting vault access policy for approle
docker exec vault vault policy write api-server-1-policy demo-app-1-policy-dev.hcl
docker exec vault vault policy write api-server-2-policy demo-app-2-policy-dev.hcl

docker exec vault vault write auth/approle/role/api-server-1 policies=api-server-1-policy period=1h
docker exec vault vault write auth/approle/role/api-server-2 policies=api-server-2-policy period=1h


@echo off

setlocal EnableDelayedExpansion

@rem retrieving role-id for app1
for /f "tokens=1-2" %%i in ('docker exec vault vault read auth/approle/role/api-server-1/role-id') do (
  if "%%i"=="role_id" (
    SET ROLE_ID=%%j
    SET ROLE_ID=!ROLE_ID:~0,36!
  )
)

@echo api-server-role-id=%ROLE_ID%

@rem retrieving secret-id for app1
for /f "tokens=1-2" %%i in ('docker exec vault vault write -f auth/approle/role/api-server-1/secret-id') do (
  if "%%i"=="secret_id" (SET SECRET_ID=%%j)
)

@echo api-server-secret-id=%SECRET_ID%

@rem writing role-id and secret-id to bootstrap.yml for app1
Set "file=%cd%\api-server-1\src\main\resources\bootstrap.template"
SEt "bootstrap_yml=%cd%\api-server-1\src\main\resources\bootstrap.yml"

@echo Writing credentials to %bootstrap_yml% ...
del %bootstrap_yml%
(for /f "delims=^ tokens=1" %%i in ('Type %file%') do (
        set str=%%i

        if not !str!==!str:*role-id:=! (
          for /f "tokens=1,2 delims=:" %%j in ("!str!") do (
             set str=%%j: %ROLE_ID%
          )
        )
        if not !str!==!str:*secret-id:=! (
          for /f "tokens=1,2 delims=:" %%j in ("!str!") do (
             set str=%%j: %SECRET_ID%
          )
        )
        echo !str!
    )
)>%bootstrap_yml%


@rem retrieving role-id for app2

for /f "tokens=1-2" %%i in ('docker exec vault vault read auth/approle/role/api-server-2/role-id') do (
  if "%%i"=="role_id" (
    SET ROLE_ID=%%j
    SET ROLE_ID=!ROLE_ID:~0,36!
  )
)

@echo api-server-2-role-id=%ROLE_ID%

@rem retrieving secret-id for app2
for /f "tokens=1-2" %%i in ('docker exec vault vault write -f auth/approle/role/api-server-2/secret-id') do (
  if "%%i"=="secret_id" (SET SECRET_ID=%%j)
)

@echo api-server-2-secret-id=%SECRET_ID%

@rem writing role-id and secret-id to bootstrap.yml for app2

Set "file=%cd%\api-server-2\src\main\resources\bootstrap.template"
SEt "bootstrap_yml=%cd%\api-server-2\src\main\resources\bootstrap.yml"

@echo Writing credentials to %bootstrap_yml% ...
del %bootstrap_yml%
(for /f "delims=^ tokens=1" %%i in ('Type %file%') do (
        set str=%%i

        if not !str!==!str:*role-id:=! (
          for /f "tokens=1,2 delims=:" %%j in ("!str!") do (
             set str=%%j: %ROLE_ID%
          )
        )
        if not !str!==!str:*secret-id:=! (
          for /f "tokens=1,2 delims=:" %%j in ("!str!") do (
             set str=%%j: %SECRET_ID%
          )
        )
        echo !str!
    )
)>%bootstrap_yml%


@rem configuring vault rabbitmq backend
docker exec vault vault write rabbitmq/config/connection  connection_uri="http://rabbimq:15672" username="mq_user" password="q"

docker exec vault vault write rabbitmq/roles/api-server-1 vhosts="{\"/app-msg-broker\":{\"configure\": \".*\", \"write\": \".*\", \"read\": \".*\"}}"
docker exec vault vault write rabbitmq/roles/api-server-2 vhosts="{\"/app-msg-broker\":{\"configure\": \".*\", \"write\": \".*\", \"read\": \".*\"}}"




docker exec vault vault kv put secret/api-server-1 greeting.title="greeting 1" greeting.description="hello app 1 from vault"
docker exec vault vault kv put secret/api-server-2 greeting.title="greeting 2" greeting.description="hello app 2 from vault"
