FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace
COPY . ./categoria-service

RUN mvn -f categoria-service/pom.xml clean package -DskipTests


FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/categoria-service/target/*.jar categoria-service.jar
ENTRYPOINT ["java", "-jar", "categoria-service.jar"]