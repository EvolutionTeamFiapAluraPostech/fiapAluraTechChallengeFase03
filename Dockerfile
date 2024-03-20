FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY . .
RUN gradle build
ENTRYPOINT ["java", "-jar", "build/fiaprestaurant.jar"]
