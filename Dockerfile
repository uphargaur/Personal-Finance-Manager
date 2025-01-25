# Use official OpenJDK runtime as base image
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the Gradle build artifacts
COPY build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080


# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]