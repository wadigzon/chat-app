# Dockerfile for Spring Boot App
FROM openjdk:17-alpine
VOLUME /tmp
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
