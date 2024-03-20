FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/fiaprestaurant-*-SNAPSHOT.jar
COPY ${JAR_FILE} fiaprestaurant.jar
ENTRYPOINT ["java", "-jar", "/fiaprestaurant.jar"]
