# build stage build the jar with all our resources
FROM openjdk:8-alpine as build
USER root

ARG WORK_DIR=/build

COPY ./.mvn ${WORK_DIR}/.mvn
COPY ./mvnw ${WORK_DIR}/mvnw
COPY ./ticket-generator/pom.xml ${WORK_DIR}/ticket-generator/pom.xml
COPY ./ticket-reseller/pom.xml ${WORK_DIR}/ticket-reseller/pom.xml
COPY ./pom.xml ${WORK_DIR}/pom.xml

WORKDIR ${WORK_DIR}

# build all dependencies for offline use
RUN ${WORK_DIR}/mvnw dependency:go-offline --file ${WORK_DIR}/pom.xml -B

# copy rest of files
COPY . ${WORK_DIR}/

RUN ${WORK_DIR}/mvnw --batch-mode --file ${WORK_DIR}/pom.xml ${MAVEN_ARGS} clean verify
RUN cp ${WORK_DIR}/ticket-generator/target/*.jar /tmp/ticket-generator.jar
RUN cp -R ${WORK_DIR}/ticket-generator/target/dependency-jars /tmp/dependency-jars

#
# Switch to the MQ image
# Tested with
#     9.2.0.0-r2
#     9.2.0.0-r1
#     9.1.5.0-r2
#     9.1.5.0-r1
#
FROM ibmcom/mq:latest
USER 0

WORKDIR /app

# Copy over config and scripts
COPY ./ticket-generator/startApp.sh ./startApp.sh
COPY ./ticket-generator/20-config.mqsc /etc/mqm/20-config.mqsc
COPY ./ticket-generator/Data.json ./Data.json
COPY ./ticket-generator/mqConfig.json ./mqConfig.json

# Copy over generated jar & dependencies from previous stage
COPY --from=build /tmp/dependency-jars ./dependency-jars
COPY --from=build /tmp/ticket-generator.jar .

# Fix permissions
RUN chmod 776 -R /app
RUN chmod a+x /app/startApp.sh

# Run MQ and execute TicketGenerator
USER 1001
ENTRYPOINT [ "/app/startApp.sh" ]
