# backend.Dockerfile
# Stage 1: build jar
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package

# Stage 2: run jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Expose backend port
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
