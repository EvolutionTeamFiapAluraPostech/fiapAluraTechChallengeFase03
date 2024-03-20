FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/fiaprestaurant-*-SNAPSHOT.jar
COPY ${JAR_FILE} fiaprestaurant.jar
ENTRYPOINT ["java", "-jar", "/fiaprestaurant.jar"]
