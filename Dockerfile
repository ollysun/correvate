FROM openjdk:8-jdk-alpine
MAINTAINER MOSES OLALERE
COPY src/main/resources src/main/resources
ARG JAR_FILE=target/correravate-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} correvate.jar
ENTRYPOINT ["java","-jar","/correvate.jar"]