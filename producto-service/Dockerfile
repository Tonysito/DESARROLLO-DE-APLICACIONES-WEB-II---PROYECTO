FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./producto-service

RUN mvn -f producto-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/producto-service/target/*.jar producto-service.jar

ENTRYPOINT ["java", "-jar", "producto-service.jar"]