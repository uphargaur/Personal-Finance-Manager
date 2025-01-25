# Use official OpenJDK runtime as base image
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the Gradle build artifacts
COPY build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set environment variables for MongoDB connection (optional)
ENV SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/finance_manager_db

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]