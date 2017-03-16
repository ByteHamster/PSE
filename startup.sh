#!/bin/sh
xhost +local: $(docker inspect --format='{{ .Config.Hostname }}' docker_simulation)
xhost +local: $(docker inspect --format='{{ .Config.Hostname }}' docker_monitor)
docker-compose up
xhost -local: $(docker inspect --format='{{ .Config.Hostname }}' docker_simulation)
xhost -local: $(docker inspect --format='{{ .Config.Hostname }}' docker_monitor)
