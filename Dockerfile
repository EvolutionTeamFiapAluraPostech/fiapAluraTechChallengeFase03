FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble

FROM openjdk:17-alpine
COPY --from=build /target/fiaprestaurant-*-SNAPSHOT.jar fiaprestaurant.jar
ENTRYPOINT ["java", "-jar", "/fiaprestaurant.jar"]
