FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./usuario-service

RUN mvn -f usuario-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/usuario-service/target/*.jar usuario-service.jar

ENTRYPOINT ["java", "-jar", "usuario-service.jar"]