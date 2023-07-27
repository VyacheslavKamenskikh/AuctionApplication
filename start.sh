#!/bin/bash


mvn clean
mvn package

docker-compose stop

docker-compose up --build -d