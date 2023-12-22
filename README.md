1. run `docker-compose up -d --build`
2. run `.\vault\reinit_vault_dev_server.bat`
3. start project api-server-1 in IntelijIdea
4. start project api-server-2 in IntelijIdea


docker-compose down && docker volume prune -f && docker-compose up -d --build && .\vault\reinit_vault_dev_server.bat