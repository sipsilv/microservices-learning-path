# ---------- Stage 1: Build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first (for caching dependencies)
COPY order-service/pom.xml .

RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY order-service/src/main/resources ./src/main/resources

# Copy source code
COPY order-service/src ./src

# Build jar
RUN mvn clean package -DskipTests


# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose port (default Spring Boot)
EXPOSE 8082

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]