#!/bin/bash

# Builds the ticket-generator application image

pushd "$(dirname "${BASH_SOURCE[0]}")" > /dev/null || exit

cd ..

docker build                                              \
  --rm                                                    \
  -f ticket-generator/Dockerfile                          \
  -t ticket-generator .

# Docker clean up - dangling images
docker image prune -f
docker rmi -f "$(docker images --filter "dangling=true" -q --no-trunc)"

popd > /dev/null || exit
