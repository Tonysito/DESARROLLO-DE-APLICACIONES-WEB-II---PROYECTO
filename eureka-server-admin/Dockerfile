FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./eureka-server-admin

RUN mvn -f eureka-server-admin/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/eureka-server-admin/target/*.jar eureka-server-admin.jar

ENTRYPOINT ["java", "-jar", "eureka-server-admin.jar"]