# Use lightweight JDK image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the fat JAR built on host
COPY build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","/app/app.jar"]
