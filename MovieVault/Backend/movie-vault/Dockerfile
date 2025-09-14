# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/movie-vault-1.0-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Set JVM options for better performance on cloud platforms
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
