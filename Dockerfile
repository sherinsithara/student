# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/student-application-0.0.1-SNAPSHOT.jar student-application.jar

# Expose the port that the application will run on
EXPOSE 8086

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "student-application.jar"]
