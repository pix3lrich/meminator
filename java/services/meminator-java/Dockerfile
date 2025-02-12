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

RUN apt-get update
RUN apt-get install -y imagemagick
# Create a directory for custom fonts
RUN mkdir -p /usr/share/fonts/truetype
# Copy font files from host to container
COPY ./Angkor/*.ttf /usr/share/fonts/truetype/
# Refresh font cache
RUN fc-cache -f -v

WORKDIR /app

COPY --from=build /app/target/meminator-0.0.1-SNAPSHOT.jar ./meminator.jar

# RG - Adding the OTel agent
COPY opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

# RG - Configuring agent env variables
ENV OTEL_SERVICE_NAME="statements"
ENV OTEL_EXPORTER_OTLP_PROTOCOL="http/protobuf"
# ENV OTEL_EXPORTER_OTLP_TRACES_ENDPOINT="https://api.honeycomb.io/v1/traces"
# ENV OTEL_EXPORTER_OTLP_TRACES_HEADERS="x-honeycomb-team=uq4F6XqUpMjvKK7ZhrWOpA"
ENV OTEL_EXPORTER_OTLP_ENDPOINT="https://bookish-space-chainsaw-jpjq5q9ph59pp-4318.app.github.dev"

EXPOSE 10117

# RG - Attach the OTel agent when running the jar 
CMD ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "meminator.jar"]
