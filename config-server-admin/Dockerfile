FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

COPY . ./config-server-admin

RUN mvn -f config-server-admin/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/config-server-admin/target/*.jar config-server-admin.jar

ENTRYPOINT ["java", "-jar", "config-server-admin.jar"]