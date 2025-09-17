# Stage 1: Download dependencies
FROM maven:3.9.6-eclipse-temurin-21-jammy AS dependencies
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:resolve dependency:resolve-plugins

# Stage 2: Build application
FROM dependencies AS build
COPY src ./src
RUN mvn -B package -DskipTests

# Stage 3: Final image
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/backend-for-frontend-0.0.1-SNAPSHOT.jar ./backend-for-frontend.jar

# RG - Adding the OTel agent
COPY opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

# RG - Configuring agent env variables
ENV OTEL_SERVICE_NAME="backend-for-frontend"
ENV OTEL_EXPORTER_OTLP_PROTOCOL="http/protobuf"
# ENV OTEL_EXPORTER_OTLP_TRACES_ENDPOINT="<endpoint>"
# ENV OTEL_EXPORTER_OTLP_TRACES_HEADERS="<header>"
ENV OTEL_EXPORTER_OTLP_ENDPOINT="https://scaling-system-4r77jgwwq3xr5-4318.app.github.dev/"

EXPOSE 10115

# RG - Attach the OTel agent when running the jar
CMD ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "backend-for-frontend.jar"]
