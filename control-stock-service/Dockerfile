FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./control-stock-service

RUN mvn -f control-stock-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/control-stock-service/target/*.jar control-stock-service.jar

ENTRYPOINT ["java", "-jar", "control-stock-service.jar"]