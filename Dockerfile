FROM maven:3.6.0-jdk-17-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-alpine
VOLUME /tmp
ARG JAR_FILE=/target/busTime-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} busTime.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/busTime/.jar"]