FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./subcategoria-service

RUN mvn -f subcategoria-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/subcategoria-service/target/*.jar subcategoria-service.jar

ENTRYPOINT ["java", "-jar", "subcategoria-service.jar"]