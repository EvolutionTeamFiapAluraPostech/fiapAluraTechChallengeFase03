FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY . .
RUN ./gradle assemble
ENTRYPOINT ["java", "-jar", "build/fiaprestaurant.jar"]
