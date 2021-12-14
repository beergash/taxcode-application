FROM openjdk:8-jdk-alpine
ARG APP_VERSION=1.0.0-SNAPSHOT
ARG JAR_FILE=tart/taxcode-application-${APP_VERSION}.jar
COPY ${JAR_FILE} taxcode-application.jar
ENTRYPOINT ["java","-jar","/taxcode-application.jar"]