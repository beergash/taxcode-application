FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift
COPY target/*.jar /taxcode-application.jar
#ARG APP_VERSION=1.0.0-SNAPSHOT
#ARG JAR_FILE=target/taxcode-application-${APP_VERSION}.jar
#COPY ${JAR_FILE} taxcode-application.jar
ENTRYPOINT ["java","-jar","/taxcode-application.jar"]