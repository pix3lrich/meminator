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
COPY --from=build /app/target/image-picker-0.0.1-SNAPSHOT.jar ./image-picker.jar
EXPOSE 10116
CMD ["java", "-jar", "image-picker.jar"]
