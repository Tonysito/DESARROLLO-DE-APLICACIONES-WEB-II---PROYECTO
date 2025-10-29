FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./rabbit_producer_service_admin

RUN mvn -f rabbit_producer_service_admin/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/rabbit_producer_service_admin/target/*.jar rabbit_producer_service_admin.jar

ENTRYPOINT ["java", "-jar", "rabbit_producer_service_admin.jar"]