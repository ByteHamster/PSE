#!/bin/sh
xhost +local:
docker-compose up
xhost -local:
