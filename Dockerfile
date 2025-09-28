# Stage 1: build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/sci-calc-sb-1.0.0.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
