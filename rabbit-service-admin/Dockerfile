FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./rabbit-service-admin

RUN mvn -f rabbit-service-admin/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/rabbit-service-admin/target/*.jar rabbit-service-admin.jar

ENTRYPOINT ["java", "-jar", "rabbit-service-admin.jar"]