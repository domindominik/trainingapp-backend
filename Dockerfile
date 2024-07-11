# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Install necessary tools and Maven
RUN apt-get update && apt-get install -y curl procps net-tools dnsutils maven

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Build the application
RUN mvn clean package

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "target/bookerapp-0.0.1-SNAPSHOT.jar"]