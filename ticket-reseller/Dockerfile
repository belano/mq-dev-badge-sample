# build stage build the jar with all our resources
FROM openjdk:8-jdk as build

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
RUN cp ${WORK_DIR}/ticket-reseller/target/*.jar /tmp/ticket-reseller.jar
RUN cp -R ${WORK_DIR}/ticket-reseller/target/dependency-jars /tmp/dependency-jars

# package stage
FROM openjdk:8-jdk-alpine

# Install bash to use 'wait-for-it'
# Also install coreutils -> https://github.com/vishnubob/wait-for-it/issues/71
RUN apk update && apk add bash && apk add --no-cache coreutils

WORKDIR /app

# Copy over generated jar & dependencies from previous stage
COPY --from=build /tmp/dependency-jars ./dependency-jars
COPY --from=build /tmp/ticket-reseller.jar .

ENV JAVA_OPTS="-Dexec.mainClass=com.belano.mq.demo.Reseller"

CMD ["java", "-jar", "/app/ticket-reseller.jar"]
