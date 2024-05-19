#!/bin/sh

cd /tmp;
ls -al;
sudo docker ps;
sudo docker stop aboba_container || true;
sudo docker load -i aboba_backend.tar;
sudo docker run --rm -d -p 8050:8050 --name aboba_container aboba_backend:latest;