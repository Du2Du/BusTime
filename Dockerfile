FROM openjdk:17-alpine
VOLUME /tmp
ARG JAR_FILE=./target/busTime-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} busTime.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/busTime/.jar"]