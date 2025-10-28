FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./api-gateway-admin

RUN mvn -f api-gateway-admin/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/api-gateway-admin/target/*.jar api-gateway-admin.jar

ENTRYPOINT ["java", "-jar", "api-gateway-admin.jar"]