# 1. Use an official lightweight Java 17 image as the base
FROM eclipse-temurin:17-jre-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy your specific built jar file into the container
COPY target/ExpenseTracker-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose the port your Spring Boot app runs on
EXPOSE 8080

# 5. The command that runs when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]