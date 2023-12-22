path "auth/approle/*" {
  capabilities = [ "create", "read", "update", "delete", "list" ]
}
path "database/creds/db-2-role" {
  capabilities = [ "create", "read", "update", "delete", "list" ]
}
path "database/creds/db-2-role/*" {
  capabilities = [ "create", "read", "update", "delete", "list" ]
}
path "secret/api-server-2" {
  capabilities = ["list", "read"]
}
path "secret/data/api-server-2" {
  capabilities = ["list", "read"]
}
path "secret/data/application" {
  capabilities = ["list", "read"]
}
path "secret/data/application/*" {
  capabilities = ["list", "read", "create", "update", "delete"]
}

path "kv/api-server-2/modules/credentials/*" {
  capabilities = ["list", "read", "create", "update", "delete"]
}

path "kv/api-server-2/modules/credentials" {
  capabilities = ["list", "read"]
}
path "rabbitmq/creds/api-server-2/*" {
   capabilities = [ "create", "read", "update", "delete", "list" ]
}


path "database/creds/db-2-role" {
  capabilities = [ "create", "read", "update", "delete", "list" ]
}
path "rabbitmq/creds/api-server-2" {
   capabilities = [ "create", "read", "update", "delete", "list" ]
}

path "rabbitmq/roles/api-server-2" {
  capabilities = ["list", "read"]
}

path "secret/application/*" {
  capabilities = ["list", "read"]
}
path "secret/api-server-2/*" {
  capabilities = ["create", "update"]
}
path "sys/leases/*" {
   capabilities = [ "create", "read", "update", "delete", "list" ]
}
path "sys/internal/*" {
   capabilities = [ "create", "read", "update", "delete", "list" ]
}
path "secret/application/production" {
  capabilities = ["list", "read"]
}

path "secret/application/production/*" {
  capabilities = ["list", "read", "create", "update", "delete"]
}