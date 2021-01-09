#!/bin/bash

# Docker Desktop for Mac workaround
# Sets the MQ_BADGE_QM_HOSTNAME to the special DNS name host.docker.internal which resolves to the internal IP address used by the host

docker run \
  -e MQ_BADGE_QM_HOSTNAME=host.docker.internal \
  -ti --name ticket-reseller ticket-reseller:latest
