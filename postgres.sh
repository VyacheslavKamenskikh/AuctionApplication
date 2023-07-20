#!/usr/bin/env sh
docker run --name postgres -e POSTGRES_USER = postgres -e POSTGRES_PASSWORD=slavakakraft228 -e POSTGRES_DB=postgres 5432:5432 --rm postgres