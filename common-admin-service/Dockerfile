FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace
COPY . ./common-admin-service

RUN mvn -f common-admin-service/pom.xml clean package -DskipTests


FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/common-admin-service/target/*.jar common-admin-service.jar
ENTRYPOINT ["java", "-jar", "common-admin-service.jar"]