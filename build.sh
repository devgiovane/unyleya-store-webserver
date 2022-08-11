#!/bin/bash

sudo rm -rf build
./gradlew build

docker-compose -f docker-compose-prod.yaml down
docker-compose -f docker-compose-prod.yaml up
