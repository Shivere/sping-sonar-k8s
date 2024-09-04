# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
#WORKDIR /app

MAINTAINER Shivere

# Copy the jar file to the container
COPY target/demo-spring-sonar-0.0.1-SNAPSHOT.jar demo-spring-sonar-0.0.1-SNAPSHOT.jar

## Expose port 8080 to the outside world
#EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "demo-spring-sonar-0.0.1-SNAPSHOT.jar"]
