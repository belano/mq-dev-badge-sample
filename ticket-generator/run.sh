#!/bin/bash

docker run -e LICENSE=accept \
  -e MQ_QMGR_NAME=QM1 \
  -e LOG_FORMAT=json \
  -e MQ_APP_PASSWORD=passw0rd \
  -p 1414:1414 \
  -p 9443:9443 \
  -ti --name ticket-generator ticket-generator:latest
